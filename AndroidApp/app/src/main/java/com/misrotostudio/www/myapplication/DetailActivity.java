package com.misrotostudio.www.myapplication;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.location.LocationServices;

public class DetailActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener{

    private String android_id;

    private Spinner type_spinner;
    private Button sig_button;
    private EditText comm_field;
    private JSONObject JSON_alerte;

    private GoogleApiClient mGoogleApiClient;
    //protected Location mLastLocation;
    protected Location location;
    protected LocationManager mLocationManager;
    private LocationListener mLocationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //GOOGLELOCATE
        buildGoogleApiClient();

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle b){

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }
        };

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        /*
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1,
                    1, mLocationListener);
            //mLastLocation = mLocationManager.getLastKnownLocation(");
            //Log.e("MAPS2", ""+ mLastLocation.getLatitude() + mLastLocation.getLongitude());
        }catch(SecurityException e){
            e.printStackTrace();
        }
        */
        location = getLocation();




        //Spinner SET
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        type_spinner = (Spinner) findViewById(R.id.type_spinner);
        String[] types_array = {"Viol", "Feu", "Tsunami", "Autre"};

        ArrayAdapter<String> type_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,types_array);

        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        type_spinner.setAdapter(type_adapter);

        //EDIT TEXT SET
        comm_field = (EditText) findViewById(R.id.commentaire_text);

        Editable text = comm_field.getText();



        //GET SPINN

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String my_type = String.valueOf(type_spinner.getSelectedItem());
                Toast.makeText(DetailActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : " + my_type,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });

        //BUTTON SEND
        sig_button = (Button) findViewById(R.id.signal_button);

        sig_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setJObject();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void setJObject(){
        try{
            JSON_alerte.put("id", android_id);
            //JSON_alerte.put()
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        /*
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
            //        mLastLocation.getLatitude()));
            Log.e("MAPS", "LATITUDE: " + mLastLocation.getLatitude() + " LONGITUDE: " + mLastLocation.getLongitude());
            //mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
            //        mLastLocation.getLongitude()));
        } else {
            Toast.makeText(this, "No Location Detected", Toast.LENGTH_LONG).show();
        }
        */
    }



    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i("MAPS", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        //Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


    public Location getLocation() {
        try {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


            // getting network status
            boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                // First get location from Network Provider

                //get the location by gps
                if (isGPSEnabled) {
                    //if (location == null) {
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1, mLocationListener);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (mLocationManager != null) {location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                Log.e("MAP4", location.getLatitude() + " "+ location.getLongitude());
                            }
                        }
                    //}
                }
                else if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,  1,  1, mLocationListener);
                    Log.d("Network", "Network");
                    if (mLocationManager != null) {
                        location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            Log.e("MAP3", location.getLatitude() + " "+ location.getLongitude());
                        }
                    }
                }
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return location;
    }
}
