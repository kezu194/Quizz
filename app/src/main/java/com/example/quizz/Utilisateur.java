package com.example.quizz;

import java.util.ArrayList;

public class Utilisateur {
    public String pseudo;
    public String identifiant;
    public String password;
    public ArrayList<Utilisateur> listFriends;
    public Integer points;

    public Utilisateur(String pseudo, String identifiant, String password, ArrayList<Utilisateur> listFriends, Integer points) {
        this.pseudo = pseudo;
        this.identifiant = identifiant;
        this.password = password;
        this.listFriends = listFriends;
        this.points = points;
    }

    public Utilisateur(String pseudo, Integer points) {
        this.pseudo = pseudo;
        this.points = points;
    }

    public Utilisateur() {

    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Utilisateur> getListFriends() {
        return listFriends;
    }

    public void setListFriends(ArrayList<Utilisateur> listFriends) {
        this.listFriends = listFriends;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points += points;
    }
}