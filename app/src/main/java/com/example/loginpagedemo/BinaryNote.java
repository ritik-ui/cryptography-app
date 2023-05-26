package com.example.loginpagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.HashSet;

public class BinaryNote extends AppCompatActivity {

    EditText binaryNoteEditText ;
    int binaryNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_binary_note);

        binaryNoteEditText = (EditText) findViewById(R.id.binaryNoteEditText);

        Intent intent= getIntent();

        binaryNoteId = intent.getIntExtra("binaryNoteId" , -1);

        if(binaryNoteId != -1)
        {
            binaryNoteEditText.setText(BinaryList.binaryArrayList.get(binaryNoteId));
        }
        else
        {
            BinaryList.binaryArrayList.add("");
            binaryNoteId = BinaryList.binaryArrayList.size()  -1;

        }

        binaryNoteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                BinaryList.binaryArrayList.set(binaryNoteId ,String.valueOf(s) );
                BinaryList.binaryArrayAdapter.notifyDataSetChanged();

                SharedPreferences binarySharedPreferences = getApplicationContext().getSharedPreferences("com.example.loginpagedemo", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<String>(BinaryList.binaryArrayList);
                binarySharedPreferences.edit().putStringSet("binaryNotes" , set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}