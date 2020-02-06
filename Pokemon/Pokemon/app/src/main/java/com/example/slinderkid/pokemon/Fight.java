package com.example.slinderkid.pokemon;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Création d'une classe Fight qu gère le combat de pokemons
 */
public class Fight extends AppCompatActivity {
    MediaPlayer mediaPlayer = null;

    private ObjectAnimator progressAnimator;
    private Pokemon[] ally = new Pokemon[3];
    private Pokemon[] ennemy = new Pokemon[3];
    private int allyFighter = 0;
    private int ennemyFighter = 0;

    private int turn = 0;
    private int percentage = 100, percentage2 = 100;

    boolean test = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != MediaPlayer.create(getApplicationContext(), R.raw.battle)){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.battle);
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
     * création des méthodes pour le combat de pokémon et du lien entre les attaques du pokémon et
     * les boutons du fichier xml associé
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight_activity);


        setTitle("Battle Arena");
        // Initialise les pokemon
        ally[0] = (Pokemon) getIntent().getSerializableExtra("Pokemon1");
        ally[1] = (Pokemon) getIntent().getSerializableExtra("Pokemon2");
        ally[2] = (Pokemon) getIntent().getSerializableExtra("Pokemon3");

        ennemy[0] = (Pokemon) getIntent().getSerializableExtra("Ennemy1");
        ennemy[1] = (Pokemon) getIntent().getSerializableExtra("Ennemy2");
        ennemy[2] = (Pokemon) getIntent().getSerializableExtra("Ennemy3");

        // Visuel
        ImageView allyPokemon = (ImageView) findViewById(R.id.Poke1);
        allyPokemon.setImageResource(ally[allyFighter].getBackImage());

        ImageView ennemyPokemon = (ImageView) findViewById(R.id.Poke2);
        ennemyPokemon.setImageResource(ennemy[ennemyFighter].getFrontImage());

        TextView allyName = (TextView) findViewById(R.id.Name1);
        allyName.setText(ally[allyFighter].getName());

        TextView ennemyName = (TextView) findViewById(R.id.Name2);
        ennemyName.setText(ennemy[ennemyFighter].getName());

        TextView hpPercent = (TextView) findViewById(R.id.textView10);
        hpPercent.setText(Math.round(ally[allyFighter].getCurrentHp())+"/"+ally[allyFighter].getMaxHp());

        final Button bouton = (Button) findViewById(R.id.button);
        bouton.setText(ally[allyFighter].getMoves()[0].getName()+"\n"+ally[allyFighter].getMoves()[0].getType());
        final Button bouton2 = (Button) findViewById(R.id.button2);
        bouton2.setText(ally[allyFighter].getMoves()[1].getName()+"\n"+ally[allyFighter].getMoves()[1].getType());
        final Button bouton3 = (Button) findViewById(R.id.button3);
        bouton3.setText(ally[allyFighter].getMoves()[2].getName()+"\n"+ally[allyFighter].getMoves()[2].getType());
        final Button bouton4 = (Button) findViewById(R.id.button4);
        bouton4.setText(ally[allyFighter].getMoves()[3].getName()+"\n"+ally[allyFighter].getMoves()[3].getType());

        // Barre de vitalité
        final ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.HpBar2);
        progressBar2.setProgress(percentage2);
        progressBar2.getProgressDrawable().setColorFilter(
                Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.HpBar1);
        progressBar.setProgress(percentage);
        progressBar.getProgressDrawable().setColorFilter(
                Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);

        // gestion evenement bouton d'attaque
        bouton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ally[allyFighter].getSpd() >= ennemy[ennemyFighter].getSpd()){
                    allyTurn(v, 0, true);
                } else{
                    opponentTurn(v,0, true);
                }

            }

        });
        bouton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ally[allyFighter].getSpd() >= ennemy[ennemyFighter].getSpd()){
                    allyTurn(v, 1, true);
                } else{
                    opponentTurn(v, 1, true);
                }
            }

        });
        bouton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ally[allyFighter].getSpd() >= ennemy[ennemyFighter].getSpd()){
                    allyTurn(v, 2, true);
                } else{
                    opponentTurn(v,2, true);
                }
            }

        });
        bouton4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ally[allyFighter].getSpd() >= ennemy[ennemyFighter].getSpd()){
                    allyTurn(v, 3, true);
                } else{
                    opponentTurn(v, 3,true);
                }
            }

        });
    }

    /**
     * fait les animation et les calculs du tour allié
     * @param v                 view
     * @param i                 index du moves
     * @param ennemyTurn        flag tourne ennemi
     */
    void allyTurn(final View v, final int i, final boolean ennemyTurn) {

        int percentageLost2 = ennemy[ennemyFighter].takenDmg(ally[allyFighter].moves[i].dealtDmg(ally[allyFighter], ennemy[ennemyFighter])[0]);
        if (percentageLost2 < 0){
            percentageLost2 = 0;
        }

        // Visuel
        TextView textBattle = (TextView) findViewById(R.id.textTurn);
        textBattle.setText(ally[allyFighter].getName() + " used " + ally[allyFighter].moves[i].getName());
        Log.e("Effectivness modif 1", ""+(ally[allyFighter].moves[i].dealtDmg(ally[allyFighter], ennemy[ennemyFighter])[1]));
        if ((ally[allyFighter].moves[i].dealtDmg(ally[allyFighter], ennemy[ennemyFighter])[1] > 1.2)){
            textBattle.setText(ally[allyFighter].getName() + " used " + ally[allyFighter].moves[i].getName()+"\n\nIt's super effective!");
        }else if((ally[allyFighter].moves[i].dealtDmg(ally[allyFighter], ennemy[ennemyFighter])[1] < 0.9)){
            textBattle.setText(ally[allyFighter].getName() + " used " + ally[allyFighter].moves[i].getName()+"\n\nIt's not very effective...");
        }
        if (percentageLost2 == percentage){
            textBattle.setText(ally[allyFighter].getName() + " used " + ally[allyFighter].moves[i].getName()+" and missed");
        }

        progressAnimator = ObjectAnimator.ofInt(findViewById(R.id.HpBar2), "progress", percentage, percentageLost2);
        progressAnimator.setDuration(3000);

        percentage = percentageLost2;

        progressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            /**
             * Au début de l'animation, changer pour montrer le texte !
             */
            public void onAnimationStart(Animator animation) {
                SwitchLayout(v);
            }

            /**
             * switch le layout quand l'animation est finie
             * @param animation pas utiliser !
             */
            @Override
            public void onAnimationEnd(Animator animation) {
                SwitchLayout(v);
                if (testDed(v)== false){
                    if (ennemyTurn == true) opponentTurn(v,i, false);
                }


            }

            /**
             * PAS UTILISER !
             * @param animation
             */
            @Override
            public void onAnimationCancel(Animator animation) {

            }

            /**
             * PAS UTILISER !
             * @param animation
             */
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        progressAnimator.start();

    }

    /**
     * fait les animation lors du tour ennemi
     * @param view              view
     * @param i                 index du move
     * @param allyTurnT         flag tour allié
     */
    void opponentTurn(final View view, final int i, final boolean allyTurnT) {

        Random rand = new Random();
        int n = rand.nextInt(3);

        int percentageLost1 = ally[allyFighter].takenDmg(ennemy[ennemyFighter].moves[n].dealtDmg(ennemy[ennemyFighter], ally[allyFighter])[0]);

        if (percentageLost1 < 0) percentageLost1 = 0;

        // visuel
        TextView textBattle = (TextView) findViewById(R.id.textTurn);

        textBattle.setText("Ennemy "+ ennemy[ennemyFighter].getName() + " used " + ennemy[ennemyFighter].moves[n].getName());

        Log.e("Effectivness modif 2", ""+ennemy[ennemyFighter].moves[n].dealtDmg(ennemy[ennemyFighter], ally[allyFighter])[1]);
        if (ennemy[ennemyFighter].moves[n].dealtDmg(ennemy[ennemyFighter], ally[allyFighter])[1] > 1.2){
            textBattle.setText("Ennemy "+ ennemy[ennemyFighter].getName() + " used " + ennemy[ennemyFighter].moves[n].getName()+"\n\nIt's super effective!");
        } else if (ennemy[ennemyFighter].moves[n].dealtDmg(ennemy[ennemyFighter], ally[allyFighter])[1] < 0.9){
            textBattle.setText("Ennemy "+ ennemy[ennemyFighter].getName() + " used " + ennemy[ennemyFighter].moves[n].getName()+"\n\nIt's not very effective...");
        }
        if (percentageLost1 == percentage2)   textBattle.setText("Ennemy "+ ennemy[ennemyFighter].getName() + " used " + ennemy[ennemyFighter].moves[n].getName()+" and missed");

        progressAnimator = ObjectAnimator.ofInt(findViewById(R.id.HpBar1), "progress", percentage2, percentageLost1);
        progressAnimator.setDuration(3000);

        percentage2= percentageLost1;

        progressAnimator.addListener(new Animator.AnimatorListener() {
            /**
             * switch le layout au début de l'annimation pour afficher le texte
             * @param animation
             */
            @Override
            public void onAnimationStart(Animator animation) {
                SwitchLayout(view);
            }

            /**
             * actualise la vie et regarde si le pokemon est mort avant de réafficher les boutons
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animator animation) {
                TextView hpPercent = (TextView) findViewById(R.id.textView10);
                int x = (int) (Math.round(ally[allyFighter].getCurrentHp()));
                if (x < 0) x=0;
                hpPercent.setText(x+"/"+ally[allyFighter].getMaxHp());
                SwitchLayout(view);
                if (testDed(view)== false) {
                    if (allyTurnT == true) allyTurn(view, i, false);
                }
            }

            /**
             * PAS UTILISER !
             * @param animation
             */
            @Override
            public void onAnimationCancel(Animator animation) {

            }

            /**
             * PAS UTILISER !
             * @param animation
             */
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        progressAnimator.start();



        turn++;
    }

    /**
     * méthode qui regarde si le pokémon est mort
     * @param v             view
     * @return              flag disant si oui ou non le pokémon est mort
     */
    private boolean testDed(final View v) {
        Log.e("Ally HP", ""+ally[allyFighter].getCurrentHp());
        Log.e("Ennemy HP", ""+ennemy[ennemyFighter].getCurrentHp());
        if (ally[allyFighter].getCurrentHp() <= 0){

            TextView textBattle = (TextView) findViewById(R.id.textTurn);

            allyFighter++;
            if (!(allyFighter < 3)){
                textBattle.setText(ally[allyFighter-1].getName()+" has fainted.\n\n"+"Ennemy "+ ennemy[ennemyFighter].getName() + " has won!");
                SwitchLayout(v);
                mediaPlayer.stop();
                mediaPlayer = mediaPlayer.create(getApplicationContext(), R.raw.v);
                mediaPlayer.start();
                new CountDownTimer(3000, 1) {

                    /**
                     * PAS UTILISER!
                     * @param millisUntilFinished
                     */
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    /**
                     * switch le layout a la fin du temps écoulé (après que l'utilisatque ait pu li-
                     * re le message).
                     */
                    @Override
                    public void onFinish() {
                        SwitchLayout(v);
                        Intent i = new Intent(Fight.this, SMIemon.class);
                        //.stop();
                        startActivity(i);
                    }
                }.start();
                allyFighter--;
            } else {
                textBattle.setText(ally[(allyFighter-1)].getName()+" has fainted.\n\n" + ally[allyFighter].getName()+ " go!");
                percentage2 = 100;

                // Visuel
                final Button bouton = (Button) findViewById(R.id.button);
                bouton.setText(ally[allyFighter].getMoves()[0].getName()+"\n"+ally[allyFighter].getMoves()[0].getType());
                final Button bouton2 = (Button) findViewById(R.id.button2);
                bouton2.setText(ally[allyFighter].getMoves()[1].getName()+"\n"+ally[allyFighter].getMoves()[1].getType());
                final Button bouton3 = (Button) findViewById(R.id.button3);
                bouton3.setText(ally[allyFighter].getMoves()[2].getName()+"\n"+ally[allyFighter].getMoves()[2].getType());
                final Button bouton4 = (Button) findViewById(R.id.button4);
                bouton4.setText(ally[allyFighter].getMoves()[3].getName()+"\n"+ally[allyFighter].getMoves()[3].getType());

                TextView hpPercent = (TextView) findViewById(R.id.textView10);
                hpPercent.setText(Math.round(ally[allyFighter].getCurrentHp())+"/"+ally[allyFighter].getMaxHp());

                ImageView allyPokemon = (ImageView) findViewById(R.id.Poke1);
                allyPokemon.setImageResource(ally[allyFighter].getBackImage());

                TextView allyName = (TextView) findViewById(R.id.Name1);
                allyName.setText(ally[allyFighter].getName());

                final ProgressBar progressBar = (ProgressBar) findViewById(R.id.HpBar1);
                progressBar.setProgress(percentage2);
                progressBar.getProgressDrawable().setColorFilter(
                        Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);

                SwitchLayout(v);
                new CountDownTimer(3000, 1) {

                    /**
                     * PAS UTILISER !
                     * @param millisUntilFinished
                     */
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    /**
                     * switch le layout a la fin du temps (après la lecture du texte par
                     * l'utilisateur).
                     */
                    @Override
                    public void onFinish() {
                        SwitchLayout(v);
                    }
                }.start();
            }


            return true;
        } else if (ennemy[ennemyFighter].getCurrentHp()<=0){

            TextView textBattle = (TextView) findViewById(R.id.textTurn);

            ennemyFighter++;
            if (!(ennemyFighter < 3)){
                textBattle.setText(ennemy[ennemyFighter-1].getName()+" has fainted.\n\n"+ally[allyFighter].getName() + " has won!");
                SwitchLayout(v);
                mediaPlayer.stop();
                mediaPlayer = mediaPlayer.create(getApplicationContext(), R.raw.v);
                mediaPlayer.start();
                new CountDownTimer(3000, 1) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        SwitchLayout(v);
                        Intent i = new Intent(Fight.this, SMIemon.class);
                        //.stop();
                        startActivity(i);
                    }
                }.start();
                ennemyFighter--;

            } else {
                textBattle.setText(ennemy[(ennemyFighter-1)].getName()+" has fainted.\n\n" + "Ennemy "+ennemy[ennemyFighter].getName()+ " has entered the battlefield.");
                percentage = 100;

                ImageView ennemyPokemon = (ImageView) findViewById(R.id.Poke2);
                ennemyPokemon.setImageResource(ennemy[ennemyFighter].getFrontImage());

                TextView ennemyName = (TextView) findViewById(R.id.Name2);
                ennemyName.setText(ennemy[ennemyFighter].getName());

                final ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.HpBar2);
                progressBar2.setProgress(percentage);
                progressBar2.getProgressDrawable().setColorFilter(
                        Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);


                SwitchLayout(v);
                new CountDownTimer(3000, 1) {
                    /**
                     * PAS UTILISER !
                     * @param millisUntilFinished
                     */
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    /**
                     * switch le layout à la fin du temps requis pour lire le texte
                     */
                    @Override
                    public void onFinish() {
                        SwitchLayout(v);
                    }
                }.start();
            }
            return true;
        }else{
            return false;
        }

    }

    /**
     * si cetait du text, switch pour les boutons de dmg et vise-versa
     * @param view          View
     */
    public void SwitchLayout(View view) {
        View v = getLayoutInflater().inflate(R.layout.box_text, null);
        LinearLayout text = (LinearLayout) findViewById(R.id.ecriture);
        LinearLayout attack = (LinearLayout) findViewById(R.id.movebox);
        if(attack.getVisibility() == View.VISIBLE)
        {
            attack.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
        }else
        {
            text.setVisibility(View.GONE);
            attack.setVisibility(View.VISIBLE);
        }


    }
}





