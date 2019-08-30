package com.example.petshopflux.Controller;

import android.content.Context;
import com.example.petshopflux.Model.DAOPet;
import com.example.petshopflux.Model.Pet;
import com.example.petshopflux.Utils.ResultListener;
import java.math.BigInteger;
import java.util.List;

public class PetController {

    private Context context;

    public PetController(Context context) {
        this.context = context;
    }

    //Obtener Pets by Status. El escuchador esta esperando que el DAO termine de recibir (o no) la data.
    public void getPetByStatus(final ResultListener<List<Pet>> viewListener, String status){

        ResultListener<List<Pet>> controllerListener = new ResultListener<List<Pet>>() {
            @Override
            public void finish(List<Pet> result) {
                viewListener.finish(result);
            }
        };

        DAOPet daoPet= new DAOPet();
        daoPet.getPetsByStatus(controllerListener,status);
    }

    //Obtener Pet by ID. El escuchador esta esperando que el DAO termine de recibir (o no) la data.
    public void getPetById(final ResultListener<Pet> viewListener, BigInteger id){

        ResultListener<Pet> controllerListener = new ResultListener<Pet>() {
            @Override
            public void finish(Pet result) {
                viewListener.finish(result);
            }
        };

        DAOPet daoPet= new DAOPet();
        daoPet.getPetsByID(controllerListener,id);
    }
}
