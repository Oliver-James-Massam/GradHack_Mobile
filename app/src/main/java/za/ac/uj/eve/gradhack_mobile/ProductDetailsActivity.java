package za.ac.uj.eve.gradhack_mobile;

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

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        //Icon in foreground
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_launcher_foreground);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);

        final String productID = preferences.getString("product_key", "");

        final TextView productName = (TextView) findViewById(R.id.productNameAns);

        final TextView productType = (TextView) findViewById(R.id.productTypeAns);

        final TextView sellBy = (TextView) findViewById(R.id.sellByProdAns);

        final TextView bestBefore = (TextView) findViewById(R.id.bestBeforeProdAns);

        final TextView productQuantity = (TextView) findViewById(R.id.productQuantityAns);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Products");

        final ImageView qrCodeImage = (ImageView) findViewById(R.id.qrCode);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren())
                {
                    Product product  = snap.getValue(Product.class);
                    if (snap.getKey().equals(productID))
                    {
                        productName.setText(product.Name);
                        String strType = "";
                        switch (product.Type)
                        {
                            case 0:
                            {
                                strType = "Fruit";
                                break;
                            }
                            case 1:
                            {
                                strType = "Cannned Food";
                                break;
                            }
                        }
                        productType.setText(strType);
                        sellBy.setText(product.SellByDate);
                        bestBefore.setText(product.BestBeforeDate);
                        Log.d("Cheese","Ham " + product.Quantity);
                        String strQuantity = String.valueOf(product.Quantity);
                        productQuantity.setText(strQuantity);

                        MultiFormatWriter mfw = new MultiFormatWriter();

                        try{
                            String text2qr = productID + "#" +  product.Name + "#" + strType + "#" + strQuantity;//text.getText().toString().trim();
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




    }
}
