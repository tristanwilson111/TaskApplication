package com.icaninteractive.tristan.taskapplication;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.timqi.sectorprogressview.SectorProgressView;

/*
    This application will run a countdown on a graphic icon, shown using a circular progress wheel from 0% to 100%.
    Length of time will be set by the user, stored in a SQLite database. After completion, application will ask the user
    if they completed the task (Yes/No buttons), and giving the user a congratulations message.

    Created by Tristan Wilson on 07/14/2017
 */

public class MainActivity extends AppCompatActivity {

    //Initialize the countdownNum, SectorProgressView object, countdownText, and the timerToPercent float
    private int countdownNum;
    private SectorProgressView spv;

    private int userNum = 45;
    private double timerPercent = (double)100/(double)userNum;
    private double progress = 0;
    private long muf;
    private long suf;


    /*
    ToDo: IMPORTANT: Utilize Implementation/Abstract methods for ProgressWheel, TaskTimer logic to be used on each activity created.
    ToDo: Add Task Completed, Task Not Completed buttons to onFinish()
    ToDo: Create a tracker to keep tasks recorded.
    ToDo: Set Top-Left reading to HH:MM:SS, then MM:SS, then SS.
    ToDo: Save the user-set variables in a SQLite database
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(400);
                //Set the spv object to a new SectorProgressView, linked to our spv id in activity_main.xml
                spv = (SectorProgressView) findViewById(R.id.spv);

                //Create Countdown Timer Object that runs for 45 seconds (45,000 Milliseconds)
                //For some reason, the timer stops at 43. Adding 2000 milliseconds resolves this issue.
                new CountDownTimer(((userNum*1000)+1000), 1000) {
                    public void onTick(long millisUntilFinished) {
                        muf = millisUntilFinished;
                        suf = muf/1000;
                        button.setText(suf+" seconds");
                        //Set top-left number to Milliseconds until finished / 1000, to show seconds until finished
                        countdownNum++;
                        spv.setPercent((float)progress);
                        progress = progress+timerPercent;

                    }
                    public void onFinish() {
                        spv.setPercent((float)progress);
                        button.setText("Time's up!");
                        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(400);
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent startSecondaryActivity = new Intent(MainActivity.this, SecondaryActivity.class);
                        startActivity(startSecondaryActivity);
                    }
                }.start();
            }
        });
    }//End of onCreate Method
}//End of MainActivity
