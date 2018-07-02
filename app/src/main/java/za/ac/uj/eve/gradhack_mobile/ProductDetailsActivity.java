package za.ac.uj.eve.gradhack_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDetailsActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        //Icon in foreground
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        TextView productName = (TextView) findViewById(R.id.productName);
        productName.setText("Name: " + getString(R.string.tab)+"Hello");

        TextView productType = (TextView) findViewById(R.id.productType);
        productType.setText("Type: " + getString(R.string.tab)+"Hello");

        TextView productQuantity = (TextView) findViewById(R.id.productQuantity);
        productQuantity.setText("Quantity: " + getString(R.string.tab)+"Hello");
    }
}
