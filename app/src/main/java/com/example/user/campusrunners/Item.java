package com.example.user.campusrunners;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yadi on 4/23/18.
 */

public class Item {
    public String name;
    public String itemId;
    public String price;
    public String businessId;
    private static final String TAG_ITEM = "items";
    private static final String TAG_ITEMID = "itemId";
    private static final String TAG_NAME = "name";
    private static final String TAG_BUSID = "businessId";
    private static final String TAG_PRICE = "price";


    // Constructor to convert JSON object into a Java class instance
    public Item(JSONObject object){
        // Storing each json item in variable

        try {
            this.itemId = object.getString(TAG_ITEMID);
            this.name = object.getString(TAG_NAME);
            this.price = object.getString(TAG_PRICE);
            this.businessId = object.getString(TAG_BUSID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //retrieve user's name
    public String getName(){
        return name;
    }

    //retrieve users' hometown
    public String getPrice(){
        return price;
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // Item.fromJson(jsonArray);
    public static ArrayList<Item> fromJson(JSONArray jsonObjects) {
        ArrayList<Item> items = new ArrayList<Item>();
        //JSONArray item_array = jsonObjects.getJSONArray(TAG_ITEM);
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                items.add(new Item(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return items;
    }
}
