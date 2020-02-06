package com.example.slinderkid.pokemon;

import java.io.Serializable;

/**
 * classe Pokémon sert à créer les pokémons avec leurs statistiques respectives
 */
public class Pokemon implements Serializable {
    /***********************************/
    /**** Déclaration des variables ****/
    /***********************************/
    private String name;                        // nom du pokémon
    private int sAtk;                           // niveau de spécial attaque du pokémon
    private int id;                             // id du pokémon
    private int atk;                            // niveau d'attaque du pokémon
    private int def;                            // niveau de défense du pokémon
    private int spd;                            // vitesse du pokémon
    private int maxHp;                          // vitalité maximum du pokémon
    private double currentHp;                   // vitalité acutel du pokémon
    private int frontImage;                     // id de l'image de front du pokémon
    private int backImage;                      // id de l'image de back du pokémon
    private int sDef;                           // niveau de défense spécial du pokémon
    private String[] types = new String[2];     // le (ou les) type(s) du pokémon
    Move[] moves = new Move[4];                 // les moves du pokémon


    /**
     * Création d'un pokémon
     * @param name          nom du pokémon
     * @param maxHp         Hp max du pokémon
     * @param atk           niveau d'attaque du pokémon
     * @param def           niveau de défense du pokémon
     * @param sAtk          niveau d'attaque spécial du pokémon
     * @param sDef          niveau de défense spécial du pokémon
     * @param spd           vitesse du pokémon
     * @param frontImage    id de l'image de front
     * @param backImage     id de l'image de back
     * @param m1            move 1
     * @param m2            move 2
     * @param m3            move 3
     * @param m4            move 4
     * @param type1         premier type du pokemon
     * @param type2         deuxieme type du pokemon (s'il y en a un)
     * @param id            id du pokemon
     */
    Pokemon(String name, int maxHp, int atk, int def, int sAtk, int sDef, int spd, int frontImage, int backImage, Move m1, Move m2, Move m3, Move m4, String type1, String type2, int id){
        this.sAtk = (sAtk*2);
        this.atk = (atk*2);
        this.def = (def*2);
        this.spd = (spd*2);
        this.maxHp = ((maxHp*3));
        this.frontImage = (frontImage);
        this.backImage = backImage;
        this.currentHp = (maxHp*3);
        this.sDef = sDef*2;
        this.id = id;
        this.moves[0] = m1;
        this.moves[1] = m2;
        this.moves[2] = m3;
        this.moves[3] = m4;
        this.name = (name);
        types[0] = type1;
        types[1] = type2;
    }

    private char mander, meleon, irizard; //Cause why wouldn't we do it`

    /**
     * méthode get pour le niveau de spécial attaque du pokémon
     * @return sAtk         niveau de spécial attaque
     */
    public int getsAtk() {
        return sAtk;
    }

    /**
     * méthode set pour le niveau de spécial attaque du pokémon
     * @param sAtk          niveau de spécial attaque du pokémon
     */

    public void setsAtk(int sAtk) {
        this.sAtk = sAtk*2;
    }

    /**
     * méthode get pour le niveau d'attaque du pokémon
     * @return atk          niveau d'attaque du pokémon
     */
    public int getAtk() {
        return atk;
    }

    /**
     * méthode set pour le niveau d'attaque du pokémon
     * @param atk           niveau d'attaque du pokémon
     */
    public void setAtk(int atk) {
        this.atk = atk*2;
    }

    /**
     * méthode get pour le niveau de défense du pokémon
     * @return def          niveau de défense du pokémon
     */
    public int getDef() {
        return def;
    }

    /**
     * méthode set pour le niveau de défense du pokémon
     * @param def           niveau de défense du pokémon
     */
    public void setDef(int def) {
        this.def = def*2;
    }

    /**
     * méthode get pour le niveau de speed du pokémon
     * @return spd          niveau de speed du pokémon
     */
    public int getSpd() {
        return spd;
    }

    /**
     * méthode set pour le niveau de speed du pokémon
     * @param spd           niveau de speed du pokémon
     */
    public void setSpd(int spd) {
        this.spd = spd*2;
    }

    /**
     * méthode get pour le niveau maximum de vitalité du pokémon
     * @return maxHp        niveau de vitalité maximum du pokémon
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * méthode set pour le niveau maximum de vitalité du pokémon
     * @param maxHp         niveau de vitalité maximum du pokémon
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp*3;
    }

    /**
     * méthode get pour le niveau actuel de vitalité du pokémon
     * @return currentHp    niveau de vitalité actuel du pokémon
     */
    public double getCurrentHp() {
        return currentHp;
    }

    /**
     * méthode set pour le niveau actuel de vitalité du pokémon
     * @param currentHp     niveau de vitalité actuel du pokémon
     */
    public void setCurrentHp(double currentHp) {
        this.currentHp = currentHp*3;
    }

    /**
     * méthode get pour l'image de front du pokémon
     * @return frontImage   id de l'image de front
     */
    public int getFrontImage() {
        return frontImage;
    }

    /**
     * méthode set pour l'image de front du pokémon
     * @param frontImage    id de l'image de front
     */
    public void setFrontImage(int frontImage) {
        this.frontImage = frontImage;
    }

    /**
     * méthode get pour l'image de back du pokémon
     * @return backImage   id de l'image de back
     */
    public int getBackImage() {
        return backImage;
    }

    /**
     * méthode set pour l'image de back du pokémon
     * @param backImage     id de l'image de back
     */
    public void setBackImage(int backImage) {
        this.backImage = backImage;
    }

    /**
     * méthode servant à mettre à jour le niveau de vitalité d'un pokémon
     * @param dmg           nombre de dégat que le pokémon a subit
     * @return              le pourcentage de vitalité que le pokémon lui reste
     */
    int takenDmg(double dmg){
        currentHp= currentHp - dmg;
        Double percent = (currentHp/maxHp)*100;
        return percent.intValue();
    }

    /**
     * méthode get pour le nom de pokémon
     * @return name         nom du pokémon
     */
    public String getName() {
        return name;
    }

    /**
     * méthode set pour le nom du pokémon
     * @param name          nom du pokémon
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * méthode get pour le (ou les) type(s) du pokémon
     * @return              le nom des types
     */
    public String[] getTypes() {
        return types;
    }

    /**
     * méthode set pour le (ou les) type(s) du pokémon
     * @param type          le nom du type 1
     * @param type2         le nom du type 2 (si null = "")
     */
    public void setTypes(String type, String type2) {
        this.types[0] = type;
        if (type2 == null){
            types[1] = "";
        } else {
            types[1] = type2;
        }

    }

    /**
     * méthode get pour les moves du pokémon (attaques)
     * @return moves        les moves du pokémon
     */
    public Move[] getMoves() {
        return moves;
    }

    /**
     * méthode set pour les moves du pokémon (attaques)
     * @param moves         les moves du pokémon
     */
    public void setMoves(Move[] moves) {
        this.moves = moves;
    }

    /**
     * méthode get pour le niveau de défense spécial du pokémon
     * @return sDef         niveau de défense spécial du pokémon
     */
    public int getsDef() {
        return sDef;
    }

    /**
     * méthode set pour le niveau de défense spécial du pokémon
     * @param sDef          niveau de défense spécial du pokémon
     */
    public void setsDef(int sDef) {
        this.sDef = sDef*2;
    }


    /**
     * méthode get pour l'id du pokémon
     * @return id           id du pokémon
     */
    public int getId() {
        return id;
    }

    /**
     * méthode set pour l'id du pokémon
     * @param id            id du pokémon
     */
    public void setId(int id) {
        this.id = id;
    }
}