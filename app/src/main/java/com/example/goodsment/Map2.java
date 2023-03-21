package com.example.goodsment;

import static android.content.ContentValues.TAG;
import static android.content.RestrictionsManager.RESULT_ERROR;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.goodsment.api.APIClient;
import com.example.goodsment.api.APIInterface;
import com.example.goodsment.api.ModelRoutes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Map2 extends AppCompatActivity implements OnMapReadyCallback {
    boolean isPermissionGranter;
    private String googleApi = "AIzaSyB1MpR_z_aGwXTyhzt8ve2lCl1-a7DfQ_E";
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    GoogleMap googleMap;
    EditText edt_Source,edt_Destination;
    TextView txtKilometer;
    List<Place.Field> fields;
    Place place;
    String latitude = "", longitude = "";

    String sType;
    double lat1 = 0,long1=0,lat2=0,long2=0;
    int flag = 0;
    Button conforimlocation;






    //////////////////////////////////////////////////////////

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    String origin;
    String destination;


    APIInterface apiInterface;
    /////////////////////////////////////////////////////////




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        //confrom location button
        conforimlocation=findViewById(R.id.conforimlocation);
        conforimlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Map2.this,CartActivity.class));
            }
        });






        edt_Source = findViewById(R.id.edt_Source);
        edt_Destination = findViewById(R.id.edt_Destination);

        txtKilometer = findViewById(R.id.txtKilometer);
        edt_Source.setFocusable(false);

        checkPermission();
        if (isPermissionGranter) {
            if (checkGooglePlaServices()) {
                SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.mapFragment, supportMapFragment).commit();
                supportMapFragment.getMapAsync(this);
            } else {
                Toast.makeText(this, "Google play services not available", Toast.LENGTH_SHORT).show();
            }
        }


        apiInterface = APIClient.getClient().create(APIInterface.class);

        //Autocomplete texyview///////////////////////////////////////////////////////////////////

        edt_Source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sType = "Source";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(Map2.this);
                startActivityForResult(intent, 100);
            }

        });

        //destination

        edt_Destination.setFocusable(false);
        edt_Destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sType = "Destination";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields)
                        .build(Map2.this);
                startActivityForResult(intent, 100);

            }
        });

        //textview for kilometer

        txtKilometer.setText("0.0 Km");
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(),googleApi);

        }
        PlacesClient placesClient = Places.createClient(this);

        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.ADDRESS,Place.Field.LAT_LNG);



    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@NonNull Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            if (resultCode == RESULT_OK){
                place = Autocomplete.getPlaceFromIntent(data);
                if (sType.equals("Source")){
                    flag++;
                    Log.i(TAG,"Place ==================>>>:" + place.getName() + "," + place.getId());
                    edt_Source.setText(place.getAddress());
                    origin= place.getAddress();
                    String sSource = String.valueOf(place.getLatLng());
                    sSource = sSource.replaceAll("lat/lng:","");
                    sSource = sSource.replace("(","");
                    sSource = sSource.replace(")","");
                    String[] split = sSource.split(",");
                    lat1 = Double.parseDouble(split[0]);
                    long1 = Double.parseDouble(split[1]);

//                    Log.d(TAG, "onActivityResult: =====>>> "+lat1);
//                    Log.d(TAG, "onActivityResult: =====>>> "+long1);

//                    LatLng latLng = new LatLng(lat1, long1);
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.title("My Position");
//                    markerOptions.position(latLng);
//                    googleMap.addMarker(markerOptions);
//                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
//                    googleMap.animateCamera(cameraUpdate);

                }else {
                    flag++;
                    edt_Destination.setText(place.getAddress());
                    destination= place.getAddress();

                    String sDestination = String.valueOf(place.getLatLng());
                    sDestination=sDestination.replaceAll("lat/lng: ","");
                    sDestination = sDestination.replace("(","");
                    sDestination = sDestination.replace(")","");
                    String[] split = sDestination.split(",");
                    lat2 = Double.parseDouble(split[0]);
                    long2 = Double.parseDouble(split[1]);

//                    LatLng latLng = new LatLng(lat2, long2);
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.title("My Position");
//                    markerOptions.position(latLng);
//                    googleMap.addMarker(markerOptions);
//                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
//                    googleMap.animateCamera(cameraUpdate);




                    Call<ModelRoutes> call = apiInterface.getResponseDistance(origin, destination, googleApi);
                    call.enqueue(new Callback<ModelRoutes>() {
                        @Override
                        public void onResponse(Call<ModelRoutes> call, Response<ModelRoutes> response) {
                            if (response.isSuccessful()) {

                                ModelRoutes directions = response.body();
                                // draw the route on the map
                                List<LatLng> points = decodePolyline(directions.getRoutes().get(0).getOverviewPolyline().getPoints());
                                PolylineOptions options = new PolylineOptions()
                                        .addAll(points)
                                        .color(Color.BLUE)
                                        .width(5);
                                googleMap.addPolyline(options);

                                // add markers for the source and destination
                                LatLng originLatLng = new LatLng(directions.getRoutes().get(0).getLegs().get(0).getStartLocation().getLat(),
                                        directions.getRoutes().get(0).getLegs().get(0).getStartLocation().getLng());
                                LatLng destinationLatLng = new LatLng(directions.getRoutes().get(0).getLegs().get(0).getEndLocation().getLat(),
                                        directions.getRoutes().get(0).getLegs().get(0).getEndLocation().getLng());
                                googleMap.addMarker(new MarkerOptions().position(originLatLng).title("Source"));
                                googleMap.addMarker(new MarkerOptions().position(destinationLatLng).title("Destination"));
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelRoutes> call, Throwable t) {
                            t.printStackTrace();


                        }
                    });



                }

                if (flag >= 2){
                    distance(lat1,long1,lat2,long2);
                }

                longitude=String.valueOf(place.getLatLng().longitude);
                latitude=String.valueOf(place.getLatLng().latitude);

            } else if (resultCode == RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG,status.getStatusMessage());

            } else if (requestCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();

            }
        }
    }

    private List<LatLng> decodePolyline(String encodedPolyline) {
        List<LatLng> points = new ArrayList<>();
        int index = 0, len = encodedPolyline.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encodedPolyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encodedPolyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            points.add(new LatLng((double) lat / 1E5, (double) lng / 1E5));
        }

        return points;
    }


    private void distance(double lat1, double long1, double lat2, double long2) {
        double longDiff = long1 - long2;
        double distance = Math.sin(deg2rad(lat1))
                *Math.sin(deg2rad(lat2))
                +Math.cos(deg2rad(lat1))
                *Math.cos(deg2rad(lat2))
                *Math.cos(deg2rad(longDiff));
        distance= Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance *60* 1.1515;
        distance = distance * 1.609344;
        txtKilometer.setText(String.format(Locale.US,"%2f km",distance));
    }

    private double rad2deg(double distance) {
        return (distance * 180.0/Math.PI);
    }

    private double deg2rad(double lat1) {
        return (lat1*Math.PI/180.0);
    } // textview close

    //////////////////////////////////////////////////////////////////////////////////////

    private  void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        String searchString = edt_Source.getText().toString();
        Geocoder geocoder = new Geocoder(Map2.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOExecption: " + e.getMessage());
        }
        if (list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location :" + address.toString());
        }
    }


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the  device current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if (mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation =(Location) task.getResult();

//                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),
//                                    DEFAULT_ZOOM,"My Location");

                        }else {
                            Log.d(TAG, "onComplete: cureent location is null");
                            Toast.makeText(Map2.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.d(TAG, "getDeviceLocation: SecurityException" + e.getMessage());
        }
    }


    private void  initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(Map2.this);
    }
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
            }else {
                ActivityCompat.requestPermissions(this,permission,LOCATION_PERMISSION_REQUEST_CODE);
            }

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantRsults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        super.onRequestPermissionsResult(requestCode, permissions, grantRsults);
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantRsults.length > 0) {
                    for (int i=0; i<grantRsults.length;i++){
                        if (grantRsults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted=false;
                            Log.d(TAG, "onRequestPermissionsResult: permissions failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permissions granted");
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////

    private boolean checkGooglePlaServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {

            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 201, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Toast.makeText(Map2.this, "User Cancelled Dialog", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();

        }

        return false;
    }

    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranter = true;
                Toast.makeText(Map2.this, "Permission Granter", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(21.230286, 72.866566);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("My Position");
        markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        googleMap.animateCamera(cameraUpdate);

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

    }

    private class DirectionsResponse {
    }


}