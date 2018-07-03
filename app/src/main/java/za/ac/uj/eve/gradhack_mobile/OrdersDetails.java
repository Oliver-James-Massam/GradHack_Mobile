package za.ac.uj.eve.gradhack_mobile;

import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

public class OrdersDetails extends AppCompatActivity {

    private static String strProductID = "";
    private static String strNGO_ID = "";
    private static String strStoreID = "";
    private static String strOrderAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_details);

        SharedPreferences preferences = getSharedPreferences("OrdersPref", MODE_PRIVATE);

        String temp = preferences.getString("ordersDetails", "");
        temp = temp.substring(8);

        final int orderID = Integer.parseInt(temp);

        final TextView orderNumber = (TextView) findViewById(R.id.orderNumberO);
        final TextView storeName = (TextView) findViewById(R.id.storeNameO);
        final TextView ngoName = (TextView) findViewById(R.id.ngoNameO);
        final TextView productName = (TextView) findViewById(R.id.productNameO);
        final TextView productQuantity = (TextView) findViewById(R.id.productQuantityO);
        final ImageView qrCodeImage = (ImageView) findViewById(R.id.qrCodeO);
        orderNumber.setText("Order #" + orderID);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();



        AppDatabase db;
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "finance")
                .allowMainThreadQueries()
                .build();
        List<Orders> orders = db.dao_database().getOrdersAll();

        strProductID = "";
        strNGO_ID = "";
        strStoreID = "";
        strOrderAmount = "";

        for (Orders order : orders)
        {
            if (order.getOrderID() == orderID)
            {
                strProductID = order.getProductID();
                strStoreID = order.getStoreID();
                strNGO_ID = order.getNgoID();
                strOrderAmount = String.valueOf(order.getQuantity());
                break;
            }
        }

        DatabaseReference ref = database.getReference("Products");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snap : dataSnapshot.getChildren())
                {
                    Product product  = snap.getValue(Product.class);

                    if (snap.getKey().equals(strProductID))
                    {
                        productName.setText(product.Name);
                        productQuantity.setText(strOrderAmount);
                        MultiFormatWriter mfw = new MultiFormatWriter();

                        try{
                            String text2qr = String.valueOf(orderID);//text.getText().toString().trim();
                            BitMatrix bm = mfw.encode(text2qr, BarcodeFormat.QR_CODE,600,600);
                            BarcodeEncoder be = new BarcodeEncoder();
                            Bitmap bitmap = be.createBitmap(bm);
                            qrCodeImage.setImageBitmap(bitmap);
                        }catch(WriterException e){
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        ref = database.getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snap : dataSnapshot.getChildren())
                {
                    User user  = snap.getValue(User.class);

                    if (snap.getKey().equals(strNGO_ID))
                    {
                        ngoName.setText(user.Name);
                    }
                    if (snap.getKey().equals(strStoreID))
                    {
                        storeName.setText(user.Name);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

    }
}
