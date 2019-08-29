package com.example.petshopflux.View.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petshopflux.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private MapView mapView;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        mapView = view.findViewById( R.id.gmap );

        // si los datos no bajan nulos, seteo la información del mapa
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        gMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker( new MarkerOptions().position( new LatLng( -34.9208142, -57.9518059)).title( "Pet Shop Flux - Capital Federal" ).snippet("Yendo!"));
        CameraPosition petShopFlux2 = CameraPosition.builder().target(new LatLng(-34.9208142, -57.9518059)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera( CameraUpdateFactory.newCameraPosition(petShopFlux2));

        googleMap.addMarker( new MarkerOptions().position( new LatLng( -34.6188126, -58.3677217)).title( "Pet Shop Flux - La Plata" ).snippet("Yendo!"));
        CameraPosition petShopFlux1 = CameraPosition.builder().target(new LatLng(-34.6188126, -58.3677217)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera( CameraUpdateFactory.newCameraPosition(petShopFlux1));


    }
}
