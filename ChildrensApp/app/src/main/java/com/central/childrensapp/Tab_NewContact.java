package com.central.childrensapp;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/*
Activity class to fill New Contact Phonebook
 */

public class Tab_NewContact extends ListActivity {

    ServerConnection connection = new ServerConnection(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Application Context
        ApplicationVariables globalVar = (ApplicationVariables)getApplicationContext();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Create a server connection and get list of characters
            connection.setOwned(globalVar.getIdCharacters());
            try {
                connection.execute("/char_list.php").get();
                setListAdapter(new Adapter_ServerObject(connection.getIcon(), connection.getTitle(), connection.getGood(), connection.getIDs(), getApplicationContext()));
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
}
