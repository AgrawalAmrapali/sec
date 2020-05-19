package saurabh.best.sec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.lang.reflect.Member;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button b, b2;
    EditText name, password, email, regno;
    TextView sign;
    //creating firebase authentication memeber
    public static FirebaseAuth fAuth;
    //details class object
    details member;
    //reference of our databse object
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        name = findViewById(R.id.e1);
        regno = findViewById(R.id.e2);
        email = findViewById(R.id.e3);
        password = findViewById(R.id.e4);
        sign = findViewById(R.id.t1);
        dbRef = FirebaseDatabase.getInstance().getReference().child("users");
        member = new details();
        //checking if already logged in
        if (fAuth.getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, secondactivity.class);
            startActivity(intent);
            finish();

        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String pass = password.getText().toString();
                String na = name.getText().toString();
                String rn = regno.getText().toString();
                //checking inputs
                if (TextUtils.isEmpty(em)) {
                    email.setError("email cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("password cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(na)) {
                    name.setError("name cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(rn)) {
                    regno.setError("Registeration Number cannot be blank");
                    return;
                }
                if (pass.length() < 6) {
                    password.setError("Password length should be greater than 6");
                    return;
                }
                //setting obj of member
                member.setName(na);
                member.setReg(Integer.parseInt(rn));
                member.setEmail(em);
                //creating user with email password
                fAuth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = fAuth.getCurrentUser().getUid();
                            dbRef.child(userId).setValue(member);
                            Toast.makeText(MainActivity.this, "user created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, secondactivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();

            }
        });

    }
}
