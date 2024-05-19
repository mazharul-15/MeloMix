package com.example.melomix;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play);

        txtSng = findViewById(R.id.song_name);
        txtSngStart = findViewById(R.id.starting_time);
        txtSngEnd = findViewById(R.id.end_time);

        btnfr = findViewById(R.id.fast_rewind);
        btnnext = findViewById(R.id.next_btn);
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
    }
}