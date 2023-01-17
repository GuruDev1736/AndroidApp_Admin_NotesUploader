package com.guruprasad.notesuplaoder;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                        Uri path = Uri.parse(model.getFileurl());
                        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                        pdfIntent.setDataAndType(path, "application/pdf");
                        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        try {
                            holder.imageView.getContext().startActivity(pdfIntent);
                            Toast.makeText(view.getContext(), "Please Wait..... File is Loading ", Toast.LENGTH_LONG).show();
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(view.getContext(), "No Application available to viewPDF", Toast.LENGTH_SHORT).show();
                        }

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
