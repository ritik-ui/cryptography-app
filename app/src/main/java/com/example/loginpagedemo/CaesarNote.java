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

public class CaesarNote extends AppCompatActivity {

    EditText caesarNoteEditText ;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_caesar_note);


        caesarNoteEditText = (EditText) findViewById(R.id.caesarNoteEditText);

        Intent intent= getIntent();
        //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

        noteId = intent.getIntExtra("caesarNoteId" , -1);

        if(noteId != -1)
        {
            caesarNoteEditText.setText(CaesarList.caesarArrayList.get(noteId));
        }
        else
        {
            CaesarList.caesarArrayList.add("");
            noteId = CaesarList.caesarArrayList.size()  -1;

        }

        caesarNoteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                CaesarList.caesarArrayList.set(noteId ,String.valueOf(s) );
                CaesarList.caesarArrayAdapter.notifyDataSetChanged();

                SharedPreferences caesarSharedPreferences = getApplicationContext().getSharedPreferences("com.example.loginpagedemo", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<String>(CaesarList.caesarArrayList);
                caesarSharedPreferences.edit().putStringSet("caesarNotes" , set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}