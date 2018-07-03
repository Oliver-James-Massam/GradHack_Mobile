package za.ac.uj.eve.gradhack_mobile;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Orders {
    @PrimaryKey(autoGenerate = true)
    private int orderID;

    @ColumnInfo(name = "storeID")
    private String storeID;

    @ColumnInfo(name = "ngoID")
    private String ngoID;

    @ColumnInfo(name = "productNameID")
    private String productID;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public Orders(String storeID, String ngoID, String productID, int quantity)
    {
        this.storeID = storeID;
        this.ngoID = ngoID;
        this.setProductID(productID);
        this.setQuantity(quantity);
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int valueID) {
        this.orderID = valueID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getNgoID() {
        return ngoID;
    }

    public void setNgoID(String ngoID) {
        this.ngoID = ngoID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}