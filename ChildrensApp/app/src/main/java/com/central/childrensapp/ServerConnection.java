package com.central.childrensapp;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class ServerConnection extends AsyncTask<String, String, String>{

    Tab_NewContact pane;

    String[] ownedIDs;
    String url = "http://10.0.2.2";

    String[] returnedIDs;
    String[] returnedTitle;
    String[] returnedMD5;
    String[] returnedIcon;
    String[] returnedGood;

    static InputStream inputStream = null;

    public ServerConnection(Tab_NewContact Pane)
    {
        pane = Pane;
    }

    public void setOwned(String[] Input)
    {
        ownedIDs = Input;
    }

    @Override
    protected String doInBackground(String... extension) {

        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String httpDest = url+ extension[0];
        HttpPost httppost = new HttpPost(httpDest);

        try {
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity httpentity = httpresponse.getEntity();
            inputStream = httpentity.getContent();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(pane, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            returnedIcon = new String[jArray.length()];
            returnedGood = new String[jArray.length()];
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                returnedIDs[i] = json_data.getString("char_id");
                returnedTitle[i] = json_data.getString("char_title");
                returnedMD5[i] = json_data.getString("char_md5");
                returnedIcon[i] = json_data.getString("char_icon");
                returnedGood[i] = json_data.getString("char_good");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Long result) {
        Toast.makeText(pane, "Finished", Toast.LENGTH_LONG).show();
        pane.createList();
    }

/*    public void getServerCharacters(boolean RemoveOwned)
    {
        //PLACEHOLDER
        Drawable temp = context.getResources().getDrawable(R.drawable.empty_icon);
        returnedIDs = new String[]      {"0455 500 001",    "0455 500 002"};
        returnedIcon = new Drawable[]   {temp,              temp};
        returnedTitle = new String[]    {"Good FromServer", "Bad FromServer"};
        returnedGood = new boolean[]    {true,              false};
    }
    */

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
        Drawable[] returnedIconResult = new Drawable[returnedIcon.length];
        for(int i = 0; i < returnedIcon.length; i++)
        {
            try
            {
                InputStream is = (InputStream) new URL(url).getContent();
                returnedIconResult[i] = Drawable.createFromStream(is, "src");
            } catch (Exception e) {
                returnedIconResult[i] = null;
            }
        }
        return returnedIconResult;
    }

    public boolean[] getGood()
    {
        boolean[] returnedGoodResult = new boolean[returnedGood.length];
        for(int i = 0; i < returnedGood.length; i++)
        {
            if(returnedGood[i].equals("t"))
                returnedGoodResult[i] = true;
            else if(returnedGood[i].equals("f"))
                returnedGoodResult[i] = false;
        }
        return returnedGoodResult;
    }
}
