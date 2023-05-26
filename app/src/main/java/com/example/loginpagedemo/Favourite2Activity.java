package com.example.loginpagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class Favourite2Activity extends AppCompatActivity {

    Button aes , des , binary , caesar;

    ImageView favBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_favourite2);

        aes = (Button) findViewById(R.id.favAes);
        des = (Button) findViewById(R.id.favDes);
        binary = (Button) findViewById(R.id.favBinary);
        caesar = (Button) findViewById(R.id.favCaesar);
        favBack = (ImageView) findViewById(R.id.favBack);

        favBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        aes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Favourite2Activity.this , AesList.class));
            }
        });

        des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Favourite2Activity.this , DesList.class));
            }
        });

        binary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Favourite2Activity.this , BinaryList.class));
            }
        });

        caesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Favourite2Activity.this , CaesarList.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
