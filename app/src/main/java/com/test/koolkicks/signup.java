package com.test.koolkicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    Button btn_singup;
    EditText reg_name;
    EditText reg_email;
    EditText reg_password;
    EditText reg_conf_pwd;
    TextView signin;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("users").document();
        reg_email=findViewById(R.id.email);
        reg_password=findViewById(R.id.pass);
        reg_conf_pwd=findViewById(R.id.cpass);
        btn_singup=findViewById(R.id.btn_signup);
        btn_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reg_email.getText().toString().equals("")) {
                    Toast.makeText(signup.this, "Please type an email id", Toast.LENGTH_SHORT).show();

                }else if(reg_password.getText().toString().equals("")){
                    Toast.makeText(signup.this, "Please type a password", Toast.LENGTH_SHORT).show();

                }else if(!reg_conf_pwd.getText().toString().equals(reg_password.getText().toString())){
                    Toast.makeText(signup.this, "Password mismatch", Toast.LENGTH_SHORT).show();

                }else {

                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists())
                            {
                                Toast.makeText(signup.this, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                            } else {
                                Map<String, Object> reg_entry = new HashMap<>();
                                reg_entry.put("Name", reg_name.getText().toString());
                                reg_entry.put("Email", reg_email.getText().toString());
                                reg_entry.put("Password", reg_password.getText().toString());

                                //   String myId = ref.getId();
                                firebaseFirestore.collection("users")
                                        .add(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(signup.this, "Successfully sign up", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error", e.getMessage());
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        });


    }
}
