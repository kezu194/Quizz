package com.example.quizz;

import java.util.ArrayList;

public class Question {
    public String intitule;
    public ArrayList<Reponse> reponses = new ArrayList<Reponse>();

    public Question(String intitule, ArrayList<Reponse> reponses) {
        this.intitule = intitule;
        this.reponses = reponses;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public ArrayList<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(ArrayList<Reponse> reponses) {
        this.reponses = reponses;
    }

    @Override
    public String toString() {
        return "Question{" +
                "intitule='" + intitule + '\'' +
                '}';
    }

    public static class Reponse {
        public String text;
        public boolean isGoodResponse;

        public Reponse(String text, boolean isGoodResponse) {
            this.text = text;
            this.isGoodResponse = isGoodResponse;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isGoodResponse() {
            return isGoodResponse;
        }

        public void setGoodResponse(boolean goodResponse) {
            isGoodResponse = goodResponse;
        }
    }

}
