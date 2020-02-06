package com.example.slinderkid.pokemon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Credit extends AppCompatActivity {

    MediaPlayer mediaPlayer = null;

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != MediaPlayer.create(getApplicationContext(), R.raw.credit)){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.credit);
        }
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayer.stop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        setTitle("Credit");
    }

    public void MenuPrincipal(View view) {
        Intent intent = new Intent(Credit.this,SMIemon.class);
        startActivity(intent);
    }
}
