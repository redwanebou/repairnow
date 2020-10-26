package com.example.repairnow;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    // variables //
    EditText naam, email, wachtwoord;
    Button register, naarloginpagina;
    RadioButton klant,mechanieker;
    String inaam,iemail, iwachtwoord,ikeuze;

    // Firebase database check auth state //
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference ref;


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
        klant = findViewById(R.id.klant);
        mechanieker = findViewById(R.id.mechanieker);
        naarloginpagina = findViewById(R.id.naarloginpagina);

        // ga terug naar loginpagina //
        naarloginpagina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
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
        inaam = naam.getText().toString();
        if (!(CheckEmail(email))) {
                email.setError("E-mailadres is niet geldig");
            }
            if (LegeVeld(wachtwoord)) {
                wachtwoord.setError("Wachtwoord is niet ingevuld");
            }

// check if username exist //
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userref = dbref.child("users").child(inaam);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    naam.setError("Gebruikersnaam is niet beschikbaar");
                }

                else if (!dataSnapshot.exists() && CheckEmail(email) &&  (!LegeVeld(wachtwoord)) ){
                    // keuze //
                    if (klant.isChecked()){
                        ikeuze = "Klant";
                    }
                    if (mechanieker.isChecked()){
                        ikeuze = "Mechanieker";
                    }
                    iemail = email.getText().toString();
                    iwachtwoord = wachtwoord.getText().toString();

                        mAuth.createUserWithEmailAndPassword(iemail, iwachtwoord)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // opslaan in realtime database //
                                            db = FirebaseDatabase.getInstance();
                                            ref = db.getReference("users");
                                            Opslag opslagruimte = new Opslag(inaam,iemail,iwachtwoord,ikeuze);
                                            ref.child(inaam).setValue(opslagruimte);

                                            Toast.makeText(getApplicationContext(), "Succesvol geregistreerd!", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                        } else {
                                            email.setError("E-mailadres is al in gebruik");
                                        }
                                    }
                                });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        userref.addListenerForSingleValueEvent(eventListener);
        }
    }