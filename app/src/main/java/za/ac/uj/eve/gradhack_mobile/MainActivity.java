package za.ac.uj.eve.gradhack_mobile;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivityDebug";
    private static int userType = 0; //Store = 0; NGO = 1;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference mDatabase;
        /*
        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String name = "PnP";//change to new user name
        String password = "12345678";//change to new user password
        User tmpUser = new User(name,"0721231234",name + "@email.com","0,0",0,0);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        String userId = mDatabase.push().getKey();
        mDatabase.child(userId).setValue(tmpUser);

         mAuth.createUserWithEmailAndPassword(name + "@email.com", password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
*/
        //Open DB
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "finance")
                .allowMainThreadQueries()
                .build();

        db.dao_database().insertOrder(new Orders(1, 1, 1, 1));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        final String email = preferences.getString("email", "");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
         mDatabase = database.getReference("Users");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        final MenuItem typeFunction = menu.findItem(R.id.navUserTypeFunction);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren())
                {
                    User user = snap.getValue(User.class);

                    if(user.Email.equals(email))
                    {
                        userType = user.Type;
                        //Log.d(TAG, "User name: " + user.Name + ", email " + user.Email + " , Type: " + userType);
                        switch(userType)// Stores
                        {
                            case 0:
                            {
                                // set new title to the MenuItem
                                typeFunction.setTitle("Products Listed");
                                typeFunction.setIcon(R.drawable.baseline_list_black_18dp);

                                break;
                            }

                            case 1:
                            {
                                // set new title to the MenuItem
                                typeFunction.setTitle("Place Order");
                                typeFunction.setIcon(R.drawable.baseline_shopping_cart_black_18dp);
                                break;
                            }
                        }
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        // add NavigationItemSelectedListener to check the navigation clicks
        navigationView.setNavigationItemSelectedListener(this);
        List<Orders> values = db.dao_database().getOrdersAll();
        Orders temp = values.get(0);
        Toast.makeText(this, "OrderID: "+ temp.getOrderID() + " StoreID: " + temp.getStoreID(), Toast.LENGTH_SHORT).show();
    }

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
//                return;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finishAffinity();
                startActivity(intent);
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            mHandler.postDelayed(mRunnable, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.navUserTypeFunction)
        {
            if(userType == 0)
            {
                startActivity(new Intent(MainActivity.this, ListProductsActivity_Stores.class));
            }
            else if(userType == 1)
            {
                startActivity(new Intent(MainActivity.this, RequestFoodActivity_NGO.class));
            }
        }
        /*if (id == R.id.navListProducts) {
            startActivity(new Intent(MainActivity.this, ListProductsActivity_Stores.class));
        }*/ else if (id == R.id.navViewOrders) {
            startActivity(new Intent(MainActivity.this, ViewOrdersActivity.class));
        } else if (id == R.id.navLeaderboard) {
            startActivity(new Intent(MainActivity.this, DonationLeaderboardActivity.class));
        } /*else if (id == R.id.navRequestFood) {
            startActivity(new Intent(MainActivity.this, RequestFoodActivity_NGO.class));
        }*/ else if (id == R.id.navScanner) {
            startActivity(new Intent(MainActivity.this, ScannerQr.class));
        } else if (id == R.id.navLogout) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
