package com.central.childrensapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class InformationActivity extends Activity {

    ServerConnection connection = new ServerConnection(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        //Get Application Context
        ApplicationVariables globalVar = (ApplicationVariables)getApplicationContext();
        Intent fromIntent = getIntent();
        String id = (String) fromIntent.getStringExtra("id");


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Create a server connection and get list of characters
            connection.setOwned(globalVar.getIdCharacters());
            try {
                connection.execute("/char_list.php?id=" + id).get();

                ImageView iconIV = (ImageView) this.findViewById(R.id.icon);
                iconIV.setImageDrawable(connection.getIcon()[0]);

                TextView titleTV = (TextView) this.findViewById(R.id.title);
                titleTV.setText(connection.getTitle()[0]);

                TextView blurbTV = (TextView) this.findViewById(R.id.blurb);
                blurbTV.setText(connection.getMD5()[0]);


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this, "No Connection Available", Toast.LENGTH_LONG).show();
        }
    }

    public void demo(View view){
        //PLACEHOLDER
    }

    public void download(View view){
        //PLACEHOLDER
    }
}
