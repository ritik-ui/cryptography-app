package com.example.loginpagedemo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.provider.ContactsContract;
import android.util.Base64;
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
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Aes extends AppCompatActivity {

    EditText inputText, inputPassword;
    TextView outputText;
    Button encBtn,decBtn;
    String outputString;
    String decOutputString;
    String AES = "AES";
    ClipboardManager cpb;
    TextView delbtn , textView;

    SwitchCompat switchCompat;

    ImageView aesBack;

    TextView btnqrgen;
    TextView scanqr;
    ImageView qrimg;

    String data;

    TextView saveBtn;
    String Text;

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_aes);

        inputText = (EditText) findViewById(R.id.inputText);
        inputPassword = (EditText) findViewById(R.id.password);
        delbtn = (TextView) findViewById(R.id.deleteBtn);
        textView = (TextView) findViewById(R.id.textView3);

        outputText = (TextView) findViewById(R.id.outputText);
        encBtn = (Button) findViewById(R.id.encBtn);
        decBtn = (Button) findViewById(R.id.decBtn);
        aesBack = (ImageView) findViewById(R.id.aesBack);

        cpb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

        btnqrgen = findViewById(R.id.btnqrgen);
        scanqr = findViewById(R.id.scanqr);
        qrimg = findViewById(R.id.qrimg);

        saveBtn = findViewById(R.id.saveBtn);
        switchCompat = (SwitchCompat) findViewById(R.id.aesSwitchButton);

        aesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCompat.isChecked()){
                    inputText.setText("");
                    inputPassword.setText("");
                    outputText.setText("");
                    encBtn.setVisibility(View.INVISIBLE);
                    decBtn.setVisibility(View.VISIBLE);
                    inputText.setHint("Enter Encrypted Text");
                    inputPassword.setHint("Enter Password");
                    scanqr.setVisibility(View.VISIBLE);
                    btnqrgen.setVisibility(View.INVISIBLE);
                    textView.setText("Decrypted Text ");
                    qrimg.setVisibility(View.INVISIBLE);




                }
                else{
                    inputText.setText("");
                    inputPassword.setText("");
                    outputText.setText("");
                    decBtn.setVisibility(View.INVISIBLE);
                    encBtn.setVisibility(View.VISIBLE);
                    inputText.setHint("Enter Text to Encrypt");
                    inputPassword.setHint("Enter Password");
                    btnqrgen.setVisibility(View.VISIBLE);
                    scanqr.setVisibility(View.INVISIBLE);
                    textView.setText("Encrypted Text ");
                    qrimg.setVisibility(View.INVISIBLE);


                }
            }
        });

        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputText.setText("");
            }
        });

        encBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputString = encrypt(inputText.getText().toString(), inputPassword.getText().toString());
                    outputText.setText(outputString);
                    data = outputString;
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        // decryption button

        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputString = decrypt(inputText.getText().toString(), inputPassword.getText().toString());
                    outputText.setText(outputString);
                }
                catch (Exception e){
                    Toast.makeText(Aes.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


    }

    //decryption
    private String decrypt(String outputString, String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(outputString, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String (decValue);
        return decryptedValue;
    }


    // encryption
    private String encrypt(String Data, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }


    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0 , bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }


    //Copy Button
    public void cpy(View view)
    {
        String data = outputText.getText().toString().trim();
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


    @RequiresApi(api = Build.VERSION_CODES.M)
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