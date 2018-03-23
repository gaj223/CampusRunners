package com.example.user.campusrunners;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class BuyerHome extends AppCompatActivity {

    private TextView mTextMessage;
    private ArrayList<Business> businesses;


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
                case R.id.navigation_cart:
                    mTextMessage.setText(R.string.title_cart);
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
        setContentView(R.layout.activity_buyer_home);

        // get all of the businesses as an arraylist
        // CJM this will be hard coded for now
        businesses = getBusinesses();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation); // this is added if 4 or more icons
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
                        //i = new Intent(BuyerHomes.this, ViewAllRunnerOrders.class);
                        //startActivity(i);
                        break;
                    case R.id.navigation_cart:
                        //i = new Intent( BuyerHomes.this, BuyerCart.class);
                        //startActivity(i);
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
        String businessName[] = {"Chick-Fil-A", "Papa John's", "UTSA Bookstore", "POD"};

        for (int i=0; i<businessId.length; i++){
            Business business = new Business(businessId[i]);
            business.setName(businessName[i]);
            businesses.add(business);
        }

        return businesses;
    }

    // Takes User to bussiness page
    public void toBussiness(View v){
        ImageButton b = new ImageButton(this);
        Business bus = new Business(1);
        switch (b.getId()) {
            case R.id.podButton:
                bus = businesses.get(1);
            case R.id.chickfilaButton:
                bus = businesses.get(2);
            case R.id.papajohnsButton:
                bus = businesses.get(3);
            case R.id.bookstoreButton:
                bus = businesses.get(4);
        }

        // change RunnerHome.class to BusinessView.class
        Intent bussinessIntent = new Intent(BuyerHome.this, BusinessView.class);
        bussinessIntent.putExtra("Business", bus);
        startActivity(bussinessIntent);

    }

}
