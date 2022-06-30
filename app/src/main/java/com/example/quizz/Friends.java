package com.example.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;

public class Friends extends AppCompatActivity {
    public MutableLiveData<ArrayList<Utilisateur>> listFriends = new MutableLiveData<ArrayList<Utilisateur>>();
    public Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String username = bundle.getString("mail");

        Database.getScore().observe(this, new Observer<ArrayList<Utilisateur>>() {
            @Override
            public void onChanged(ArrayList<Utilisateur> strings) {
                if(strings != null)listFriends.setValue(strings);
            }
        });

        Database.findFriends(username).observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Friends.this, android.R.layout.simple_list_item_1, strings);
                ListView listView = (ListView)findViewById(R.id.friendList);
                listView.setAdapter(adapter);
                ArrayList<Integer> listeScore = new ArrayList<Integer>();
                if(listFriends != null) {
                    for(Utilisateur u : listFriends.getValue()) {
                        for(int i = 0; i < strings.size(); i++) {
                            if(u.pseudo.equals(strings.get(i))) {
                                listeScore.add(u.score);

                            }
                        }
                    }
                    ArrayAdapter<Integer> adapterScore = new ArrayAdapter<Integer>(Friends.this, android.R.layout.simple_list_item_1, listeScore);
                    ListView listViewScore = (ListView)findViewById(R.id.scoreList);
                    listViewScore.setAdapter(adapterScore);
                }
            }
        });

        this.start = this.findViewById(R.id.startButton);

        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Friends.this, QuestionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            };
        });
    }

}
