package za.ac.uj.eve.gradhack_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListProductsActivity_Stores extends AppCompatActivity {

    private static final String TAG = "DEBUG_STORES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products__stores);
    }

    @Override
    public void onStart() {
        super.onStart();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("products");
        final ArrayList<Product> items = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren())
                {
                    Product product  = snap.getValue(Product.class);
                    items.add(product);
                    Log.d("ListProducts",TAG + ": " + product.Name);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        ArrayAdapter dispAdp;
        ListView Display = (ListView) findViewById(R.id.productList);
        if (Display != null) {
            Display.setAdapter(new ArrayAdapter<String>(ListProductsActivity_Stores.this, android.R.layout.simple_list_item_1, new ArrayList<String>()));

            dispAdp = (ArrayAdapter) Display.getAdapter();
            dispAdp.clear();
            dispAdp.addAll(items);
        }
    }
}
