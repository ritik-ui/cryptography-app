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

public class Binary extends AppCompatActivity {

    SwitchCompat switchCompat;

    EditText input;
    TextView output , binaryTextView1 , binaryTextView2 , scanqr;
    ClipboardManager cpb;

    TextView btnqrgen;
    ImageView qrimg;
    Button btnenc , btndec;
    String data;
    ImageView binaryBack;
    TextView saveBtn;
    String Text;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_binary);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        cpb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

        btnqrgen = findViewById(R.id.btnqrgen);
        qrimg = findViewById(R.id.qrimg);
        switchCompat = (SwitchCompat) findViewById(R.id.binarySwitchButton);
        binaryTextView1 = (TextView) findViewById(R.id.binaryTextView1);
        binaryTextView2 = (TextView) findViewById(R.id.binaryTextView2);
        scanqr = (TextView) findViewById(R.id.scanqr);
        btnenc = (Button) findViewById(R.id.btnenc);
        btndec = (Button) findViewById(R.id.btndec);
        binaryBack = (ImageView) findViewById(R.id.binaryBack);


        saveBtn = findViewById(R.id.saveBtn);

        binaryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCompat.isChecked()){
                    btnqrgen.setVisibility(View.INVISIBLE);
                    qrimg.setVisibility(View.INVISIBLE);
                    binaryTextView1.setText("Enter Binary Code");
                    input.setText("");
                    output.setText("");
                    binaryTextView2.setText("Decrypted Text :");
                    qrimg.setVisibility(View.INVISIBLE);
                    btnqrgen.setVisibility(View.INVISIBLE);
                    scanqr.setVisibility(View.VISIBLE);
                    btnenc.setVisibility(View.INVISIBLE);
                    btndec.setVisibility(View.VISIBLE);



                }
                else{
                    btnqrgen.setVisibility(View.VISIBLE);
                    qrimg.setVisibility(View.INVISIBLE);
                    binaryTextView1.setText("Enter Normal Text ");
                    input.setText("");
                    output.setText("");
                    binaryTextView2.setText("Encrypted Text :");
                    qrimg.setVisibility(View.INVISIBLE);
                    btnqrgen.setVisibility(View.VISIBLE);
                    scanqr.setVisibility(View.INVISIBLE);
                    btndec.setVisibility(View.INVISIBLE);
                    btnenc.setVisibility(View.VISIBLE);

                }
            }
        });


    }


    public void enc(View view)
    {
        String temp = input.getText().toString();
        String rv = binaryEncrypt.enc(temp);
        output.setText(rv);
        data = rv;
    }

    public void dec(View view)
    {
        String temp = input.getText().toString();
        String rv = binaryDecrypt.dec(temp);
        output.setText(rv);
    }

    public void cp2(View view)
    {
        String data = output.getText().toString().trim();
        if(!data.isEmpty())
        {
            ClipData temp = ClipData.newPlainText("text",data);
            cpb.setPrimaryClip(temp);
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
        }
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
        Text = output.getText().toString().trim();
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


    public void scanqr(View view){
        startActivity(new Intent(getApplicationContext(),QRScanner.class));
    }


    @Override
    public void onBackPressed() {
        finish();
    }

}