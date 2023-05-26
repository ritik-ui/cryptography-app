package com.example.loginpagedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class CaesarCipher extends AppCompatActivity {

    EditText input;
    EditText inputKey;
    TextView output;
    ClipboardManager cpb;
    Button encrpt , decrypt;
    TextView btnqrgen , btnqrscan , textView1 , textView2 , textView3;
    ImageView qrimg;
    SwitchCompat switchCompat;
    String data;
    ImageView caesarBack;

    TextView saveBtn;
    String Text;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_caesar_cipher);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        input = findViewById(R.id.ccInput);
        inputKey = findViewById(R.id.ccInputKey);
        output = findViewById(R.id.ccOutputTextView);
        cpb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        encrpt = (Button) findViewById(R.id.ccBtnenc);
        decrypt = (Button) findViewById(R.id.ccBtndec);
        textView1 = (TextView) findViewById(R.id.ccTextView1);
        textView2 = (TextView) findViewById(R.id.ccTextView2);
        textView3 = (TextView) findViewById(R.id.ccTextView3);
        btnqrscan = (TextView) findViewById(R.id.ccScanqr);
        btnqrgen = findViewById(R.id.ccBtnqrgen);
        qrimg = findViewById(R.id.ccQrimg);
        switchCompat = (SwitchCompat) findViewById(R.id.ccSwitchButton);

        saveBtn = findViewById(R.id.ccSaveBtn);
        caesarBack = (ImageView) findViewById(R.id.caesarBack);

        caesarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCompat.isChecked()){

                    textView1.setText("Enter Encrypted Text ");
                    textView3.setText("Decrypted Text ");
                    encrpt.setVisibility(View.INVISIBLE);
                    decrypt.setVisibility(View.VISIBLE);
                    btnqrscan.setVisibility(View.VISIBLE);
                    btnqrgen.setVisibility(View.INVISIBLE);
                    input.setText("");
                    inputKey.setText("");
                    output.setText("");
                    qrimg.setVisibility(View.INVISIBLE);

                }
                else{
                    input.setText("");
                    inputKey.setText("");
                    output.setText("");
                    encrpt.setVisibility(View.VISIBLE);
                    decrypt.setVisibility(View.INVISIBLE);
                    btnqrgen.setVisibility(View.VISIBLE);
                    btnqrscan.setVisibility(View.INVISIBLE);
                    qrimg.setVisibility(View.INVISIBLE);
                    textView1.setText("Enter Plain Text");
                    textView3.setText("Encrypted Text ");



                }
            }
        });


    }


    public void enc(View view)
    {
        String temp = input.getText().toString();
        int key = Integer.parseInt(inputKey.getText().toString());
        String res = ccEncrypt.enc(temp,key);
        output.setText(res);
        data = res;
    }

    public void dec(View view)
    {
        String temp = input.getText().toString();
        int key = Integer.parseInt(inputKey.getText().toString());
        String res = ccDecrypt.dec(temp,key);
        output.setText(res);
    }




    //Copy Button
    public void cpy(View view)
    {
        String data = output.getText().toString().trim();
        if(!data.isEmpty())
        {
            ClipData temp = ClipData.newPlainText("text",data);
            cpb.setPrimaryClip(temp);
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
        }
    }

    // Scan button
    public void scanqr(View view){
        startActivity(new Intent(getApplicationContext(),QRScanner.class));
    }



    public void genqr(View view){
        qrimg.setVisibility(View.VISIBLE);
        if(data.isEmpty()){
            Toast.makeText(this, "Value Required", Toast.LENGTH_SHORT).show();
        }
        else {

            try {
                QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);
                Bitmap qrBits = qrgEncoder.getBitmap();
                qrimg.setImageBitmap(qrBits);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void save(View view){
        Text = data.toString().trim();
        if(Text.isEmpty()){
            Toast.makeText(this, "Value Required", Toast.LENGTH_SHORT).show();
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions,WRITE_EXTERNAL_STORAGE_CODE);
                }
                else{
                    saveToTxtFile(Text);
                }
            }
            else{
                saveToTxtFile(Text);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED){
                    saveToTxtFile(Text);
                }
                else{
                    Toast.makeText(this, "Storage Permission Required", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveToTxtFile(String Text) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        try {
            File path = Environment.getExternalStorageDirectory();
            File dir = new File(path + "/My Files/");
            dir.mkdirs();
            String fileName = "MyFile_" + timeStamp + ".txt";

            File file = new File(dir, fileName);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Text);
            bw.close();

            Toast.makeText(this, fileName+" is saved to\n" +dir, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }


}