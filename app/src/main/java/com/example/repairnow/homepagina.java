package com.example.repairnow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.repairnow.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homepagina extends Fragment {
    String GETMYID;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ListView loaddata;
    String keuze = "";

    int countrow = 100;
    String[] omschrijvingen;
    String[] merk;
    String[] model;
    String[] brandstof;
    String[] motor;
    String[] naam;
    String[] telefoon;
    String[] adres;

    String GETMYOTHERID ="";

    int count;
    public row row;


    public homepagina() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepagina, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // find the variable //
        loaddata = view.findViewById(R.id.loaddata);
        GETMYID = user.getUid();
        GetKeuze();
        LoadFromDatabase();
    }

    public void GetKeuze() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.child(GETMYID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                keuze = dataSnapshot.child("keuze").getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }});
        }


    public void LoadFromDatabase(){

        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("autos");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
        public void onDataChange(final DataSnapshot dataSnapshot) {
             countrow = (int)dataSnapshot.getChildrenCount();
             omschrijvingen = new String[countrow];
             merk= new String[countrow];
             model= new String[countrow];
             brandstof= new String[countrow];
             motor= new String[countrow];
             naam = new String[countrow];
             telefoon = new String[countrow];
             adres = new String[countrow];
             for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                 String getomschrijving = postSnapshot.child("omschrijving").getValue().toString();
                 String getmerk = postSnapshot.child("merk").getValue().toString();
                 String getmodel = postSnapshot.child("model").getValue().toString();
                 String getbrandstof = postSnapshot.child("brandstof").getValue().toString();
                 String getmotor = postSnapshot.child("motor").getValue().toString();
                 String getnaam = postSnapshot.child("naam").getValue().toString();
                 String gettelefoon = postSnapshot.child("telefoon").getValue().toString();
                 String getadres = postSnapshot.child("adres").getValue().toString();

                 omschrijvingen[count] = getomschrijving;
                 merk[count] = getmerk;
                 model[count] = getmodel;
                 brandstof[count] = getbrandstof;
                 motor[count] = getmotor;
                 naam[count] = getnaam;
                 telefoon[count] = gettelefoon;
                 adres[count] = getadres;

                count++;
            }
             row = new row(getContext(), omschrijvingen,merk,model,brandstof,motor,naam,telefoon,adres);
             loaddata.setAdapter(row);
             row.notifyDataSetChanged();
   }

        @Override
          public void onCancelled(DatabaseError databaseError) { }
       });
    }
}