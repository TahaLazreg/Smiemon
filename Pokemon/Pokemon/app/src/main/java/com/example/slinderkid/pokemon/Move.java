package com.example.slinderkid.pokemon;

import android.util.Log;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Classe servant a créer les pokémons et leur donner leurs statistiques
 */

public class Move implements Serializable {
    /***********************************/
    /**** Déclaration des variables ****/
    /***********************************/
    private Hashtable<String, Double> faiblesse;        // faiblesse et resistance des attaques
    private int accuracy;                               // chance de hit avec l'attaque
    private int pwr;                                    // dommage de l'attaque
    private int minHit;                                 // nombre de hit minimum de l'attaque pour 1 coup
    private int maxHit;                                 // nombre de hit maximum de l'attaque pour 1 coup
    private int effect;                                 // id de l'effet de l'attaque
    private String type;                                // type de l'attaque
    private boolean attackType;                         //
    private int pp;                                     // nombre de fois que l'attaque peut etre fait
    private String name;                                // nom de l'attaque

    /**
     * Constructeur de l'attaque
     * @param name                  nom de l'attaque
     * @param accuracy              chance de hit avec l'attaque
     * @param pwr                   dommage de l'attaque
     * @param maxHit                nombre de hit maximum de l'attaque pour 1 coup
     * @param minHit                nombre de hit minimum de l'attaque pour 1 coup
     * @param effect                id de l'effet de l'attaque
     * @param type                  type de l'attaque
     * @param attackType
     * @param pp                    nombre de fois que l'attaque peut etre fait
     */
    Move(String name, int accuracy, int pwr, int maxHit, int minHit, int effect, String type, boolean attackType, int pp){
        this.accuracy = accuracy;
        this.pwr = pwr;
        this.maxHit = maxHit;
        this.minHit = minHit;
        this.effect = effect;
        this.type = type;
        this.attackType = attackType;
        this.name = name;
        this.pp = pp;
    }


    /**
     * méthode get le type de l'attaque
     * @return type             type de l'attaque
     */
    public String getType() {
        return type;
    }

    public int getPwr() {
        return pwr;
    }

    /**
     * méthode set du type de l'attaque
     * @param type              type de l'attaque
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * méthode si
     * @return
     */
    public boolean isAttackType() {
        return attackType;
    }

    /**
     *
     * @param attackType
     */
    public void setAttackType(boolean attackType) {
        this.attackType = attackType;
    }

    public boolean getAttackType(){return this.attackType;}
    /**
     * méthode get les chances de hit de l'attaque
     * @return accuracy         les chances de hit l'attaque
     */
    public int getAccuracy() {
        return accuracy;
    }

    /**
     * méthode set les chances de hit de l'attaque
     * @param accuracy          les chances de hit l'attaque
     */
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * méthode get les dommages de l'attaque
     * @return pwr              les dommages de l'attaque
     */
    public int getpwr() {
        return pwr;
    }

    /**
     * méthode set les dommages de l'attaque
     * @param pwr               les dommages de l'attaque
     */
    public void setpwr(int pwr) {
        this.pwr = pwr;
    }

    /**
     * méthode get le nombre de hit minimal de l'attaque pour 1 tour
     * @return minHit           le nombre de hit minimal de l'attaque pour 1 tour
     */
    public int getMinHit() {
        return minHit;
    }

    /**
     * méthode set le nombre de hit minimal de l'attaque pour 1 tour
     * @param minHit            le nombre de hit minimal de l'attaque pour 1 tour
     */
    public void setMinHit(int minHit) {
        this.minHit = minHit;
    }

    /**
     * méthode get le nombre de hit maximal de l'attaque pour 1 tour
     * @return maxHit           le nombre de hit maximal de l'attaque pour 1 tour
     */
    public int getMaxHit() {
        return maxHit;
    }

    /**
     * méthode set le nombre de hit maximal de l'attaque pour 1 tour
     * @param maxHit            le nombre de hit maxima; de l'attaque pour 1 tour
     */
    public void setMaxHit(int maxHit) {
        this.maxHit = maxHit;
    }

    /**
     * méthode get l'id de l'effet de l'attaque
     * @return effect           id de l'effet de l'attaque
     */
    public int getEffect() {
        return effect;
    }

    /**
     * méthode set id de l'effet de l'attaque
     * @param effect            id de l'effet de l'attaque
     */
    public void setEffect(int effect) {
        this.effect = effect;
    }

    /**
     * méthode get nom de l'attaque
     * @return name             nom de l'attaque
     */
    public String getName() {
        return name;
    }

    /**
     * méthode set nom de l'attaque
     * @param name              nom de l'attaque
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * méthode get le nombre de hit de l'attaque
     * @return pp               le nombre de hit de l'attaque
     */
    public int getPp() {
        return pp;
    }

