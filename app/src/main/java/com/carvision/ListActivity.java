package com.carvision;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.carvision.model.SQLiteAdapter;

public class ListActivity extends AppCompatActivity {

    private RecyclerView carListRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        carListRecyclerView  = findViewById(R.id.carListRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        carListRecyclerView.setLayoutManager(layoutManager);
        carListRecyclerView.setAdapter(new CarListAdapter(this, new SQLiteAdapter(this).loadAll()));
    }


}
