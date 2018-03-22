/**
 * Created by super and edit by Miguel on 3/21/2018.
 */

package com.example.user.campusrunners;

/**
 * Created by Yadi on 3/16/18.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class JSONParser {

    static InputStream is = null;
    static JSONObject postDataParams = null;
    static JSONObject responseParm = null;
    static String json = "";
    HttpURLConnection conn;
    String message = "";
    URL url =null;

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String stUrl, String method,
                                      HashMap<String,String> params) {

        // Making HTTP request
        switch (method) {
            case "GET":
                try {
                    String encodedParm;
                    //build parameters
//                    postDataParams = new JSONObject();
//                    Set keys = params.keySet();
//
//                    for (Iterator i = keys.iterator(); i.hasNext(); ) {
//                        String key = (String) i.next();
//                        String value = (String) params.get(key);
//                        postDataParams.put(key, value);
//                    }
                    Log.e("postDataParms", "json: " + postDataParams);
                    //encodedParm = getPostDataString(postDataParams);

                    encodedParm = buildQueryString(params);
                    //encodedParm= "pid=4";


                    url = new URL(stUrl + "?" + encodedParm);
                    Log.e("url", "Url: " + url);
                    conn = (HttpURLConnection) url.openConnection();
                    //conn.setReadTimeout(15000 /* milliseconds */);
                    //conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    //new
                    //conn.setRequestProperty("content-type", "application/json");
                    conn.setRequestProperty("cache-control", "no-cache");
//                    InputStream response = connection.getInputStream();
//                    InputStream response = new URL(url).openStream();

                    int responseCode = conn.getResponseCode();
                    Log.e("responseError", "" + responseCode);

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        reader.close();
                        json = sb.toString();

                        Log.e("in json string", "Json:  " + json);
                        try {
                            responseParm = new JSONObject(json);
                        } catch (JSONException e) {

                            Log.e("JSON Parser", "Error parsing data " + e.toString());
                        }

                        return responseParm;
                    } else {
                        Log.e("responseError", "" + responseCode);

                        return postDataParams;
                        // }
                    }
                } catch (ProtocolException e) {
                    Log.e("protocol error", "" + e.getMessage());
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    Log.e("malformed error", "" + e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("i/o error", "" + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.e("encode error", "" + e.getMessage());
                    e.printStackTrace();
                }
                if (conn != null) {
                    conn.disconnect();
                }
//                } catch (Exception e) {
//                    String error = new String("Exception: " + e.getMessage());
//                    Log.e("Connection error", "Exception" + e.getMessage());
//                }
                break;
            case "POST":
                try {
                    URL url = new URL(stUrl);
                    Log.e("url", "Url: " + url);
                    //build parameters
                    postDataParams = new JSONObject();
                    Set keys = params.keySet();

                    for (Iterator i = keys.iterator(); i.hasNext(); ) {
                        String key = (String) i.next();
                        String value = (String) params.get(key);
                        postDataParams.put(key, value);
                    }
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    //new
                    conn.setRequestProperty("content-type", "application/json");
                    conn.setRequestProperty("cache-control", "no-cache");

                    //other site stuff
                    message = postDataParams.toString();
                    conn.setFixedLengthStreamingMode(message.getBytes().length);
                    conn.connect();

                    //setup send
                    OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                    os.write(message.getBytes());
                    //clean up
                    os.flush();
                    os.close();

                    int responseCode = conn.getResponseCode();
                    Log.e("responseError", "" + responseCode);

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        reader.close();
                        json = sb.toString();

                        Log.e("in json string", "Json:  " + json);
                        try {
                            responseParm = new JSONObject(json);
                        } catch (JSONException e) {

                            Log.e("JSON Parser", "Error parsing data " + e.toString());
                        }

                        return responseParm;
                    } else {
                        Log.e("responseError", "" + responseCode);

                        return postDataParams;
                        // }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            }
            return responseParm;
            }

    //    public String getPostDataString(JSONObject params) {
//
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//
//        Iterator<String> itr = params.keys();
//
//        while(itr.hasNext()){
//
//            String key= itr.next();
//            Object value = null;
//            try {
//                value = params.get(key);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            try {
//                result.append(URLEncoder.encode(key, "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            result.append("=");
//            try {
//                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//        }
//        Log.e("result in getpost",result.toString());
//        return result.toString();
//    }
    public static String buildQueryString(final HashMap<String, String> map) {
        try {
            final Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            final StringBuilder sb = new StringBuilder(map.size() * 8);
            while (it.hasNext()) {
                final Map.Entry<String, String> entry = it.next();
                final String key = entry.getKey();
                if (key != null) {
                    sb.append(URLEncoder.encode(key, "UTF-8"));
                    sb.append("=");
                    final String value = entry.getValue();
                    final String valueAsString = value != null ? URLEncoder.encode(value.toString(), "UTF-8") : "";
                    sb.append(valueAsString);
                    if (it.hasNext()) {
                        sb.append("&");
                    }
                } else {
                    // Do what you want...for example:
                    assert false : String.format("Null key in query map: %s", map.entrySet());
                    Log.e("instringbulider error", "map"+it);
                    break;
                }
            }
            return sb.toString();
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}