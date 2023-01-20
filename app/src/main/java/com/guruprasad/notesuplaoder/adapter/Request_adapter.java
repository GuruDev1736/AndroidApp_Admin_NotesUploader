package com.guruprasad.notesuplaoder.adapter;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.model.Request_model;

public class Request_adapter extends FirebaseRecyclerAdapter<Request_model,Request_adapter.viewholder> {

    public Request_adapter(@NonNull FirebaseRecyclerOptions<Request_model> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull Request_model model) {

        holder.name.setText("Name : "+model.getName());
        holder.email.setText("Email : "+model.getEmail());
        holder.cat.setText("Category : "+model.getCategory());
        holder.author.setText("Author : "+model.getAuthor());
        holder.userid.setText("User ID : "+model.getUid());
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(holder.cardView.getContext());
                alert.setCancelable(false);
                alert.setTitle("Delete");
                alert.setMessage("Are You Sure ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        database.getReference().child("Requested_Data").child(model.getKey()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.itemView.getContext(), "Request Has Been Deleted", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();

                return true;
            }
        });


    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_layout,parent,false);
        return new Request_adapter.viewholder(view);
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView name , email , author , cat , userid ;
        CardView cardView ;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.request_name);
            email = itemView.findViewById(R.id.request_email);
            author = itemView.findViewById(R.id.request_author);
            cat = itemView.findViewById(R.id.request_category);
            cardView = itemView.findViewById(R.id.cardview);
            userid = itemView.findViewById(R.id.user_id);
        }
    }
}
