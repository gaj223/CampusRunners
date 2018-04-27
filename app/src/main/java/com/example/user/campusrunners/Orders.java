package com.example.user.campusrunners;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 3/10/18.
 */

public class Orders implements Serializable {
    public int orderId;
    public String businessName; // Name of the Business
    public int buyerId;
    public String buyerNote;
    public String buyerLocation;
    public Timestamp timePlaced;
    public ArrayList<String> items = new ArrayList<String>(); // item names
    public ArrayList<Integer> quantities = new ArrayList<Integer>(); // quantity for each item
    public ArrayList<Float> prices = new ArrayList<Float>(); // price for each item
    public String status; // active or completed or open
    public Timestamp timeCompleted;
    public int amountItems = 4; // Temporary
    public String businessNames[] = {"Chick-Fil-A", "Papa John's", "UTSA Bookstore", "POD"};
    // Bookstore Info
    public String listItemsBook[] = {"Pens, Pack of 8", "Pencils, Pack of 8",
            "Composition Notebook", "Spiral Notebook", "Red Scrantron, 100 questions",
            " Red Scrantron, 25 questions", "Bluebook"};
    public float listPricesBook[] = {2.00f,1.50f,0.75f,1.00f,0.50f,0.25f,1.00f};
    // Chick Fil A Info
    public String listItemsChick[] = {"Spicy Chicken Sandwich", "Chick-fil-A Chicken Sandwich",
            "Chick-fil-A Nuggets, 8ct", "Chick-fil-A Nuggets, 12ct", "Waffle Potato Fries, Medium",
            " Waffle Potato Fries, Large", "Yogurt Parfait"};
    public float listPricesChick[] = {3.29f,3.05f,3.05f,4.45f,1.65f,1.85f,2.45f};
    // POD Info
    public String listItemsPOD[] = {"Chocolate Chip Muffin", "Snickers",
            "Cough Drop, Cherry", "Bottle of Water", "Mountain Dew",
            "Pepsi", "Pack of spearmint"};
    public float listPricesPOD[] = {2.00f,1.50f,1.75f,1.50f,1.75f,1.75f,1.00f};
    // Papa Johns Info
    public String listItemsPapa[] = {"Cheese Pizza", "Pepperoni Pizza",
            "Meat Trio Pizza", "Hawaian Pizza", "Cheese BreadSticks", "Fountain Drink, Medium",
            "Fountain Drink, Large"};
    public float listPricesPapa[] = {4.00f,4.25f,4.50f,4.50f,2.50f,1.25f,1.75f};
    // general info
    public String listItems[];
    public float listPrices[];
    public Random rand = new Random(); // temporary, chose random numbers from item list

    // constructor with API Calls
    public Orders(int orderId){
        this.orderId = orderId;
        setOrder();
        this.buyerId = 5; // Add API Call to Database
        this.buyerNote = "Text When Here"; // Add API Call to Database (Not Sure)
        this.buyerLocation = "JPL Study Room 2";
        //this.timePlaced = ; //Add API Call

    }

    // Add API Call to Database here
    // add all the parts of an order to its own arraylist
    private void setOrder(){
        int name = rand.nextInt(4);
        this.businessName = businessNames[name];
        if (name == 0){
            listItems = listItemsChick;
            listPrices = listPricesChick;
        } else if (name == 1) {
            listItems = listItemsPapa;
            listPrices = listPricesPapa;
        } else if (name == 2) {
            listItems = listItemsBook;
            listPrices = listPricesBook;
        } else {
            listItems = listItemsPOD;
            listPrices = listPricesPOD;
        }
        for(int i = 0; i < amountItems; i++){

            int n = rand.nextInt(7);
            this.items.add(listItems[n]);
            this.prices.add(listPrices[n]);
            this.quantities.add(rand.nextInt(3) + 1);

        }


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
        if (total > 20){
            return 4;
        } else if (total > 15)
            return 3;
        else if (total > 10 )
            return 2;
        else
            return 1;

    }
}