    /**
     * méthode set le nombre de hit de l'attaque
     * @param pp                le nombre de hit de l'attaque
     */
    public void setPp(int pp) {
        this.pp = pp;
    }

    /**
     * méthode pour faire régler le dommage selon les faiblesse et résistances des pokémons
     * @param ally          pokémon dont c'est le tour
     * @param ennemy        pokémon adverse
     * @return dmg          damage fait pas le pokémopn
     */
    double[] dealtDmg(Pokemon ally, Pokemon ennemy){
        double[] dmg = new double[2];
        surrenderToMadness();

        String faible1, faible2;

        faible1 = (ennemy.getTypes()[0]+"_"+type);
        if(ennemy.getTypes()[1] == ""){
            faible2 = "Normal_Normal";
        }else{
            faible2 = (ennemy.getTypes()[1]+"_"+type);
        }

        double modif1 = faiblesse.get(new String(faible1));
        double modif2 = faiblesse.get(new String(faible2));
        double modif = modif1*modif2;
        dmg[1] = modif;

        int random = (int)(Math.random() * 100 + 1);
        if (random <= accuracy){
            if (attackType == false){
                dmg[0] = (((22*pwr*(ally.getAtk()/(ennemy.getDef())))/50)+2)*modif;
            } else{
                dmg[0] = (((22*pwr*(ally.getsAtk()/(ennemy.getsDef())))/50)+2)*modif;
            }
        }else{
            dmg[0] = 0;
        }
        return dmg;
    }

