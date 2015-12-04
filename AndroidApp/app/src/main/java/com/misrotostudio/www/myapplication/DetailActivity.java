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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.location.LocationServices;
import com.misrotostudio.www.myapplication.app.AppConfig;
import com.misrotostudio.www.myapplication.app.AppController;
import com.misrotostudio.www.myapplication.helper.EditTextCustom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener{

    private String android_id;

    private Spinner type_spinner;
    private ImageButton sig_button;
    private EditText comm_field;
    private JSONObject JSON_alerte;

    private GoogleApiClient mGoogleApiClient;
    protected Location location;
    protected LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private ArrayList types_arrayL;
    private String[] types_array;
    private ArrayList types_level_arrayL;
    private String[] types_level_array;
    private String selected_type;

    private String commentaire;

    private int[] id;
    private int posId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        commentaire = new String();

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


        location = getLocation();




        //Spinner SET
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = (int[]) extras.get("types_index");
            types_arrayL = (ArrayList) extras.get("types_array");
            types_array = new String[types_arrayL.size()];
            types_level_arrayL = (ArrayList) extras.get("level_array");
            types_level_array = new String[types_level_arrayL.size()];
            types_array = (String[]) types_arrayL.toArray(types_array);
            types_level_array = (String[]) types_level_arrayL.toArray(types_level_array);

            Log.v("ARRAY", id.toString());

        }
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        type_spinner = (Spinner) findViewById(R.id.type_spinner);


        ArrayAdapter<String> type_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types_array);

        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        type_spinner.setAdapter(type_adapter);

        //EDIT TEXT SET
        comm_field = (EditText) findViewById(R.id.commentaire_text);
        
        //GET SPINN

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_type = String.valueOf(type_spinner.getSelectedItem());
                posId = type_spinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });

        //BUTTON SEND
        sig_button = (ImageButton) findViewById(R.id.signal_button);

        sig_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentaire = comm_field.getText().toString();

                sendAlerteReq();
                Toast.makeText(DetailActivity.this,
                        "Signalement envoyé!",
                        Toast.LENGTH_SHORT).show();

                Log.v("JSONSET", selected_type + location.getLongitude() + location.getLatitude() + commentaire);
                finish();
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

    private JSONObject setJObject(){
        try{
            JSON_alerte = new JSONObject();
            JSON_alerte.put("id", android_id);
            JSON_alerte.put("type", selected_type);
            JSON_alerte.put("commentaire", commentaire);
            JSONObject tmp = new JSONObject();
            tmp.put("longitude", location.getLongitude());
            tmp.put("latitude", location.getLatitude());
            JSON_alerte.put("coord", tmp);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return JSON_alerte;

    }

    protected void sendAlerteReq(){
        String tag_string_req = "set_alert_req";
        String uri = new String(AppConfig.api_url + "?f=signaler&id_type=" + id[posId] + "&pos_latitude=" + location.getLatitude() + "&pos_longitude=" + location.getLongitude() + "&description=" + commentaire);
        Log.e("S", uri);

        StringRequest request = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SEND RES", response.toString() + " " +id[posId]);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        AppController.getInstance().addToRequestQueue(request, tag_string_req);
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

    }



    @Override
    public void onConnectionFailed(ConnectionResult result) {

        Log.i("MAPS", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.

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

                if (isGPSEnabled) {

                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1, mLocationListener);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (mLocationManager != null) {location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                Log.v("MAP4", location.getLatitude() + " "+ location.getLongitude());
                            }
                        }

                }
                else if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,  1,  1, mLocationListener);
                    Log.d("Network", "Network");
                    if (mLocationManager != null) {
                        location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            Log.v("MAP3", location.getLatitude() + " "+ location.getLongitude());
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
