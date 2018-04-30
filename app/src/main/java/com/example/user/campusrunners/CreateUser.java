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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View.OnClickListener;
import java.util.HashMap;
import static com.example.user.campusrunners.Constants.*;

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
    private static String urlCreate =SERVER_PATH+create_user_api;
    // Progress Dialog
    private ProgressDialog pDialog;
    Button button_makeUser;
    String role ="";
    String gender="";
    private static final String TAG_SUCCESS     = "success";
    private static final String TAG_PRODUCTS    = "products";
    private static final String TAG_PID         = "pid";
    private static final String TAG_USER_ID     = "userId";
    private static final String TAG_NAME_ARRAY  = "user";
    private static final String TAG_USER_ROLE   = "role";
    //public Intent intentLogin;

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
        EditText inputDOB;
        RadioGroup roleSelected;
        RadioGroup genderSelected;
        RadioButton radioRole;
        RadioButton radioGender;

        EditText inputPhoneNumber;
        EditText inputRole;
        EditText inputABC123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_users);

//        if (inputName.length() > 0) {
//            inputName.getText().clear();
//        }

        roleSelected = (RadioGroup) findViewById(R.id.role_radio_group);
        genderSelected = (RadioGroup) findViewById(R.id.role_radio_group);

        roleSelected.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_runner_btn:
                        role = "runner";
                        // do operations specific to this selection
                        break;
                    case R.id.radio_buyer_btn:
                        // do operations specific to this selection
                        role = "buyer";
                        break;

                }
            }
        });

//        genderSelected.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch(checkedId){
//                    case R.id.female_radio_btn:
//                        gender = "female";
//                        // do operations specific to this selection
//                        break;
//                    case R.id.male_radio_btn:
//                        // do operations specific to this selection
//                        gender = "male";
//                        break;
//
//                }
//            }
//        });



        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        //added to test if this is where listner needs to be
        //onRadioButtonClickedRole();
        JSONParser jsonParser = new JSONParser();
        button_makeUser = (Button) findViewById(R.id.btn_signup);
        inputEmail    = (EditText)findViewById(R.id.input_email);
        inputPassword = (EditText)findViewById(R.id.input_password);
        inputName     = (EditText)findViewById(R.id.input_userFirstName);
        inputLastName = (EditText)findViewById(R.id.input_userLastName);
        inputDOB      = (EditText)findViewById(R.id.input_DOB);
        inputABC123   = (EditText)findViewById(R.id.input_userABC123);
        inputPhoneNumber = (EditText)findViewById(R.id.input_phoneNumber);
        //radioGender   = (RadioButton) findViewById(R.id.male_radio_btn);
        radioRole     = (RadioButton) findViewById(R.id.radio_runner_btn);


        button_makeUser.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              pDialog = new ProgressDialog(CreateUser.this);
              pDialog.setMessage("Creating User. Please wait...");
              pDialog.setIndeterminate(false);
              pDialog.setCancelable(false);
              pDialog.show();

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

//    public void onRadioButtonClickedRole() {
//
//        roleSelected = (RadioGroup) findViewById(R.id.gender_radio_group);
//        button_makeUser = (Button) findViewById(R.id.btn_signup);
//
//        button_makeUser.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                // get selected radio button from radioGroup
//                int selectedId = roleSelected.getCheckedRadioButtonId();
//
//                // find the radiobutton by returned id
//                radioGender = (RadioButton) findViewById(selectedId);
//                gender= radioGender.getText().toString();
//                Log.d("Gender is assigned",gender);
//
//                //Toast.makeText(MyAndroidAppActivity.this,
//                      //  gender.getText(), Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//
//    }
//    public void onRadioButtonClickedRole(View view){
//       boolean checked = ((RadioButton)view).isChecked();
//       switch(view.getId() ){
//           case R.id.radio_buyer_btn:
//               if(checked){
//                    role = "buyer";
//               }
//               break;
//           case R.id.radio_runner_btn:
//               if(checked){
//                   role = "runner";
//               }
//               break;
//       }
//    }
//    public void onRadioButtonClickedGender(View view){
//        boolean checked = ((RadioButton)view).isChecked();
//        HashMap<String, String> choice = new HashMap<String, String>();
//        switch(view.getId() ){
//            case R.id.female_radio_btn:
//                if(checked){
//                    gender = "female";
//                }
//                break;
//            case R.id.male_radio_btn:
//                if(checked){
//                    gender ="male";
//                }
//                break;
//        }
//    }
    /////////////////////////////////////////////////////////// Inner Class


    class UserCreated extends AsyncTask<String,String,String> {
        private  int answerReturned;
        @Override

        protected  void onPreExecute(){
            Log.d("DoInBack","onPreExecute");


            //onRadioButtonClickedRole();

//            super.onPreExecute();
//            pDialog = new ProgressDialog(CreateUser.this);
//            pDialog.setMessage("Creating Product..");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }
        //Required Abstract Method
        protected String doInBackground(String...params){

            final String  name    = inputName.getText().toString();
            final String password = inputPassword.getText().toString();
            final String lastName = inputLastName.getText().toString();
            final String email    = inputEmail.getText().toString();
//           final String userRole = inputRole.getText().toString();
            final String ABC123   = inputABC123.getText().toString();
            final String phoneNumber = inputPhoneNumber.getText().toString();





            Log.d("DoInBack","past the convert");
            //runOnUiThread(new Runnable() {
              //  @Override
              //  public void run() {
                    Log.d("DoInBack","inside runOnUiThread()");
                   // String user_role = " ";
                   // String user_ID   = " ";
                    HashMap<String, String> choice = new HashMap<String, String>();
                    int answerReturned =0;
                    // enter convert input into a hashmap to be read by the php file, via POST
                     choice.put("first_name",name);
                     choice.put("last_name",lastName);  ///not yet using lastName
                     choice.put("password",password);
                     choice.put("email",email);
                     choice.put("abc123",ABC123);
                     choice.put("phone_number",phoneNumber);
                     choice.put("user_role",role);
                     choice.put("gender",gender);
                    Log.d("Sending to api", choice.toString());
                   // try {
                        jsonObj = jsonParser.makeHttpRequest(urlCreate, "POST", choice);
                        Log.d("DoInBack", "jsonObj is good i think"  );
///////ERROR IS HAPPENING HERE
                       //answerReturned = jsonObj.getInt(TAG_SUCCESS);
                       // Log.d("CAMPUSRUNNER_API", "answerReturned "+ answerReturned  );
                        //get user logged in
                        //jsonObj.getJSONArray("user");
                        //Catch needed to use jsonObj.getInt....
//                        if(answerReturned == 1){
//                            Log.d("logInReturn","should have return to home screen");
//                            intentLogIn = new Intent(getApplicationContext(), Login.class);
//                            startActivity(intentLogIn);
//                            //finish();
//                        }else{
//                            //throw a loop back, instance of correct creds not valid.
//
////                        }
//                    }catch (JSONException jError){
//                        jError.printStackTrace();
//                        jError.getLocalizedMessage();
//                    }
               // }
           // });
            return "blk";
        }

        public int getAnswerReturned() {
            return answerReturned;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            Toast.makeText(getBaseContext(), "User created successfully!", Toast.LENGTH_SHORT).show();

            intentLogIn = new Intent(getApplicationContext(), Login.class);
            startActivity(intentLogIn);
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
