package saurabh.best.sec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class thirdactivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    details member;
    TextView t0, t1, t2, t3, t4;
    EditText e1, e2, e3, e4;
    Button b1;
    FirebaseAuth fAuth;
    ImageView i1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdactivity);

        Intent intent = getIntent();


        //Intent intent=getIntent();
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        e4 = findViewById(R.id.e4);
        t0 = findViewById(R.id.t0);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        b1 = findViewById(R.id.b1);
        i1 = findViewById(R.id.i1);
        //creating firebase authorisation object
        fAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        member = new details();
        //storing uid of user at firebase cloud storage
        String userId = fAuth.getCurrentUser().getUid();
        //showing profile set by default by the user
        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer batch = dataSnapshot.child("batch").getValue(Integer.class);
                Integer mobile = dataSnapshot.child("mobileno").getValue(Integer.class);
                String branch = dataSnapshot.child("branch").getValue(String.class);
                String course = dataSnapshot.child("course").getValue(String.class);
                Log.d("info", "-_-" + batch);
                if (batch != null && batch != 0) {
                    e2.setText(batch.toString());
                }
                if (branch != null) {
                    e1.setText(branch);
                }
                if (mobile != null && mobile != 0) {
                    e3.setText(mobile.toString());
                }
                if (course != null) {
                    e4.setText(course);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(thirdactivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        //updating the profile
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String batch = e2.getText().toString();
                String branch = e1.getText().toString();
                String mobi = e3.getText().toString();
                String course = e4.getText().toString();

                if (TextUtils.isEmpty(batch)) {
                    e1.setError("batch cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(branch)) {
                    e2.setError("batch cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(course)) {
                    e4.setError("course cannot be blank");
                    return;
                }
                // creating map for update
                HashMap<String, Object> values = new HashMap<>();
                values.put("branch", branch);
                values.put("mobileno", Integer.parseInt(mobi));
                values.put("batch", Integer.parseInt(batch));
                values.put("course", course);

                String userId = fAuth.getCurrentUser().getUid();
                mDatabase.child(userId).updateChildren(values).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(thirdactivity.this, "updated successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(thirdactivity.this, secondactivity.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(thirdactivity.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}

