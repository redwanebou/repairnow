package com.example.repairnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
// variables //
EditText email;
EditText wachtwoord;
Button naarregister;
Button inlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        wachtwoord = findViewById(R.id.wachtwoord);
        inlog = findViewById(R.id.inlog);
        StuurDoor();
    }
    // naar register pagina //
    public void StuurDoor() {
        final Context context = this;
        naarregister = findViewById(R.id.naarregister);
        naarregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });
        // show error na er op login is geklikt //
        inlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorLog();
            }
        });
    }
    // check op lege velden //
    boolean LegeVeld(EditText text){
        CharSequence leeg = text.getText().toString();
        return TextUtils.isEmpty(leeg);
    }
    // check of email wel geldig is //
    boolean CheckEmail(EditText text){
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    void ErrorLog(){
        if (LegeVeld(email) && LegeVeld(wachtwoord)){
            // Toast is witte balkje //
            Toast t = Toast.makeText(this, "Je hebt niks ingevuld", Toast.LENGTH_SHORT);
            t.show();
        }
        if (!(CheckEmail(email))){
            email.setError("E-mailadres is niet geldig");
        }
        if (LegeVeld(wachtwoord)){
            wachtwoord.setError("Wachtwoord is niet ingevuld");
        }
    }
 }