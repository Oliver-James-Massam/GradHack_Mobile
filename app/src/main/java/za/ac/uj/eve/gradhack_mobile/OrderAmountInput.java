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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class OrderAmountInput extends DialogFragment
{
        private static Product productInfo = new Product();
        private static int amount;
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

           // txtInput.setHint(productInfo.Quantity);

            builder.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            AppDatabase db;
                            db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "finance")
                                    .allowMainThreadQueries()
                                    .build();
                            int value = Integer.parseInt(txtInput.getText().toString());
                            amount = value;
                            if (amount > productInfo.Quantity)
                                amount = productInfo.Quantity;
                            //Subtract from database
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("Products");

                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    for (DataSnapshot snap : dataSnapshot.getChildren())
                                    {
                                        Product product  = snap.getValue(Product.class);
                                        if (snap.getKey().equals(productID))
                                        {
                                            snap.getRef().child("Amount").setValue(product.Quantity - amount);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println("The read failed: " + databaseError.getCode());
                                }
                            });

                            db.dao_database().insertOrder(new Orders(storeID, userID, productID, value));
                            Toast.makeText(getContext(), "Order placed for " + String.valueOf(amount) + " item " + productInfo.Name , Toast.LENGTH_SHORT).show();
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
