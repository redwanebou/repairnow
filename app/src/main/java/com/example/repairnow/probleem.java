package com.example.repairnow;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class probleem extends Fragment {
// variables //
Spinner spin;
ArrayAdapter<String> brandstof;
EditText merk, model,motor,omschrijving,adres;
Button maakaan;
String mmerk,mmodel,mmotor,oomschrijving,ttank,address;
String[] listbrandstof = { "Diesel", "Benzine", "Hybride", "Elektrisch", "LPG/CNG (GAS)" };

    // Firebase database check auth state //
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase db;
    private DatabaseReference ref;
    String GETMYID;

    public probleem() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_probleem, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find the variable //
        spin = view.findViewById(R.id.spinner);
        brandstof = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, listbrandstof);

        // everything inside //
        merk = view.findViewById(R.id.merk);
        model = view.findViewById(R.id.model);
        motor = view.findViewById(R.id.motor);
        omschrijving = view.findViewById(R.id.omschrijving);
        adres = view.findViewById(R.id.adres);
        maakaan = view.findViewById(R.id.maakaan);
        CheckTheCar();
    }


    public void CheckTheCar(){
        GETMYID = user.getUid();
                brandstof.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(brandstof);
        // LETS BEGIN //
        maakaan.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View arg0) {
                                           // check lege velden //
                                           if (LegeVeld(merk) || LegeVeld(model) || LegeVeld(motor) || LegeVeld(omschrijving)) {
                                               Toast.makeText(getContext(), "Je hebt een veld leeg gelaten", Toast.LENGTH_SHORT).show();
                                           }

                                           // check length //
                                           if (!CheckOmschrijving((omschrijving))) {
                                               omschrijving.setError("Je omschrijving mag niet meer dan 100 karakters hebben");
                                           }

                                           if (!CheckMotor(motor)) {
                                               motor.setError("Je motor uitgedrukt in cc kan niet meer dan 4 karakters hebben");
                                           }
                                           // check if everything is OK //
                                           if (!LegeVeld(merk) && !LegeVeld(model) && !LegeVeld(motor) && !LegeVeld(omschrijving) && CheckOmschrijving((omschrijving)) && CheckMotor(motor)) {
                                               // get the INFO //
                                               mmerk = merk.getText().toString();
                                               mmodel = model.getText().toString();
                                               mmotor = motor.getText().toString();
                                               ttank = spin.getSelectedItem().toString();
                                               oomschrijving = omschrijving.getText().toString();
                                               address = adres.getText().toString();
                                               GETMYID = user.getUid();
                                               final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

                                               reference.child(GETMYID).addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                      final String naamm = dataSnapshot.child("naam").getValue().toString();
                                                      final String telefoonn = dataSnapshot.child("telefoon").getValue().toString();
                                                       // opslaan in db //
                                                       db = FirebaseDatabase.getInstance();
                                                       ref = db.getReference("autos");
                                                       Autos opslagruimte = new Autos(mmerk, mmodel, mmotor, ttank, oomschrijving,naamm,telefoonn,address);
                                                       ref.child(GETMYID).setValue(opslagruimte);
                                                       LinkToHome();
                                                       Toast.makeText(getContext(), "Je probleem is aangemaakt", Toast.LENGTH_SHORT).show();
                                                   }


                                                   @Override
                                                   public void onCancelled(DatabaseError databaseError) { }
                                               });
                                           }
                       }
        });
    }

    // check op lege velden //
    boolean LegeVeld(EditText text) {
        CharSequence leeg = text.getText().toString();
        return TextUtils.isEmpty(leeg);
    }

    boolean CheckOmschrijving(EditText text){
        if (text.length() < 100){
            return true;
        }
        return false;
    }

    boolean CheckMotor(EditText text){
        if (text.length() == 4){
            return true;
        }
        return false;
    }

    public void LinkToHome(){
        Intent i = new Intent(getActivity(),
                HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}