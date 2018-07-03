package za.ac.uj.eve.gradhack_mobile;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public  class DatabaseWrapper
{
    private static String result;

    public static String getUserID(String email) // A user is a ngo or store
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String tmpEmail = email;
        result = "";
        DatabaseReference ref = database.getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren())
                {
                    User user = snap.getValue(User.class);

                    if(user.Email.equals(tmpEmail))
                    {
                        result = snap.getKey();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return result;
    }
}
