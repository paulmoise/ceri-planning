package fr.ceri.prototypeinterface.ceriplanning.model;

import java.util.ArrayList;
import java.util.List;

public class Matiere {

    private String nom;

    private List<Enseignant> enseignants = new ArrayList<>();


    public Matiere(String nom, List<Enseignant> enseignants) {
        this.nom = nom;
        this.enseignants = enseignants;
    }

    public Matiere(String nom) {
        this.nom = nom;
    }

    public Matiere() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }



}
