package com.guruprasad.notesuplaoder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.guruprasad.notesuplaoder.R;

import java.util.List;

public class uploadAdpter extends RecyclerView.Adapter<uploadAdpter.myviewholder> {

    List<String> files , status ;

    public uploadAdpter(List<String> files, List<String> status) {
        this.files = files;
        this.status = status;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design,parent,false);
        return new myviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        String filename = files.get(position);
        if (filename.length()>15)
        {
            filename = filename.substring(0,15)+"....";

            holder.filename.setText(filename);

            String fileststatus= status.get(position);
            if (fileststatus.equals("loading"))
            {
                holder.pbar.setImageResource(R.drawable.progress);
            }
            if (fileststatus.equals("done"))
            {
                holder.pbar.setImageResource(R.drawable.check);

            }
        }



    }

    @Override
    public int getItemCount() {
        return files.size();
    }






    public  class myviewholder extends RecyclerView.ViewHolder
    {

        ImageView fileicon  , pbar ;
        TextView filename ;
        CardView cardView ;


        public myviewholder(@NonNull View itemView) {
                super(itemView);

                filename = itemView.findViewById(R.id.filename);
                fileicon = itemView.findViewById(R.id.fileicon);
                pbar = itemView.findViewById(R.id.pbar);



        }
    }

}
