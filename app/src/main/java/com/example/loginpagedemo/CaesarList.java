package com.example.loginpagedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class CaesarList extends AppCompatActivity {

    ListView caesarListView;
    SharedPreferences caesarSharedPreferences;
    static ArrayList<String> caesarArrayList;
    static ArrayAdapter<String> caesarArrayAdapter;
    ImageView caesarAdd  , caesarListBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_caesar_list);

        caesarListView = (ListView) findViewById(R.id.caesarListView);
        caesarAdd = (ImageView) findViewById(R.id.caesarAddText);
        caesarListBack = (ImageView) findViewById(R.id.caesarListBack);

        caesarSharedPreferences = getApplicationContext().getSharedPreferences("com.example.loginpagedemo", Context.MODE_PRIVATE);



        caesarArrayList = new ArrayList<String>();

        HashSet<String> set = (HashSet<String>) caesarSharedPreferences.getStringSet("caesarNotes" , null);

        if(set == null)
        {
            caesarArrayList.add("No item Available");
        }
        else
        {
            caesarArrayList = new ArrayList(set);
        }




        caesarArrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1,caesarArrayList);
        caesarListView.setAdapter(caesarArrayAdapter);

        caesarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(MainActivity.this, arrayList.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext() , CaesarNote.class);
                intent.putExtra("caesarNoteId" , position);
                intent.putStringArrayListExtra("caesarNotes" , caesarArrayList);
                startActivity(intent);


            }
        });

        caesarListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int itemToDelete = position;

                new AlertDialog.Builder(CaesarList.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("ARE YOU SURE")
                        .setMessage("You Want To Delete This Note")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                Toast.makeText(CaesarList.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                                caesarArrayList.remove(itemToDelete);
                                caesarArrayAdapter.notifyDataSetChanged();


                                HashSet<String> set = new HashSet<String>(CaesarList.caesarArrayList);
                                caesarSharedPreferences.edit().putStringSet("caesarNotes" , set).apply();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }
        });

        caesarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CaesarList.this , CaesarNote.class));
            }
        });

        caesarListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}