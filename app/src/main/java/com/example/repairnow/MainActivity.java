package com.example.repairnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
// variables //
EditText email,wachtwoord;
Button naarregister,inlog;

    // Firebase database check auth state //
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        wachtwoord = findViewById(R.id.wachtwoord);
        inlog = findViewById(R.id.inlog);
        naarregister = findViewById(R.id.naarregister);


        // naar register pagina //
            naarregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                }
            });

            // show error na er op login is geklikt //
            inlog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Inloggen();
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
    private void Inloggen() {
        String iemail, iwachtwoord;
        iemail = email.getText().toString();
        iwachtwoord = wachtwoord.getText().toString();

        if (!(CheckEmail(email))) {
            email.setError("E-mailadres is niet geldig");
        }
        if (LegeVeld(wachtwoord)) {
            wachtwoord.setError("Wachtwoord is niet ingevuld");
        }

        if (CheckEmail(email) &&  (!LegeVeld(wachtwoord))) {
            mAuth.signInWithEmailAndPassword(iemail, iwachtwoord)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Succesvol ingelogd!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));

                            } else {
                                Toast.makeText(getApplicationContext(), "E-mailadres of wachtwoord is onjuist", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}