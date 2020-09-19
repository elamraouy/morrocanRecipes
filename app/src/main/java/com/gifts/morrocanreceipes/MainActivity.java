package com.gifts.morrocanreceipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ReceipeAdapter receipeAdapter;
    private List<ListItem> listItems;
    private static final String URL_DATA = "http://10.0.2.2:8000/api/receipes/all/";
    // create app menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        // recycler view
        RecyclerView recyclerView = findViewById(R.id.receipe_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SwipeRefreshLayout refreshLayout = findViewById(R.id.swipeContainer);
        listItems = new ArrayList<>();
        loadRecyclerViewData();
        receipeAdapter = new ReceipeAdapter(listItems, getApplicationContext());
        recyclerView.setAdapter(receipeAdapter);

        // refresh data from server
        refreshLayout.setOnRefreshListener(() -> {
            loadRecyclerViewData();
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
        });

    }
    private void loadRecyclerViewData() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_DATA,
                response -> {
                    try {
                        JSONArray json = new JSONArray(response);
                        listItems.clear();
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject o = json.getJSONObject(i);
                            String title;
                            String description;
                            String receipe_image;

                            title = formatData(o.getString("title"));
                            description = formatData(o.getString("receipe"));
                            receipe_image = "https://res.cloudinary.com/dmxmme1zp/image/upload/v1599263423/IMG-20181203-WA0003_mxirnp.jpg";

                            ListItem item = new ListItem(
                                    title,
                                    description,
                                    Uri.parse(receipe_image)
                            );

                            listItems.add(item);
                        }
                        receipeAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {

                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    public void gotoAdd(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, AddReceipe.class);
        startActivity(intent);
    }

    public static String formatData(String v){
        String formated;
        formated = new String(v.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        return formated;
    }
}