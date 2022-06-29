package com.example.quizz;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

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

    public static MutableLiveData<ArrayList<Utilisateur>> getScore() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<ArrayList<Utilisateur>> listFriends = new MutableLiveData<>(new ArrayList<Utilisateur>());
        database.collection("utilisateurs").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Utilisateur> listScore = new ArrayList<Utilisateur>();
                        List<DocumentSnapshot> users = (ArrayList) queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : users) {
                            String dataPseudo = d.getData().get("pseudo").toString();
                            Integer dataPoints = Integer.parseInt(d.getData().get("score").toString());
                            Utilisateur u = new Utilisateur(dataPseudo, dataPoints);
                            listScore.add(u);
                        }
                        listFriends.setValue(listScore);
                    }

                });
        return listFriends;
    }

    public static MutableLiveData<ArrayList<Question>> getQuestion() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<ArrayList<Question>> listQuestions = new MutableLiveData<>(new ArrayList<Question>());
        database.collection("questions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Question> listQuestion = new ArrayList<Question>();
                List<DocumentSnapshot> questions = (ArrayList) queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d : questions){
                    String intitule = d.getData().get("intitule").toString();
                    HashMap<String, Map<String, Any>> response = (HashMap) d.getData().get("reponses");
                    ArrayList<Question.Reponse> listReponse = new ArrayList<>();
                    for(int i = 0; i<3; i++){
                        String text = "" + response.get(String.valueOf(i)).get("text");
                        Boolean isGoodResponse = Boolean.valueOf(""+response.get(String.valueOf(i)).get("isGoodResponse"));
                        listReponse.add(new Question.Reponse(text, isGoodResponse));
                    }
                    listQuestion.add(new Question(intitule, listReponse));
                }
                listQuestions.setValue(listQuestion);
            }
        });
        return listQuestions;
    }

    public static void updateScore(String username){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<Utilisateur> userMutable = new MutableLiveData<Utilisateur>();
        database.collection("utilisateurs").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                long score = (long) documentSnapshot.getData().get("score");
                ArrayList<String> friendList = (ArrayList<String>) documentSnapshot.getData().get("listFriend");
                String mdp = (String) documentSnapshot.getData().get("mdp");
                Utilisateur user = new Utilisateur(username, mdp, friendList, (int)score);
                userMutable.setValue(user);
            }
        });
        Map<String, Object> map = new HashMap<String, Object>() {};
        map.put("listFriend", userMutable.getValue().getListFriends());
        map.put("mdp", userMutable.getValue().getMdp());
        map.put("pseudo", username);
        map.put("score", userMutable.getValue().getScore()+10);
        database.collection("utilisateurs").document(username).update(map);

    }

}
