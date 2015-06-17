package com.example.mthomsen.airhockitygame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;


public class Settings extends Activity {
    private int noPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final RadioGroup pointsGroup  = (RadioGroup) findViewById(R.id.pointsGroup);
        pointsGroup.check(R.id.radio_three);
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
                    noPoints = 3;
                } else if (checkedId == R.id.radio_five){
                    noPoints = 5;
                } else if (checkedId == R.id.radio_ten){
                    noPoints = 10;
                }
            }
        });

        retButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent results = new Intent();
                Bundle result = new Bundle();
                result.putInt("points",noPoints);
                results.putExtras(result);
                setResult(RESULT_OK,results);
                finish();
            }
        });



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
