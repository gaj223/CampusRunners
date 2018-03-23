package com.example.user.campusrunners;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BusinessView extends AppCompatActivity {

    private TextView mTextMessage;
    private HashMap<Integer, Integer> itemsInOrder = new HashMap<Integer,Integer>(); // first int is itemId, second int is how many times that item is selected
    static final int TOTAL_PRICE_BUTTON_ID = 4;
    Business bus;
    public Intent intentOrderNow;

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
        setContentView(R.layout.activity_business_view);

        mTextMessage = (TextView) findViewById(R.id.message);

        // get the Id of the business we need to display
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        displayBusiness(b);

        // create an "add to cart" button that displays the total price
        addToCartButton();

        // set up the bottom navigation view
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
                        i = new Intent(BusinessView.this, BuyerHome.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        //TODO
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
                        i = new Intent(BusinessView.this, BuyerHome.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }

    public void displayBusiness(Bundle b){
        if (b==null)
            return;
        bus = (Business) b.getSerializable("Business");

        ArrayList<Item> items = bus.getItems();
        LinearLayout layout = (LinearLayout)findViewById(R.id.scroll);

        // make a button for each of the items in the arraylist
        for (int i=0; i<items.size(); i++){
            Item curItem = (Item) items.get(i);

            final Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // name and price of the item
            String text = curItem.getName() + "\t\t\t$" + curItem.getPrice();
            btnTag.setText(text);
            // button tag is the item's ID
            btnTag.setId(curItem.getItemId());
            final int id_ = btnTag.getId();
            btnTag.setWidth(1200);
            btnTag.setHeight(300);
            btnTag.setTextSize(24);

            // When the button is pressed, add the item to the arraylist
            btnTag.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int id = btnTag.getId();
                    if (itemsInOrder.containsKey(id)){
                        int numTimes = itemsInOrder.get(id);
                        itemsInOrder.put(id,numTimes+=1);
                    }
                    else{
                        itemsInOrder.put(id, 1);
                    }
                    updateTotalPrice();
                }
            });

            layout.addView(btnTag);
        }
    }

    public void addToCartButton(){
        // To allow info to pass to accept job page
        intentOrderNow =new Intent(this, BuyerCart.class);

        LinearLayout layout = (LinearLayout)findViewById(R.id.scroll);

        final Button btnTag = new Button(this);
        btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        // name and price of the item
        String text = "Total Price:" + "\t\t\t$" + getTotalPrice();
        btnTag.setText(text);
        // button tag is the item's ID
        btnTag.setId(TOTAL_PRICE_BUTTON_ID);
        final int id_ = btnTag.getId();
        btnTag.setWidth(1200);
        btnTag.setHeight(300);
        btnTag.setTextSize(24);

        // Takes the runner to detail about the order when button is pressed
        btnTag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intentOrderNow.putExtra("OrderItems", itemsInOrder);

                startActivity(intentOrderNow);
            }
        });

        layout.addView(btnTag);
    }

    public double getTotalPrice(){
        Iterator<Integer> items = itemsInOrder.keySet().iterator();
        double totalPrice = 0;

        while (items.hasNext()){
            int itemId = items.next();
            Item curItem = bus.getItem(itemId);
            double price = curItem.getPrice();
            int numOrdered = itemsInOrder.get(itemId);
            totalPrice += price*numOrdered;
        }

        return totalPrice;
    }

    public void updateTotalPrice(){
        Button button = (Button)findViewById(TOTAL_PRICE_BUTTON_ID);
        String text = "Total Price:" + "\t\t\t$" + getTotalPrice();
        button.setText(text);
    }
}
