import java.util.*;


// A priority queue.
public class PriorityQueue<E> {
	public ArrayList<E> heap = new ArrayList<E>();
	private Comparator<E> comparator;

	// hash innehåller E som nyckel och en integer som representerar det index som E finns på i heapen
	private Map<E, Integer> hash = new HashMap<>();

	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	// Returns the size of the priority queue.
	public int size() {
		return heap.size();
	}
     
	// Adds an item to the priority queue.
	//Time complexity : O(log n)
	public void add(E e)
	{
		assert invariant() : showHeap();

		hash.put(e,heap.size()-1);
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
	//Time complexity : O()
	public void deleteMinimum() {

		assert invariant() : showHeap();

		if (size() == 0)
			throw new NoSuchElementException();

		//Upddaterar hashmap först.
		hash.put(heap.get(heap.size()-1),0);
		hash.remove(heap.get(0));

		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);

		if (heap.size() > 0) siftDown(0);

		assert invariant() : showHeap();

	}

	// Sifts a node up.
	// siftUp(index) fixes the invariant if the element at 'index' may
	// be less than its parent, but all other elements are correct.
	// Time complexity : O(log n)
	private void siftUp(int index) {

		// value representerar det aktuella budet som ska siftas
		E value = heap.get(index);

		while (index != 0) {

			//index för föräldern
			int parentIndex = parent(index);
			//budet som finns i föräldern
			E parentValue = heap.get(parentIndex);

			//Om child har högre prio än sin förälder, byt plats
			if (comparator.compare(parentValue,value) > 0) {

				hash.put(parentValue,index);
				heap.set(index, parentValue);
			}

			else break;

			index = parentIndex;
		}

		hash.put(value,index);
		heap.set(index, value);



	}

	// Update tar emot en kopia av ett gammalt bud och ett nytt bud och byter ut det gamla budet mot det
	//nya
	//Time complexity : O(log n)(I värsta fall givet att budet ändras så att det behöver
	// siftas genom hela heapen)
	public void update(E oldBid, E newBid) {

		assert invariant() : showHeap();

		if (hash.containsKey(oldBid)) {
			// Använder sig av heapen för att få fram rätt index till oldBid
			int i = hash.get(oldBid);
			heap.set(i,newBid);
			hash.put(newBid,i);
			hash.remove(oldBid);
			// Om newBid har högre prioritet än oldBid måste newBid siftas uppåt
			if (comparator.compare(oldBid,newBid) > 0) {siftUp(i);}
			// Om newBid har lägre prioritet än oldBid måste newBid siftas nedåt
			else if (comparator.compare(oldBid,newBid) < 0) {siftDown(i);}

		}
		else throw new UnsupportedOperationException();

		assert invariant() : showHeap();
	}

	
	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct.
	// Time complexity : O(log n)
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

				hash.put(childValue,index);
				heap.set(index, childValue);
				index = child;
			} else break;
		}

		hash.put(value,index);
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


	// Kollar att invarianten upprätthålls, dvs att varje barn i heapen är större/ mindre än sin
	//förälder beroende på comparator samt att alla index i hash överensstämmer med heapen.
	// Time complexity : O(n)

	private boolean invariant () {

		if (heap.isEmpty()) return true;

		// Om hash och heap inte är lika stora så är invarianten bruten.
		if (hash.size() != heap.size()) {return false;}

		for (int i = 0; i < heap.size();i++) {
			E child = heap.get(i);
			E parent = heap.get(parent(i));

			// Kollar så att hash innehåller det aktuella budet och att dess värde är samma som indexet i heap,
			// om inte är invarianten bruten
			if (!(hash.containsKey(child) && hash.get(child) == i)) {return false;}
			//Om child/ parent inte uppfyller comparators jämförelse så är invarianten bruten.
			if (comparator.compare(parent,child) > 0) return false;
		}
		return true;
	}

	// Retunerar en string med beskrivning av samtliga element i heapen
	// Time complexity : O(n)
	private String showHeap() {
		StringBuilder sb = new StringBuilder();

		for (E e : heap) {
			sb.append(e.toString()+"\n");
		}
		return sb.toString();
	}

}
