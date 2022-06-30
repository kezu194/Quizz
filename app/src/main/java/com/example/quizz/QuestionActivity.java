package com.example.quizz;

import static java.lang.Integer.valueOf;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    public MutableLiveData<ArrayList<Question>> listQuestions = new MutableLiveData<ArrayList<Question>>();
    public MutableLiveData<Utilisateur> user = new MutableLiveData<Utilisateur>();
    public Button reponse0;
    public Button reponse1;
    public Button reponse2;
    public TextView intitule;
    public TextView compteur;
    public TextView compteurNextQuestion;
    public String bonneReponse;
    private int counter = 10;
    private CountDownTimer countDown;
    private CountDownTimer countDownNextQuestion;
    public String username;
    public Observer<Utilisateur> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("username");

        reponse0 = (Button) findViewById(R.id.reponse0);
        reponse1 = (Button) findViewById(R.id.reponse1);
        reponse2 = (Button) findViewById(R.id.reponse2);
        intitule = (TextView) findViewById(R.id.intitule);
        compteur = (TextView) findViewById(R.id.compteur);
        compteurNextQuestion = (TextView) findViewById(R.id.compteurNextQuestion);

        observer = new Observer<Utilisateur>() {
            @Override
            public void onChanged(Utilisateur utilisateur) {
                if(utilisateur !=null){
                    user.setValue(utilisateur);
                    Map<String, Object> map = new HashMap<String, Object>() {};
                    map.put("listFriend", user.getValue().getListFriends());
                    map.put("mdp", user.getValue().getMdp());
                    map.put("pseudo", username);
                    map.put("score", user.getValue().getScore()+10);
                    Database.update(map, username);
                }
            }
        };

        Database.getQuestion().observe(this, new Observer<ArrayList<Question>>() {
            @Override
            public void onChanged(ArrayList<Question> questions) {
                if(questions != null)listQuestions.setValue(questions);
                if(listQuestions.getValue().size() != 0) {
                    //On sélectionne une question au hasard
                    int r = new Random().nextInt(listQuestions.getValue().size());
                    Question q = listQuestions.getValue().get(r);

                    intitule.setText(q.getIntitule());
                    reponse0.setText(q.getReponses().get(0).getText());
                    reponse1.setText(q.getReponses().get(1).getText());
                    reponse2.setText(q.getReponses().get(2).getText());


                    if(q.getReponses().get(0).isGoodResponse()){
                        bonneReponse = q.getReponses().get(0).getText();
                    }

                    if(q.getReponses().get(1).isGoodResponse()){
                        bonneReponse = q.getReponses().get(1).getText();
                    }

                    if(q.getReponses().get(2).isGoodResponse()){
                        bonneReponse = q.getReponses().get(2).getText();
                    }

                }
            }
        });

        reponse0.setOnClickListener(this);
        reponse1.setOnClickListener(this);
        reponse2.setOnClickListener(this);
        this.countDown = new CountDownTimer(10000, 1000){

            @Override
            public void onTick(long l) {
                if (counter>0){
                    compteur.setText(String.valueOf(counter));
                    counter--;
                }
            }

            @Override
            public void onFinish() {
                compteur.setText("Temps épuisé");
                if(bonneReponse == reponse0.getText()){
                    reponse0.setBackgroundColor(Color.YELLOW);
                }
                if(bonneReponse == reponse1.getText()){
                    reponse1.setBackgroundColor(Color.YELLOW);
                }
                if(bonneReponse == reponse2.getText()){
                    reponse2.setBackgroundColor(Color.YELLOW);
                }
                reponse0.setEnabled(false);
                reponse1.setEnabled(false);
                reponse2.setEnabled(false);
                counter=3;
                countDownNextQuestion.start();
            }
        }.start();

        this.countDownNextQuestion = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long l) {
                if (counter>0){
                    compteurNextQuestion.setText(String.valueOf(counter));
                    counter--;
                }
            }

            @Override
            public void onFinish() {

                finish();
                startActivity(getIntent());
            }
        };


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reponse0:
                if(reponse0.getText()==bonneReponse){
                    reponse0.setBackgroundColor(Color.GREEN);
                    compteur.setText("Bonne réponse !");
                    Database.updateScore(username).observe(this, observer);
                }
                else{
                    reponse0.setBackgroundColor(Color.RED);
                    compteur.setText("Mauvaise réponse !");
                }
                break;
            case R.id.reponse1:
                if(reponse1.getText()==bonneReponse){
                    reponse1.setBackgroundColor(Color.GREEN);
                    compteur.setText("Bonne réponse !");
                    Database.updateScore(username).observe(this, observer);
                }
                else{
                    reponse1.setBackgroundColor(Color.RED);
                    compteur.setText("Mauvaise réponse !");
                }
                break;
            case R.id.reponse2:
                if(reponse2.getText()==bonneReponse){
                    reponse2.setBackgroundColor(Color.GREEN);
                    compteur.setText("Bonne réponse !");
                    Database.updateScore(username).observe(this, observer);
                }
                else{
                    reponse2.setBackgroundColor(Color.RED);
                    compteur.setText("Mauvaise réponse !");
                }
                break;
        }
        reponse0.setEnabled(false);
        reponse1.setEnabled(false);
        reponse2.setEnabled(false);
        countDown.cancel();
        counter=3;
        countDownNextQuestion.start();


    }
}