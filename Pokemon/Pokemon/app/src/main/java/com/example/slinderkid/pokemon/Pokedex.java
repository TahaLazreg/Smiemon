package com.example.slinderkid.pokemon;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Création de la classe Pokedex qui gere le pokedex et la lecture de la DB
 */
public class Pokedex extends Activity {
    MediaPlayer mediaPlayer = null;

    ArrayList<PokeEntry> entry = new ArrayList<PokeEntry>();
    Pokemon pokemon = new Pokemon("",0,0,0,0,0,0,0,0,null, null, null, null, "0", "0", 0);
    Pokemon[] chosenPoke = new Pokemon[3];
    int selectedPokemon;
    Pokemon[] ennemy = new Pokemon[3];

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != MediaPlayer.create(getApplicationContext(), R.raw.pokedex)){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pokedex);
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
     * Lors de la création de la classe, on fait la lecture de la DB pour sélection 3 pokemons
     * ennemis, initiallizer le listView et son OnItemClicked pour qu'il aille chercher les infos.
     * du pokémon à afficher.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokedex);



        setTitle("Pokemon Selection");
        Toast.makeText(this, "Please choose your 3 pokemon", Toast.LENGTH_LONG).show();
        // Création de la base de données
        final SQLiteDatabase db = openOrCreateDatabase("info_pokemon",MODE_PRIVATE,null);
        BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.database_pokemon)));
        String line = null;

        try {
            line = reader.readLine();

            String sql="";
            while (line != null){
                if(!line.isEmpty()) {
                    sql+=line;

                    if(sql.charAt(sql.length()-1) == ';'){
                        db.execSQL(sql);
                        sql = "";
                    }
                }
                line = reader.readLine();
            }
        }catch(IOException e){
            Log.e("TEST_DB", e.getMessage());
        }

        // Association image par rapport au pokemon
        Cursor c;
        int i = 1;
        c = db.rawQuery("SELECT nom FROM pokemon", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                Resources resources = this.getResources();
                final int resourceId = resources.getIdentifier("front"+i, "drawable",
                        this.getPackageName());
                entry.add(new PokeEntry(resourceId,i,c.getString(0)));
                i++;
                c.moveToNext();
            }
        }

        // Selectionne pokemon ennemi + association de ces stats
        Resources resources = this.getResources();
        Random rand = new Random();
        int id;
        for (int g = 0; g<3; g++){
            ennemy[g] = new Pokemon("",0,0,0,0,0,0,0,0,null, null, null, null, "0", "0", 0);
            id = rand.nextInt(151)+1;

            c = db.rawQuery("SELECT nom FROM pokemon WHERE pokemon.id="+id+"",null);
            if (c.moveToFirst()) ennemy[g].setName(c.getString(0));

            c = db.rawQuery("SELECT MaxHp FROM pokemon WHERE pokemon.id="+id+"",null);
            if (c.moveToFirst()) {
                ennemy[g].setMaxHp(c.getShort(0));
                ennemy[g].setCurrentHp(c.getShort(0));
            }

            c = db.rawQuery("SELECT Attack FROM pokemon WHERE pokemon.id="+id+"",null);
            if (c.moveToFirst()) ennemy[g].setAtk(c.getShort(0));

            ennemy[g].setId((int) id);

            c = db.rawQuery("SELECT Def FROM pokemon WHERE pokemon.id="+id+"",null);
            if (c.moveToFirst())ennemy[g].setDef(c.getShort(0));

            c = db.rawQuery("SELECT SpAttack FROM pokemon WHERE pokemon.id="+id+"",null);
            if (c.moveToFirst()) ennemy[g].setsAtk(c.getShort(0));

            c = db.rawQuery("SELECT types.type FROM pokemon LEFT JOIN pokemon_types ON pokemon_types.nom=pokemon.id LEFT OUTER JOIN types ON types.id=pokemon_types.type WHERE pokemon.id="+id+"",null);

            int h = 0;
            String tempTableau[] = new String[2];
            if (c.moveToFirst()) {
                while ( !c.isAfterLast() ) {
                    tempTableau[h] = c.getString(0);
                    c.moveToNext();
                    h++;
                }
            }

            ennemy[g].setTypes(tempTableau[0], tempTableau[1]);

            c = db.rawQuery("SELECT SpDef FROM pokemon WHERE pokemon.id="+id+"",null);
            if (c.moveToFirst()) ennemy[g].setsDef(c.getShort(0));


            c = db.rawQuery("SELECT Speed FROM pokemon WHERE pokemon.id="+id+"",null);
            if (c.moveToFirst()) ennemy[g].setSpd(c.getShort(0));

            int resourceId = resources.getIdentifier("front"+ennemy[g].getId(), "drawable",
                    this.getPackageName());
            ennemy[g].setFrontImage(resourceId);

            int resourceId2 = resources.getIdentifier("back"+ennemy[g].getId(), "drawable",
                    this.getPackageName());
            ennemy[g].setBackImage(resourceId2);

            ArrayList<Move> possibleMoves = new ArrayList<Move>();
            Cursor c1, c2;
            int idType = 0;

            // Selectionne toute les attaques que le pokemon a droit
            c = db.rawQuery("SELECT pokemon_types.type FROM pokemon_types LEFT JOIN pokemon ON pokemon.id = pokemon_types.nom WHERE pokemon.nom = " + '\"' + ennemy[g].getName() + '\"', null);
            if (c.moveToFirst()){
                while (!(c.isAfterLast())){
                    // selectionne les stats pour une attaque
                    idType = Integer.parseInt(c.getString(0));
                    c2= db.rawQuery("SELECT moves.move FROM moves WHERE moves.type="+idType, null);
                    if(c2.moveToFirst()) {
                        while(!c2.isAfterLast()) {
                            Move move = new Move("", 0, 0, 1, 1, 0, "0",false,0);
                            move.setName(c2.getString(0));
                            c1 = db.rawQuery("SELECT power FROM moves WHERE  moves.move =" + '\"'+ c2.getString(0)+ '\"', null);
                            if(c1.moveToFirst())  move.setpwr(Integer.parseInt(c1.getString(0)));
                            c1 = db.rawQuery("SELECT accuracy FROM moves WHERE moves.move=" + '\"'+ c2.getString(0)+ '\"', null);
                            if(c1.moveToFirst()) move.setAccuracy(Integer.parseInt(c1.getString(0)));
                            c1 = db.rawQuery("SELECT pp FROM moves WHERE moves.move=" + '\"'+ c2.getString(0)+ '\"', null);
                            if(c1.moveToFirst()) move.setPp(Integer.parseInt(c1.getString(0)));
                            c1 = db.rawQuery("SELECT types.type FROM types WHERE types.id=" + idType, null);
                            if(c1.moveToFirst()) move.setType(c1.getString(0));
                            Log.d("tototata", c1.getString(0));
                            c1 = db.rawQuery("SELECT moves.attackType FROM moves WHERE moves.move=" + '\"'+ c2.getString(0)+ '\"',null);
                            if(c1.moveToFirst()) Log.d("tototata", c1.getString(0));
                            if(c1.moveToFirst()){
                                if(c1.getInt(0) == 1){
                                    move.setAttackType(true);
                                } else{
                                    move.setAttackType(false);
                                }
                            }
                            c1.close();

                            possibleMoves.add(move);
                            c2.moveToNext();
                        }
                    }
                    c.moveToNext();
                }
            }

            c.close();

            // Randomise 4 moves pour le pokemon
            int[] result = new int[4];

            Random r = new Random();
            Move[] realMoves = new Move[4];
            realMoves[0] = new Move("", 0, 0, 1, 1, 0, "0",false,0);
            realMoves[1] = new Move("", 1, 0, 1, 1, 0, "0",false,0);
            realMoves[2] = new Move("", 2, 0, 1, 1, 0, "0",false,0);
            realMoves[3] = new Move("", 3, 0, 1, 1, 0, "0",false,0);

            ArrayList<Integer> list = new ArrayList<Integer>();

            for (int f=0; f<(possibleMoves.size()); f++) {
                list.add(new Integer(f));
            }
            Collections.shuffle(list);
            for (int f=0; f<4; f++) {
                result[f] = (list.get(f));
            }

            realMoves[0] = possibleMoves.get(result[0]);
            realMoves[1] = possibleMoves.get(result[1]);
            realMoves[2] = possibleMoves.get(result[2]);
            realMoves[3] = possibleMoves.get(result[3]);

            ennemy[g].setMoves(realMoves);
        }



        ListView listPokemon = findViewById(R.id.ListViewPokemon);
        listPokemon.setAdapter(new PokedexAdapter());

        listPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * association du pokemon avec ces stats sur reaction de listPokemon
             * @param parent                ?
             * @param view                  View
             * @param position              position du pokemon
             * @param id                    id du pokemon
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Association des stats du pokemon
                Cursor c;

                id = position+1;

                c = db.rawQuery("SELECT nom FROM pokemon WHERE pokemon.id="+id+"",null);
                if (c.moveToFirst()) pokemon.setName(c.getString(0));

                c = db.rawQuery("SELECT MaxHp FROM pokemon WHERE pokemon.id="+id+"",null);
                if (c.moveToFirst()) {
                    pokemon.setMaxHp(c.getShort(0));
                    pokemon.setCurrentHp(c.getShort(0));
                }

                c = db.rawQuery("SELECT Attack FROM pokemon WHERE pokemon.id="+id+"",null);
                if (c.moveToFirst()) pokemon.setAtk(c.getShort(0));

                pokemon.setId((int) id);

                c = db.rawQuery("SELECT Def FROM pokemon WHERE pokemon.id="+id+"",null);
                if (c.moveToFirst())pokemon.setDef(c.getShort(0));

                c = db.rawQuery("SELECT SpAttack FROM pokemon WHERE pokemon.id="+id+"",null);
                if (c.moveToFirst()) pokemon.setsAtk(c.getShort(0));

                c = db.rawQuery("SELECT types.type FROM pokemon LEFT JOIN pokemon_types ON pokemon_types.nom=pokemon.id LEFT OUTER JOIN types ON types.id=pokemon_types.type WHERE pokemon.id="+id+"",null);

                int i = 0;
                String tempTableau[] = new String[2];
                if (c.moveToFirst()) {
                    while ( !c.isAfterLast() ) {
                        tempTableau[i] = c.getString(0);
                        c.moveToNext();
                        i++;
                    }
                }

                pokemon.setTypes(tempTableau[0], tempTableau[1]);

                c = db.rawQuery("SELECT SpDef FROM pokemon WHERE pokemon.id="+id+"",null);
                if (c.moveToFirst()) pokemon.setsDef(c.getShort(0));

                c = db.rawQuery("SELECT Speed FROM pokemon WHERE pokemon.id="+id+"",null);
                if (c.moveToFirst()) pokemon.setSpd(c.getShort(0));

                // Sort tout les moves que le pokemon a droit
                ArrayList<Move> possibleMoves = new ArrayList<Move>();
                int y = 0;
                Cursor c1, c2;
                int idType = 0;

                c = db.rawQuery("SELECT pokemon_types.type FROM pokemon_types LEFT JOIN pokemon ON pokemon.id = pokemon_types.nom WHERE pokemon.nom = " + '\"' + pokemon.getName() + '\"', null);
                if (c.moveToFirst()){
                    while (!(c.isAfterLast())){
                        //association des stats pour 1 spell
                        idType = Integer.parseInt(c.getString(0));

                        c2= db.rawQuery("SELECT moves.move FROM moves WHERE moves.type="+idType, null);
                        if(c2.moveToFirst()) {
                            while(!c2.isAfterLast()) {
                                Move move = new Move("", 0, 0, 1, 1, 0, "0",false,0);

                                move.setName(c2.getString(0));
                                c1 = db.rawQuery("SELECT power FROM moves WHERE  moves.move =" + '\"'+ c2.getString(0)+ '\"', null);
                                if(c1.moveToFirst())  move.setpwr(Integer.parseInt(c1.getString(0)));
                                c1 = db.rawQuery("SELECT accuracy FROM moves WHERE moves.move=" + '\"'+ c2.getString(0)+ '\"', null);
                                if(c1.moveToFirst()) move.setAccuracy(Integer.parseInt(c1.getString(0)));
                                c1 = db.rawQuery("SELECT pp FROM moves WHERE moves.move=" + '\"'+ c2.getString(0)+ '\"', null);
                                if(c1.moveToFirst()) move.setPp(Integer.parseInt(c1.getString(0)));
                                c1 = db.rawQuery("SELECT types.type FROM types WHERE types.id=" + idType, null);
                                if(c1.moveToFirst()) move.setType(c1.getString(0));
                                c1 = db.rawQuery("SELECT moves.attackType FROM moves WHERE moves.move=" + '\"'+ c2.getString(0)+ '\"',null);
                                if(c1.moveToFirst()){
                                    if(c1.getInt(0) == 1){
                                        move.setAttackType(true);
                                    } else{
                                        move.setAttackType(false);
                                    }
                                }
                                Log.e("TESTLECTURE", ""+move.getAttackType());
                                c1.close();

                                possibleMoves.add(move);
                                c2.moveToNext();
                            }
                        }
                        c.moveToNext();
                    }
                }

                c.close();
                int[] result = new int[4];

                // Randomise pour les 4 vrai moves du pokemon
                Random r = new Random();
                Move[] realMoves = new Move[4];
                realMoves[0] = new Move("", 0, 0, 1, 1, 0, "0",false,0);
                realMoves[1] = new Move("", 1, 0, 1, 1, 0, "0",false,0);
                realMoves[2] = new Move("", 2, 0, 1, 1, 0, "0",false,0);
                realMoves[3] = new Move("", 3, 0, 1, 1, 0, "0",false,0);

                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int f=0; f<(possibleMoves.size()); f++) {
                    list.add(new Integer(f));
                }
                Collections.shuffle(list);
                for (int f=0; f<4; f++) {
                    result[f] = (list.get(f));
                }


                realMoves[0] = possibleMoves.get(result[0]);
                realMoves[1] = possibleMoves.get(result[1]);
                realMoves[2] = possibleMoves.get(result[2]);
                realMoves[3] = possibleMoves.get(result[3]);


                pokemon.setMoves(realMoves);
                startEntryActivity(pokemon);
            }
        });

    }

    /**
     * Démare l'activité entry en lui envoyant les données necessaire
     * à l'affichage des stats. du pokemon
     * @param p
     */
    private void startEntryActivity(Pokemon p) {
        Intent intent = new Intent(Pokedex.this, Entry.class);
        intent.putExtra("pokemon", p);
        startActivityForResult(intent, 1);
    }

    /**
     * Récupétaion des stats. du pokémon choisi et, après la sélection de 3 pokémons,
     * démarre la classe fight
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.e("here1", "here");

        if (resultCode == RESULT_OK) {
            chosenPoke[selectedPokemon] =(Pokemon) data.getSerializableExtra("pokemon");
            Log.e("here1", "here 2");

            selectedPokemon++;
            if (selectedPokemon >= 3){
                Intent fight = new Intent(this, Fight.class);
                fight.putExtra("Pokemon1",chosenPoke[0]);
                fight.putExtra("Pokemon2",chosenPoke[1]);
                fight.putExtra("Pokemon3",chosenPoke[2]);

                fight.putExtra("Ennemy1", ennemy[0]);

                fight.putExtra("Ennemy2", ennemy[1]);

                fight.putExtra("Ennemy3", ennemy[2]);

                //.stop();

                startActivity(fight);
            }else{
                Log.e("here1", "plz");
                Toast.makeText(this, (3-selectedPokemon)+" pokemon left to choose", Toast.LENGTH_LONG).show();
            }


        }

    }

    private class PokedexWrapper {
        private TextView name = null;
        private TextView id = null;
        private ImageView sprite = null;
        private View row = null;
        PokedexWrapper(View row) {
            this.row = row;
        }
        public TextView getName() {
            if (name == null)  name = (TextView) row.findViewById(R.id.PokeEntryName);
            return name;
        }
        public TextView getId(){
            if(id == null) id = (TextView) row.findViewById(R.id.PokeEntryId);
            return id;
        }
        public ImageView getSprite() {
            if (sprite == null)  sprite = (ImageView) row.findViewById(R.id.sprite);
            return sprite;
        }
        public void setEntry(PokeEntry entry) {
            getName().setText(entry.getname());
            getId().setText(String.valueOf(entry.getid()));
            getSprite().setImageResource(entry.getsprite());
        }
    }
    class PokedexAdapter extends ArrayAdapter<PokeEntry> {
        PokedexAdapter() {
            super(Pokedex.this, R.layout.row, R.id.PokeEntryId, entry);
        }//ListeJour est lâ€™activity principale et listeJournee mon ArrayList de Journee

        public View getView(int position, View convertView, ViewGroup parent) {
            Pokedex.PokedexWrapper wrapper;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.row, null);
                wrapper = new Pokedex.PokedexWrapper(convertView);
                convertView.setTag(wrapper);
            } else
                wrapper = (Pokedex.PokedexWrapper) convertView.getTag();

            wrapper.setEntry(getItem(position));

            return convertView;
        }
    }
}

