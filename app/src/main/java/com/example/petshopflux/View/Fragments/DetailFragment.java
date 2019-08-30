package com.example.petshopflux.View.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.petshopflux.Controller.PetController;
import com.example.petshopflux.Model.Pet;
import com.example.petshopflux.R;
import com.example.petshopflux.Utils.ResultListener;
import com.example.petshopflux.View.Activities.DetailActivity;
import java.math.BigInteger;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    Pet pet;

    PetController petController;

    TextView textViewName;
    TextView textViewStatus;
    TextView textViewID;

    BigInteger petID;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        textViewName = view.findViewById(R.id.textViewDetailPetName);
        textViewStatus = view.findViewById(R.id.textViewDetailPetStatus);
        textViewID = view.findViewById(R.id.textViewDetailPetId);

        //Fue necesario pasarlo a BigInteger por la cantidad de digitos. Podría haberlo trabajado como String pero fui por este camino.
        Bundle bundle = getArguments();
        petID = new BigInteger(bundle.getString(DetailActivity.PET_ID));

        //Creo el controller con su listener que aguarda la respuesta del DAO de mascotas.
        petController = new PetController(getActivity());
        ResultListener<Pet> viewListener = new ResultListener<Pet>() {
            @Override
            public void finish(Pet result) {
                if (result!=null){
                    pet = checkNullsInPetName(result);
                    loadPetData(pet);
                }
            }
        };

        petController.getPetById(viewListener,petID);

        return view;
    }

    //Función para setear los textviews de la mascota.
    private void loadPetData(Pet pet){
        textViewName.setText(pet.getName());
        textViewStatus.setText(pet.getStatus());
        textViewID.setText(String.valueOf(pet.getId()));
    }

    //Función para trabajar el nombre de la mascota en caso de ser null, vacío o "".
    public Pet checkNullsInPetName(Pet pet){

        if (pet.getName() == null || pet.getName() == "" || pet.getName().isEmpty()){
            pet.setName("Animalito sin nombre :(");
        }

        return pet;
    }
}
