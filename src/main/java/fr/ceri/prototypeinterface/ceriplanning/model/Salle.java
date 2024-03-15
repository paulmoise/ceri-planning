package fr.ceri.prototypeinterface.ceriplanning.model;

public class Salle {

    private String nom;


    public Salle(String nom) {
        this.nom = nom;
    }

    public Salle() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
