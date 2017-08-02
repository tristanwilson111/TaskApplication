package com.icaninteractive.tristan.taskapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    public int countdownNum;
    public SectorProgressView spv;

    public int userNum = 1000;
    public double timerPercent = (double)100/(double)userNum;
    public double progress = 0;
    public long muf;
    public long suf;

    /*
    ToDo: Align layout correctly
    ToDo: Add Start button, move code to onClickListener(). Remove code form onCreate()
    ToDo: Add Task Completed, Task Not Completed buttons to onFinish()
    ToDo: Create a tracker to keep tasks recorded.
    ToDo: Decide whether the timer will be in intervals of 5, or to fix progress wheel not finishing if stopping on number not multiple of 5
    ToDo: Set Top-Left reading to HH:MM:SS, then MM:SS, then SS.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView countdownText = (TextView) findViewById(R.id.countdownText);
        //Set the spv object to a new SectorProgressView, linked to our spv id in activity_main.xml
        spv = (SectorProgressView) findViewById(R.id.spv);

        //Create Countdown Timer Object that runs for 45 seconds (45,000 Milliseconds)
        //For some reason, the timer stops at 43. Adding 2000 milliseconds resolves this issue.
        new CountDownTimer(((userNum*1000)+1000), 1000) {
            public void onTick(long millisUntilFinished) {
                muf = millisUntilFinished;
                suf = muf/1000;
                //Set top-left number to Milliseconds until finished / 1000, to show seconds until finished
                //countdownText.setText(String.valueOf(suf));
                countdownNum++;
                spv.setPercent((float)progress);
                progress = progress+timerPercent;

                countdownText.setText(""+suf);

            }

            public void onFinish() {
                spv.setPercent((float)progress);
                countdownText.setText("Time's up!");
            }

        }.start();
    }//End of onCreate Method
}//End of MainActivity
