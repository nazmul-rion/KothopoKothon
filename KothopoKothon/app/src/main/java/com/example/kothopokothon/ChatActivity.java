package com.example.kothopokothon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    String recieveid,recievename,senderid;
    ImageButton sendmsgbtn;
    FirebaseAuth mAuth;
    EditText msginput;
    Toolbar chttoolbar;
    DatabaseReference rootRef;
    private final List<msges> msglist = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private msgAdapter msgAdapter;
    private RecyclerView usermsglist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recieveid=getIntent().getExtras().get("Visit_user_id").toString();
        recievename=getIntent().getExtras().get("Visit_user_name").toString();
        chttoolbar=findViewById(R.id.chat_toolbar);
        setSupportActionBar(chttoolbar);
        getSupportActionBar().setTitle(recievename);
        sendmsgbtn=findViewById(R.id.send_msg_btn);
        msginput=findViewById(R.id.input_msg);
        mAuth=FirebaseAuth.getInstance();
        senderid=mAuth.getCurrentUser().getUid();
        rootRef= FirebaseDatabase.getInstance().getReference();
        msgAdapter=new msgAdapter(msglist);
        usermsglist=findViewById(R.id.msg_of_user);
        linearLayoutManager=new LinearLayoutManager(this);
        usermsglist.setLayoutManager(linearLayoutManager);
        usermsglist.setAdapter(msgAdapter);



        sendmsgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmsg();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        rootRef.child("Message").child(senderid).child(recieveid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                msges msges=dataSnapshot.getValue(msges.class);
                msglist.add(msges);
                msgAdapter.notifyDataSetChanged();
                usermsglist.smoothScrollToPosition(usermsglist.getAdapter().getItemCount());
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

    private void sendmsg() {

        String msg = msginput.getText().toString();
        if(TextUtils.isEmpty(msg))
        {
            Toast.makeText(this, "Kotha bola hoi ny", Toast.LENGTH_SHORT).show();
        }
        else {

            String msgsenderref ="Message/"+senderid +"/"+ recieveid;
            String msgrecverref ="Message/"+recieveid +"/"+ senderid;

            DatabaseReference usermsgkeyRef = rootRef.child("Messages").child(senderid).child(recieveid).push();
            String msgpushId =usermsgkeyRef.getKey();

            Map msgTextBody = new HashMap();
            msgTextBody.put("message",msg);
            msgTextBody.put("type","text");
            msgTextBody.put("from",senderid);

            Map msgBodyDetails = new HashMap();
            msgBodyDetails.put(msgsenderref +"/" + msgpushId,msgTextBody);
            msgBodyDetails.put(msgrecverref +"/" + msgpushId,msgTextBody);
            rootRef.updateChildren(msgBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(ChatActivity.this, "Kotha chole geche....", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(ChatActivity.this, "Kothata gelo na :') ...", Toast.LENGTH_SHORT).show();

                    }
                    msginput.setText("");
                }
            });


        }

    }
}
