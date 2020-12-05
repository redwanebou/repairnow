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
    EditText naam, email;
    Button aanpassen;
    RadioButton klant,mechanieker;
    String inaam,iemail,ikeuze,iwachtwoord;
    String GETMYID;
    private FirebaseAuth mAuth;

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
        email = view.findViewById(R.id.email);
        aanpassen = view.findViewById(R.id.aanpassen);
        klant = view.findViewById(R.id.klant);
        mechanieker = view.findViewById(R.id.mechanieker);

        UpdateProfile();
    }

    public void UpdateProfile(){

        GETMYID = user.getUid();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.child(GETMYID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // show me the value's from the database //
                naam.setText(dataSnapshot.child("naam").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                if (dataSnapshot.child("keuze").getValue().toString().equals("Klant")){
                    klant.setChecked(true);
                }
                else{
                    mechanieker.setChecked(true);
                }

                aanpassen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // collect the data //
                        inaam = naam.getText().toString();
                        iemail = email.getText().toString();
                        ikeuze = dataSnapshot.child("keuze").getValue().toString();
                        iwachtwoord = dataSnapshot.child("wachtwoord").getValue().toString();

                        if (!inaam.equals((dataSnapshot.child("naam").getValue().toString()))){
                            // change the value //
                            dataSnapshot.getRef().child("naam").setValue(inaam);
                            Uitloggen();
                            Toast.makeText(getContext(), "Je naam is aangepast. Log opnieuw in", Toast.LENGTH_SHORT).show();
                        }

                        if (!iemail.equals((dataSnapshot.child("email").getValue().toString())) && (CheckEmail(email))) {
                                mAuth.fetchSignInMethodsForEmail(iemail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                        if (task.getResult().getSignInMethods().size() == 0) {
                                            // change the value in realtime database //
                                            dataSnapshot.getRef().child("email").setValue(iemail);
                                            // change the value in auth //
                                            user.updateEmail(iemail);
                                            Uitloggen();
                                        } else {
                                            email.setError("E-mailadres bestaat al");
                                        }
                                    }
                                });
                        }

                        if (!iemail.equals((dataSnapshot.child("email").getValue().toString())) && !(CheckEmail(email))){
                            email.setError("E-mailadres is niet geldig");
                        }

                        if (klant.isChecked() && ikeuze.equals("Mechanieker")){
                            dataSnapshot.getRef().child("keuze").setValue("Klant");
                            Uitloggen();
                            Toast.makeText(getContext(), "Je keuze is aangepast naar klant. Log opnieuw in", Toast.LENGTH_SHORT).show();
                        }

                        if (mechanieker.isChecked() && ikeuze.equals("Klant")){
                            dataSnapshot.getRef().child("keuze").setValue("Mechanieker");
                            Uitloggen();
                            Toast.makeText(getContext(), "Je keuze is aangepast naar mechanieker. Log opnieuw in", Toast.LENGTH_SHORT).show();
                        }
                        }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
    });
    }

public void Uitloggen(){
    FirebaseAuth.getInstance().signOut(); //signout firebase
    Intent i = new Intent(getActivity(),
            MainActivity.class);
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
            Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(i);
}

    // check of email wel geldig is //
    boolean CheckEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
