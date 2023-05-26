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

public class BinaryList extends AppCompatActivity {

    ListView binaryListView;
    SharedPreferences binarySharedPreferences;
    static ArrayList<String> binaryArrayList;
    static ArrayAdapter<String> binaryArrayAdapter;
    ImageView binaryAdd , binaryListBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_binary_list);

        binaryListView = (ListView) findViewById(R.id.binaryListView);
        binaryAdd = (ImageView) findViewById(R.id.binaryAddText);
        binaryListBack = (ImageView) findViewById(R.id.binaryListBack);

        binarySharedPreferences = getApplicationContext().getSharedPreferences("com.example.loginpagedemo", Context.MODE_PRIVATE);



        binaryArrayList = new ArrayList<String>();

        HashSet<String> set = (HashSet<String>) binarySharedPreferences.getStringSet("binaryNotes" , null);

        if(set == null)
        {
            binaryArrayList.add("No item Available");
        }
        else
        {
            binaryArrayList = new ArrayList(set);
        }




        binaryArrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1,binaryArrayList);
        binaryListView.setAdapter(binaryArrayAdapter);

        binaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(MainActivity.this, arrayList.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext() , BinaryNote.class);
                intent.putExtra("binaryNoteId" , position);
                intent.putStringArrayListExtra("binaryNotes" , binaryArrayList);
                startActivity(intent);


            }
        });

        binaryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int itemToDelete = position;

                new AlertDialog.Builder(BinaryList.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("ARE YOU SURE")
                        .setMessage("You Want To Delete This Note")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                Toast.makeText(BinaryList.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                                binaryArrayList.remove(itemToDelete);
                                binaryArrayAdapter.notifyDataSetChanged();


                                HashSet<String> set = new HashSet<String>(AesList.arrayList);
                                binarySharedPreferences.edit().putStringSet("binaryNotes" , set).apply();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }
        });

        binaryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BinaryList.this , BinaryNote.class));
            }
        });

        binaryListBack.setOnClickListener(new View.OnClickListener() {
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