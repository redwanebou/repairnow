package com.example.repairnow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    // variables //
    EditText naam;
    EditText email;
    EditText wachtwoord;
    Button register;
    Button naarloginpagina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // wat wil je laten zien? //
        setContentView(R.layout.activity_register);
        // koppelen aan .xml //
        naam = findViewById(R.id.naam);
        email = findViewById(R.id.email);
        wachtwoord = findViewById(R.id.wachtwoord);
        register = findViewById(R.id.register);

        // ga terug naar loginpagina //
        final Context context = this;
        naarloginpagina = findViewById(R.id.naarloginpagina);
        naarloginpagina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        // show error na er op register is geklikt //
        register.setOnClickListener(new View.OnClickListener(){
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
        if (LegeVeld(naam) && LegeVeld(email) && LegeVeld(wachtwoord)){
            // Toast is witte balkje //
            Toast t = Toast.makeText(this, "Je hebt niks ingevuld", Toast.LENGTH_SHORT);
            t.show();
        }
        if (LegeVeld(naam)){
            naam.setError("Naam is niet ingevuld");
        }
        if (!(CheckEmail(email))){
            email.setError("E-mailadres is niet geldig");
        }
        if (LegeVeld(wachtwoord)){
            wachtwoord.setError("Wachtwoord is niet ingevuld");
        }
    }
}
