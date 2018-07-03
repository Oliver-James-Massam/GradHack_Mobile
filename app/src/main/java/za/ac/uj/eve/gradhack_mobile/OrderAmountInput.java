package za.ac.uj.eve.gradhack_mobile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import static android.content.Context.MODE_PRIVATE;

public class OrderAmountInput extends DialogFragment
{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            //SharedPreferences preferences = getSharedPreferences();

           // final String product = preferences.getString("product_key", "");
            builder.setView(inflater.inflate(R.layout.input_dialog, null))
                    // Add action buttons
                    .setPositiveButton("Order", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {


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
