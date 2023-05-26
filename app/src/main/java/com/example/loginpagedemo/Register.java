package com.example.loginpagedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    EditText mobileNumberEditText;
    ImageView registerButton;
    TextView alreadyHaveAccount;


    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    String userId;
    ProgressBar registerProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

         alreadyHaveAccount = (TextView) findViewById(R.id.alreadyAccountButtonImageView);
         registerButton = (ImageView) findViewById(R.id.registerButtonImageView);
         passwordEditText = (EditText) findViewById(R.id.registerPasswordEditText) ;
         confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
         emailEditText = (EditText) findViewById(R.id.emailEditText);
         mobileNumberEditText = (EditText) findViewById(R.id.mobileEditText);
         registerProgressBar = (ProgressBar) findViewById(R.id.registerProgressBar);



        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



/*
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(Register.this , Home.class));
            finish();
        }

 */




        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String sEmail = emailEditText.getText().toString().trim();
                final String sPassword = passwordEditText.getText().toString().trim();
                final String sMobileNo = mobileNumberEditText.getText().toString();
                final String sConfirmPassword = confirmPasswordEditText.getText().toString();

                if(TextUtils.isEmpty(sEmail))
                {
                    emailEditText.setError(" Email id is Required");
                    return;
                }
                if(TextUtils.isEmpty(sMobileNo)){
                    mobileNumberEditText.setError("Enter Mobile Number");
                    return;
                }
                else if(sMobileNo.length() <10){
                    mobileNumberEditText.setError("Invalid Mobile Number");
                    return;
                }

                if(TextUtils.isEmpty(sPassword))
                {
                    passwordEditText.setError(" Password  is Required");
                    return;
                }
                else if(sPassword.length() <6)
                {
                    passwordEditText.setError("Password Must be 6 to 12 character Long");
                    return;
                }


                if(TextUtils.isEmpty(sConfirmPassword)){
                    confirmPasswordEditText.setError("Enter Confirm Password ");
                    return;
                }


                if(! sPassword.matches(sConfirmPassword)){
                    confirmPasswordEditText.setError("Confirm Password Does not Match");
                    return;
                }


                registerProgressBar.setVisibility(View.VISIBLE);





                firebaseAuth.createUserWithEmailAndPassword(sEmail,sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(Register.this, "Registered", Toast.LENGTH_LONG).show();

                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = db.collection("User" ).document(userId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Email id", sEmail);
                            user.put("Mobile Number", sMobileNo);
                            user.put("Pasword", sPassword);
                            user.put("Confirm Pasword", sConfirmPassword);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Log.i(" ","onSuccess:  User Profile is Created for " + userId);

                                }
                            });

                            registerProgressBar.setVisibility(View.GONE);

                            startActivity(new Intent(Register.this, MainActivity.class));

                        }
                        else{
                            registerProgressBar.setVisibility(View.GONE);

                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }



                    }
                });


            }
        });


        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Register.this , MainActivity.class));

            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}