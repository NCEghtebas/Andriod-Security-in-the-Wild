package com.example.jeffponnor.securityproject1;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Button;
import android.os.Handler;
import android.media.AudioTrack;
import android.media.AudioFormat;
import android.media.MediaPlayer;


public class MainActivity extends AppCompatActivity {

    public static AudioManager AM;
    public static MediaPlayer MP;
    private final int duration = 3; // seconds
    private final int sampleRate = 8000;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private final double freqOfTone = 440; // hz

    private final byte generatedSnd[] = new byte[2 * numSamples];
    Handler handler = new Handler();
    private final int soundPlaying = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        AM  = (AudioManager) getSystemService(AUDIO_SERVICE);


        final Button button = (Button) findViewById(R.id.audioButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (AM.isMusicActive()) {
                    String TAG = "MainActivity";
                    String msg = "Music Playing";
                    Log.i(TAG, msg);
                } else {
                    String TAG = "MainActivity";
                    String msg = "No Music Playing";
                    Log.i(TAG, msg);
                }
            }
        });



        final Button button2 = (Button) findViewById(R.id.soundButton);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                button2.setText("Sound Playing");
                MP = new MediaPlayer();

                MP = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                MP.setLooping(false);
                MP.setAudioStreamType(AudioManager.STREAM_MUSIC);
                if (!AM.isMusicActive()) {
                    String TAG = "MainActivity";
                    String msg = "Music To Start";
                    Log.i(TAG, msg);
                    button2.setText("Music to Start");
                }
                MP.start();
                String TAG2 = "MainActivity";
                String msg2 = "1";
                Log.i(TAG2, msg2);
                button2.setText("12");
                try {
                    Thread.sleep(2500);
                } catch (Exception e){};
                if (AM.isMusicActive()) {
                    String TAG = "MainActivity";
                    String msg = "Music Start";
                    Log.i(TAG, msg);
                    button2.setText("Music Started");
                }

            }
        });





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
