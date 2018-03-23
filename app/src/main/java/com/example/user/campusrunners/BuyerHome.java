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

    // listener for the bottom navigation bar
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
            // create a new business
            Business business = new Business(businessId[i]);
            business.setName(businessName[i]);
            // get all the items associated with this business
            createBusinessItems(business);
            // add business to arraylist
            businesses.add(business);
        }
        // this is the arraylist of businesses
        return businesses;
    }

    // creates the items for a given business and adds them to business arraylist
    public void createBusinessItems(Business business){
        // TODO should be API calls to get all the items for a business from the DB
        // TODO right now will only add items for chickfila
        switch (business.getId()){
            case 0: // chick-fil-a
                addChickFilAItems(business);
                break;
            default:
                break;
        }
    }

    // add the items to the chickfila business
    // TODO this should not be hard coded
    public void addChickFilAItems(Business b){
        int bid = b.getId();
        Item item = new Item(1, "Chicken Sandwich", bid, 4.00);
        b.addItem(item);
        item = new Item(2, "Waffle Fries", bid, 2.00);
        b.addItem(item);
        item = new Item(3, "Lemonade", bid, 1.50);
        b.addItem(item);
        item = new Item(4, "Ice Cream Cone", bid, 1.50);
        b.addItem(item);
    }

    // Takes User to business page
    // this is an OnClick function for the buttons on the view
    public void toBusiness(android.view.View v){
        Business bus = new Business();
        switch (v.getId()) {
            case R.id.podButton:
                bus = businesses.get(3);
                break;
            case R.id.chickfilaButton:
                bus = businesses.get(0);
                break;
            case R.id.papajohnsButton:
                bus = businesses.get(1);
                break;
            case R.id.bookstoreButton:
                bus = businesses.get(2);
                break;
        }

        Intent businessIntent = new Intent(BuyerHome.this, BusinessView.class);
        businessIntent.putExtra("Business", bus);
        startActivity(businessIntent);

    }

}
