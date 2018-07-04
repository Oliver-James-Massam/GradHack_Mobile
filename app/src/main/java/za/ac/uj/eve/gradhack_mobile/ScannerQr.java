package za.ac.uj.eve.gradhack_mobile;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ScannerQr  extends AppCompatActivity {
    private Button scan_btn;
    private  AppDatabase db;
    private static final String TAG = "LoginActivityDebug";
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
        Intent intent = new Intent(this,PickUpAuth.class) ;
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                String strs = result.getContents();

                if(!strs.contains ("\\#")){
                    intent = new Intent(this,PickUpAuth.class);
                    SharedPreferences productPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor productEditor = productPref.edit();
                    Log.d("Product_List",result.getContents());
                    productEditor.putString("product_key",result.getContents()).apply();
                    // hahahah okay lets just edit a thing

                    //final ArrayList<Triplet<String ,String,Product>> items = new ArrayList<>();
                    //final ListView listItemView = (ListView) findViewById(R.id.requestFoodList);

                    db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "finance")
                            .allowMainThreadQueries()
                            .build();
                    List<Orders> values = db.dao_database().getOrdersAll();

                    Orders temp = null;
                    double total = 0;
                    int idOrder = Integer.parseInt(strs);
                    for (Orders o: values){
                        if (o.getOrderID() == idOrder){
                            temp = o;
                            total += o.getQuantity();
                        }
                    }
                    //Toast.makeText(this, temp.getStoreID(), Toast.LENGTH_LONG).show();
                    final double ftotal = 0;
                    final Orders donation = temp;
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("Users");

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            for (DataSnapshot snap : dataSnapshot.getChildren())
                            {
                                User user  = snap.getValue(User.class);
                                if (snap.getKey().equals(donation.getStoreID()))
                                {
                                    snap.getRef().child("Points").setValue(user.Points + ftotal);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });


                }else{
                    StringTokenizer tok = new StringTokenizer(strs,"#");
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
