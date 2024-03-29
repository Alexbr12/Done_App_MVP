package com.example.done_app_mvp.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.done_app_mvp.R;
import com.example.done_app_mvp.adapter.LocaisAdapter;
import com.example.done_app_mvp.model.Pessoa;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LocaisActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener  {

    //String [] nomes;
    double [] longitude;
    double [] latitude;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> myNomesDataset;
    private ArrayList<Pessoa> listPessoas;

    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais);

        //Configure Recycler
        //Fill the list
        Intent i = getIntent();
        listPessoas = new ArrayList<>();
        Pessoa pessoa;

        Parcelable [] list = getIntent().getExtras().getParcelableArray("listParcel");
        int tam = list.length;

        myNomesDataset  = new ArrayList<>();
        longitude       = new double[tam];
        latitude        = new double[tam];

        for (int j = 0; j < tam; j++) {
            pessoa = (Pessoa)list[j];
            listPessoas.add(pessoa);
            myNomesDataset.add(pessoa.getName());
            longitude[j] = pessoa.log;
            latitude[j] = pessoa.lat;
        }



        recyclerView = (RecyclerView) findViewById(R.id.recycleLocais);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new LocaisAdapter(myNomesDataset, list);
        recyclerView.setAdapter(mAdapter);


        //Configure Map
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(7);
        LatLng bh = new LatLng(-19.923348, -43.944840);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(bh));

        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        gmap.setMyLocationEnabled(true);
        gmap.setOnMyLocationButtonClickListener(this);
        gmap.setOnMyLocationClickListener(this);

        MarkerOptions markerOptions;
        int tam = myNomesDataset.size();

        for (int i = 0; i < tam; i++) {
            markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(latitude[i], longitude[i])).title(myNomesDataset.get(i));

            Marker marker = gmap.addMarker(markerOptions);
            marker.showInfoWindow();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
