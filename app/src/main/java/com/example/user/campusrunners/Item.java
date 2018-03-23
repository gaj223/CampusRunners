package com.example.user.campusrunners;

import java.io.Serializable;

/**
 * Created by cassidy on 3/23/2018.
 */

public class Item implements Serializable {

    private int itemId;
    private String name;
    private int businessId;
    private double price;

    public Item(int itemId, String name, int businessId, double price){
        this.businessId = businessId;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    // GETTERS AND SETTERS
    public int getItemId(){
        return this.itemId;
    }

    public String getName(){
        return this.name;
    }

    public int getBusinessId(){
        return this.businessId;
    }

    public double getPrice(){
        return this.price;
    }



}
