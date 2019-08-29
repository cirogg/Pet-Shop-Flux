package com.example.petshopflux.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshopflux.Model.Pet;
import com.example.petshopflux.R;

import java.util.ArrayList;
import java.util.List;

public class PetsAdapter extends RecyclerView.Adapter {

    public Context context;

    private List<Pet> petList;
    private PetListenerInterface petListenerInterface;

    public PetsAdapter(Context context,PetListenerInterface petListenerInterface) {
        this.context = context;
        this.petList = new ArrayList<>();
        this.petListenerInterface = petListenerInterface;
    }

    public void setPetList(List<Pet> petList) {
        if (petList != null){
            this.petList.clear();
            this.petList = petList;
            notifyDataSetChanged();
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cell = inflater.inflate(R.layout.cell_main_pet,parent,false);
        ViewHolderPet viewHolderPet = new ViewHolderPet(cell);

        return viewHolderPet;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Pet pet = petList.get(position);
        ViewHolderPet viewHolderPet = (ViewHolderPet) holder;
        viewHolderPet.setDataPets(pet);

    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public class ViewHolderPet extends RecyclerView.ViewHolder {

        private TextView textViewPetName;

        public ViewHolderPet(@NonNull View itemView) {
            super(itemView);

            textViewPetName = itemView.findViewById(R.id.textViewPetName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    petListenerInterface.petClicked(petList.get(getAdapterPosition()));
                }
            });
        }

        public void setDataPets(Pet pet){
           if (pet.getName() != null){
               textViewPetName.setText(pet.getName());
           }
        }
    }

    public interface PetListenerInterface {
        void petClicked(Pet pet);
    }
}
