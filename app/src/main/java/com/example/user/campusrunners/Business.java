package com.example.user.campusrunners;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cassidy on 3/20/2018.
 */

public class Business implements Serializable
{
    // characteristics of business
    private int businessId;
    private String name;
    private String location;
    private String hours;
    private ArrayList<Item> items = new ArrayList<Item>();

    public Business(int businessId){
        this.businessId = businessId;
    }

    // second constructor
    public Business(){}

    // GETTERS AND SETTERS
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return this.businessId;
    }

    public void setId(int id){
        this.businessId = id;
    }

    public void addItem(int index, Item newItem){

        this.items.add(index, newItem);
    }

    public ArrayList<Item> getItems(){
        return this.items;
    }

    public Item getItem(int id){
        return this.items.get(id);
    }
}
