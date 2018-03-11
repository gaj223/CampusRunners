package com.example.user.campusrunners;

import java.util.ArrayList;

// For all Open Orders
public class OpenJob {

    public int orderId;
    public String businessName; // Name of the Business
    public String date;
    public ArrayList<String> items; // item names
    public ArrayList<Integer> quantities; // quantity for each item
    public ArrayList<Float> prices; // price for each item
    public int fee;

    // constructor with API Calls
    /*public OpenJob(int orderId){
        this.orderId = orderId;
        this.items = items; // API Call to Database
        this.buyerId = buyerId; // API Call to Database
        this.businessName = business; // API Call to Database
        this.buyerNote = note; // API Call to Database (Not Sure)
        this.quantities = quantities; // API Call to Database
        this.prices = prices; // API Call to Database
        this.date = date; // API Call

    }*/

    // constructor - Temporary until API Calls are put
    public OpenJob(int orderId, String business,
                   ArrayList<String> items, ArrayList<Integer> quantities,
                   ArrayList<Float> prices, String date ){
        this.orderId = orderId;
        this.businessName = business;
        this.items = items;
        this.quantities = quantities;
        this.prices = prices;
        this.date = date;

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