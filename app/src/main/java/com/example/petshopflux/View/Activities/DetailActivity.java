package com.example.petshopflux.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.petshopflux.R;
import com.example.petshopflux.View.Fragments.DetailFragment;
import com.example.petshopflux.View.Fragments.MapsFragment;
import com.google.android.gms.maps.MapFragment;

import java.math.BigInteger;

public class DetailActivity extends AppCompatActivity {

    public static String PET_ID = "id";
    String pet_id;
    FragmentManager fragmentManager;

    Button buttonToMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        buttonToMaps = findViewById(R.id.buttonToMaps);

        buttonToMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsFragment mapsFragment = new MapsFragment();
                loadFragmentMap(mapsFragment);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pet_id = bundle.getString(PET_ID);

        DetailFragment detailFragment = new DetailFragment();
        loadFragmentPet(detailFragment);


    }

    private void loadFragmentPet(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detailFragmentContainer, fragment);
        Bundle bundle = new Bundle();
        bundle.putString(PET_ID,pet_id);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }
    private void loadFragmentMap(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detailFragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
