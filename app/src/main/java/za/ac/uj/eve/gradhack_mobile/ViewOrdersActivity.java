package za.ac.uj.eve.gradhack_mobile;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class ViewOrdersActivity extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<String>();
    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("Orders");
        //Open DB
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "finance")
                .allowMainThreadQueries()
                .build();

        List<Orders> values = db.dao_database().getOrdersAll();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        ListView lv = (ListView) findViewById(R.id.listview);

        for(int i = 0; i < values.size(); i++) {
            data.add(" Order #" + values.get(i).getOrderID());
        }
        lv.setAdapter(new MyListAdaper(this, R.layout.list_item, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ViewOrdersActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
                SharedPreferences ordersPref = getApplicationContext().getSharedPreferences("OrdersPref", MODE_PRIVATE);
                SharedPreferences.Editor ordersEditor = ordersPref.edit();
                String tmpkey = data.get(position);
                ordersEditor.putString("ordersDetails",tmpkey).apply();

                Intent intent = new Intent(ViewOrdersActivity.this,OrdersDetails.class);

                startActivity(intent);
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
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                //viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
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