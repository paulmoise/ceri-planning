package fr.ceri.prototypeinterface.ceriplanning.model;

public class Etudiant {

    private String fullName;
    private String email;
    private String password;
    private String numeroEtudiant;

    private Formation formation;

    public Etudiant(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public Etudiant() {
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

    public String getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public void setNumeroEtudiant(String numeroEtudiant) {
        this.numeroEtudiant = numeroEtudiant;
    }


    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }


}
