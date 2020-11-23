package com.example.volleyrequest_hw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button requestButton = (Button) findViewById(R.id.request_btn);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest();
            }
        });
    }
    private void makeRequest() {
        // Instantiate the RequestQueue.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                // Show an explanation to the user
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0);
            }
        }
        else {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://date.jsontest.com/";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String date;
                            String mse;
                            String time;
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                date = jsonResponse.getString("date");
                                mse = jsonResponse.getString("milliseconds_since_epoch");
                                time = jsonResponse.getString("time");
                            } catch (JSONException e) {
                                date = "Unable to parse date";
                                mse = "Unable to parse mse";
                                time = "Unable to parse time";
                            }
                            TextView dateTV = (TextView)findViewById(R.id.dateValue_tv);
                            TextView mseTV = (TextView)findViewById(R.id.mseValue_tv);
                            TextView timeTV = (TextView)findViewById(R.id.timeValue_tv);
                            dateTV.setText(date);
                            mseTV.setText(mse);
                            timeTV.setText(time);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Request failed: " + error.toString(), Toast.LENGTH_LONG).show();
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
}