package saurabh.best.sec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MyGroup extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference dRef;
  ArrayList<groups> mygrplist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group);
        fAuth=FirebaseAuth.getInstance();
        dRef= FirebaseDatabase.getInstance().getReference();
        String userid=fAuth.getCurrentUser().getUid();
        dRef.child("users").child(userid).child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> groupids=new ArrayList<>();
                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                while(i.hasNext()){
                   groupids.add( i.next().getKey());

                }
               // Log.i("",groupids.get(0));
             getGrpsInfo(groupids);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void getGrpsInfo(final ArrayList<String> grpIdsList)
    {
        Iterator<String> i=grpIdsList.iterator();
        while(i.hasNext())
        {   final String grpId = i.next();
            dRef.child("groups").child(grpId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   groups grp =dataSnapshot.getValue(groups.class);
                   grp.setId(grpId);
                   mygrplist.add(grp);

                   Log.i("-_-",grp.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
