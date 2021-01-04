package com.example.repairnow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class row extends BaseAdapter {
    String[] omschrijving, merk, model, brandstof, motor, naam, telefoon,adres;
    TextView om, mer, mod, bran, mot, na, tel,ad;
    Context context;
    LayoutInflater inflater;

    public row(Context applicationContext, String[] omschrijving, String[] merk, String[] model, String[] brandstof, String[] motor, String[] naam, String[] telefoon, String[] adres) {
        this.omschrijving = omschrijving;
        this.merk = merk;
        this.model = model;
        this.brandstof = brandstof;
        this.motor = motor;
        this.naam = naam;
        this.telefoon = telefoon;
        this.adres = adres;
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return omschrijving.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup view_Grp) {
        view = inflater.inflate(R.layout.row, null);
        om = view.findViewById(R.id.omschrijving);
        mer = view.findViewById(R.id.merk);
        mod = view.findViewById(R.id.model);
        bran = view.findViewById(R.id.brandstof);
        mot = view.findViewById(R.id.motor);
        ad = view.findViewById(R.id.adress);
        na = view.findViewById(R.id.naam);
        tel = view.findViewById(R.id.telefoon);

        ad.setText(adres[i]);
        om.setText(omschrijving[i]);
        mer.setText(merk[i]);
        mod.setText(model[i]);
        bran.setText(brandstof[i]);
        mot.setText(motor[i]);
        na.setText(naam[i]);
        tel.setText(telefoon[i]);
        return view;
    }
}
