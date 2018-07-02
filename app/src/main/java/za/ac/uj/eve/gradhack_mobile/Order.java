package za.ac.uj.eve.gradhack_mobile;

@SuppressWarnings("WeakerAccess")
public class Order
{
    public int StoreID;
    public int NGO_ID;
    public int ProductID;
    public int Quantity;
    public boolean PickedUp;

    public Order()
    {

    }

    public Order(int StoreID,int NGO_ID,int ProductID,int Quantity,boolean PickedUp)
    {
        this.StoreID = StoreID;
        this.NGO_ID = NGO_ID;
        this.ProductID = ProductID;
        this.Quantity = Quantity;
        this.PickedUp = PickedUp;
    }

}
