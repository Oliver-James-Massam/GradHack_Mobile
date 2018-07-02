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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class ListProductsActivity_Stores extends AppCompatActivity {

    private static final String TAG = "DEBUG_STORES";

    ListView listItemView;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products__stores);
        listItemView = (ListView)findViewById(R.id.listView1);
        fab = (FloatingActionButton)findViewById(R.id.fab1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               //Toast.makeText(MainActivity.this, "Fab Clicked", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ListProductsActivity_Stores.this, AddProduct.class));
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Products");
        final ArrayList<Product> items = new ArrayList<>();
        Log.d(TAG," A" );
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren())
                {

                    Product product  = snap.getValue(Product.class);
                    items.add(product);
                }
                String[] itemArray = new String[items.size()];
                for (int i = 0; i < items.size();i++)
                {
                    itemArray[i] = items.get(i).Name;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListProductsActivity_Stores.this,android.R.layout.simple_list_item_2, android.R.id.text1, itemArray);

                listItemView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Products");
        final ArrayList<Product> items = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren())
                {

                    Product product  = snap.getValue(Product.class);
                    items.add(product);

                }
                String[] itemArray = new String[items.size()];
                for (int i = 0; i < items.size();i++)
                {
                    itemArray[i] = items.get(i).Name;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListProductsActivity_Stores.this,android.R.layout.simple_list_item_2, android.R.id.text1, itemArray);

                listItemView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}
