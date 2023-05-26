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

public class AesNote extends AppCompatActivity {

    EditText aesNoteEditText ;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_aes_note);

        aesNoteEditText = (EditText) findViewById(R.id.aesNoteEditText);

        Intent intent= getIntent();
        //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

        noteId = intent.getIntExtra("noteId" , -1);

        if(noteId != -1)
        {
            aesNoteEditText.setText(AesList.arrayList.get(noteId));
        }
        else
        {
            AesList.arrayList.add("");
            noteId = AesList.arrayList.size()  -1;

        }

        aesNoteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                AesList.arrayList.set(noteId ,String.valueOf(s) );
                AesList.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.loginpagedemo", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<String>(AesList.arrayList);
                sharedPreferences.edit().putStringSet("notes" , set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}