package com.central.childrensapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class ServerConnection extends AsyncTask<String, String, String>{

    Context context;

    String[] ownedIDs;
    String url = "http://10.0.2.2";

    String[] returnedIDs;
    String[] returnedTitle;
    String[] returnedMD5;
    Drawable[] returnedIcon;
    boolean[] returnedGood;

    static InputStream inputStream = null;

    public ServerConnection(Context Context)
    {
        context = Context;
    }

    public void setOwned(String[] Input)
    {
        ownedIDs = Input;
    }

    @Override
    protected String doInBackground(String... extension) {

        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String httpDest = url + extension[0];
        HttpPost httppost = new HttpPost(httpDest);

        try {
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity httpentity = httpresponse.getEntity();
            inputStream = httpentity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");}

                inputStream.close();
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jArray = new JSONArray(result);

            returnedIDs = new String[jArray.length()];
            returnedTitle = new String[jArray.length()];
            returnedMD5 = new String[jArray.length()];
            returnedIcon = new Drawable[jArray.length()];
            returnedGood = new boolean[jArray.length()];
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                returnedIDs[i] = json_data.getString("char_id");
                returnedTitle[i] = json_data.getString("char_title");
                returnedMD5[i] = json_data.getString("char_md5");
                try {
                    InputStream iconURL = (InputStream) new URL(url + json_data.getString("char_icon")).getContent();
                    Drawable iconTemp = Drawable.createFromStream(iconURL, "src name");
                    returnedIcon[i] = iconTemp;
                } catch (Exception e) {
                    returnedIcon[i] = context.getResources().getDrawable(R.drawable.empty_icon);
                }

                if(json_data.getString("char_good").equals("t"))
                { returnedGood[i] = true; }
                else
                { returnedGood[i] = false; }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "t";
    }

    public String[] getIDs()
    {
        return returnedIDs;
    }

    public String[] getTitle()
    {
        return returnedTitle;
    }

    public String[] getMD5()
    {
        return returnedMD5;
    }

    public Drawable[] getIcon()
    {
        return returnedIcon;
    }

    public boolean[] getGood()
    {
        return returnedGood;
    }
}
