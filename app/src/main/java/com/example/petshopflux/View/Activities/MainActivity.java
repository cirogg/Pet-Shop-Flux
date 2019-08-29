package com.example.petshopflux.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.petshopflux.Controller.PetController;
import com.example.petshopflux.Model.Pet;
import com.example.petshopflux.R;
import com.example.petshopflux.Utils.ResultListener;
import com.example.petshopflux.View.Adapters.PetsAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PetsAdapter.PetListenerInterface {

    PetController petController;

    RecyclerView recyclerViewPets;
    PetsAdapter adapterPets;

    SwipeRefreshLayout swipeRefreshLayout;
    FrameLayout frameLayoutLoading;
    LottieAnimationView lottieAnimationView;

    String status = "available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        frameLayoutLoading = findViewById(R.id.frameLayout_animation_loading_main);
        lottieAnimationView = findViewById(R.id.animation_dino_loading_main);
        lottieAnimationView.playAnimation();
        frameLayoutLoading.setVisibility(View.VISIBLE);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPets();
            }
        });

        createRecycler();
        loadPets();


    }



    private void createRecycler(){
        recyclerViewPets = findViewById(R.id.recyclerView_main_pets);
        adapterPets = new PetsAdapter(this,this);
        recyclerViewPets.setAdapter(adapterPets);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewPets.setLayoutManager(layoutManager);
    }

    private void loadPets(){

//        frameLayoutLoading.setVisibility(View.VISIBLE);
//        lottieAnimationView.playAnimation();

        PetController petController = new PetController(this);
        ResultListener<List<Pet>> viewListener = new ResultListener<List<Pet>>() {
            @Override
            public void finish(List<Pet> result) {
                if (result != null){
                    adapterPets.setPetList(result);
                    Toast.makeText(MainActivity.this, "Pets cargadas correctamente", Toast.LENGTH_SHORT).show();

                    //Oculto la loading screen.
                    lottieAnimationView.cancelAnimation();
                    frameLayoutLoading.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                }else{
                    Toast.makeText(MainActivity.this, "Pets FAILURE", Toast.LENGTH_SHORT).show();
                }
            }
        };

        petController.getPetByStatus(viewListener,status);
    }

    @Override
    public void petClicked(Pet pet) {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailActivity.PET_ID, String.valueOf(pet.getId()));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
