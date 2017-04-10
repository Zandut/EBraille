package com.ebraille.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSON {

    private JSONObject JsonObject = null;
    private ArrayList<JSONObject> arrayJsonObject = new ArrayList<JSONObject>();
    private String url = "";
    private InputStream is = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getXMLToJsonObject() {
        String json = "";
        try {


            url = url.replaceAll(" ", "%20");

            URL urlObject = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();

            conn.setConnectTimeout(10000);

            is = conn.getInputStream();

        } catch (Exception e) {

            e.printStackTrace();


        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(

                    is, "iso-8859-1"), 8);

            StringBuilder sb = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");

            }

            is.close();

            json = sb.toString();


        } catch (Exception e) {

            Log.e("Buffer Error", "Error converting result " + e.toString());

        }

        // try parse the string to a JSON object

        try {

            com.ebraille.models.JSONObject xmlJSONObj = XML.toJSONObject(json);
            String jsonPrettyPrintString = xmlJSONObj.toString(4);
            Log.d("TEST", jsonPrettyPrintString);
            JsonObject = new JSONObject(jsonPrettyPrintString);

        } catch (JSONException e) {

            Log.e("JSON Parser", "Error parsing data " + e.toString());

        }

        // return JSON String

        return JsonObject;
    }

    public JSONObject getJsonObject() {
        String json = "";
        HttpURLConnection conn = null;
        try {


            url = url.replaceAll(" ", "%20");

            URL urlObject = new URL(url);
            conn = (HttpURLConnection) urlObject.openConnection();
            conn.setConnectTimeout(10000);


            is = conn.getInputStream();

        } catch (Exception e) {

            e.printStackTrace();

        }


        try {
            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(

                        is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                is.close();

                json = sb.toString();

            }
        } catch (Exception e) {

            Log.e("Buffer Error", "Error converting result " + e.toString());

        }

        // try parse the string to a JSON object

        try {
            JsonObject = new JSONObject(json);


        } catch (JSONException e) {

            Log.e("JSON Parser", "Error parsing data " + e.toString());

        }

        // return JSON String

        conn.disconnect();
        return JsonObject;

    }

    public JSONObject getJsonObject(HashMap<String, String> parameter) {
        String json = "";
        boolean result = false;
        OutputStream out = null;
        int status = 0;
        HttpURLConnection httpCon = null;
        try {


            url = url.replaceAll(" ", "%20");


            URL serverUrl = null;
            try {
                serverUrl = new URL(url);
                
            } catch (MalformedURLException e) {
                Log.e("AppUtil", "URL Connection Error: "
                        + url, e);
                result = false;
            }

            StringBuilder postBody = new StringBuilder();
            
            Iterator<Map.Entry<String, String>> iterator = parameter.entrySet()
                    .iterator();

            
            while (iterator.hasNext()) {
                Map.Entry<String, String> param = iterator.next();
                postBody.append(param.getKey()).append("=")
                        .append(param.getValue());
                if (iterator.hasNext()) {
                    postBody.append("&");
                }
            }
            
            
            
            String body = postBody.toString();
            
            byte[] bytes = body.getBytes();

            try {
                httpCon = (HttpURLConnection) serverUrl.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setRequestProperty("Content-Type", "application/json");
                httpCon.setConnectTimeout(10000);
                httpCon.setUseCaches(false);
                httpCon.setRequestMethod("POST");
                
                
                out = httpCon.getOutputStream();
                out.write(bytes);
                
                
                

                status = httpCon.getResponseCode();
                if (status == 200) {
                    result = true;
                    is = httpCon.getInputStream();
                } else {
                    result = false;
                }
            
            } 
            catch (Exception ex)
            {
            	ex.printStackTrace();
            }
            


        } catch (Exception e) {

            e.printStackTrace();

        }
        
        Log.d("RESULT", ""+result+" STATUS : "+status);

        if (result) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(

                        is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                is.close();

                out.close();
                json = sb.toString();

            } catch (Exception e) {

                Log.e("Buffer Error", "Error converting result " + e.toString());

            }

            // try parse the string to a JSON object

            try {
                JsonObject = new JSONObject(json);


            } catch (JSONException e) {

                Log.e("JSON Parser", "Error parsing data " + e.toString());

            }
        }
        httpCon.disconnect();
        // return JSON String

        return JsonObject;

    }

    public List<JSONObject> getArrayJsonObject() {
        String json = "";
        HttpURLConnection conn = null;
        try {

            url = url.replaceAll(" ", "%20");
            URL urlObject = new URL(url);
            conn = (HttpURLConnection) urlObject.openConnection();
            conn.setConnectTimeout(10000);

            is = conn.getInputStream();

        } catch (Exception e) {

            e.printStackTrace();

        }


        try {
            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(

                        is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                is.close();

                json = sb.toString();
            }

        } catch (Exception e) {

            Log.e("Buffer Error", "Error converting result " + e.toString());

        }

        // try parse the string to a JSON object


        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayJsonObject.add(jsonArray.getJSONObject(i));
            }

        } catch (JSONException e) {

            Log.e("JSON Parser", "Error parsing data " + e.toString());

        }
        conn.disconnect();
        // return JSON String

        return arrayJsonObject;
    }

    public String getJSONFromURL() {
        String json = "";
        try {

            url = url.replaceAll(" ", "%20");

            URL urlObject = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
            conn.setConnectTimeout(10000);
            is = conn.getInputStream();

        } catch (Exception e) {

            e.printStackTrace();

        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(

                    is, "iso-8859-1"), 8);

            StringBuilder sb = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");

            }

            is.close();

            json = sb.toString();

        } catch (Exception e) {

            Log.e("Buffer Error", "Error converting result " + e.toString());

        }

        return json;
    }


}
