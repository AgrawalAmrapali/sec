package saurabh.best.sec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class secondactivity extends AppCompatActivity {
    DatabaseReference dRef;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);
        dRef = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();

        setTitle("Menu");
        setContentView(R.layout.activity_secondactivity);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> menu = new ArrayList<String>();

        setTitle("Menu");
        setContentView(R.layout.activity_secondactivity);
        //creating list in a list view
        listView = (ListView) findViewById(R.id.listView);
        menu = new ArrayList<String>();
        menu.add("Create group");
        menu.add("Join group");
        menu.add("Find people");
        menu.add("Communities");
        menu.add("Friend circle");
        menu.add("Volunteer group");
        menu.add("My groups");
        menu.add("Group request");
        menu.add("Resume");
        menu.add("Chat");
        menu.add("Profile");
        menu.add("Logout");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, menu);

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 10) {
                    Intent intent = new Intent(getApplicationContext(), thirdactivity.class);
                    intent.putExtra("note", i);
                    startActivity(intent);
                } else if (i == 0) {    //calling request function
                    RequestNewGroup();
                }
                else if(i == 11){
                    if(fAuth.getCurrentUser() != null){
                        fAuth.signOut();
                        startActivity(new Intent(secondactivity.this,MainActivity.class));
                    }
                }else if(i==6)
                {
                    startActivity(new Intent(secondactivity.this,MyGroup.class));

                }else if (i == 2) {
                    //Find people
                    Intent intent = new Intent(getApplicationContext(), findPeople.class);
                    intent.putExtra("note", i);
                    startActivity(intent);
                }
                else if (i==1){
                    Intent intent = new Intent(getApplicationContext(), joinGroupActivity.class);
                    intent.putExtra("note", i);
                    startActivity(intent);
                }



            }
        });
    }

    private void CreateGroup(final String s) { //pushing group name to table groups
        String userId = fAuth.getCurrentUser().getUid();
        groups nGrp = new groups();
        nGrp.setName(s);
        nGrp.addUser(userId);
        DatabaseReference grpRef = dRef.child("groups").push();
        String grpId = grpRef.getKey();
        grpRef.setValue(nGrp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(secondactivity.this, s + " Group created", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(secondactivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dRef.child("users").child(userId).child("groups").child(grpId).setValue(true);


    }

        private void RequestNewGroup () {   //creating alert box to create group in database
            AlertDialog.Builder builder = new AlertDialog.Builder(secondactivity.this);
            builder.setTitle("GROUP NAME");
            final EditText e1 = new EditText(secondactivity.this);
            e1.setHint(" e.g. Coding chef");
            builder.setView(e1);
            builder.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (TextUtils.isEmpty(e1.getText().toString())) {
                        Toast.makeText(secondactivity.this, "Enter Group name", Toast.LENGTH_SHORT).show();
                    } else {
                        CreateGroup(e1.getText().toString());
                    }

                }
            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
    }
