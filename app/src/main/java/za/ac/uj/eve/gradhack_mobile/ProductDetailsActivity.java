package za.ac.uj.eve.gradhack_mobile;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        //Icon in foreground
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_launcher_foreground);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        SharedPreferences preferences = getSharedPreferences("product", MODE_PRIVATE);
        final String email = preferences.getString("email", "");

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
        productName.setText("Name:     " + getString(R.string.tab)+"Hello");

        TextView productType = (TextView) findViewById(R.id.productType);
        productType.setText("Type:     " + getString(R.string.tab)+"Hello");

        TextView productQuantity = (TextView) findViewById(R.id.productQuantity);
        productQuantity.setText("Quantity: " + getString(R.string.tab)+"Hello");

        ImageView qrCodeImage = (ImageView) findViewById(R.id.qrCode);
        MultiFormatWriter mfw = new MultiFormatWriter();
        try{
            String text2qr = "";//text.getText().toString().trim();
            BitMatrix bm = mfw.encode(text2qr, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder be = new BarcodeEncoder();
            Bitmap bitmap = be.createBitmap(bm);
            qrCodeImage.setImageBitmap(bitmap);
        }catch(WriterException e){
            e.printStackTrace();
        }
    }
}
