package com.example.user.campusrunners;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class BuyerHomes extends AppCompatActivity {

    private TextView mTextMessage;
    public ArrayList<Business> businesses;
    public String businessName[] = {"Chick-Fil-A", "Papa John's", "UTSA Bookstore", "POD"};



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_orders:
                    mTextMessage.setText(R.string.title_orders);
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.title_profile);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_homes);

        // get all of the businesses as an arraylist
        // CJM this will be hard coded for now
        //businesses = getBusinesses();

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
                        i = new Intent(BuyerHomes.this, BuyerHomes.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(BuyerHomes.this, ViewAllBuyerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
                        // CJM: changed this to redirect back to the buyer home (ie it does nothing)
                        i = new Intent(BuyerHomes.this, BuyerProfile.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }

    // Create an arraylist of all the known businesses
    /*public ArrayList<Business> getBusinesses()
    {
        ArrayList<Business> businesses = new ArrayList<>();

        // CJM TODO This will be an API call to get all the businesses
        int businessId[] = {0, 1, 2, 3};
        String businessName[] = {"Chick-Fil-A", "Papa John's", "UTSA Bookstore", "POD"};

        for (int i=0; i<businessId.length; i++){
            Business business = new Business(businessId[i]);
            business.setName(businessName[i]);
            businesses.add(business);
        }

        return businesses;
    }*/

    // Takes User to Chick Fil A page
    public void toChickFilA(View v){
        String bus = businessName[0];
        int id = 1;

        // change RunnerHome.class to BusinessView.class
        Intent bussinessIntent = new Intent(BuyerHomes.this, BusinessViews.class);
        bussinessIntent.putExtra("Business", bus);
        bussinessIntent.putExtra("BusinessID", id);
        startActivity(bussinessIntent);

    }

    // Takes User to Papa Johns page
    public void toPapaJohns(View v){
        String bus = businessName[1];
        int id = 2;

        // change RunnerHome.class to BusinessView.class
        Intent bussinessIntent = new Intent(BuyerHomes.this, BusinessViews.class);
        bussinessIntent.putExtra("Business", bus);
        bussinessIntent.putExtra("BusinessID", id);
        startActivity(bussinessIntent);

    }

    // Takes User to Bookstore page
    public void toBookStore(View v){
        String bus = businessName[2];
        int id = 3;

        // change RunnerHome.class to BusinessView.class
        Intent bussinessIntent = new Intent(BuyerHomes.this, BusinessViews.class);
        bussinessIntent.putExtra("Business", bus);
        bussinessIntent.putExtra("BusinessID", id);
        startActivity(bussinessIntent);

    }

    // Takes User to POD page
    public void toPOD(View v){
        String bus = businessName[3];
        int id = 4;

        // change RunnerHome.class to BusinessView.class
        Intent bussinessIntent = new Intent(BuyerHomes.this, BusinessViews.class);
        bussinessIntent.putExtra("Business", bus);
        bussinessIntent.putExtra("BusinessID", id);
        startActivity(bussinessIntent);

    }



}
