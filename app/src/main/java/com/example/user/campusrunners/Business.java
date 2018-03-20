package com.example.user.campusrunners;

import java.io.Serializable;

/**
 * Created by cassidy on 3/20/2018.
 */

public class Business implements Serializable
{
    // characteristics of business
    int businessId;
    String name;
    String location;
    String hours;

    public Business(int businessId){
        this.businessId = businessId;
    }
}
