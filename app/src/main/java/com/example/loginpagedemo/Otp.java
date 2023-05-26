package com.example.loginpagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Otp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_otp);

        EditText otpEditText = (EditText) findViewById(R.id.otpEditText);
        Button submit = (Button) findViewById(R.id.submitButton);
        TextView otpAgain = (TextView) findViewById(R.id.sendOtpAgainButton);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Otp.this, "Verification Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Otp.this , Newpassword.class));

            }
        });

        otpAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Otp.this , Forgot_Password.class));

            }
        });

    }
}