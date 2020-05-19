package saurabh.best.sec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class joinGroupActivity extends AppCompatActivity {
    private static final String TAG = "UserList";
    private DatabaseReference userlistReference;
    private ValueEventListener mUserListListener;
    private FirebaseAuth fAuth;
    ArrayList<Group> grouplist = new ArrayList<>();
    GrpListAdapter arrayAdapter;


    ListView userListView;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //setting up logout view
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        final ListView listView = findViewById(R.id.findGroup);
        fAuth = FirebaseAuth.getInstance();
        //getting current userid
        final String userid = fAuth.getCurrentUser().getUid();
        //setting adapter for join group list
        arrayAdapter = new GrpListAdapter(this, R.layout.grplistadapter, grouplist);
        listView.setAdapter(arrayAdapter);
        userlistReference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference groupsdRef = rootRef.child("groups");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Group g = grouplist.get(i);
                //checking group type
                if (g.getType().equals("Public")) {
                    groupsdRef.child(g.getId()).child("users").child(userid).setValue(true);

                    userlistReference.child(userid).child("groups").child(g.getId()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(joinGroupActivity.this, "You joined group successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                } else {
                    Toast.makeText(joinGroupActivity.this, "Sorry Group is private.Need to send request.feature under development", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String gid = ds.getKey();
                    Object val = ds.child("users").child(userid).getValue();
                   //checking if user exist in group or not
                    if (val == null) {
                        Group g = ds.getValue(Group.class);
                        g.setId(gid);
                        grouplist.add(g);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        groupsdRef.addListenerForSingleValueEvent(eventListener);
    }

}
