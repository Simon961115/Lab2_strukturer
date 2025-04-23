import java.util.Comparator;

public class BuyBidComparator implements Comparator<Bid> {

    //Tar emot två bud och retunerar -1 om det första budet är större än det andra,
    //0 om båda buden är lika stora och 1 om det andra budet är störst.
    @Override
    public int compare (Bid bid1,Bid bid2) {
        if (bid1.bid > bid2.bid) {return -1;}
        else if(bid1.bid == bid2.bid) {return 0;}
        return 1;
    }
}