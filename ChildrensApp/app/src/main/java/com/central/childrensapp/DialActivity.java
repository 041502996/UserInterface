package com.central.childrensapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/*
This Activity class is to be used as the video-player activity
Intent should ALWAYS have String Extra "path" as the directory
 */

public class DialActivity extends Activity {

    Button back;
    VideoView vid;
    String path;
    InterstitialAd mInterstitialAd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        //Add banner here
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(this);
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
         // Defined in res/values/strings.xml
         mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
         // Request for Ads & Add a test device to show Test Ads
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("CC5F2C72DF2B356BBF0DA198").build();

         // Load ads into Interstitial Ads
          mInterstitialAd.loadAd(adRequest);

          // Prepare an Interstitial Ad Listener
          mInterstitialAd.setAdListener(new AdListener() {
          public void onAdLoaded() {
          // Call displayInterstitial() function
          showInterstitial();
           }
         });

                                        }
                                    } );
    }
    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();

        }
    }
}
