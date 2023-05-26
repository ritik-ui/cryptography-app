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

public class AesList extends AppCompatActivity {

    ListView aesListView;
    SharedPreferences sharedPreferences;
    static ArrayList<String> arrayList;
    static ArrayAdapter<String> arrayAdapter;
    ImageView aesAdd , aesListBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_aes_list);

        aesListView = (ListView) findViewById(R.id.aesListView);
        aesAdd = (ImageView) findViewById(R.id.aesAddText);
        aesListBack = (ImageView) findViewById(R.id.aesListBack);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.loginpagedemo", Context.MODE_PRIVATE);



        arrayList = new ArrayList<String>();

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes" , null);

        if(set == null)
        {
            arrayList.add("No item Available");
        }
        else
        {
            arrayList = new ArrayList(set);
        }




        arrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1,arrayList);
        aesListView.setAdapter(arrayAdapter);

        aesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(MainActivity.this, arrayList.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext() , AesNote.class);
                intent.putExtra("noteId" , position);
                intent.putStringArrayListExtra("notes" , arrayList);
                startActivity(intent);


            }
        });

        aesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int itemToDelete = position;

                new AlertDialog.Builder(AesList.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("ARE YOU SURE")
                        .setMessage("You Want To Delete This Note")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                Toast.makeText(AesList.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                                arrayList.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();


                                HashSet<String> set = new HashSet<String>(AesList.arrayList);
                                sharedPreferences.edit().putStringSet("notes" , set).apply();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }
        });

        aesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AesList.this , AesNote.class));
            }
        });

        aesListBack.setOnClickListener(new View.OnClickListener() {
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