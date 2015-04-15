package com.central.childrensapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

/*
This Activity class is to be used as the video-player activity
Intent should ALWAYS have String Extra "path" as the directory
 */

public class DialActivity extends Activity {
    Button back;
    VideoView vid;
    final Context context=this;
    String path;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        //Get path from intent
//        Intent fromIntent = getIntent();
//        String path = (String) fromIntent.getStringExtra("path");

        //PLACEHOLDER
        TextView tv = (TextView) findViewById(R.id.title);
        tv.setText(path);


        back= (Button) findViewById(R.id.btCancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vid=(VideoView)findViewById(R.id.videoView);
        //Intent intent = getIntent();
        //path = intent.getStringExtra("path");
        path= "android.resource://"+ getPackageName() + "/"+ R.raw.sample;
        vid.setVideoURI(Uri.parse(path));

        vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared (MediaPlayer arg0){
                vid.start();
            }
        } );
        //Here we can add AD after the character is finished.
        vid.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        public void onCompletion (MediaPlayer mp){
                                            mp.reset();
                                            //vid .setVideoPath(path);
                                            path = "android.resource://" + getPackageName() + "/" + R.raw.sample;
                                            vid.setVideoURI(Uri.parse(path));
                                            vid.start();
                                        }
                                    }

        );

    }
}
