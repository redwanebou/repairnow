package com.example.repairnow;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.View.OnTouchListener;

import org.jetbrains.annotations.NotNull;


public class HomeActivity extends AppCompatActivity {
    /* variables */
    TextView email, name;
    MenuItem probleem, probleemremove;
    private FirebaseAuth mAuth;
    boolean checkpoint;
    // check login state //
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final DrawerLayout homepage = findViewById(R.id.homepage);

        findViewById(R.id.opennav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homepage.openDrawer(GravityCompat.START);
            }
        });

        // give menu icon a color //
        NavigationView nav = findViewById(R.id.menu);
        nav.setItemIconTintList(null);

        // find menu item's //
        Menu menu = nav.getMenu();
        probleem = menu.findItem(R.id.probleem);
        probleemremove = menu.findItem(R.id.probleemremove);


        // call up the navcontroller //
        NavController con = Navigation.findNavController(this, R.id.navfrag);
        NavigationUI.setupWithNavController(nav, con);

        // everything about menu lijst //
        View header = nav.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.gebruikersnaam);
        email = (TextView) header.findViewById(R.id.showmail);


        mAuth = FirebaseAuth.getInstance();
        String emaill = mAuth.getCurrentUser().getEmail();

        // set email //
        email.setText(emaill);

        // haal uit database //
        Database();

    }

    public void Database() {
        final String GETMYID = user.getUid();
        CheckTheProb(GETMYID);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.child(GETMYID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // set naam //
                name.setText("Welkom " + dataSnapshot.child("naam").getValue().toString());

                // check keuze && of het al aangemaakt is //
                String keuze = dataSnapshot.child("keuze").getValue().toString();
                if (keuze.equals("Klant") && !checkpoint) {
                    probleem.setVisible(true);
                    probleemremove.setVisible(false);
                }

                if (checkpoint){
                    probleem.setVisible(false);
                    probleemremove.setVisible(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void CheckTheProb(String GETMYID) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("autos").child(GETMYID);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    checkpoint = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }
}