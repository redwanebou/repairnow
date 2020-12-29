package com.example.repairnow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/* just a simple logout function for navbar */
public class probleemremove extends Fragment {
    private DatabaseReference ref;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public probleemremove() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// lets remove this problem //
        ref = FirebaseDatabase.getInstance().getReference()
                .child("autos").child(user.getUid());
        ref.removeValue();
        Intent i = new Intent(getActivity(),
                MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Toast.makeText(getContext(), "Je probleem is verwijderd", Toast.LENGTH_SHORT).show();
    }
}