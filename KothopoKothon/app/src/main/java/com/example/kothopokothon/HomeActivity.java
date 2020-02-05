package com.example.kothopokothon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ViewPager mviewpager;
    SectionPagerAdapter msectionpager;
    TabLayout mtablayout;
    private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
      mtoolbar = findViewById(R.id.home_page_toolbar);
        mviewpager = findViewById(R.id.tab_pager);
        mtablayout = findViewById(R.id.home_tab_layout);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("KothopoKothon");
        msectionpager = new SectionPagerAdapter(getSupportFragmentManager(), 1);
        mviewpager.setAdapter(msectionpager);
        mtablayout.setupWithViewPager(mviewpager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.home_logout)
        {mAuth.signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();}
        if(item.getItemId()==R.id.home_user)
        {
            startActivity(new Intent(getApplicationContext(),UsersActivity.class));
            finish();}

        return  true;
    }
}
