package za.ac.uj.eve.gradhack_mobile;

public class Product
{

    public String BestBeforeDate;
    public String SellByDate;
    public String Name;
    public int Type;
    public int Quantity;

    public Product()
    {

    }

    public Product(String BestBefore,String SellBy,String Name,int Type,int Quantity)
    {
        this.BestBeforeDate = BestBefore;
        this.SellByDate = SellBy;
        this.Name = Name;
        this.Type = Type;
        this.Quantity = Quantity;
    }
}
