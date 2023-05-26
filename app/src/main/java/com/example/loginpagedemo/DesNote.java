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

public class DesNote extends AppCompatActivity {

    EditText desNoteEditText ;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_des_note);

        desNoteEditText = (EditText) findViewById(R.id.desNoteEditText);

        Intent intent= getIntent();
        //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

        noteId = intent.getIntExtra("desnoteId" , -1);

        if(noteId != -1)
        {
            desNoteEditText.setText(DesList.desArrayList.get(noteId));
        }
        else
        {
            DesList.desArrayList.add("");
            noteId = DesList.desArrayList.size()  -1;

        }

        desNoteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                DesList.desArrayList.set(noteId ,String.valueOf(s) );
                DesList.desArrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.loginpagedemo", Context.MODE_PRIVATE);
                HashSet<String> desSet = new HashSet<String>(DesList.desArrayList);
                sharedPreferences.edit().putStringSet("desnotes" , desSet).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}