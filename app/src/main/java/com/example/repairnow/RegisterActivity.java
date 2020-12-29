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
    EditText naam, email, wachtwoord,telefoon;
    Button register, naarloginpagina;
    RadioButton klant,mechanieker;
    String inaam,iemail, iwachtwoord,ikeuze,itelefoon;

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
        telefoon = findViewById(R.id.telefoon);
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
            public void onClick(View arg0) {
                Registreer();
            }
        });

    }

    private void Registreer() {
        itelefoon = telefoon.getText().toString();
        inaam = naam.getText().toString();
        iemail = email.getText().toString();
        iwachtwoord = wachtwoord.getText().toString();

        if (!(CheckEmail(email))) {
                email.setError("E-mailadres is niet geldig");
            }
            if (LegeVeld(wachtwoord)) {
                wachtwoord.setError("Wachtwoord is niet ingevuld");
            }
            if (LegeVeld(naam)){
                naam.setError("Naam is niet ingevuld");
            }
            if (!CheckTelefoon(telefoon)){
                telefoon.setError("Telefoonnummer is niet geldig");
            }
                    if (CheckEmail(email) && !LegeVeld(wachtwoord) && !LegeVeld(naam) && CheckTelefoon(telefoon)){
                    // keuze //
                    if (klant.isChecked()){
                        ikeuze = "Klant";
                    }
                    if (mechanieker.isChecked()){
                        ikeuze = "Mechanieker";
                    }

                        mAuth.createUserWithEmailAndPassword(iemail, iwachtwoord)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // opslaan in realtime database //
                                            db = FirebaseDatabase.getInstance();
                                            ref = db.getReference("users");
                                            Opslag opslagruimte = new Opslag(inaam,iemail,iwachtwoord,itelefoon,ikeuze);
                                            ref.child(mAuth.getUid()).setValue(opslagruimte);
                                            Toast.makeText(getApplicationContext(), "Succesvol geregistreerd!", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                        } else {
                                            Toast.makeText(getApplicationContext(), "E-mailadres is niet geldig/wachtwoord is te zwak", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                }
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

    boolean CheckTelefoon(EditText text){
        if (text.length() != 10){
            return false;
        }
        else{
            return true;
        }
    }
}