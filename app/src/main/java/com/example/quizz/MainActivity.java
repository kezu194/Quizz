package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button bouton_login;
    private EditText mail;
    private EditText passwd;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        bouton_login = (Button) findViewById(R.id.IdButtonLogin);
        mail = findViewById(R.id.IdMail);
        passwd = findViewById(R.id.IdPasswd);

        bouton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check for existing account
                login(mail.getText().toString(), passwd.getText().toString())
                        .observe(MainActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer success) {
                                if(success == 0) {
                                    Intent intent = new Intent(MainActivity.this, Friends.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("mail", mail.getText().toString());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    // finish();
                                }
                                else if(success == 1) {
                                    Toast.makeText(MainActivity.this, "Le pseudo ou le mot de passe est incorrecte", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }

    public static MutableLiveData<Integer> login(String username, String password) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<Integer> exist = new MutableLiveData<Integer>(-1);

        database.collection("utilisateurs").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                String dataUsername = d.getData().get("pseudo").toString();
                                String dataPassword = d.getData().get("mdp").toString();

                                if (username.equals(dataUsername) && password.equals(dataPassword)) {
                                    exist.setValue(0);
                                }

                            }

                            if(exist.getValue() == -1) {
                                exist.setValue(1);
                            }
                        }
                    }
                });

        return exist;
    }



}