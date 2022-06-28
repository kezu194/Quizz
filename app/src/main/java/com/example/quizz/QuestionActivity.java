package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    public MutableLiveData<ArrayList<Question>> listQuestions = new MutableLiveData<ArrayList<Question>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String username = bundle.getString("username");
        Database.getQuestion().observe(this, new Observer<ArrayList<Question>>() {
            @Override
            public void onChanged(ArrayList<Question> questions) {
                if(questions != null)listQuestions.setValue(questions);
                Button reponse0 = (Button) findViewById(R.id.reponse0);
                Button reponse1 = (Button) findViewById(R.id.reponse1);
                Button reponse2 = (Button) findViewById(R.id.reponse2);
                TextView intitule = (TextView) findViewById(R.id.intitule);
                if(listQuestions != null) {
                    System.out.println(listQuestions.getValue());
//                    Question q = listQuestions.getValue().get(0);
//                    intitule.setText(q.getIntitule());
//                    reponse0.setText(q.getReponses().get(0).getText());
//                    reponse1.setText(q.getReponses().get(1).getText());
//                    reponse2.setText(q.getReponses().get(2).getText());

                }
            }
        });


    }
}