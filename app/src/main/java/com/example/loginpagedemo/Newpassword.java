package com.example.loginpagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Newpassword extends AppCompatActivity {

    EditText newPassword;
    EditText confirmNewPassword;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_newpassword);

        newPassword = (EditText) findViewById(R.id.newPasswordEditText);
        confirmNewPassword =(EditText) findViewById(R.id.confirmNewPasswordEditText);
        resetButton =(Button) findViewById(R.id.resetPasswordButton);



            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(newPassword.getText().toString().equals(confirmNewPassword.getText().toString())){

                    Toast.makeText(Newpassword.this, "Password Successfully Changed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Newpassword.this , Reset_password_Successful.class));

                    }
                    else{

                        Toast.makeText(Newpassword.this, "Confirm Password Doesn't Match !!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });




    }
}