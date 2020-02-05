package com.example.kothopokothon;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView fullname,phone;

    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);
        fullname=itemView.findViewById(R.id.user_name);
        phone=itemView.findViewById(R.id.user_phone);
    }
}
