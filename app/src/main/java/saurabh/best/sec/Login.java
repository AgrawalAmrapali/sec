package saurabh.best.sec;

import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextView t1;
    EditText e1, e2;
    ImageView i1;
    Button login;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        t1 = findViewById(R.id.t1);
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        i1 = findViewById(R.id.i1);
        login = findViewById(R.id.b1);
        fAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login using email and password
                fAuth.signInWithEmailAndPassword(e1.getText().toString(), e2.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d("info", "success");
                        Intent i = new Intent(Login.this, MainActivity2.class);
                        startActivity(i);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("info", "Task Failed ");
                        Toast.makeText(Login.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                });
            }
        });

    }
}
