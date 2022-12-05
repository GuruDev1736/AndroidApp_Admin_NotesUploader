package com.guruprasad.notesuplaoder;

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

public class myadapter extends FirebaseRecyclerAdapter<filemodel,myadapter.myviewholder> {

    public myadapter(@NonNull FirebaseRecyclerOptions<filemodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull filemodel model) {
                holder.header.setText(model.getFiletitle());
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setType("application/pdf");
                        intent.setData(Uri.parse(model.getFileurl()));
                        holder.imageView.getContext().startActivity(intent);

                    }
                });

         holder.card.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_view_anime_2));

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowitem,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
            TextView header ;
            ImageView imageView ;
              CardView card ;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            header = itemView.findViewById(R.id.header);
            imageView = itemView. findViewById(R.id.img1);
            card = itemView.findViewById(R.id.cardview);

        }
    }
}
