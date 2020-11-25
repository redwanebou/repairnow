package com.example.repairnow;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

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



public class HomeActivity extends AppCompatActivity {
    /* variables */
    TextView email, name;
    MenuItem auto;
    private FirebaseAuth mAuth;
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
        auto = menu.findItem(R.id.auto);


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


    public void Database(){
        String GETMYID = user.getUid();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.child(GETMYID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // set naam //
                String usernaam = dataSnapshot.child("naam").getValue().toString();
                name.setText("Welkom " + usernaam);

            // check keuze //
                String keuze = dataSnapshot.child("keuze").getValue().toString();
                if (keuze.equals("Klant")){
                    auto.setVisible(true);
                }
                else{
                    auto.setVisible(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}