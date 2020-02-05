package com.example.kothopokothon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class msgAdapter extends RecyclerView.Adapter<msgAdapter.MsgViewHolder> {

    private List<msges> usermsglist;
    FirebaseAuth mAuth;
    DatabaseReference userref;

    public msgAdapter(List<msges> usermsglist) {
        this.usermsglist = usermsglist;
    }

    public class MsgViewHolder extends RecyclerView.ViewHolder {
        public TextView sendermsg, recivermsg;
        public ImageView senderimg;

        public MsgViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermsg = itemView.findViewById(R.id.sender_msgtxt);
            recivermsg = itemView.findViewById(R.id.reciever_msgtxt);
            senderimg = itemView.findViewById(R.id.msg_propic);

        }
    }


    @NonNull
    @Override
    public MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.msg_layout, parent, false);
        mAuth = FirebaseAuth.getInstance();


        return new MsgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgViewHolder holder, int position) {

        String msgsenderid = mAuth.getCurrentUser().getUid();
        msges msges = usermsglist.get(position);
        String fromuserId = msges.getFrom();
        String frommsgtype = msges.getType();
        userref = FirebaseDatabase.getInstance().getReference().child("Users").child(fromuserId);
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (frommsgtype.equals("text")) {
            holder.sendermsg.setVisibility(View.INVISIBLE);
            holder.recivermsg.setVisibility(View.INVISIBLE);
            holder.senderimg.setVisibility(View.INVISIBLE);


            if (fromuserId.equals(msgsenderid)) {
                holder.sendermsg.setVisibility(View.VISIBLE);

                holder.sendermsg.setText(msges.getMessage());

            } else {
                holder.recivermsg.setVisibility(View.VISIBLE);
                holder.senderimg.setVisibility(View.VISIBLE);
                holder.recivermsg.setText(msges.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {


        return usermsglist.size();
    }


}
