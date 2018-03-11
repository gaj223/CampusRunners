package com.example.user.campusrunners;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

public class RunnerHome extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_orders);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_profile);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner_home);

        // Create bottom bar
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Gets list of Open Orders
        ArrayList<OpenJob> openOrders = addOrders();

        // Add Orders (Buttons) to Screen
        individualRequest(openOrders);




    }

    // Get list of all the Current Orders
    public ArrayList<OpenJob> addOrders(){
        ArrayList<OpenJob> orders = new ArrayList<OpenJob>();
        // API Call to get list of open orders
        int orderNums[] = {200,201,202,203};
        for (int i = 0; i < orderNums.length; i++){
            //OpenJob job = new OpenJob(orders[i]);

            // Delete when API Calls are added
            ArrayList<String> items = new ArrayList<String>(); // item names
            items.add("Pen, pack of 8");
            items.add("Notebook");
            ArrayList<Integer> quantities = new ArrayList<Integer>();; // quantity for each item
            quantities.add(i+1);
            quantities.add(i+2);
            ArrayList<Float> prices = new ArrayList<Float>();// price for each item
            prices.add(3.25f);
            prices.add(1.00f);
            OpenJob job = new OpenJob(orderNums[i], "Bookstore",items, quantities, prices, "3/15/2018" );

            // add open job to array list
            orders.add(job);
        }

        return orders;

    }

    // Create a button for each open request and add to the layout
    public void individualRequest(ArrayList<OpenJob> jobs){

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"
        layout.setBackgroundColor(Color.rgb(255,140,0));
        for (int i = 0; i < jobs.size(); i++) {
            LinearLayout row = new LinearLayout(this);
            OpenJob curr = jobs.get(i);
            row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));


            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            btnTag.setText(curr.businessName + "\t\t\t$" + curr.getTotal() +
                        " | $" + curr.getFee() + "\n" + curr.date);
            btnTag.setId(i + 1);
            btnTag.setWidth(1200);
            btnTag.setHeight(400);
            btnTag.setTextSize(24);
            row.addView(btnTag);

            layout.addView(row);

        }
        setContentView(layout);


    }


}
