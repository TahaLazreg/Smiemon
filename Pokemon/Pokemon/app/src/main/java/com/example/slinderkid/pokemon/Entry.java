package com.example.slinderkid.pokemon;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Création d'une activity entry qui permet d'afficher les statistiques du pokémon avant de le choisir
 */
public class Entry extends Activity {

MediaPlayer mediaPlayer = null;

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != MediaPlayer.create(getApplicationContext(), R.raw.entry)){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.entry);
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
    /**
     * Création de l'activité Entry (génération de la page de spec des pokemons)
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);


        final Pokemon pokemon = (Pokemon) getIntent().getSerializableExtra("pokemon");
        // met les valeur du pokémon dans les textView
        TextView name = (TextView) findViewById(R.id.Name);
        name.setText(pokemon.getName());

        TextView type = (TextView) findViewById(R.id.type);
        String[] temp = new String[2];
        temp[0] = pokemon.getTypes()[0];
        temp[1] = pokemon.getTypes()[1];
        type.setText(temp[0]+" "+temp[1]);

        TextView atk = (TextView) findViewById(R.id.attack);
        atk.setText(Integer.toString(pokemon.getAtk()));

        TextView def = (TextView) findViewById(R.id.defense);
        def.setText(Integer.toString(pokemon.getDef()));

        TextView spd = (TextView) findViewById(R.id.speed);
        spd.setText(Integer.toString(pokemon.getSpd()));

        TextView sAtk = (TextView) findViewById(R.id.sAttack);
        sAtk.setText(Integer.toString(pokemon.getsAtk()));

        TextView sDef = (TextView) findViewById(R.id.sDefense);
        sDef.setText(Integer.toString(pokemon.getsDef()));

        TextView hp = (TextView) findViewById(R.id.hp);
        hp.setText(Integer.toString(pokemon.getMaxHp()));

        TextView id = (TextView) findViewById(R.id.id);
        id.setText(Integer.toString(pokemon.getId()));

        final Button back = (Button) findViewById(R.id.button9);

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Resources resources = this.getResources();
        final int resourceId = resources.getIdentifier("front"+pokemon.getId(), "drawable",
                this.getPackageName());
        pokemon.setFrontImage(resourceId);
        image.setImageResource(resourceId);

        final int resourceId2 = resources.getIdentifier("back"+pokemon.getId(), "drawable",
                this.getPackageName());
        pokemon.setBackImage(resourceId2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mediaPlayer.stop();
                finish();
            }
        });

        Button select = (Button) findViewById(R.id.button10);

        // Envoye les données du pokemon si tu selectionne ce pokemon
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("pokemon", pokemon);
                //mediaPlayer.stop();
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
}
