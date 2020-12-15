package com.example.volleyrequest_hw;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onclick(View v) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://date.jsontest.com/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String date;
                        String mse;
                        String time;
                        try {
                            date = response.getString("date");
                            mse = response.getString("milliseconds_since_epoch");
                            time = response.getString("time");
                        } catch (JSONException e) {
                            date = "Unable to parse date";
                            mse = "Unable to parse mse";
                            time = "Unable to parse time";
                        }
                        TextView dateTV = (TextView) findViewById(R.id.dateValue_tv);
                        TextView mseTV = (TextView) findViewById(R.id.mseValue_tv);
                        TextView timeTV = (TextView) findViewById(R.id.timeValue_tv);
                        dateTV.setText(date);
                        mseTV.setText(mse);
                        timeTV.setText(time);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Request failed: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        request.setTag("TAG");
        queue.add(request);
    }
}