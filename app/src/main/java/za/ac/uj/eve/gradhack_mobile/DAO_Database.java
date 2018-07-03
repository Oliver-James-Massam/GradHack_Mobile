package za.ac.uj.eve.gradhack_mobile;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DAO_Database {

    // --------- Value ----------------------------------------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrder(Orders... order);

    @Update
    void updateValue(Orders... order);

    @Delete
    void deleteValue(Orders... order);

    @Query("SELECT * FROM Orders")
    List<Orders> getOrdersAll();

    @Query("DELETE FROM Orders")
    void deleteAllOrders();
}
