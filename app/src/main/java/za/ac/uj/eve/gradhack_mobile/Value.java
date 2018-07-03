package za.ac.uj.eve.gradhack_mobile;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Value {
    @PrimaryKey(autoGenerate = true)
    private int valueID;

    @ColumnInfo(name = "valueType")
    private String valueType;

    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "categoryName")
    private String categoryName;

    @ColumnInfo(name = "transactionDate")
    private Date transactionDate;

    @ColumnInfo(name = "recurringTimes")
    private int recurringTimes;

    public Value(String valueType, int amount, String categoryName, Date transactionDate, int recurringTimes)
    {
        this.valueType = valueType;
        this.amount = amount;
        this.setCategoryName(categoryName);
        this.setTransactionDate(transactionDate);
        this.setRecurringTimes(recurringTimes);
    }

    public int getValueID() {
        return valueID;
    }

    public void setValueID(int valueID) {
        this.valueID = valueID;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getRecurringTimes() {
        return recurringTimes;
    }

    public void setRecurringTimes(int recurringTimes) {
        this.recurringTimes = recurringTimes;
    }
}
