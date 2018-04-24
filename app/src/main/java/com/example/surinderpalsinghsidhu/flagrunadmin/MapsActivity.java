package com.example.surinderpalsinghsidhu.flagrunadmin;

import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference root = database.getReference();
    PolylineOptions options;
    ArrayList<LatLng> vertices;
    private void addMarkers(){
        if(mMap != null){


            LatLng torontoPublic = new LatLng(43.7742667825012,-79.33657939414968);
            LatLng missisauga = new LatLng(43.77450693844755, -79.33477694969167);
            LatLng vaughan = new LatLng(43.77333910214474,	-79.33499109484802);

            LatLng torontoCity = new LatLng(43.77321514838842, -79.33562141396652);
            LatLng brampton =  new LatLng(43.7742667825012,-79.33657939414968);
//new LatLng(43.77331727809993, -79.3353009223938)

            List<LatLng> list = new ArrayList<>();
            list.add(torontoPublic);
            list.add(missisauga);
            list.add(vaughan);
            list.add(torontoCity);
            list.add(brampton);
            list.add(torontoPublic);
            vertices = new ArrayList<>();
            options = new PolylineOptions().width(2).color(Color.BLUE).geodesic(true);


            PolygonOptions rectOptions = new PolygonOptions()
                    .add(torontoPublic,
                            missisauga,
                            vaughan,torontoCity,brampton, torontoPublic);
            Polygon polygon = mMap.addPolygon(rectOptions);
            polygon.setFillColor(Color.LTGRAY);
            for (int z = 0; z < list.size(); z++) {
                LatLng point = list.get(z);
                vertices.add(point);
                options.add(point);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(torontoCity));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(missisauga, 17.0f));//10
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        getData();
    }


public void getData(){



    root.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            mMap.clear();

addMarkers();
            for (DataSnapshot postSnapshot : snapshot.child("Team A").getChildren()) {


                Locations location = postSnapshot.getValue(Locations.class);

                Log.d("test longitude", String.valueOf(location.longitude));
                Log.d("test latitude", String.valueOf(location.latitude));
                Log.d("test playerName", String.valueOf(location.Name));
                String h=String.valueOf(location.Name);
                if(h.equals("Team A Flag")){

                    LatLng sydney = new LatLng(Double.valueOf(location.latitude), Double.valueOf(location.longitude));
                    mMap.addMarker(new MarkerOptions().position(sydney).title(location.Name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                }else{

                    LatLng sydney = new LatLng(Double.valueOf(location.latitude), Double.valueOf(location.longitude));
                    mMap.addMarker(new MarkerOptions().position(sydney).title(location.Name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                }


            }


            for (DataSnapshot postSnapshot : snapshot.child("Team B").getChildren()) {


                Locations location = postSnapshot.getValue(Locations.class);

                Log.d("test longitude", String.valueOf(location.longitude));
                Log.d("test latitude", String.valueOf(location.latitude));
                Log.d("test playerName", String.valueOf(location.Name));
                String hw=String.valueOf(location.Name);
                if(hw.equals("Team B Flag")){

                    LatLng sydney = new LatLng(Double.valueOf(location.latitude), Double.valueOf(location.longitude));
                    mMap.addMarker(new MarkerOptions().position(sydney).title(location.Name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                }else {

                    LatLng sydney = new LatLng(Double.valueOf(location.latitude), Double.valueOf(location.longitude));
                    mMap.addMarker(new MarkerOptions().position(sydney).title(location.Name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                }

            }


        }
        @Override
        public void onCancelled(DatabaseError firebaseError) {
            /*
             * You may print the error message.
             **/
        }
    });
}






}


