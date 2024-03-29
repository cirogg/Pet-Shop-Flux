package com.example.petshopflux.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import com.example.petshopflux.R;
import com.example.petshopflux.View.Fragments.DetailFragment;
import com.example.petshopflux.View.Fragments.MapsFragment;


public class DetailActivity extends AppCompatActivity {

    public static String PET_ID = "id";
    private String pet_id;
    private FragmentManager fragmentManager;

    private FrameLayout frameLayoutAlphaView;
    private FrameLayout frameLayoutMaps;

    private  Boolean mapsOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mapsOn = false;

        frameLayoutMaps = findViewById(R.id.container_maps);
        frameLayoutAlphaView = findViewById(R.id.view_alpha);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pet_id = bundle.getString(PET_ID);

        DetailFragment detailFragment = new DetailFragment();
        loadFragmentPet(detailFragment);
    }

    //Función para cargar fragment de detalles de mascota.
    private void loadFragmentPet(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detailFragmentContainer, fragment);
        Bundle bundle = new Bundle();
        bundle.putString(PET_ID,pet_id);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    //Función para cargar fragment de mapas.
    private void loadFragmentMap(Fragment fragment){
        mapsOn = true;
        frameLayoutMaps.setVisibility(View.VISIBLE);
        frameLayoutAlphaView.setVisibility(View.VISIBLE);
        frameLayoutAlphaView.animate().alpha(0.6f);
        frameLayoutMaps.animate().alpha(1.0f);
        frameLayoutMaps.setClickable(true);
        frameLayoutAlphaView.setClickable(true);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_maps, fragment);
        fragmentTransaction.commit();

        frameLayoutAlphaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unloadFragmentMap();
            }
        });

    }

    //Función para ocultar el fragment de mapas. No hace un "unload", simplemente lo oculta.
    private void unloadFragmentMap(){
        mapsOn = false;
        frameLayoutAlphaView.animate().alpha(0f);
        frameLayoutMaps.animate().alpha(0f);
        frameLayoutMaps.setVisibility(View.INVISIBLE);
        frameLayoutAlphaView.setVisibility(View.INVISIBLE);
        frameLayoutMaps.setClickable(false);
        frameLayoutAlphaView.setClickable(false);
    }

    //Menu del Appbar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_detail,menu);

        menu.findItem(R.id.itemMap).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (mapsOn){
                    unloadFragmentMap();
                }else{
                    MapsFragment mapsFragment = new MapsFragment();
                    loadFragmentMap(mapsFragment);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
