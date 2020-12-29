package com.example.repairnow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profiel extends Fragment {

    // check login state //
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    EditText naam, telefoon,oldww,newww;
    Button aanpassen;
    RadioButton klant,mechanieker;
    String inaam,ikeuze,itelefoon,ioldww,inewww;
    String GETMYID;


    public profiel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profiel, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find the variable //
        naam = view.findViewById(R.id.naam);
        telefoon = view.findViewById(R.id.telefoon);
        oldww = view.findViewById(R.id.oldww);
        newww = view.findViewById(R.id.newww);
        aanpassen = view.findViewById(R.id.aanpassen);
        klant = view.findViewById(R.id.klant);
        mechanieker = view.findViewById(R.id.mechanieker);

        UpdateProfile();
    }

    public void UpdateProfile() {

        GETMYID = user.getUid();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.child(GETMYID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                // show me the value's from the database //
                naam.setText(dataSnapshot.child("naam").getValue().toString());
                telefoon.setText(dataSnapshot.child("telefoon").getValue().toString());


                if (dataSnapshot.child("keuze").getValue().toString().equals("Klant")) {
                    klant.setChecked(true);
                } else {
                    mechanieker.setChecked(true);
                }
                aanpassen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // collect the data //
                        inaam = naam.getText().toString();
                        itelefoon = telefoon.getText().toString();
                        ikeuze = dataSnapshot.child("keuze").getValue().toString();
                        ioldww = oldww.getText().toString();
                        inewww = newww.getText().toString();

                        if (LegeVeld(naam)){
                            naam.setError("Je naam mag niet leeg zijn");
                        }
                        if (LegeVeld(newww) && !LegeVeld(oldww)){
                            newww.setError("Je nieuwe wachtwoord mag niet leeg zijn");
                        }

                        if (!LegeVeld(naam) && CheckTelefoon(telefoon)){

                        if (!inaam.equals(dataSnapshot.child("naam").getValue().toString())) {
                            // change the value //
                            dataSnapshot.getRef().child("naam").setValue(inaam);
                            LinkToHome();
                            Toast.makeText(getContext(), "Je naam is succesvol aangepast", Toast.LENGTH_SHORT).show();
                        } else {
                            naam.setError("Je hebt niks veranderd aan je naam");
                        }

                        if (!itelefoon.equals(dataSnapshot.child("telefoon").getValue().toString())) {
                            // change the value //
                            dataSnapshot.getRef().child("telefoon").setValue(itelefoon);
                            LinkToHome();
                            Toast.makeText(getContext(), "Je telefoonnummer is succesvol aangepast", Toast.LENGTH_SHORT).show();
                        } else {
                            telefoon.setError("Telefoonnummer is niet toegelaten");
                        }


                        if (klant.isChecked() && ikeuze.equals("Mechanieker")) {
                            dataSnapshot.getRef().child("keuze").setValue("Klant");
                            LinkToHome();
                            Toast.makeText(getContext(), "Je keuze is aangepast naar klant", Toast.LENGTH_SHORT).show();
                        }

                        if (mechanieker.isChecked() && ikeuze.equals("Klant")) {
                            dataSnapshot.getRef().child("keuze").setValue("Mechanieker");
                            LinkToHome();
                            Toast.makeText(getContext(), "Je keuze is aangepast naar mechanieker", Toast.LENGTH_SHORT).show();
                        }

                        // ww //
                        if (ioldww.equals(dataSnapshot.child("wachtwoord").getValue().toString()) && !LegeVeld(newww) ) {
                            // change in auth //
                            user.updatePassword(inewww)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                dataSnapshot.getRef().child("wachtwoord").setValue(inewww);
                                                LinkToHome();
                                                Toast.makeText(getContext(), "Je wachtwoord is aangepast", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                newww.setError("Je nieuwe wachtwoord is te zwak");
                                            }
                                        }
                                    });
                        }
                        else{
                            oldww.setError("Je oude wachtwoord komt niet overeen met de nieuwe");
                        }
                    }
                    }
                });
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void LinkToHome(){
        Intent i = new Intent(getActivity(),
                HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    // check of email wel geldig is //
    boolean CheckEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    // check op lege velden //
    boolean LegeVeld(EditText text) {
        CharSequence leeg = text.getText().toString();
        return TextUtils.isEmpty(leeg);
    }
    boolean CheckTelefoon(EditText text){
        if (text.length() != 10){
            return false;
        }
            return true;
    }
}
