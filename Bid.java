public class Bid {
	final public String name;
	final public int bid;

	public Bid(String name, int bid) {
		this.name = name;
		this.bid = bid;
	}

	public int hashCode() {
		return 1 + 23*bid + 31*name.hashCode();
	}

	public int getBid () {return bid;}

	public String getName () {return name;}

	@Override
	public boolean equals(Object obj){
		if (obj == null || !(obj instanceof Bid)) return false;
		Bid bid = (Bid) obj;

		if (hashCode() == bid.hashCode()) {return true;}
		return false;

	}

	@Override
	public String toString(){
		Integer bidInteger = bid;
		String bidString = bidInteger.toString();

		return name+" "+bidString+" ";
	}

}

