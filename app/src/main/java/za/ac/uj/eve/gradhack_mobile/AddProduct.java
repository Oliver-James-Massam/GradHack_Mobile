package za.ac.uj.eve.gradhack_mobile;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddProduct extends AppCompatActivity
{
    EditText txtName;
    EditText txtType;
    EditText txtSellBy;
    EditText txtBestBefore;
    EditText txtQuantity;
    Button btnAdd;
    private static String userID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        final String email = preferences.getString("email", "");
        final String userID = preferences.getString("UserID", "");


        txtName = (EditText)findViewById(R.id.txtItemName);
        txtType = (EditText)findViewById(R.id.txtType);
        txtSellBy = (EditText)findViewById(R.id.txtSellBy);
        txtBestBefore = (EditText)findViewById(R.id.txtBestBefore);
        txtQuantity = (EditText)findViewById(R.id.txtQuantity);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                String strName = txtName.getText().toString();
                String strType = txtType.getText().toString();
                String strSellBy = txtSellBy.getText().toString();
                String strBestBefore = txtBestBefore.getText().toString();
                String strQuantity = txtQuantity.getText().toString();
                boolean cancel = false;
                View focusView = null;
                if (strName.equals(""))
                {
                    txtName.setError(getString(R.string.error_invalid_product_name));
                    focusView = txtName;
                    cancel = true;
                }
                if (strType.equals(""))
                {
                    txtType.setError(getString(R.string.error_invalid_product_type));
                    focusView = txtType;
                    cancel = true;
                }
                if (strSellBy.equals(""))
                {
                    txtSellBy.setError(getString(R.string.error_invalid_product_sellby));
                    focusView = txtSellBy;
                    cancel = true;
                }
                if (strBestBefore.equals(""))
                {
                    txtBestBefore.setError(getString(R.string.error_invalid_product_bestbefore));
                    focusView = txtBestBefore;
                    cancel = true;
                }
                if (strQuantity.equals(""))
                {
                    txtQuantity.setError(getString(R.string.error_invalid_product_quantity));
                    focusView = txtQuantity;
                    cancel = true;
                }
                if (cancel)
                {
                    focusView.requestFocus();
                }
                else
                {
                    Product product = new Product(strBestBefore,strSellBy,strName,Integer.parseInt(strType),Integer.parseInt(strQuantity),userID);

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Products");
                    String productID = mDatabase.push().getKey();
                    mDatabase.child(productID).setValue(product);
                }
            }
        });


    }
}
