package com.example.kothopokothon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button blog,breg;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blog=findViewById(R.id.logbtn);
        breg=findViewById(R.id.regbtn);
        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });

        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
                finish();
            }
        });

    }
}
