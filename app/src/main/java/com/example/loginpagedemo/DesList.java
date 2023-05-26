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

public class DesList extends AppCompatActivity {

    ListView desListView;
    SharedPreferences desSharedPreferences;
    static ArrayList<String> desArrayList;
    static ArrayAdapter<String> desArrayAdapter;
    ImageView desAdd , desListBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_des_list);

        desListView = (ListView) findViewById(R.id.desListView);
        desAdd = (ImageView) findViewById(R.id.desAddText);
        desListBack = (ImageView) findViewById(R.id.desListback);

        desSharedPreferences = getApplicationContext().getSharedPreferences("com.example.loginpagedemo", Context.MODE_PRIVATE);



        desArrayList = new ArrayList<String>();

        HashSet<String> desSet = (HashSet<String>) desSharedPreferences.getStringSet("desnotes" , null);

        if(desSet == null)
        {
            desArrayList.add("No item Available");
        }
        else
        {
            desArrayList = new ArrayList(desSet);
        }




        desArrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1,desArrayList);
        desListView.setAdapter(desArrayAdapter);

        desListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(MainActivity.this, arrayList.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext() , DesNote.class);
                intent.putExtra("desnoteId" , position);
                intent.putStringArrayListExtra("desnotes" , desArrayList);
                startActivity(intent);


            }
        });

        desListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int itemToDelete = position;

                new AlertDialog.Builder(DesList.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("ARE YOU SURE")
                        .setMessage("You Want To Delete This Note")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                Toast.makeText(DesList.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                                desArrayList.remove(itemToDelete);
                                desArrayAdapter.notifyDataSetChanged();


                                HashSet<String> desSet = new HashSet<String>(DesList.desArrayList);
                                desSharedPreferences.edit().putStringSet("desnotes" , desSet).apply();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }
        });

        desAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DesList.this , DesNote.class));
            }
        });

        desListBack.setOnClickListener(new View.OnClickListener() {
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