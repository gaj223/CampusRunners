package com.example.user.campusrunners;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CreateUser extends AppCompatActivity {
    public Intent intentLogIn;
    JSONArray products = null;
    JSONObject jsonObj;
    JSONParser jsonParser = new JSONParser();
    HashMap<String, String> userInfoMap;
    private static String urlCreate ="http://13.59.142.19/CampusRunnerBack/updated_apis/user/create_user.php";
    // Progress Dialog
    private ProgressDialog pDialog;
    Button button_makeUser;

    private static final String TAG_SUCCESS     = "success";
    private static final String TAG_PRODUCTS    = "products";
    private static final String TAG_PID         = "pid";
    private static final String TAG_USER_ID     = "userId";
    private static final String TAG_NAME_ARRAY  = "user";
    private static final String TAG_USER_ROLE   = "role";

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
//    private final Runnable mHideRunnable = new Runnable() {
//        @Override
//        public void run() {
//            hide();
//        }
//    };
//    /**
//     * Touch listener to use for in-layout UI controls to delay hiding the
//     * system UI. This is to prevent the jarring behavior of controls going away
//     * while interacting with activity UI.
//     */
//    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (AUTO_HIDE) {
//                delayedHide(AUTO_HIDE_DELAY_MILLIS);
//            }
//            return false;
//        }
//    };

        EditText inputName;
        EditText inputLastName;
        EditText inputPassword;
        EditText inputEmail;
        EditText inputPhoneNumber;
        EditText inputAddress;
        EditText inputRole;
        EditText inputABC123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        JSONParser jsonParser = new JSONParser();
        button_makeUser = (Button) findViewById(R.id.btn_signup);
//        inputName     = findViewById(R.id.input_userName);
//        inputLastName = (EditText)findViewById(R.id.input_userLastName);
        inputEmail    = (EditText)findViewById(R.id.input_email);
        inputPassword = (EditText)findViewById(R.id.input_password);

        button_makeUser.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                new UserCreated().execute();
          }
        });

//        mVisible = true;
//        mControlsView = findViewById(R.id.fullscreen_content_controls);
        //mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        /*mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });*/

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
       // findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }
    /////////////////////////////////////////////////////////// Inner Class


    class UserCreated extends AsyncTask<String,String,String> {
        private  int answerReturned;
        @Override
        protected  void onPreExecute(){
            Log.d("DoInBack","onPreExecute");
//            super.onPreExecute();
//            pDialog = new ProgressDialog(CreateUser.this);
//            pDialog.setMessage("Creating Product..");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }
        //Required Abstract Method
        protected String doInBackground(String...params){
//            final String  name    = inputName.getText().toString();
            final String password = inputPassword.getText().toString();
//            final String lastName = inputLastName.getText().toString();
            final String email    = inputEmail.getText().toString();
//            final String userRole = inputRole.getText().toString();
//            final String abc123   = inputABC123.getText().toString();
//            final String phoneNumber = inputPhoneNumber.getText().toString();
//            Log.d("DoInBack"," " + name);
// {"abc123": "abc123",
//     "email": "abc123@my.utsa.edu",
//      "name": "Yadi",
// "user_role": "runner",
// "street_address": "1 UTSA Circle",
// "phone_number": "7757209035",
//     "password": "abc123"}
            Log.d("DoInBack","past the convert");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("DoInBack","inside runOnUiThread()");
                    String user_role = " ";
                    String user_ID   = " ";
                    HashMap<String, String> choice = new HashMap<String, String>();
                    int answerReturned =0;
                    // enter convert input into a hashmap to be read by the php file, via POST
//                     choice.put("name",name);
                     choice.put("password",password);
//                     choice.put("  ",lastName);  ///not yet using lastName
                     choice.put("email",email);

//                     choice.put("user_role",userRole);
                     choice.put("abc123","hot321");
//                     choice.put("user_role",phoneNumber);
                    try {
                        jsonObj = jsonParser.makeHttpRequest(urlCreate, "POST", choice);
                        Log.d("DoInBack", "jsonObj is good i think"  );

                        answerReturned = jsonObj.getInt(TAG_SUCCESS);
                        Log.d("DoInBack", "answerReturned "+ answerReturned  );
                        jsonObj.getJSONArray("user");
                        //Catch needed to use jsonObj.getInt....
                        if(answerReturned == 1){
                            intentLogIn = new Intent(getApplicationContext(), Login.class);
                            startActivity(intentLogIn);
                            //finish();
                        }else{
                            //throw a loop back, instance of correct creds not valid.

                        }
                    }catch (JSONException jError){
                        jError.printStackTrace();
                        jError.getLocalizedMessage();
                    }
                }
            });
            return "blk";
        }

        public int getAnswerReturned() {
            return answerReturned;
        }
    }



//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//
//        // Trigger the initial hide() shortly after the activity has been
//        // created, to briefly hint to the user that UI controls
//        // are available.
//        delayedHide(100);
//    }
//
//    private void toggle() {
//        if (mVisible) {
//            hide();
//        } else {
//            show();
//        }
//    }

//    private void hide() {
//        // Hide UI first
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
       // mControlsView.setVisibility(View.GONE);
//        mVisible = false;
//
//        // Schedule a runnable to remove the status and navigation bar after a delay
//        mHideHandler.removeCallbacks(mShowPart2Runnable);
//        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
//    }
//
//    @SuppressLint("InlinedApi")
//    private void show() {
//        // Show the system bar
//        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//        mVisible = true;
//
//        // Schedule a runnable to display UI elements after a delay
//        mHideHandler.removeCallbacks(mHidePart2Runnable);
//        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
//    }
//
//    /**
//     * Schedules a call to hide() in delay milliseconds, canceling any
//     * previously scheduled calls.
//     */
//    private void delayedHide(int delayMillis) {
//        mHideHandler.removeCallbacks(mHideRunnable);
//        mHideHandler.postDelayed(mHideRunnable, delayMillis);
//    }
}
