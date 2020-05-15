package saurabh.best.sec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        //getting ids of all groups of signed in user
        dRef.child("users").child(userid).child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> groupids=new ArrayList<>();
                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                while(i.hasNext()){
                   groupids.add( i.next().getKey());

                }

             getGrpsInfo(groupids);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    //getting info of groups from group ids
    private void getGrpsInfo(final ArrayList<String> grpIdsList)
    {
        final Iterator<String> i=grpIdsList.iterator();
        while(i.hasNext())
        {   final String grpId = i.next();
            dRef.child("groups").child(grpId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   groups grp =dataSnapshot.getValue(groups.class);
                   grp.setId(grpId);
                   mygrplist.add(grp);
                   if(!i.hasNext())
                   {   ArrayList<String> names=new ArrayList<>();
                       Iterator<groups> mygrplistiterator=mygrplist.iterator();
                       while(mygrplistiterator.hasNext())
                       {
                           names.add(mygrplistiterator.next().getName());
                       }
                       listview(names);
                   }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
    private void listview(ArrayList<String> grpnamelist)
    {
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, grpnamelist);

        listView.setAdapter(arrayAdapter);
    }
}
