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
    private int storeID;

    @ColumnInfo(name = "ngoID")
    private int ngoID;

    @ColumnInfo(name = "productNameID")
    private int productID;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public Orders(int storeID, int ngoID, int productID, int quantity)
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

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getNgoID() {
        return ngoID;
    }

    public void setNgoID(int ngoID) {
        this.ngoID = ngoID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}