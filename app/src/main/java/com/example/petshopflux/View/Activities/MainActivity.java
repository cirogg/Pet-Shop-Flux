package com.example.petshopflux.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.petshopflux.Controller.PetController;
import com.example.petshopflux.Model.Pet;
import com.example.petshopflux.R;
import com.example.petshopflux.Utils.ResultListener;
import com.example.petshopflux.View.Adapters.PetsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PetsAdapter.PetListenerInterface {


    RecyclerView recyclerViewPets;
    PetsAdapter adapterPets;

    SwipeRefreshLayout swipeRefreshLayout;
    FrameLayout frameLayoutLoading;
    LottieAnimationView lottieAnimationView;

    List<Pet> listOfPets;

    String status = "available";

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        frameLayoutLoading = findViewById(R.id.frameLayout_animation_loading_main);
        lottieAnimationView = findViewById(R.id.animation_dino_loading_main);
        lottieAnimationView.playAnimation();
        frameLayoutLoading.setVisibility(View.VISIBLE);

        listOfPets = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPets();
                //invalidateOptionsMenu();
                searchView.setQuery("", false);
                searchView.clearFocus();
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

        PetController petController = new PetController(this);
        ResultListener<List<Pet>> viewListener = new ResultListener<List<Pet>>() {
            @Override
            public void finish(List<Pet> result) {
                if (result.size() != 0){
                    listOfPets = checkNullsInListOfPets(result);
                    adapterPets.notifyDataSetChanged();
                    adapterPets.setPetList(listOfPets);
                    adapterPets.setPetListForFilter(new ArrayList<Pet>(listOfPets));
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.service_succesful), Toast.LENGTH_SHORT).show();

                    //Oculto la loading screen.
                    lottieAnimationView.cancelAnimation();
                    frameLayoutLoading.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                }else{
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.service_failure), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterPets.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public List<Pet> checkNullsInListOfPets(List<Pet> list){
        for (Pet pet : list) {
            if (pet.getName() == null || pet.getName() == "" || pet.getName().isEmpty()){
                pet.setName("Animalito sin nombre :(");
            }
        }

        return list;
    }
}
