package com.guruprasad.notesuplaoder.adapter;

import static com.guruprasad.notesuplaoder.Constants.info_toast;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.model.MusicModel;

import java.io.IOException;

public class PomodoroMusicAdapter extends FirebaseRecyclerAdapter<MusicModel,PomodoroMusicAdapter.onviewholder> {

    public PomodoroMusicAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }



    @NonNull
    @Override
    public onviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showmusiclayout,parent,false);
        return new onviewholder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull onviewholder holder, int position, @NonNull MusicModel model) {

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {

            ProgressDialog pd = new ProgressDialog(holder.itemView.getContext());
            pd.setTitle("Preparing Music");
            pd.setMessage("Please Wait ...");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();


            mediaPlayer.setDataSource(model.getUrl());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    pd.dismiss();
                    info_toast(holder.itemView.getContext(),"Buffering Finish");
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        holder.name.setText(model.getName());

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mediaPlayer.isPlaying())
                {
                    mediaPlayer.start();
                    holder.play.setImageResource(R.drawable.baseline_pause_24);
                }
                else
                {
                    mediaPlayer.pause();
                    holder.play.setImageResource(R.drawable.baseline_play_arrow_24);
                }
            }
        });




    }

    public class onviewholder extends RecyclerView.ViewHolder {

        TextView name;
        RelativeLayout rl ;
        ImageButton play ;
        public onviewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvMusicName);
            rl = itemView.findViewById(R.id.rlMusic);
            play = itemView.findViewById(R.id.ibtnPlay);

        }
    }


}
