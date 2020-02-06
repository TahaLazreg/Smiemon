package com.example.slinderkid.pokemon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SMIemon extends AppCompatActivity {
    MediaPlayer mediaPlayer = null;

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != MediaPlayer.create(getApplicationContext(), R.raw.opening)){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.opening);
        }
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayer.stop();

    }

    /**
     * Création du programme
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pokemon - Battle Simulator (Gen 1 Edition)");


    }

    /**
     * ouverture du pokédex
     * @param view
     */
    public void OpenPokedex(View view) {
        Intent intent = new Intent(SMIemon.this,Pokedex.class);
        //.stop();
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.credit) {
            Intent intent = new Intent(this, Credit.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
