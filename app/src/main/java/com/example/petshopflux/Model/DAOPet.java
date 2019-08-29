package com.example.petshopflux.Model;

import com.example.petshopflux.Utils.ResultListener;
import com.example.petshopflux.Utils.ServicePets;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAOPet {

    private Retrofit retrofit;
    private ServicePets servicePets;

    public DAOPet(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://petstore.swagger.io/v2/").addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.client(httpClient.build()).build();
        servicePets = retrofit.create(ServicePets.class);
    }

    public void getPetsByStatus(final ResultListener<List<Pet>> controllerListener, String status){
        Call<List<Pet>> retrofitCall = servicePets.getPetsByStatus(status);
        retrofitCall.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                List<Pet> petList = response.body();
                controllerListener.finish(petList);
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                controllerListener.finish(new ArrayList<Pet>());
            }
        });
    }

    public void getPetsByID(final ResultListener<Pet> controllerListener, BigInteger id){
        Call<Pet> retrofitCall = servicePets.getPetID(id);
        retrofitCall.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                Pet pet = response.body();
                controllerListener.finish(pet);
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                //TODO
            }
        });
    }

}
