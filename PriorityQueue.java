import java.util.*;


// A priority queue.
public class PriorityQueue<E> {
	public ArrayList<E> heap = new ArrayList<E>();
	private Comparator<E> comparator;
	//private Map<E, Integer> hash = new HashMap<>();

	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	// Returns the size of the priority queue.
	public int size() {
		return heap.size();
	}
     
	// Adds an item to the priority queue.
	public void add(E e)
	{
		assert invariant() : showHeap();

//		hash.put(e,heap.size()-1);
		heap.add(e);

		siftUp(heap.size()-1);

		assert invariant() : showHeap();
	}

	// Returns the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public E minimum() {
		if (size() == 0)
			throw new NoSuchElementException();

		return heap.get(0);
	}

	// Removes the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public void deleteMinimum() {


		assert invariant() : showHeap();

		if (size() == 0)
			throw new NoSuchElementException();

		//Upddaterar hashmap först.
//		hash.put(heap.get(heap.size()-1),0);
//		hash.remove(heap.get(0));

		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);

		if (heap.size() > 0) siftDown(0);

		assert invariant() : showHeap();

	}

	// Sifts a node up.
	// siftUp(index) fixes the invariant if the element at 'index' may
	// be less than its parent, but all other elements are correct.
	private void siftUp(int index) {

		E value = heap.get(index);

		while (index != 0) {

			int parentIndex = parent(index);
			E parentValue = heap.get(parentIndex);

			//Om child är mindre än parent, byt plats
			if (comparator.compare(parentValue,value) > 0) {

				heap.set(index, parentValue);
			}

			else break;

			index = parentIndex;
		}
		heap.set(index, value);



	}

	public void update(E oldBid, E newBid) {

		assert invariant() : showHeap();

		for (int i = 0; i<heap.size();i++) {
			if (heap.get(i).equals(oldBid)) {
				heap.set(i,newBid);
				if (comparator.compare(oldBid,newBid) > 0) {siftUp(i);}
				else if (comparator.compare(oldBid,newBid) < 0) {siftDown(i);}
			}
		}

		assert invariant() : showHeap();
	}

	
	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct.
	private void siftDown(int index) {
		E value = heap.get(index);
		
		// Stop when the node is a leaf.
		while (leftChild(index) < heap.size()) {
			int left    = leftChild(index);
			int right   = rightChild(index);

			// Work out whether the left or right child is smaller.
			// Start out by assuming the left child is smaller...
			int child = left;
			E childValue = heap.get(left);

			// ...but then check in case the right child is smaller.
			// (We do it like this because maybe there's no right child.)
			if (right < heap.size()) {
				E rightValue = heap.get(right);
				if (comparator.compare(childValue, rightValue) > 0) {
					child = right;
					childValue = rightValue;
				}
			}

			// If the child is smaller than the parent,
			// carry on downwards.
			if (comparator.compare(value, childValue) > 0) {

				heap.set(index, childValue);
				index = child;
			} else break;
		}

		heap.set(index, value);
	}

	// Helper functions for calculating the children and parent of an index.
	private final int leftChild(int index) {
		return 2*index+1;
	}

	private final int rightChild(int index) {
		return 2*index+2;
	}

	private final int parent(int index) {
		return (index-1)/2;
	}


	private boolean invariant () {

		if (heap.isEmpty()) return true;

		for (int i = 1; i < heap.size();i++) {
			E child = heap.get(i);
			E parent = heap.get(parent(i));

			if (comparator.compare(parent,child) > 0) return false;
		}
		return true;
	}

	public String showHeap() {
		StringBuilder sb = new StringBuilder();

		for (E e : heap) {
			sb.append(e.toString()+"\n");
		}
		return sb.toString();
	}

}
