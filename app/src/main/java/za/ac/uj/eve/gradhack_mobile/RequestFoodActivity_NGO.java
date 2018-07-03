package za.ac.uj.eve.gradhack_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class  RequestFoodActivity_NGO  extends AppCompatActivity{

    private ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_food__ngo);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Products");

        final ArrayList<Pair<String,Product>> items = new ArrayList<>();

        final ListView listItemView = (ListView) findViewById(R.id.requestFoodList);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren())
                {

                    Product product  = snap.getValue(Product.class);
                    items.add(new Pair<String, Product>(snap.getKey(),product));
                }
                String[] itemArray = new String[items.size()];
                for (int i = 0; i < items.size();i++)
                {
                    itemArray[i] = items.get(i).second.Name;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RequestFoodActivity_NGO.this,android.R.layout.simple_list_item_2, android.R.id.text1, itemArray);

                listItemView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogFragment dialog = new OrderAmountInput();
                dialog.show(getSupportFragmentManager(), "OrderAmountInput");

                SharedPreferences productPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor productEditor = productPref.edit();
                String tmpkey = items.get(i).first;
                Log.d("Product_List",tmpkey);
                productEditor.putString("product_key_from_order",tmpkey).apply();

                /*
                Intent intent = new Intent(RequestFoodActivity_NGO.this,ProductDetailsActivity.class);
                intent.putExtra("Product Name",listItemView.getItemIdAtPosition(i));
                // shared pref

                SharedPreferences productPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor productEditor = productPref.edit();
                String tmpkey = items.get(i).first;
                Log.d("Product_List",tmpkey);
                productEditor.putString("product_key",tmpkey).apply();
                startActivity(intent);
                */
            }
        });
    }




    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            RequestFoodActivity_NGO.ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                RequestFoodActivity_NGO.ViewHolder viewHolder = new RequestFoodActivity_NGO.ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                //viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (RequestFoodActivity_NGO.ViewHolder) convertView.getTag();
//            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
//                }
//            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }
    }
    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        //Button button;
    }
}