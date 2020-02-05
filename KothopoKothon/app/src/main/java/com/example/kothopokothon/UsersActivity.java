package com.example.kothopokothon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class UsersActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private RecyclerView muserlist;
    private DatabaseReference muserdatabase;
    private ArrayList<user> arrayList;
    private FirebaseRecyclerOptions<user> options;
    private FirebaseRecyclerAdapter<user,FirebaseViewHolder> adapter;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        muserdatabase=FirebaseDatabase.getInstance().getReference().child("Users");
        muserdatabase.keepSynced(true);
        setContentView(R.layout.activity_users);
        mtoolbar=findViewById(R.id.user_app_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        muserlist=findViewById(R.id.user_list);
        muserlist.setHasFixedSize(true);
        muserlist.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<user>();
        options=new FirebaseRecyclerOptions.Builder<user>().setQuery(muserdatabase,user.class).build();
        adapter=new FirebaseRecyclerAdapter<user, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull final user model) {

                final String uid = getRef(position).getKey();
                holder.fullname.setText(model.getFullname());
                holder.phone.setText(model.getPhone());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent chatintent = new Intent(getApplicationContext(),ChatActivity.class);
                        chatintent.putExtra("Visit_user_id",uid);
                        chatintent.putExtra("Visit_user_name",model.getFullname());


                        startActivity(chatintent);
                    }
                });


            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(UsersActivity.this).inflate(R.layout.user_single_layout,parent,false));
            }
        };



        muserlist.setAdapter(adapter);



    }

}
