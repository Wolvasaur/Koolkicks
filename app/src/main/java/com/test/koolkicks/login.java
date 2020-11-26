package com.test.koolkicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class login extends AppCompatActivity {
    Button login;
    TextView signup;
    EditText pwd;
    TextView email;
    ProgressBar progress;
    FirebaseFirestore db;
    public  boolean auth=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.txt_email);
        pwd=findViewById(R.id.txt_password);
        login = findViewById(R.id.btn_signin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("")){
                    Toast.makeText(login.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }else if( pwd.getText().toString().equals("")){
                    Toast.makeText(login.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                }
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot doc : task.getResult()){
                                        if(email.getText().toString().equalsIgnoreCase(doc.getString("Email")) & pwd.getText().toString().equalsIgnoreCase(doc.getString("Password"))) {
                                            Toast.makeText(login.this,  "Successfully Sign in !" , Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent home = new Intent(login.this, MainActivity.class);
                                            startActivity(home);
                                            auth=true;
                                            break;

                                        }
                                        else
                                            auth=false;
                                    }
                                    if(!auth)
                                        Toast.makeText(login.this, "Invalid", Toast.LENGTH_SHORT).show();


                                }
                            }
                        });
            }
        });
        signup = findViewById(R.id.sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_view=new Intent(login.this,signup.class);
                startActivity(register_view);
            }
        });

        db= FirebaseFirestore.getInstance();



    }


    }



