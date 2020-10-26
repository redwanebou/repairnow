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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    // variables //
    EditText naam, email, wachtwoord;
    Button register, naarloginpagina;

    // Firebase database check auth state //
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();
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

        // reg button //
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registreer();
            }
        });

    }

    // check op lege velden //
    boolean LegeVeld(EditText text) {
        CharSequence leeg = text.getText().toString();
        return TextUtils.isEmpty(leeg);
    }

    // check of email wel geldig is //
    boolean CheckEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void Registreer() {
        String iemail, iwachtwoord;
       // inaam = naam.getText().toString(); //
        iemail = email.getText().toString();
        iwachtwoord = wachtwoord.getText().toString();

            if (!(CheckEmail(email))) {
                email.setError("E-mailadres is niet geldig");
            }
            if (LegeVeld(wachtwoord)) {
                wachtwoord.setError("Wachtwoord is niet ingevuld");
            }

               if (CheckEmail(email) &&  (!LegeVeld(wachtwoord))) {
                mAuth.createUserWithEmailAndPassword(iemail, iwachtwoord)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Succesvol geregistreerd!", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Niet gelukt!", Toast.LENGTH_LONG).show();
                                }
                            }
               });
            }
        }
    }