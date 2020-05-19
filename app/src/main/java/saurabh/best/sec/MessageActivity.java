package saurabh.best.sec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    DatabaseReference dRef;
    DatabaseReference usersRef;
    FirebaseAuth fAuth;
    TextView grp_name;
    ImageButton send_arrow;
    EditText send_msg;
    List<Messages> messages = new ArrayList<Messages>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        fAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        dRef = FirebaseDatabase.getInstance().getReference("groups");
        final String userid = fAuth.getCurrentUser().getUid();
        // Recycler View
        final RecyclerView rc = findViewById(R.id.r1);
        final Message_Adapter msgadapter = new Message_Adapter(this, this.messages, userid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rc.setLayoutManager(layoutManager);
        rc.setAdapter(msgadapter);

        // Group Name
        grp_name = findViewById(R.id.t1);
        send_arrow = findViewById(R.id.ib1);
        send_msg = findViewById(R.id.e1);
        Bundle bundle = getIntent().getExtras();
//      Group g= (Group)bundle.getSerializable("group");
        Group g = (Group) getIntent().getSerializableExtra("group");

        grp_name.setText(g.getName());

        send_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Group g = (Group) getIntent().getSerializableExtra("group");
//               Group g= (Group)bundle.getSerializable("group");
                final String grpid = g.getId();
                usersRef.child(userid).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Messages message = new Messages();
                        message.setName((String) dataSnapshot.getValue());
                        message.setSenderid(userid);
                        message.setText(send_msg.getText().toString());
                        dRef.child(grpid).child("messages").push().setValue(message);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
//        dRef.child(g.getId()).child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("","Adding Message");
//                Iterator<DataSnapshot> ds = dataSnapshot.getChildren().iterator();
////                messages.clear();
//                while(ds.hasNext()) {
//                    Messages m = (Messages) ds.next().getValue(Messages.class);
//                    messages.add(m);
//                    Log.d("", m.getName());
//                }
//                msgadapter.notifyDataSetChanged();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        dRef.child(g.getId()).child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Messages m = dataSnapshot.getValue(Messages.class);
                messages.add(m);
                msgadapter.notifyDataSetChanged();
                rc.smoothScrollToPosition(msgadapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
