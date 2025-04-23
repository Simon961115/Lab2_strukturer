public class Main {
    public static void main (String[] args) {

        PriorityQueue prique = new PriorityQueue(new SellBidComparator());

        Bid bid1 = new Bid("c",17);
        Bid bid2 = new Bid("c",17);

        System.out.println(bid1.equals(new Bid("c",17)));

//        prique.add(new Bid("a",7));
//        prique.add(new Bid("b",12));
//        prique.add(bid1);
//        prique.add(new Bid("d",24));
//        prique.add(new Bid("e",22));
//        prique.add(new Bid("f",20));
//        prique.add(new Bid("g",3));
//
//
//        prique.update(bid1,new Bid("ö",0));
//
//        prique.add(new Bid("g",-5));
//        System.out.println(prique.showHeap());


    }

}
