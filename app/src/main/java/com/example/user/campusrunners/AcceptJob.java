package com.example.user.campusrunners;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AcceptJob extends AppCompatActivity implements OnMapReadyCallback {

    private TextView mTextMessage;
    private Intent intent;
    private GoogleMap mMap;


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
        setContentView(R.layout.activity_accept_job);

        // get Order
        Bundle bundle = getIntent().getExtras();
        Orders order = (Orders) bundle.getSerializable("Order");

        // To allow info to pass to detail accepted job page
        intent =new Intent(this, RunnerDetailAccepted.class);
        intent.putExtra("Order", order);

        // Add Order Details to page
        addOrderDetail(order);


        // Add map to page


        // Bottom Naviagation
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
                        i = new Intent(AcceptJob.this, RunnerHome.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_orders:
                        i = new Intent(AcceptJob.this, ViewAllRunnerOrders.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_profile:
                        // add later when Yadira creates profile page
                        break;
                }
                return false;
            }
        });
    }


    // Add Order Details
    public void addOrderDetail(Orders order){

        RatingBar buyerRate = (RatingBar) findViewById(R.id.ratingBar);
        buyerRate.setRating(4.00f); // Get buyer rating through API Call

        TextView textElement = (TextView) findViewById(R.id.textViewStore);
        textElement.setText(order.businessName); // Add Bussiness Name
        //textElement = (TextView) findViewById(R.id.textViewDate);
        //textElement.setText(order.date); // Add Date
        textElement = (TextView) findViewById(R.id.textViewNote);
        textElement.setText(order.buyerNote); // Add Buyer's Note
        textElement = (TextView) findViewById(R.id.textViewDetails);
        String detail = "";
        for (int i = 0; i < order.items.size();i++){ // Add the list of item detail
            detail = detail +"\t"+ order.quantities.get(i) +" " + order.items.get(i) + "\n";
        }
        detail = detail +"Total                      $" +order.getTotal() + "\n";
        detail = detail +"Money Made                 $" +order.getFee();
        textElement.setText(detail);

    }

    // When Fill Order Button pressed will change to the detail accepted order page
    public void fillOrder(View v){

        //API call to update order status
        startActivity(intent);

    }

    // Add map to the page
    public void createMap(){

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
