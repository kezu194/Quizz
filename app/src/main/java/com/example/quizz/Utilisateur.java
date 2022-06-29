package com.example.quizz;

import java.util.ArrayList;

public class Utilisateur {
    public String pseudo;
    public String mdp;
    public ArrayList<String> listFriends;
    public Integer score;

    public Utilisateur(String pseudo, String mdp, ArrayList<String> listFriends, Integer score) {
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.listFriends = listFriends;
        this.score = score;
    }

    public Utilisateur(String pseudo, Integer score) {
        this.pseudo = pseudo;
        this.score = score;
    }

    public Utilisateur() {

    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public ArrayList<String> getListFriends() {
        return listFriends;
    }

    public void setListFriends(ArrayList<String> listFriends) {
        this.listFriends = listFriends;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score += score;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "pseudo='" + pseudo + '\'' +
                ", mdp='" + mdp + '\'' +
                ", listFriends=" + listFriends +
                ", score=" + score +
                '}';
    }
}