package za.ac.uj.eve.gradhack_mobile;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {Orders.class}, version = 4)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract DAO_Database dao_database();
}
