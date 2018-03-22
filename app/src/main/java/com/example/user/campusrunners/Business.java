package com.example.user.campusrunners;

import java.io.Serializable;

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

    public Business(int businessId){
        this.businessId = businessId;
    }

    // GETTERS AND SETTERS
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}
