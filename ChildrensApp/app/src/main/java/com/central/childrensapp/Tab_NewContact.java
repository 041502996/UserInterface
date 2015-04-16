package com.central.childrensapp;

import android.app.ListActivity;
import android.os.Bundle;

/*
Activity class to fill New Contact Phonebook
 */

public class Tab_NewContact extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Application Context
        ApplicationVariables globalVar = (ApplicationVariables)getApplicationContext();

        //Create a server connection and get list of characters
        ServerConnection connection= new ServerConnection(this);
        connection.setOwned(globalVar.getIdCharacters());
        connection.getServerCharacters();

        // Binding resources Array to ListAdapter
        this.setListAdapter(new Adapter_ServerObject(connection.getIcon(), connection.getTitle(), connection.getGood(), connection.getIDs(), this.getApplicationContext()));
    }
}
