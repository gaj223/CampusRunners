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

import java.util.ArrayList;

public class ViewAllRunnerOrders extends AppCompatActivity {

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
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_runner_orders);

        // To allow info to pass to active or completed order page
        intentActive =new Intent(this, RunnerDetailAccepted.class);
        intentCompleted =new Intent(this, IndividualCompletedOrder.class);

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
                        i = new Intent(ViewAllRunnerOrders.this, RunnerHome.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(ViewAllRunnerOrders.this, ViewAllRunnerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
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
        int orderNums[] = {192,191};
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

        LinearLayout layout = (LinearLayout)findViewById(R.id.active);
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
            btnTag.setTextSize(20);


            btnTag.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    intentActive.putExtra("Order", active.get(id_));

                    startActivity(intentActive);
                }
            });

            layout.addView(btnTag);

        }


    }

    // Add to completed orders to the view
    public void addCompletedOrders(ArrayList<Orders> orders){

        LinearLayout layout = (LinearLayout)findViewById(R.id.completed);
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
            btnTag.setTextSize(20);


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
