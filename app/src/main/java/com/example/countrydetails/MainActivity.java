package com.example.countrydetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView txtCountryName, txtCapital, txtRegion, txtPopulation, txtDemonym,
            txtTimeZone, txtNativeName, txtCallingCode, txtTopLvlDomain;
    private Button btnSearch;
    private EditText editCountryName;
    private RequestQueue requestQueue;
    private String countryName;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        Picasso.with(this)
                .load("https://image.freepik.com/free-photo/white-cloud-blue-sky-sea_74190-4488.jpg")
                .fit()
                .centerCrop()
                .into(imageView);

        requestQueue = Volley.newRequestQueue(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countryName = editCountryName.getText().toString();
                parseJson(countryName);
            }
        });
    }

    private void parseJson(String countryName){
        String url = "https://restcountries-v1.p.rapidapi.com/name/"+countryName;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject object = response.getJSONObject(0);
                            txtCountryName.setText("Country name: "+object.getString("name"));
                            txtCapital.setText("Capital: "+object.getString("capital"));
                            txtRegion.setText("Region: "+object.getString("subregion"));
                            txtPopulation.setText("Population: "+object.getInt("population"));
                            txtDemonym.setText("Demonym: "+object.getString("demonym"));
                            JSONArray timezones = object.getJSONArray("timezones");
                            txtTimeZone.setText("Time zone: "+timezones.get(0));
                            txtNativeName.setText("Native name: "+object.getString("nativeName"));
                            JSONArray callingCodes = object.getJSONArray("callingCodes");
                            txtCallingCode.setText("Calling code: "+callingCodes.get(0));
                            JSONArray topLvlDomain = object.getJSONArray("topLevelDomain");
                            txtTopLvlDomain.setText("Top level domain: "+topLvlDomain.get(0));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("x-rapidapi-host", "restcountries-v1.p.rapidapi.com");
                headers.put("x-rapidapi-key", "c2e00598a9mshc88f4d1fe79456bp1794b2jsnd229b61f95a5");
                return headers;
            }
        };
        requestQueue.add(request);
    }

    private void initializeViews(){
        txtCountryName = findViewById(R.id.txtCountryName);
        txtCapital = findViewById(R.id.txtCapital);
        txtRegion = findViewById(R.id.txtRegion);
        txtPopulation = findViewById(R.id.txtPopulation);
        txtDemonym = findViewById(R.id.txtDemonym);
        txtTimeZone = findViewById(R.id.txtTimeZone);
        txtNativeName = findViewById(R.id.txtNativeName);
        txtCallingCode = findViewById(R.id.txtCallingCode);
        txtTopLvlDomain = findViewById(R.id.txtTopLvlDomain);
        btnSearch = findViewById(R.id.btnSearch);
        editCountryName = findViewById(R.id.editCountryName);
        imageView = findViewById(R.id.imageView);
    }
}
