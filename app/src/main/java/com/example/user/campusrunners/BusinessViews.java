package com.example.user.campusrunners;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ListView.*;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import static com.example.user.campusrunners.Constants.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BusinessViews extends AppCompatActivity {

    private TextView mTextMessage;
//    public String listItems[] = {"Pens, Pack of 8", "Pencils, Pack of 8",
//            "Composition Notebook", "Spiral Notebook", "Red Scrantron, 100 questions",
//            " Red Scrantron, 25 questions", "Bluebook"};
//    public float listPrices[] = {2.00f,1.50f,0.75f,1.00f,0.50f,0.25f,1.00f};
//    public int quantities[] = new int[listItems.length];
    public String business;
    //New test code
    // Progress Dialog
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> itemList;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ITEM = "ITEM";
    private static final String TAG_ITEMID = "itemId";
    private static final String TAG_NAME = "name";
    public int id;

    // products JSONArray
    JSONArray items = null;


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
        setContentView(R.layout.activity_business_views);

        // Business Name
        //get stuff from buyer home
        Bundle bundle = getIntent().getExtras();
        business = bundle.getString("Business");
        TextView textElement = (TextView) findViewById(R.id.busName);
        textElement.setText(business);
        id =  bundle.getInt("BusinessID");
//
//        // Add Business Items to view
//        addItemsPage();
        itemList= new ArrayList<HashMap<String, String>>();
        //load items in background thread
        new LoadBusinessItems().execute();

        //nav stuff
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Allow user to navigate between activities
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        i = new Intent(BusinessViews.this, BuyerHomes.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(BusinessViews.this, ViewAllRunnerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
                        // CJM: changed this to redirect back to the buyer home (ie it does nothing)
                        i = new Intent(BusinessViews.this, BuyerHomes.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }

    public void getItems(Business b){
        //Use API call to get Business items and prices

    }

    // Adds the items that business sells
    public void addItemsPage (){

//        quantities = new int[listItems.length];
//        LinearLayout layout = (LinearLayout)findViewById(R.id.scroll2);
//
//        // List each item on to the screen
//        for(int i = 0; i < listItems.length; i++ ){
//
//            quantities[i] = 0;
//
//            // Add Item name
//            TextView item = new TextView(this);
//            item.setText("$" + listPrices[i] + " " + listItems[i]);
//            item.setId((i + 1) * 1000);
//            final int id_ = item.getId();
//
//            // Set the quantity to 0
//            TextView quantity = new TextView(this);
//            quantity.setText("0");
//            quantity.setId((i + 1) * 2000);
//            final int id2_ = quantity.getId();
//
//            // Plus Button
//            Button plus = new Button(this);
//            //plus.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            String text = "+";
//            plus.setText(text);
//            plus.setId(i + 1);
//            plus.setMaxWidth(20);
//            plus.setMaxHeight(20);
//            final int id3_ = plus.getId();
//
//            // Minus Button
//            Button minus = new Button(this);
//            minus.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            text = "-";
//            minus.setText(text);
//            minus.setId((i + 1) * -1);
//            minus.setMaxWidth(20);
//            minus.setMaxWidth(20);
//            final int id4_ = minus.getId();
//
//            // add to quantity
//            plus.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    TextView x = (TextView) findViewById(id2_);
//                    int q = Integer.parseInt((String)x.getText());
//                    q = q + 1;
//                    quantities[id3_ - 1] = q;
//                    x.setText(q + "");
//                }
//            });
//
//            // subtract from quantity
//            minus.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    TextView x = (TextView) findViewById(id2_);
//                    int q = Integer.parseInt((String)x.getText());
//                    if (q > 0) {
//                        q = q - 1;
//                        x.setText(q + "");
//                    }
//                    quantities[id3_ - 1] = q;
//
//                }
//            });
//
//            // Add everything to the view
//            layout.addView(item);
//            layout.addView(plus);
//            layout.addView(quantity);
//            layout.addView(minus);
//        }

    }

    public void toCart(View v){
//        // Change to add to BuyerCart.class
//        Intent x = new Intent(BusinessViews.this, BuyerPlaceOrder.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("business", business);
//        bundle.putStringArray("listItems", listItems);
//        bundle.putFloatArray("listPrices", listPrices);
//        bundle.putIntArray("quantities", quantities);
//        x.putExtras(bundle);
//        startActivity(x);

    }
    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadBusinessItems extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BusinessViews.this);
            pDialog.setMessage("Loading items. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Pass hash map to store parameters to be passed
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("itemId", Integer.toString(id));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(SERVER_PATH+get_items_api, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    items = json.getJSONArray(TAG_ITEM);

                    // looping through All Products
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ITEMID);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ITEMID, id);
                        map.put(TAG_NAME, name);

                        // adding HashList to ArrayList
                        itemList.add(map);
                        Log.e("testing", itemList.toString());
                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            BusinessViews.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
//        protected void onPostExecute(String file_url) {
//            // dismiss the dialog after getting all products
//            pDialog.dismiss();
//            // updating UI from Background Thread
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    /**
//                     * Updating parsed JSON data into ListView
//                     * */
//                    ListAdapter adapter = new SimpleAdapter(
//                            BusinessViews.this, itemList,
//                            R.layout.list_item, new String[] { TAG_ITEMID,
//                            TAG_NAME},
//                            new int[] { R.id.itemId, R.id.name });
//                    // updating listview
//                    setListAdapter(adapter);
//                }
//            });
//
//        }

}

}
