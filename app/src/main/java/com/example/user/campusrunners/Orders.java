package com.example.user.campusrunners;

import java.util.ArrayList;

/**
 * Created by user on 3/10/18.
 */

public class Orders {
    public int orderId;
    public String businessName; // Name of the Business
    public int buyerId;
    public String buyerNote;
    public String date;
    public ArrayList<String> items = new ArrayList<String>(); // item names
    public ArrayList<Integer> quantities = new ArrayList<Integer>(); // quantity for each item
    public ArrayList<Float> prices = new ArrayList<Float>(); // price for each item
    public String status; // active or completed

    // constructor with API Calls
    public Orders(int orderId){
        this.orderId = orderId;
        this.items.add("Pen, Pack of 8"); // API Call to Database
        this.items.add("Notebook");
        this.buyerId = 5; // API Call to Database
        this.businessName = "Bookstore"; // API Call to Database
        this.buyerNote = "Text When Here"; // API Call to Database (Not Sure)
        this.quantities.add(2); // API Call to Database
        this.quantities.add(5);
        this.prices.add(3.0f); // API Call to Database
        this.prices.add(1.0f);
        this.date = "3/15/2018"; // API Call\

    }



    // Returns the Total for the Order
    public float getTotal(){
        float total = 0;
        for (int i=0; i<items.size(); i++){
            total = total + quantities.get(i) * prices.get(i);
        }
        return total;

    }

    // Calculates the fee for the runner
    public float getFee(){

        float total = getTotal();
        if (total > 15)
            return 3;
        else if (total > 10 )
            return 2;
        else
            return 1;

    }
}
