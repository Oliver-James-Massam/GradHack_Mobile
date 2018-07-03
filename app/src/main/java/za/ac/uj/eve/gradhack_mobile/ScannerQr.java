package za.ac.uj.eve.gradhack_mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.StringTokenizer;

public class ScannerQr  extends AppCompatActivity {
    private Button scan_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_qr);
        //Icon in foreground
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_launcher_foreground);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        scan_btn = (Button) findViewById(R.id.scan_btn);
        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(this,MainActivity.class);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }

            else {
                StringTokenizer tok = new StringTokenizer(result.getContents(),"#");
                if(tok.countTokens() == 1){
                    intent = new Intent(this,PickUpAuth.class);
                }else{
                    String ID = tok.nextToken();
                    String name = tok.nextToken();
                    String type = tok.nextToken();
                    if (type == "fruit"){
                        intent = new Intent(this,ProductDetailsActivity.class);
                    }else{
                        intent = new Intent(this,ProductDetailsActivity.class);
                    }
                    SharedPreferences productPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor productEditor = productPref.edit();
                    Log.d("Product_List",ID);
                    productEditor.putString("product_key",ID).apply();

                }
                startActivity(intent);
                }

            }
        else
        {
           super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
