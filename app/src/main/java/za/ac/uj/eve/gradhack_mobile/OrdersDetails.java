package za.ac.uj.eve.gradhack_mobile;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrdersDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_details);

        SharedPreferences preferences = getSharedPreferences("OrdersPref", MODE_PRIVATE);

        String temp = preferences.getString("ordersDetails", "");
        temp = temp.substring(8);

        final int productID = Integer.parseInt(temp);

        final TextView orderNumber = (TextView) findViewById(R.id.orderNumberO);
        final TextView storeName = (TextView) findViewById(R.id.storeNameO);
        final TextView ngoName = (TextView) findViewById(R.id.ngoNameO);
        final TextView productName = (TextView) findViewById(R.id.productNameO);
        final TextView productQuantity = (TextView) findViewById(R.id.productQuantityO);
        final ImageView qrCode = (ImageView) findViewById(R.id.qrCodeO);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Products");


    }
}
