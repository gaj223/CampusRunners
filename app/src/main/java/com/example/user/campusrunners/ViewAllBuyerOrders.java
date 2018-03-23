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
import java.lang.*;

import java.util.ArrayList;

public class ViewAllBuyerOrders extends AppCompatActivity {

    private TextView mTextMessage;
    public ArrayList<Orders> active;
    public ArrayList<Orders> completed;
    public Intent intentActive;
    public Intent intentCompleted;

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
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_buyer_orders);

        // To allow info to pass to active or completed order page
        intentActive =new Intent(this, BuyerOrderPlaced.class);
        intentCompleted =new Intent(this, IndividualCompletedOrderBuyer.class);

        //get all the active and completed orders
        active = getActiveOrders();
        completed = getCompletedOrders();

        // Add active and completed orders to view
        addActiveOrder(active);
        addCompletedOrders(completed);


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
                        i = new Intent(ViewAllBuyerOrders.this, BuyerHomes.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(ViewAllBuyerOrders.this, ViewAllBuyerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
                        i = new Intent(ViewAllBuyerOrders.this, BuyerProfile.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }
    // Get list of all the active Orders
    public ArrayList<Orders> getActiveOrders(){
        ArrayList<Orders> orders = new ArrayList<Orders>();
        // API Call to get list of active orders
        int orderNums[] = {192};
        for (int i = 0; i < orderNums.length; i++){

            Orders order = new Orders(orderNums[i]);

            // add open job to array list
            orders.add(order);
        }

        return orders;

    }

    // Get list of all the completed Orders
    public ArrayList<Orders> getCompletedOrders(){
        ArrayList<Orders> orders = new ArrayList<Orders>();
        // API Call to get list of completed orders
        int orderNums[] = {100,151,180};
        for (int i = 0; i < orderNums.length; i++){

            Orders order = new Orders(orderNums[i]);

            // add open job to array list
            orders.add(order);
        }

        return orders;

    }


    // Adds the active orders to the view
    public void addActiveOrder(ArrayList<Orders> orders){

        LinearLayout layout = (LinearLayout)findViewById(R.id.activeBuyer);
        for (int i = 0; i < orders.size(); i++) {
            Orders current = orders.get(i);


            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            String text = current.businessName + "\t\t\t$"
                    + current.getTotal() + " | $" + current.getFee() + "\n 03/15/2018" ;//+ current.date;
            btnTag.setText(text);
            btnTag.setId(i);
            final int id_ = btnTag.getId();
            btnTag.setWidth(1200);
            btnTag.setHeight(200);
            btnTag.setTextSize(24);


            btnTag.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Orders order = active.get(id_);
                    String business = order.businessName;
                    String items [] = new String[order.items.size()];
                    order.items.toArray(items);
                    Float p [] = new Float[order.items.size()];
                    order.prices.toArray(p);
                    Integer q[] = new Integer[order.items.size()];
                    order.quantities.toArray(q);
                    float prices [] = new float[order.items.size()];
                    int quantities [] = new int[order.items.size()];
                    // Convert Integer and Float arrays to primitive arrays
                    for(int i = 0; i < order.items.size(); i++ ){
                        prices[i] = p[i].floatValue();
                        quantities[i] = q[i].intValue();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("business", business);
                    bundle.putStringArray("listItems", items);
                    bundle.putFloatArray("listPrices", prices);
                    bundle.putIntArray("quantities", quantities);
                    intentActive.putExtras(bundle);
                    //intentActive.putExtra("Order", active.get(id_));

                    startActivity(intentActive);
                }
            });

            layout.addView(btnTag);

        }


    }

    // Add to completed orders to the view
    public void addCompletedOrders(ArrayList<Orders> orders){

        LinearLayout layout = (LinearLayout)findViewById(R.id.completedBuyer);
        for (int i = 0; i < orders.size(); i++) {
            Orders current = orders.get(i);


            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            String text = current.businessName + "\t\t\t$"
                    + current.getTotal() + " | $" + current.getFee() + "\n 03/05/2018" ;//+ current.date;
            btnTag.setText(text);
            btnTag.setId(i);
            final int id2_ = btnTag.getId();
            btnTag.setWidth(1200);
            btnTag.setHeight(200);
            btnTag.setTextSize(24);


            btnTag.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    intentCompleted.putExtra("Order", completed.get(id2_));

                    startActivity(intentCompleted);
                }
            });

            layout.addView(btnTag);

        }


    }

}
