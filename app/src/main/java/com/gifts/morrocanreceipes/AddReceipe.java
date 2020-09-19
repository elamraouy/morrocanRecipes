package com.gifts.morrocanreceipes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddReceipe extends AppCompatActivity {
    EditText title;
    EditText receipe;
    EditText ingredient;
    TextView debug;
    Button send;
    private static final String URL_Social = "http://10.0.2.2:8000/api/receipe/add/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipe);

        send = findViewById(R.id.sendreceipe);
        title = findViewById(R.id.title);
        receipe = findViewById(R.id.receipe);
        ingredient = findViewById(R.id.ingredient);

        debug = findViewById(R.id.debug);

        send.setOnClickListener(view -> sendReceipe());


    }

    private void sendReceipe(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Social,
                response -> {
                    try {
                        JSONObject o = new JSONObject(response);
                        if (o.getString("success").equals("true")) {
                            Intent intent = new Intent(AddReceipe.this, MainActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        debug.setText(e.toString());

                    }
                },
                this::onErrorResponse
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title.getText().toString());
                params.put("ingredient", ingredient.getText().toString());
                params.put("receipe", receipe.getText().toString());
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void onErrorResponse(VolleyError volleyError) {
        debug.setText(volleyError.toString());
    }

}