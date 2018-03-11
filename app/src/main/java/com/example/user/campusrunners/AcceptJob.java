package com.example.user.campusrunners;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class AcceptJob extends AppCompatActivity {

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
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_job);

        // get RunnerId and OrderId
        Bundle bundle = getIntent().getExtras();
        int orderId = bundle.getInt("orderID");
        Orders order = new Orders(orderId);

        // Add Order Detail to page
        TextView textElement = (TextView) findViewById(R.id.textViewStore);
        textElement.setText(order.businessName); // Add Bussiness Name
        textElement = (TextView) findViewById(R.id.textViewDate);
        textElement.setText(order.date); // Add Date
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


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
