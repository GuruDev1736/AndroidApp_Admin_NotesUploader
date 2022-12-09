package com.guruprasad.notesuplaoder.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.file_model;

public class lab_manual_adapter extends FirebaseRecyclerAdapter<file_model,lab_manual_adapter.my_view_holder> {


    public lab_manual_adapter(@NonNull FirebaseRecyclerOptions<file_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull my_view_holder holder, int position, @NonNull file_model model) {

        holder.header.setText(model.getFile_title());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(model.getFile_url()));
                holder.imageView.getContext().startActivity(intent);

            }
        });

        holder.card.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_view_anime_2));


    }

    @NonNull
    @Override
    public lab_manual_adapter.my_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowitem,parent,false);
        return new my_view_holder(view);
    }



    public class my_view_holder extends RecyclerView.ViewHolder
    {
        TextView header ;
        ImageView imageView ;
        CardView card ;


        public my_view_holder(@NonNull View itemView) {
            super(itemView);

            header = itemView.findViewById(R.id.header);
            imageView = itemView. findViewById(R.id.img1);
            card = itemView.findViewById(R.id.cardview);

        }
    }
}