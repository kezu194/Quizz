package com.example.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Friends extends AppCompatActivity {
    private ArrayList<String> friends;
    private ArrayAdapter<String> adapter;
    private ListView friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String mail = intent.getStringExtra("mail");

        // Get the
        friends = new ArrayList<String>() {};
        findFriends(mail).observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                friends = strings;
                adapter = new ArrayAdapter<String>(Friends.this, android.R.layout.simple_list_item_1, friends);
                friendList = findViewById(R.id.friendList);
                friendList.setAdapter(adapter);
            }
        });

    }



    public static MutableLiveData<ArrayList<String>> findFriends(String username) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<ArrayList<String>> listFriend = new MutableLiveData<>(new ArrayList<String>());
        database.collection("utilisateurs").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<String> friends = (ArrayList) documentSnapshot.getData().get("listFriend");
                listFriend.setValue(friends);
            }
        });
        return listFriend;
    }


}
