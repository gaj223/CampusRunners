package com.example.user.campusrunners;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BuyerPlaceOrder extends AppCompatActivity {

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
        setContentView(R.layout.activity_buyer_place_order);

        // Order Info
        Bundle bundle = this.getIntent().getExtras();
        business = bundle.getString("business");
        listItems = bundle.getStringArray("listItems");
        listPrices = bundle.getFloatArray("listPrices");
        quantities = bundle.getIntArray("quantities");

        // Puts the order info on the view
        orderInfo();

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
                        i = new Intent(BuyerPlaceOrder.this, BuyerHomes.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(BuyerPlaceOrder.this, ViewAllBuyerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
                        // CJM: changed this to redirect back to the buyer home (ie it does nothing)
                        i = new Intent(BuyerPlaceOrder.this, BuyerProfile.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }

    // place order info on the view
    public void orderInfo(){

        TextView textElement = (TextView) findViewById(R.id.businessName);
        textElement.setText(business);
        textElement = (TextView) findViewById(R.id.order);
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

    // place order when button pressed
    public void placeOrder(View v){

        //API Call to place order (insert into to database)

        EditText edit  = (EditText)  findViewById(R.id.inputbuyernote);
        String note = edit.getText().toString(); // holds the buyer input for their note
        edit  = (EditText)  findViewById(R.id.inputbuyerlocation);
        String location = edit.getText().toString(); // holds the buyer location

        // Change to add to BuyerCart.class
        Intent x = new Intent(BuyerPlaceOrder.this, BuyerOrderPlaced.class);
        Bundle bundle = new Bundle();
        bundle.putString("business", business);
        bundle.putStringArray("listItems", listItems);
        bundle.putFloatArray("listPrices", listPrices);
        bundle.putIntArray("quantities", quantities);
        bundle.putString("order",order);
        x.putExtras(bundle);
        startActivity(x);

    }

}
