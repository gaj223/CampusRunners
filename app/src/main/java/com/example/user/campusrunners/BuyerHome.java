package com.example.user.campusrunners;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cassidy on 3/19/2018.
 */

public class BuyerHome extends AppCompatActivity {
    // bottom bar -- similar to runner but add button for cart
    // home page is simple -- four static businesses
    /*         String var="hello";
        Log.e("myTag","I am here"+var);
        */

    private TextView mTextMessage;

    // bottom navigation bar
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("Home");
                    return true;
                case R.id.navigation_orders:
                    mTextMessage.setText("Orders");
                    return true;
                case R.id.navigation_cart:
                    mTextMessage.setText("Cart");
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText("Profile");
                    return true;
            }
            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner_home);

        // To allow info to pass to accept job page
        intentAccept =new Intent(this, AcceptJob.class);

        // Gets list of Open Orders
        openOrders = addOrders();

        // Create bottom bar
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
