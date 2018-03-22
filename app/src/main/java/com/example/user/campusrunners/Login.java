package com.example.user.campusrunners;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Intent;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.StrictMode;//added via Yadddddi
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    public Intent intentRunnerHome ;
    private static String urlLogin ="http://13.59.142.19/CampusRunnerBack/Config/TestHiveStuff/userlogin.php";
    //JSON NODE Tags
    private static final String TAG_SUCCESS     = "success";
    private static final String TAG_PRODUCTS    = "products";
    private static final String TAG_PID         = "pid";
    private static final String TAG_NAME_ARRAY  = "user";
    private static final String TAG_USER_ROLE   = "role";
    JSONObject jsonObj;
    //come back to this
    DbVerify tempDbVerify = new DbVerify();

    //Product JSONArray
    JSONArray products = null;
    JSONParser jsonParser = new JSONParser();
    //= new Intent(this, RunnerHome.class);
    // not imported yet public Intent intentBuyerHome  = new Intent(this, BuyerHome.class);;

    public static int getRequestReadContacts() {
        return REQUEST_READ_CONTACTS;
    }



    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    EditText inputUserName, editPsWd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


        // Set up the login form...
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();
        //EDIT-TEXT versions
        inputUserName = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL){
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {


            @Override//premade function...i did not mess with
            public void onClick(View view) {
                new DbVerify().execute();
                // this was here automatically built    attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView  = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email    = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        //if the db returns with a value of 1 then this is true
        //if(tempDbVerify.getAnswerReturned() ==1 ){
           // intentRunnerHome = new Intent(this, RunnerHome.class);
            //startActivity(intentRunnerHome);
        //}
        //API call should go here...nope
        if(email.length() > 5){
            return true;
        }else{
            return false;
        }
        // Orginally here...return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic

        return password.length() > 4;
    }
    class DbVerify extends AsyncTask<String,String,String>{
        private  int answerReturned;


        @Override
        protected  void onPreExecute(){

        }
        //Required Abstract Method
        protected String doInBackground(String...params){
            final String  abc123  = inputUserName.getText().toString();
            final String password = mPasswordView.getText().toString();
            Log.d("DoInBack","past the convert");
             runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        Log.d("DoInBack","before the try loop,and inside Run()");
                    //int answerReturned;
                        // enter convert input into a hashmap to be read by the php file, via POST
                        HashMap<String, String> choice = new HashMap<String, String>();
                        // choice.put("abc123",abc123);
                        // choice.put("password",password);
                        //Hardcoded for testing
                        choice.put("abc123","abc321");
                        choice.put("password","321CBA");
                    try {
                        jsonObj = jsonParser.makeHttpRequest(urlLogin, "POST", choice);
                        Log.d("DoInBack", "jsonObj is good i think"  );


                        //if( jsonObj.isNull(TAG_SUCCESS))
                          //Log.d("DoInBack","null all day");
                            //JSONArray testJson = jsonObj.getJSONArray(TAG_SUCCESS);

                        answerReturned = jsonObj.getInt(TAG_SUCCESS);///////////////error is being thrown here, apparently nothing is inside the jsonObj

                        jsonObj.getJSONArray("user");
                    //Catch needed to use jsonObj.getInt....
                        if(answerReturned == 1){
                            ///////////////////////////////


                           //jsonObj.getJSONArray(TAG_NAME_ARRAY).get(1);
                            //if(jsonObj.getJSONArray(TAG_NAME_ARRAY).getString(TAG_USER_ROLE) == "runner"){
                            Log.d("DoInBack","heh " + jsonObj.getJSONArray(TAG_NAME_ARRAY).get(0).toString() );
                          //  if(jsonObj.getJSONArray(TAG_NAME_ARRAY).get(0).        == "runner" ){
                            //}else if(jsonObj.getJSONArray(TAG_NAME_ARRAY).get(1)  == "buyer"){

                            //}
                           //do I call the URL HERE?
                            //getApplicationContext() is simlar to this, but used in Async
                            intentRunnerHome = new Intent(getApplicationContext(), RunnerHome.class);
                            startActivity(intentRunnerHome);
                        }else{
                            //throw a loop back, instance of correct creds not valid.
                        }
                    }catch (JSONException jError){
                        jError.printStackTrace();
                        jError.getLocalizedMessage();
                    }
                }
            });
            return "heeh";
        }

        public int getAnswerReturned() {
            return answerReturned;
        }
    }


    public JSONArray getUserId_Role(){
        try {
            return jsonObj.getJSONArray(TAG_NAME_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }










    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Login.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

