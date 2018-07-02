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

    String[] listItemsValue = new String[] {
            "Android",
            "PHP",
            "Web Development",
            "Blogger",
            "SEO",
            "Photoshop",
            "Android Studio",
            "Eclipse",
            "SDK Manager",
            "AVD Manager"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products__stores);

        listItemView = (ListView)findViewById(R.id.listView1);
        fab = (FloatingActionButton)findViewById(R.id.fab1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2, android.R.id.text1, listItemsValue);

        listItemView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               //Toast.makeText(MainActivity.this, "Fab Clicked", Toast.LENGTH_LONG).show();

            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("products");
        final ArrayList<Product> items = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
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
            public void onCancelled(DatabaseError databaseError)
            {
                // ...
            }
        });

        /**ArrayAdapter dispAdp;
        ListView Display = (ListView) findViewById(R.id.);
        if (Display != null) {
            Display.setAdapter(new ArrayAdapter<String>(ListProductsActivity_Stores.this, android.R.layout.simple_list_item_1, new ArrayList<String>()));

            dispAdp = (ArrayAdapter) Display.getAdapter();
            dispAdp.clear();
            dispAdp.addAll(items);
        }*/
    }
}
