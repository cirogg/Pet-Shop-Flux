package com.example.petshopflux.Utils;
import com.example.petshopflux.Model.Pet;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicePets {

    // XML to JSon
    @Headers({"Accept: application/json"})

    // obtengo por la URL lista de mascotas
    @GET("pet/findByStatus?")
    Call<List<Pet>> getPetsByStatus(@Query("status") String status);

    // obtengo por la URL el id de la mascota
    @GET("pet/{petId}")
    Call<Pet> getPetID(@Path("petId") BigInteger petId);

}
