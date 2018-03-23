package com.example.user.campusrunners;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BusinessViews extends AppCompatActivity {

    private TextView mTextMessage;
    public String listItems[] = {"Pens, Pack of 8", "Pencils, Pack of 8",
            "Composition Notebook", "Spiral Notebook", "Red Scrantron, 100 questions",
            " Red Scrantron, 25 questions", "Bluebook"};
    public float listPrices[] = {2.00f,1.50f,0.75f,1.00f,0.50f,0.25f,1.00f};
    public int quantities[] = new int[listItems.length];
    public String business;

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
        setContentView(R.layout.activity_business_views);

        // Business Name
        Bundle bundle = getIntent().getExtras();
        business = bundle.getString("Business");
        TextView textElement = (TextView) findViewById(R.id.busName);
        textElement.setText(business);
        int id =  bundle.getInt("BusinessID");



        // Add Business Items to view
        addItemsPage();

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
                        i = new Intent(BusinessViews.this, BuyerHomes.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(BusinessViews.this, ViewAllRunnerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
                        // CJM: changed this to redirect back to the buyer home (ie it does nothing)
                        i = new Intent(BusinessViews.this, BuyerHomes.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }

    public void getItems(Business b){
        //Use API call to get Business items and prices

    }

    // Adds the items that business sells
    public void addItemsPage (){

        quantities = new int[listItems.length];
        LinearLayout layout = (LinearLayout)findViewById(R.id.scroll2);

        // List each item on to the screen
        for(int i = 0; i < listItems.length; i++ ){

            quantities[i] = 0;

            // Add Item name
            TextView item = new TextView(this);
            item.setText("$" + listPrices[i] + " " + listItems[i]);
            item.setId((i + 1) * 1000);
            final int id_ = item.getId();

            // Set the quantity to 0
            TextView quantity = new TextView(this);
            quantity.setText("0");
            quantity.setId((i + 1) * 2000);
            final int id2_ = quantity.getId();

            // Plus Button
            Button plus = new Button(this);
            //plus.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            String text = "+";
            plus.setText(text);
            plus.setId(i + 1);
            plus.setMaxWidth(20);
            plus.setMaxHeight(20);
            final int id3_ = plus.getId();

            // Minus Button
            Button minus = new Button(this);
            minus.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            text = "-";
            minus.setText(text);
            minus.setId((i + 1) * -1);
            minus.setMaxWidth(20);
            minus.setMaxWidth(20);
            final int id4_ = minus.getId();

            // add to quantity
            plus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TextView x = (TextView) findViewById(id2_);
                    int q = Integer.parseInt((String)x.getText());
                    q = q + 1;
                    quantities[id3_ - 1] = q;
                    x.setText(q + "");
                }
            });

            // subtract from quantity
            minus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TextView x = (TextView) findViewById(id2_);
                    int q = Integer.parseInt((String)x.getText());
                    if (q > 0) {
                        q = q - 1;
                        x.setText(q + "");
                    }
                    quantities[id3_ - 1] = q;

                }
            });

            // Add everything to the view
            layout.addView(item);
            layout.addView(plus);
            layout.addView(quantity);
            layout.addView(minus);
        }

    }

    public void toCart(View v){
        // Change to add to BuyerCart.class
        Intent x = new Intent(BusinessViews.this, BuyerPlaceOrder.class);
        Bundle bundle = new Bundle();
        bundle.putString("business", business);
        bundle.putStringArray("listItems", listItems);
        bundle.putFloatArray("listPrices", listPrices);
        bundle.putIntArray("quantities", quantities);
        x.putExtras(bundle);
        startActivity(x);

    }

}
