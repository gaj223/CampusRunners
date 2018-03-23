package com.example.user.campusrunners;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class RunnerDetailAccepted extends AppCompatActivity {

    private TextView mTextMessage;

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
        setContentView(R.layout.activity_runner_detail_accepted);

        // get RunnerId and OrderId
        Bundle bundle = getIntent().getExtras();
        Orders order = (Orders) bundle.getSerializable("Order");

        // Add Order Details to page
        addOrderDetail(order);

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
                        i = new Intent(RunnerDetailAccepted.this, RunnerHome.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(RunnerDetailAccepted.this, ViewAllRunnerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
                        i = new Intent(RunnerDetailAccepted.this, RunnerProfile.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }

    // Add Order Details
    public void addOrderDetail(Orders order){

        RatingBar buyerRate = (RatingBar) findViewById(R.id.ratingBar2);
        buyerRate.setRating(4.00f); // Get buyer rating through API Call

        TextView textElement = (TextView) findViewById(R.id.textViewStore);
        textElement.setText(order.businessName); // Add Bussiness Name
        //textElement = (TextView) findViewById(R.id.textViewDate);
        //textElement.setText(order.date); // Add Date
        textElement = (TextView) findViewById(R.id.textViewNote);
        textElement.setText(order.buyerNote); // Add Buyer's Note
        textElement = (TextView) findViewById(R.id.textViewDetails);
        String detail = "";
        for (int i = 0; i < order.items.size();i++){ // Add the list of item detail
            detail = detail +"\t"+ order.quantities.get(i) +" " + order.items.get(i) + "\n";
        }
        detail = detail +"Total                      $" +order.getTotal() + "\n";
        detail = detail +"Money Made                 $" +order.getFee();
        textElement.setText(detail);

    }

    // Takes runner to rate the buyer when complete button is pressed
    public void completeOrder(View v){

        // Update Order status to completed by API Call
        Intent submit = new Intent(RunnerDetailAccepted.this, RateTheBuyer.class);
        startActivity(submit);

    }

    // Takes runner to call the buyer
    public void callBuyer(View v){

        String number = "8327164026"; // API call to get buyer's number
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        String phone = "tel:" + number;
        callIntent.setData(Uri.parse(phone));
        startActivity(callIntent);

    }

    // Takes runner to map of UTSA
    public void toMap(View v){

        Intent mapIntent = new Intent(RunnerDetailAccepted.this, MapUTSA.class);
        startActivity(mapIntent);

    }
}
