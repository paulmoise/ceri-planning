package fr.ceri.prototypeinterface.ceriplanning.model;

import java.util.ArrayList;
import java.util.List;

public class Enseignant {

    private String fullName;
    private String email;

    public Enseignant(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public Enseignant(String fullName) {
        this.fullName = fullName;
    }

    public Enseignant() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }

    private String password;

    List<Matiere> matieres = new ArrayList<>();

}
