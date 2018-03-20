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
    /*  String var="hello";
        Log.e("myTag","I am here"+var);
    */

    private TextView mTextMessage;
    public Intent intentAccept;
    private ArrayList<Business> businesses;

    // bottom runner_navigation bar
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
        setContentView(R.layout.activity_buyer_home);

        // To allow info to pass to accept job page
        intentAccept = new Intent(this, AcceptJob.class);

        // get all of the businesses as an arraylist
        // CJM this will be hard coded for now
        businesses = getBusinesses();

        // create buttons for each of the businesses
        createBusinessButtons(businesses);

        // Create bottom bar
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Allow user to navigate between activities
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        i = new Intent(BuyerHome.this, BuyerHome.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(BuyerHome.this, ViewAllRunnerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_cart:
                        i = new Intent( BuyerHome.this, BuyerCart.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
                        // CJM: changed this to redirect back to the buyer home (ie it does nothing)
                        i = new Intent(BuyerHome.this, BuyerHome.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }

    // Create an arraylist of all the known businesses
    public ArrayList<Business> getBusinesses()
    {
        ArrayList<Business> businesses = new ArrayList<>();

        // CJM TODO This will be an API call to get all the businesses
        int businessId[] = {0, 1, 2, 3};

        for (int i=0; i<businessId.length; i++){
            Business business = new Business(businessId[i]);
            businesses.add(business);
        }

        return businesses;
    }

    // Create and show all the buttons for the businesses
    public void createBusinessButtons(ArrayList<Business> businesses)
    {

    }
}
