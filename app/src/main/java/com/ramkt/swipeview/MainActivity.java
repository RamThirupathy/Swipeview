package com.ramkt.swipeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rview);
        final LinearLayoutManager layoutManger = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManger);
        recyclerView.setAdapter(new SwipeAdapter(this));

    }

}