    /**
     * méthode créant une table associative entre les faiblesse et les résistances des pokémons et
     * leurs malus
     */
    private void surrenderToMadness() {
        faiblesse = new Hashtable<String ,Double>();
        faiblesse.put("Normal_Normal",1.0);
        faiblesse.put("Normal_Fire",1.0);
        faiblesse.put("Normal_Water",1.0);
        faiblesse.put("Normal_Electric",1.0);
        faiblesse.put("Normal_Grass",1.0);
        faiblesse.put("Normal_Ice",1.0);
        faiblesse.put("Normal_Fighting",2.0);
        faiblesse.put("Normal_Poison",1.0);
        faiblesse.put("Normal_Ground",1.0);
        faiblesse.put("Normal_Flying",1.0);
        faiblesse.put("Normal_Psychic",1.0);
        faiblesse.put("Normal_Bug",1.0);
        faiblesse.put("Normal_Rock",1.0);
        faiblesse.put("Normal_Ghost",0.0);
        faiblesse.put("Normal_Dragon",1.0);
        faiblesse.put("Fire_Normal",1.0);
        faiblesse.put("Fire_Fire",0.5);
        faiblesse.put("Fire_Water",2.0);
        faiblesse.put("Fire_Electric",1.0);
        faiblesse.put("Fire_Grass",0.5);
        faiblesse.put("Fire_Ice",0.5);
        faiblesse.put("Fire_Fighting",1.0);
        faiblesse.put("Fire_Poison",1.0);
        faiblesse.put("Fire_Ground",2.0);
        faiblesse.put("Fire_Flying",1.0);
        faiblesse.put("Fire_Psychic",1.0);
        faiblesse.put("Fire_Bug",0.5);
        faiblesse.put("Fire_Rock",2.0);
        faiblesse.put("Fire_Ghost",1.0);
        faiblesse.put("Fire_Dragon",1.0);
        faiblesse.put("Water_Normal",1.0);
        faiblesse.put("Water_Fire",0.5);
        faiblesse.put("Water_Water",0.5);
        faiblesse.put("Water_Electric",2.0);
        faiblesse.put("Water_Grass",2.0);
        faiblesse.put("Water_Ice",0.5);
        faiblesse.put("Water_Fighting",1.0);
        faiblesse.put("Water_Poison",1.0);
        faiblesse.put("Water_Ground",1.0);
        faiblesse.put("Water_Flying",1.0);
        faiblesse.put("Water_Psychic",1.0);
        faiblesse.put("Water_Bug",1.0);
        faiblesse.put("Water_Rock",1.0);
        faiblesse.put("Water_Ghost",1.0);
        faiblesse.put("Water_Dragon",1.0);
        faiblesse.put("Electric_Normal",1.0);
        faiblesse.put("Electric_Fire",1.0);
        faiblesse.put("Electric_Water",1.0);
        faiblesse.put("Electric_Electric",0.5);
        faiblesse.put("Electric_Grass",1.0);
        faiblesse.put("Electric_Ice",1.0);
        faiblesse.put("Electric_Fighting",1.0);
        faiblesse.put("Electric_Poison",1.0);
        faiblesse.put("Electric_Ground",2.0);
        faiblesse.put("Electric_Flying",0.5);
        faiblesse.put("Electric_Psychic",1.0);
        faiblesse.put("Electric_Bug",1.0);
        faiblesse.put("Electric_Rock",1.0);
        faiblesse.put("Electric_Ghost",1.0);
        faiblesse.put("Electric_Dragon",1.0);
        faiblesse.put("Grass_Normal",1.0);
        faiblesse.put("Grass_Fire",2.0);
        faiblesse.put("Grass_Water",0.5);
        faiblesse.put("Grass_Electric",0.5);
        faiblesse.put("Grass_Grass",0.5);
        faiblesse.put("Grass_Ice",2.0);
        faiblesse.put("Grass_Fighting",1.0);
        faiblesse.put("Grass_Poison",2.0);
        faiblesse.put("Grass_Ground",0.5);
        faiblesse.put("Grass_Flying",2.0);
        faiblesse.put("Grass_Psychic",1.0);
        faiblesse.put("Grass_Bug",2.0);
        faiblesse.put("Grass_Rock",1.0);
        faiblesse.put("Grass_Ghost",1.0);
        faiblesse.put("Grass_Dragon",1.0);
        faiblesse.put("Ice_Normal",1.0);
        faiblesse.put("Ice_Fire",2.0);
        faiblesse.put("Ice_Water",1.0);
        faiblesse.put("Ice_Electric",1.0);
        faiblesse.put("Ice_Grass",1.0);
        faiblesse.put("Ice_Ice",0.5);
        faiblesse.put("Ice_Fighting",2.0);
        faiblesse.put("Ice_Poison",1.0);
        faiblesse.put("Ice_Ground",1.0);
        faiblesse.put("Ice_Flying",1.0);
        faiblesse.put("Ice_Psychic",1.0);
        faiblesse.put("Ice_Bug",1.0);
        faiblesse.put("Ice_Rock",2.0);
        faiblesse.put("Ice_Ghost",1.0);
        faiblesse.put("Ice_Dragon",1.0);
        faiblesse.put("Fighting_Normal",1.0);
        faiblesse.put("Fighting_Fire",1.0);
        faiblesse.put("Fighting_Water",1.0);
        faiblesse.put("Fighting_Electric",1.0);
        faiblesse.put("Fighting_Grass",1.0);
        faiblesse.put("Fighting_Ice",1.0);
        faiblesse.put("Fighting_Fighting",1.0);
        faiblesse.put("Fighting_Poison",1.0);
        faiblesse.put("Fighting_Ground",1.0);
        faiblesse.put("Fighting_Flying",2.0);
        faiblesse.put("Fighting_Psychic",2.0);
        faiblesse.put("Fighting_Bug",0.5);
        faiblesse.put("Fighting_Rock",0.5);
        faiblesse.put("Fighting_Ghost",1.0);
        faiblesse.put("Fighting_Dragon",1.0);
        faiblesse.put("Poison_Normal",1.0);
        faiblesse.put("Poison_Fire",1.0);
        faiblesse.put("Poison_Water",1.0);
        faiblesse.put("Poison_Electric",1.0);
        faiblesse.put("Poison_Grass",0.5);
        faiblesse.put("Poison_Ice",1.0);
        faiblesse.put("Poison_Fighting",0.5);
        faiblesse.put("Poison_Poison",0.5);
        faiblesse.put("Poison_Ground",2.0);
        faiblesse.put("Poison_Flying",1.0);
        faiblesse.put("Poison_Psychic",2.0);
        faiblesse.put("Poison_Bug",0.5);
        faiblesse.put("Poison_Rock",1.0);
        faiblesse.put("Poison_Ghost",1.0);
        faiblesse.put("Poison_Dragon",1.0);
        faiblesse.put("Ground_Normal",1.0);
        faiblesse.put("Ground_Fire",1.0);
        faiblesse.put("Ground_Water",2.0);
        faiblesse.put("Ground_Electric",0.0);
        faiblesse.put("Ground_Grass",2.0);
        faiblesse.put("Ground_Ice",2.0);
        faiblesse.put("Ground_Fighting",1.0);
        faiblesse.put("Ground_Poison",0.5);
        faiblesse.put("Ground_Ground",1.0);
        faiblesse.put("Ground_Flying",1.0);
        faiblesse.put("Ground_Psychic",1.0);
        faiblesse.put("Ground_Bug",1.0);
        faiblesse.put("Ground_Rock",0.5);
        faiblesse.put("Ground_Ghost",1.0);
        faiblesse.put("Ground_Dragon",1.0);
        faiblesse.put("Flying_Normal",1.0);
        faiblesse.put("Flying_Fire",1.0);
        faiblesse.put("Flying_Water",1.0);
        faiblesse.put("Flying_Electric",2.0);
        faiblesse.put("Flying_Grass",0.5);
        faiblesse.put("Flying_Ice",2.0);
        faiblesse.put("Flying_Fighting",0.5);
        faiblesse.put("Flying_Poison",1.0);
        faiblesse.put("Flying_Ground",0.0);
        faiblesse.put("Flying_Flying",1.0);
        faiblesse.put("Flying_Psychic",1.0);
        faiblesse.put("Flying_Bug",0.5);
        faiblesse.put("Flying_Rock",2.0);
        faiblesse.put("Flying_Ghost",1.0);
        faiblesse.put("Flying_Dragon",1.0);
        faiblesse.put("Psychic_Normal",1.0);
        faiblesse.put("Psychic_Fire",1.0);
        faiblesse.put("Psychic_Water",1.0);
        faiblesse.put("Psychic_Electric",1.0);
        faiblesse.put("Psychic_Grass",1.0);
        faiblesse.put("Psychic_Ice",1.0);
        faiblesse.put("Psychic_Fighting",0.5);
        faiblesse.put("Psychic_Poison",1.0);
        faiblesse.put("Psychic_Ground",1.0);
        faiblesse.put("Psychic_Flying",1.0);
        faiblesse.put("Psychic_Psychic",0.5);
        faiblesse.put("Psychic_Bug",2.0);
        faiblesse.put("Psychic_Rock",1.0);
        faiblesse.put("Psychic_Ghost",2.0);
        faiblesse.put("Psychic_Dragon",1.0);
        faiblesse.put("Bug_Normal",1.0);
        faiblesse.put("Bug_Fire",2.0);
        faiblesse.put("Bug_Water",1.0);
        faiblesse.put("Bug_Electric",1.0);
        faiblesse.put("Bug_Grass",0.5);
        faiblesse.put("Bug_Ice",1.0);
        faiblesse.put("Bug_Fighting",0.5);
        faiblesse.put("Bug_Poison",1.0);
        faiblesse.put("Bug_Ground",0.5);
        faiblesse.put("Bug_Flying",2.0);
        faiblesse.put("Bug_Psychic",1.0);
        faiblesse.put("Bug_Bug",1.0);
        faiblesse.put("Bug_Rock",2.0);
        faiblesse.put("Bug_Ghost",1.0);
        faiblesse.put("Bug_Dragon",1.0);
        faiblesse.put("Rock_Normal",0.5);
        faiblesse.put("Rock_Fire",0.5);
        faiblesse.put("Rock_Water",2.0);
        faiblesse.put("Rock_Electric",1.0);
        faiblesse.put("Rock_Grass",2.0);
        faiblesse.put("Rock_Ice",1.0);
        faiblesse.put("Rock_Fighting",2.0);
        faiblesse.put("Rock_Poison",0.5);
        faiblesse.put("Rock_Ground",2.0);
        faiblesse.put("Rock_Flying",0.5);
        faiblesse.put("Rock_Psychic",1.0);
        faiblesse.put("Rock_Bug",1.0);
        faiblesse.put("Rock_Rock",1.0);
        faiblesse.put("Rock_Ghost",1.0);
        faiblesse.put("Rock_Dragon",1.0);
        faiblesse.put("Ghost_Normal",0.0);
        faiblesse.put("Ghost_Fire",1.0);
        faiblesse.put("Ghost_Water",1.0);
        faiblesse.put("Ghost_Electric",1.0);
        faiblesse.put("Ghost_Grass",1.0);
        faiblesse.put("Ghost_Ice",1.0);
        faiblesse.put("Ghost_Fighting",0.0);
        faiblesse.put("Ghost_Poison",0.5);
        faiblesse.put("Ghost_Ground",1.0);
        faiblesse.put("Ghost_Flying",1.0);
        faiblesse.put("Ghost_Psychic",1.0);
        faiblesse.put("Ghost_Bug",0.5);
        faiblesse.put("Ghost_Rock",1.0);
        faiblesse.put("Ghost_Ghost",2.0);
        faiblesse.put("Ghost_Dragon",1.0);
        faiblesse.put("Dragon_Normal",1.0);
        faiblesse.put("Dragon_Fire",0.5);
        faiblesse.put("Dragon_Water",0.5);
        faiblesse.put("Dragon_Electric",0.5);
        faiblesse.put("Dragon_Grass",0.5);
        faiblesse.put("Dragon_Ice",2.0);
        faiblesse.put("Dragon_Fighting",1.0);
        faiblesse.put("Dragon_Poison",1.0);
        faiblesse.put("Dragon_Ground",1.0);
        faiblesse.put("Dragon_Flying",1.0);
        faiblesse.put("Dragon_Psychic",1.0);
        faiblesse.put("Dragon_Bug",1.0);
        faiblesse.put("Dragon_Rock",1.0);
        faiblesse.put("Dragon_Ghost",1.0);
        faiblesse.put("Dragon_Dragon",2.0);
    }
}
