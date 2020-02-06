package com.example.slinderkid.pokemon;

/**
 * classe gérant l'affichage des pokémons dans le ListView de pokedex
 */
public class PokeEntry {
    public int m_sprite;        // mettre image
    public int m_id;            // id du pokemon
    public String m_name;       // nom de l'image

    /**
     * classe servant à la création des requête pour les sprites
     * @param sprite        mettre l'image
     * @param id            id du pokemon
     * @param name          nom de l'image
     */
    PokeEntry(int sprite, int id, String name) {
        m_id = id;
        m_sprite = sprite;
        m_name = name;
    }

    /**
     * méthode set pour l'id pour l'image
     * @param id            id pour l'image
     */
    public void setid(int id) {
        id = m_id;
    }

    /**
     * méthode get pour l'id pour l'image
     * @param name          id pour l'image
     */
    public void setname(String name) {
        name = name;
    }

    /**
     * méthode set pour le sprite du pokémon
     * @param sprite        mettre image
     */
    public void setsprite(int sprite) {
        sprite = m_sprite;
    }

    /**
     * méthode get pour le sprite du pokémon
     * @return m_sprite     mettre image
     */
    public int getsprite() {
        return m_sprite;
    }

    /**
     * méthode get pour l'id du pokemon
     * @return  m_id        id du pokemon
     */
    public int getid() {
        return m_id;
    }

    /**
     * méthode get pour le nom de la sprite
     * @return m_name       nom de la sprite
     */
    public String getname() {
        return m_name;
    }
}
