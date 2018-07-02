package za.ac.uj.eve.gradhack_mobile;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class zbraScan extends AppCompatActivity {

    EditText text;
    Button gen_btn;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbra_scan);
        text = findViewById(R.id.text);
        gen_btn = findViewById(R.id.gen_btn);
        image = findViewById(R.id.image);
        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiFormatWriter mfw = new MultiFormatWriter();
                try{
                    String text2qr = text.getText().toString().trim();
                    BitMatrix bm = mfw.encode(text2qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder be = new BarcodeEncoder();
                    Bitmap bitmap = be.createBitmap(bm);
                    image.setImageBitmap(bitmap);
            }catch(WriterException e){
                    e.printStackTrace();
                }
        }
        });
    }
}
