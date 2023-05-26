package com.example.loginpagedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView forgotPassword;
    TextView createAccountButton;
    ImageView loginButton;

    FirebaseAuth firebaseAuth;
    ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        createAccountButton = (TextView) findViewById(R.id.createAccountTextView);
        loginButton = (ImageView) findViewById(R.id.loginButtonImageView);
        username = (EditText) findViewById(R.id.usernameEditText);
        password  = (EditText) findViewById(R.id.registerPasswordEditText);
        forgotPassword = (TextView) findViewById(R.id.forgotPasswordTextView);
        loginProgressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this , Home.class));
            finish();
        }















        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , Register.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                final String sEmail = username.getText().toString().trim();
                final String sPassword = password.getText().toString().trim();


                if(TextUtils.isEmpty(sEmail))
                {
                    username.setError(" Email id is Required");
                    return;
                }
                if(TextUtils.isEmpty(sPassword))
                {
                    password.setError(" Password  is Required");
                    return;
                }
                if(sPassword.length() <6)
                {
                    password.setError("Password Must be 6 to 12 character Long");
                    return;
                }

                loginProgressBar.setVisibility(View.VISIBLE);
                // Authenticate email and Password

                firebaseAuth.signInWithEmailAndPassword(sEmail , sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){


                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this , Home.class);
                            String email = username.getText().toString();
                            intent.putExtra("Email" , email);
                            loginProgressBar.setVisibility(View.GONE);
                            startActivity(intent);


                        }
                        else{
                            loginProgressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Error ! " +task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            username.setText("");
                            password.setText("");
                        }

                    }
                });




            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this , Forgot_Password.class));

            }
        });

    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}