package com.example.user.campusrunners;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        populateUsersList();
    }

    private void populateUsersList() {
        // Construct the data source
        ArrayList<Item> arrayOfUsers = new ArrayList<Item>();
        //JSONArray jsonArray = ...;


// Create the adapter to convert the array to views
        ItemsAdapter adapter = new ItemsAdapter(this, arrayOfUsers);
        adapter.addAll(arrayOfUsers);
// Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(adapter);
    }

}
