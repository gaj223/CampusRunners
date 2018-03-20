package com.example.user.campusrunners;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

public class RunnerHome extends AppCompatActivity {

    private TextView mTextMessage;
    public Intent intentAccept;
    public ArrayList<Orders> openOrders;

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

    // Refresh page each time page is called
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner_home);

        // To allow info to pass to accept job page
        intentAccept =new Intent(this, AcceptJob.class);

        // Gets list of Open Orders
        openOrders = addOrders();

        //Add Orders (Buttons) to Screen
        individualRequest(openOrders);


        // Create bottom bar
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
                        i = new Intent(RunnerHome.this, RunnerHome.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(RunnerHome.this, ViewAllRunnerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
                        i = new Intent(RunnerHome.this, MapsActivity.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });


    }

    // Get list of all the Current Orders
    public ArrayList<Orders> addOrders(){
        ArrayList<Orders> orders = new ArrayList<Orders>();
        // API Call to get list of open orders
        int orderNums[] = {200,201,202,203,204,205,206};
        for (int i = 0; i < orderNums.length; i++){

            Orders order = new Orders(orderNums[i]);

            // add open job to array list
            orders.add(order);
        }

        return orders;

    }

    // Create a button for each open request and add to the layout
    public void individualRequest(ArrayList<Orders> orders){

        //ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.container);
        LinearLayout layout = (LinearLayout)findViewById(R.id.scroll);
        for (int i = 0; i < orders.size(); i++) {
            Orders current = orders.get(i);


            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            String text = current.businessName + "\t\t\t$"
                    + current.getTotal() + " | $" + current.getFee() + "\n 03/15/2018" ;//+ current.date;
            btnTag.setText(text);
            btnTag.setId(i);
            final int id_ = btnTag.getId();
            btnTag.setWidth(1200);
            btnTag.setHeight(200);
            btnTag.setTextSize(24);

            // Takes the runner to detail about the order when button is pressed
            btnTag.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    intentAccept.putExtra("Order", openOrders.get(id_));

                    startActivity(intentAccept);
                }
            });

            layout.addView(btnTag);

        }



    }


}
