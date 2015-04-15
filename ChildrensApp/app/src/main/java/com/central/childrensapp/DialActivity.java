package com.central.childrensapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

/*
This Activity class is to be used as the video-player activity
Intent should ALWAYS have String Extra "path" as the directory
 */

public class DialActivity extends Activity {

    Button back;
    VideoView vid;
    String path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        //Get path from intent
        Intent fromIntent = getIntent();
        path = (String) fromIntent.getStringExtra("path");

        //Set cancel button OnClick to finish();
        back= (Button) findViewById(R.id.btCancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            } } );

        //Get and Set Video inside of Player
        vid=(VideoView)findViewById(R.id.videoView);
        vid.setVideoURI(Uri.parse(path));

        //When loaded, start
        vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared (MediaPlayer arg0){
                vid.start();
            } } );

        //When finished, close. Ad scripting goes here.
        vid.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        public void onCompletion (MediaPlayer mp){
                                            //Ad Code PlaceHolder
                                            finish();
                                        }
                                    } );
    }
}
