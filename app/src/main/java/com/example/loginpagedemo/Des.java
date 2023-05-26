package com.example.loginpagedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginpagedemo.ui.App;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Des extends AppCompatActivity {

    Button  cipherCopy , cipherDelete , encryptBtn , decryptBtn;
    EditText editText  , secretCode;
    TextView textCount , cipherCount , copyBtn , deletetBtn ,textView , encryptedTextView , copybtn2 , delbtn2 , textView2;
    Context c;
    SwitchCompat switchCompat;
    ImageView desBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_des);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#0007cc"));

        }
        c = Des.this;


        switchCompat = (SwitchCompat)  findViewById(R.id.switchButton);
        textView = (TextView) findViewById(R.id.textView);
        encryptedTextView = (TextView) findViewById(R.id.encryptedTextView);

        copyBtn = (TextView)  findViewById(R.id.copyBtn);
        deletetBtn = (TextView)  findViewById(R.id.deleteBtn);
        textView2 = (TextView) findViewById(R.id.textView2);

        copybtn2 = (TextView)  findViewById(R.id.copyBtn2);
        delbtn2 = (TextView)  findViewById(R.id.deleteBtn2);
        encryptBtn = (Button)  findViewById(R.id.encryptBtn);
        decryptBtn = (Button)  findViewById(R.id.decryptBtn);

        editText = (EditText) findViewById(R.id.normalEditText);
        // cipherEditText = (EditText) findViewById(R.id.cipherEditText);
        secretCode = (EditText) findViewById(R.id.secretCode);
        desBack = (ImageView) findViewById(R.id.desBack);


        //textCount = (TextView) findViewById(R.id.textCount);
        //cipherCount = (TextView) findViewById(R.id.cipherCount);

        desBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCompat.isChecked()){
                    decryptBtn.setVisibility(View.VISIBLE);
                    encryptBtn.setVisibility(View.INVISIBLE);
                    textView.setText("Enter Cipher Text ");
                    encryptedTextView.setText("");
                    editText.setText("");
                    secretCode.setText("");
                    copyBtn.setVisibility(View.VISIBLE);
                    deletetBtn.setVisibility(View.VISIBLE);
                    editText.setHint("Enter Cipher Text");
                    textView2.setText("Decrypted Text");



                }
                else{
                    encryptBtn.setVisibility(View.VISIBLE);
                    decryptBtn.setVisibility(View.INVISIBLE);
                    textView.setText("Enter Plain Text ");
                    encryptedTextView.setText("");
                    editText.setText("");
                    secretCode.setText("");
                    copyBtn.setVisibility(View.INVISIBLE);
                    deletetBtn.setVisibility(View.INVISIBLE);
                    editText.setHint("Enter Plain Text");
                    textView2.setText("Encrypted Text");

                }
            }
        });

        encryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().matches("") || secretCode.getText().toString().matches("")){

                    App.ToastMaker(c , "Enter Plain Text and Key");
                }
                else if(secretCode.getText().toString().length() != 8){
                    App.ToastMaker(c,"Enter Key of 8 Character");

                }
                else{
                    encryptedTextView.setText(encrypt(editText.getText().toString() , c));
                }
            }
        });

        decryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().matches("") || secretCode.getText().toString().matches("")){

                    App.ToastMaker(c , "Enter Encrypted Text and Key");
                }
                else if(secretCode.getText().toString().length() != 8){
                    App.ToastMaker(c,"Enter Key of 8 Character");

                }
                else{
                    encryptedTextView.setText(decrypt(editText.getText().toString() , c));
                }


            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("cipher text" , editText.getText().toString());
                clipboard.setPrimaryClip(clip);
                App.ToastMaker(c , "Text Copied");

            }
        });

        copybtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("cipher text" , encryptedTextView.getText().toString());
                clipboard.setPrimaryClip(clip);
                App.ToastMaker(c , " Cipher Text Copied");

            }
        });



        deletetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        delbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encryptedTextView.setText("");
            }
        });


/*
        normalEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textCount.setText(normalEditText.getText().toString().length()+"");

            }
        });

        cipherEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cipherCount.setText(cipherEditText.getText().toString().length()+"");

            }
        });


 */

    }


    public String decrypt(String value , Context c){

        String coded;
        String result = null;
        if(value.startsWith("code==")){
            coded = value.substring(6 , value.length()).trim();

        }
        else{
            coded = value.trim();
        }

        try {
            byte[] byteDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
            SecretKeySpec key = new SecretKeySpec(secretCode.getText().toString().getBytes() , "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");
            cipher.init(Cipher.DECRYPT_MODE , key);
            byte[] textDecrypted = cipher.doFinal(byteDecoded);
            result = new String(textDecrypted);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        } catch (BadPaddingException e) {
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        }
        catch (Exception e){
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" + e.getMessage())  ;
        }

        return result;

    }


    public String encrypt(String value  , Context c){
        String crypted = "";
        try{
            byte[] clearText = value.getBytes("UTF-8");
            SecretKeySpec key = new SecretKeySpec(secretCode.getText().toString().getBytes() , "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");
            cipher.init(Cipher.ENCRYPT_MODE , key);
            crypted = Base64.encodeToString(cipher.doFinal(clearText) , Base64.DEFAULT);


        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        } catch (BadPaddingException e) {
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        }
        catch (Exception e){
            e.printStackTrace();
            App.DialogMaker(c , "Encrypt Error" , "Error" + "\n" +e.getMessage());
            return "Encrypt Error" ;
        }
        return crypted;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}