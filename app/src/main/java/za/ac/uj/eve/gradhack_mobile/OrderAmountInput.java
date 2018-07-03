package za.ac.uj.eve.gradhack_mobile;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class OrderAmountInput extends DialogFragment
{
        private static Product productInfo = new Product();
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            SharedPreferences preferences = getActivity().getSharedPreferences("MyPref",MODE_PRIVATE);
            final String productID = preferences.getString("product_key_from_order", "");
            final String storeID = preferences.getString("store_key_from_product", "");
            final String userID = preferences.getString("UserID", "");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("Products");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    for (DataSnapshot snap : dataSnapshot.getChildren())
                    {
                        Product product  = snap.getValue(Product.class);
                        Log.d("Spiderman", "Optimus Prime: " + productID + " " + snap.getKey());
                        if (snap.getKey().equals(productID))
                        {

                            productInfo = product;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();

            View mView = inflater.inflate(R.layout.input_dialog, null);
            final EditText txtInput = (EditText)mView.findViewById(R.id.input);
            builder.setView(mView);

//            txtInput.setHint(productInfo.Quantity);

            builder.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            AppDatabase db;
                            db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "finance")
                                    .allowMainThreadQueries()
                                    .build();
                            int value = Integer.parseInt(txtInput.getText().toString());

                            db.dao_database().insertOrder(new Orders(storeID, userID, productID, value));

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            OrderAmountInput.this.getDialog().cancel();
                        }
                    });

            // Create the AlertDialog object and return it
            return builder.create();
        }
}
