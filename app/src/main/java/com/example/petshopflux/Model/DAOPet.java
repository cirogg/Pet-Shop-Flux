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
    private static final String BASEURL = "https://petstore.swagger.io/v2/";

    public DAOPet(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        //Creo Retrofit.
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.client(httpClient.build()).build();
        servicePets = retrofit.create(ServicePets.class);
    }

    //LLamada por Retrofit a la API para obtener pets by status
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

    //LLamada por Retrofit a la API para obtener pet by id
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

            }
        });
    }

}
