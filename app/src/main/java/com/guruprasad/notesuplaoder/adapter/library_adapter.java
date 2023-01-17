package com.guruprasad.notesuplaoder.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.file_model;

import java.util.ArrayList;

public class library_adapter extends FirebaseRecyclerAdapter<file_model,library_adapter.my_view_holder> implements Filterable {

    ArrayList<file_model> data;
    ArrayList<file_model> backup ;
    Context context ;


    public library_adapter(@NonNull FirebaseRecyclerOptions<file_model> options, ArrayList<file_model> data, ArrayList<file_model> backup, Context context) {
        super(options);
        this.data = data;
        this.backup = backup;
        this.context = context;
    }


    public library_adapter(@NonNull FirebaseRecyclerOptions<file_model> options ) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull my_view_holder holder, int position, @NonNull file_model model) {

        holder.header.setText(model.getFile_title());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri path = Uri.parse(model.getFile_url());
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
    public library_adapter.my_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_layout,parent,false);
        return new my_view_holder(view);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {

            ArrayList<file_model> filtered_data = new ArrayList<>() ;

            if (keyword.toString().isEmpty())
            {
                filtered_data.addAll(backup);
            }
            else
            {
                for (file_model obj : backup)
                {
                    if (obj.getFile_title().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                    {
                        filtered_data.add(obj);
                    }
                }
            }
                FilterResults results = new FilterResults();
            results.values = filtered_data;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((ArrayList<file_model>)results.values);
            notifyDataSetChanged();

        }
    };


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