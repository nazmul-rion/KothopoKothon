package com.example.kothopokothon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText mname,mphone,mpassword,memail;
    Button breg;
    TextView loghere;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ProgressBar pbar;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mname=findViewById(R.id.nametxt);
        mphone=findViewById(R.id.phonetxt);
        mpassword=findViewById(R.id.passtxt);
        memail=findViewById(R.id.mailtxt);
        breg=findViewById(R.id.btnr);
        loghere=findViewById(R.id.logherebtn);
        mAuth=FirebaseAuth.getInstance();
        pbar=findViewById(R.id.progressBarlreg);


        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname=mname.getText().toString();
                final String phone=mphone.getText().toString();
                String pass=mpassword.getText().toString();
                final String email=memail.getText().toString();

                if(TextUtils.isEmpty(email))
                {
                    memail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    mpassword.setError("Password is required.");
                    return;
                }
                if(pass.length()<6)
                {
                    mpassword.setError("Password must have 6 character");
                    return;
                }
                pbar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            uid=mAuth.getCurrentUser().getUid();
                            user info = new user(fullname,email,phone);

                            FirebaseDatabase.getInstance().getReference("Users").
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(info).addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(RegistrationActivity.this, "User created.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );


                            pbar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }
                        else{
                            Toast.makeText(RegistrationActivity.this, "Error..."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        loghere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });






    }
}
