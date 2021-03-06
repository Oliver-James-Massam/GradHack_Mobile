package za.ac.uj.eve.gradhack_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.Task;
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
    private static String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products__stores);
        listItemView = (ListView)findViewById(R.id.listView1);
//        fab = (FloatingActionButton)findViewById(R.id.fab1);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//               //Toast.makeText(MainActivity.this, "Fab Clicked", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(ListProductsActivity_Stores.this, AddProduct.class));
//            }
//        });
        ImageButton btnAddIncome = (ImageButton) findViewById(R.id.btnAddProduct);
        btnAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListProductsActivity_Stores.this, AddProduct.class));
            }
        });

        /*
        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        final String email = preferences.getString("email", "");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("Products");
        final ArrayList<Product> items = new ArrayList<>();
        Log.d(TAG," A" );
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ListProducts","A");
                String UserID = "A";
                SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                final String email = preferences.getString("email", "");
                UserID = DatabaseWrapper.getUserID(email);

                for (DataSnapshot snap : dataSnapshot.getChildren())
                {

                    Product product  = snap.getValue(Product.class);

                    if (product != null && UserID != null && product.StoreID.equals(UserID))
                        items.add(product);
                    Toast.makeText(ListProductsActivity_Stores.this, "Fab Clicked", Toast.LENGTH_LONG).show();
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
        });*/
    }

    @Override
    public void onStart() {
        super.onStart();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users");
        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);

        final String UserID = preferences.getString("UserID", "");

        ref = database.getReference("Products");
        final ArrayList<Pair<String,Product>> items = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren())
                {

                    Product product  = snap.getValue(Product.class);
                    if (product != null && UserID != null && product.StoreID.equals(UserID))
                        items.add(new Pair<String, Product>(snap.getKey(),product));

                }
                String[] itemArray = new String[items.size()];
                for (int i = 0; i < items.size();i++)
                {
                    itemArray[i] = items.get(i).second.Name;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListProductsActivity_Stores.this,android.R.layout.simple_list_item_2, android.R.id.text1, itemArray);

                listItemView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListProductsActivity_Stores.this,ProductDetailsActivity.class);
                intent.putExtra("Product Name",listItemView.getItemIdAtPosition(i));
                // shared pref
                SharedPreferences productPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor productEditor = productPref.edit();
                String tmpkey = items.get(i).first;
                Log.d("Product_List",tmpkey);
                productEditor.putString("product_key",tmpkey).apply();
                startActivity(intent);
            }
        });
    }
}
