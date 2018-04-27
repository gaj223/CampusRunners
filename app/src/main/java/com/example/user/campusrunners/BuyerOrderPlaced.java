package com.example.user.campusrunners;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BuyerOrderPlaced extends AppCompatActivity {

    private TextView mTextMessage;
    public String business;
    public String listItems[];
    public float listPrices[];
    public int quantities[];
    public String order;

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
        setContentView(R.layout.activity_buyer_order_placed);

        // Order Info
        Bundle bundle = this.getIntent().getExtras();
        business = bundle.getString("business");
        listItems = bundle.getStringArray("listItems");
        listPrices = bundle.getFloatArray("listPrices");
        quantities = bundle.getIntArray("quantities");
        //order = bundle.getString("order");

        // Puts the order info on the view
        orderInfo();

        // Show Runner name if the order becomes active
        boolean active = false;
        if (active){
            TextView textElement = (TextView) findViewById(R.id.runnerName);
            textElement.setText("John Doe");
        }

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
                        i = new Intent(BuyerOrderPlaced.this, BuyerHomes.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(BuyerOrderPlaced.this, ViewAllBuyerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
                        // CJM: changed this to redirect back to the buyer home (ie it does nothing)
                        i = new Intent(BuyerOrderPlaced.this, BuyerProfile.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }



    // place order info on the view
    public void orderInfo(){

        TextView textElement = (TextView) findViewById(R.id.textBusName);
        textElement.setText(business);
        textElement = (TextView) findViewById(R.id.textDetails);
        order = "";
        String line = "";
        float total = 0;
        for (int i = 0; i < listItems.length; i++){
            if(quantities[i] > 0){
                line = quantities[i] + " " + listItems[i] + "\n";
                order = order + line;
                total = total + quantities[i] * listPrices[i];
            }
        }
        order = order + "-----------------------\n";
        order = order + "Total:             $" + total;
        textElement.setText(order);


    }

    // Takes runner to call the runner
    public void callRunner(View v){

        String number = "2102343323"; // API call to get buyer's number
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        String phone = "tel:" + number;
        callIntent.setData(Uri.parse(phone));
        startActivity(callIntent);

    }

    // Takes runner to map of UTSA
    public void toMap(View v){

        Intent mapIntent = new Intent(BuyerOrderPlaced.this, MapUTSA.class);
        startActivity(mapIntent);

    }

    // Takes buyer to rate the runner when complete button is pressed
    public void orderCompleted(View v){

        // Update Order status to completed by API Call
        Intent submit = new Intent(BuyerOrderPlaced.this, RateTheRunner.class);
        startActivity(submit);

    }

}
