package com.example.mthomsen.airhockitygame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;


public class Settings extends Activity {


    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final RadioGroup pointsGroup  = (RadioGroup) findViewById(R.id.pointsGroup);
        pointsGroup.clearCheck();
        Button defButton = (Button) findViewById(R.id.default_button);
        Button retButton = (Button) findViewById(R.id.return_button);

        defButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointsGroup.check(R.id.radio_three);
            }
        });

        pointsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(R.id.radio_three == checkedId){
                    prefs.edit().putInt("points", 3).commit();
                } else if (checkedId == R.id.radio_five){
                    prefs.edit().putInt("points",5).commit();
                } else if (checkedId == R.id.radio_ten){
                    prefs.edit().putInt("points",10).commit();
                }
            }
        });

        retButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }



}
