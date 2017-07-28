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

    public int userNum = 45;
    public int timerPercent = 100/userNum;
    public int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView countdownText = (TextView) findViewById(R.id.countdownText);
        //Set the spv object to a new SectorProgressView, linked to our spv id in activity_main.xml
        spv = (SectorProgressView) findViewById(R.id.spv);

        //Create Countdown Timer Object that runs for 45 seconds (45,000 Milliseconds)
        //For some reason, the timer stops at 43. Adding 2000 milliseconds resolves this issue.
        new CountDownTimer(47000, 1000) {
            public void onTick(long millisecondsUntilFinished) {
                countdownText.setText(String.valueOf(countdownNum));
                countdownNum++;
                spv.setPercent(progress);
                progress = progress+timerPercent;
            }

            public void onFinish() {

            }
        }.start();
    }//End of onCreate Method

    public int timerToPercent(int userNum){
        //100 / countdownNum = percentage
        int percent = 100/countdownNum;
        return percent;
    }
}//End of MainActivity
