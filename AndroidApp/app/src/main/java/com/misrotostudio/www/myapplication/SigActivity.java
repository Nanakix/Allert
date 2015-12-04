package com.misrotostudio.www.myapplication;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.misrotostudio.www.myapplication.app.AppConfig;
import com.misrotostudio.www.myapplication.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SigActivity extends AppCompatActivity {

    private ImageButton sig_button;
    private ArrayList types;
    private ArrayList types_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig);

        sig_button = (ImageButton) findViewById(R.id.sig_button);

        types = new ArrayList();
        types_level = new ArrayList();

        getTypesReq();

        sig_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SigActivity.this, DetailActivity.class);
                i.putStringArrayListExtra("types_array", types);
                i.putStringArrayListExtra("level_array", types_level);
                Log.e("ARRB", types.toString());
                startActivity(i);
            }
        });



    }

    protected void getTypesReq(){
        String tag_string_req = "get_types_req";
        String uri = new String(AppConfig.api_url + "?f=getTypes");


        StringRequest request = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TYPES RES", response.toString());
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i =0; i<jsonArray.length(); i++){
                                JSONObject jObj = jsonArray.getJSONObject(i);
                                types.add(i, jObj.get("nom"));
                                Log.e("JSON", jObj.get("nom") + " ");
                                types_level.add(i, jObj.get("coef_de_gravite"));

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.e("JSONErr", "ERRROOO");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }
}
