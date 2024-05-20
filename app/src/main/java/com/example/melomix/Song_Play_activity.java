package com.example.melomix;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Song_Play_activity extends AppCompatActivity {

    static MediaPlayer mediaPlayer;
    ArrayList<File> mySongs;
    String songName;
    int position;

    TextView txtSng, txtSngStart, txtSngEnd;
    SeekBar songSeek;
    ImageButton btnfr, btnprev, btnplay, btnnext, btnff;
    ImageView audioTrackImg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play);

        txtSng = findViewById(R.id.song_name);
        audioTrackImg = findViewById(R.id.audio_track_img);
        txtSngStart = findViewById(R.id.starting_time);
        txtSngEnd = findViewById(R.id.end_time);

        btnfr = findViewById(R.id.fast_rewind);
        btnprev = findViewById(R.id.previous_btn);
        btnplay = findViewById(R.id.play_pause_btn);
        btnnext = findViewById(R.id.next_btn);
        btnff = findViewById(R.id.fast_forward_btn);

        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        /// getting data from intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        position = bundle.getInt("position");

        /// setting song name and create media player
        txtSng.setSelected(true);
        songName = mySongs.get(position).getName();
        Uri uri = Uri.parse(mySongs.get(position).toString());
        txtSng.setText(songName);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        /// play_pause_btn;
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    btnplay.setImageResource(R.drawable.ic_pause_btn);
                    mediaPlayer.pause();
                }else {
                    btnplay.setImageResource(R.drawable.ic_play_btn);
                    mediaPlayer.start();
                }
            }
        });

        /// next button
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();

                position = ((position + 1) % mySongs.size());
                songName = mySongs.get(position).getName();
                Uri uri = Uri.parse(mySongs.get(position).toString());

                txtSng.setText(songName);
                startAnimation(audioTrackImg, "next");
                btnplay.setImageResource(R.drawable.ic_play_btn);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
            }
        });

        /// prev button
        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();

                position = (position-1) < 0 ? mySongs.size()-1 : position - 1;
                songName = mySongs.get(position).getName();
                Uri uri = Uri.parse(mySongs.get(position).toString());

                txtSng.setText(songName);
                startAnimation(audioTrackImg, "prev");
                btnplay.setImageResource(R.drawable.ic_play_btn);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
            }
        });
    }

    public void startAnimation(View view, String direction)
    {
        ObjectAnimator animator = new ObjectAnimator();

        if(direction == "next") animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        else  animator = ObjectAnimator.ofFloat(view, "rotation", 360f, 0f);

        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }
}