package com.example.user.campusrunners;

import java.util.ArrayList;

// For all Open Orders
public class OpenJob {

    public int orderId;
    public String businessName; // Name of the Business
    public int buyerId;
    public String buyerNote;
    public ArrayList<String> items; // item names
    public ArrayList<Integer> quantities; // quantity for each item
    public ArrayList<Float> prices; // price for each item

    // constructor with API Calls
    /*public OpenJob(int orderId){
        this.orderId = orderId;
        this.businessName = business; // API Call to Database
        this.buyerId = buyerId; // API Call to Database
        this.buyerNote = note; // API Call to Database (Not Sure)
        this.items = items; // API Call to Database
        this.quantities = quantities; // API Call to Database
        this.prices = prices; // API Call to Database

    }*/

    // constructor - Temporary until API Calls are put
    public OpenJob(int orderId, String business, int buyerId, String note,
                   ArrayList<String> items, ArrayList<Integer> quantities,
                   ArrayList<Float> prices ){
        this.orderId = orderId;
        this.businessName = business;
        this.buyerId = buyerId;
        this.buyerNote = note;
        this.items = items;
        this.quantities = quantities;
        this.prices = prices;

    }

    // Returns the Total for the Order
    public float getTotal(){
        float total = 0;
        for (int i=0; i<items.size(); i++){
            total = total + quantities.get(i) * prices.get(i);
        }
        return total;

    }
}