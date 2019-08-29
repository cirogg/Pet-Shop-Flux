package com.example.petshopflux.View.Fragments;


import android.content.Intent;
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

import javax.xml.transform.Result;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    Pet pet;

    PetController petController;

    TextView textViewName;
    TextView textViewStatus;
    TextView textViewID;

    String petName;
    String petStatus;
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

        Bundle bundle = getArguments();
//        petID = Integer.parse(bundle.getString(DetailActivity.PET_ID));
        petID = new BigInteger(bundle.getString(DetailActivity.PET_ID));

        petController = new PetController(getActivity());
        ResultListener<Pet> viewListener = new ResultListener<Pet>() {
            @Override
            public void finish(Pet result) {
                if (result!=null){
                    pet = result;
                    loadPetData(pet);
                }
            }
        };

        petController.getPetById(viewListener,petID);

//        petName = ;
//        petStatus;
//        petID;

        return view;
    }

    private void loadPetData(Pet pet){
        textViewName.setText(pet.getName());
        textViewStatus.setText(pet.getStatus());
        textViewID.setText(String.valueOf(pet.getId()));
    }
}
