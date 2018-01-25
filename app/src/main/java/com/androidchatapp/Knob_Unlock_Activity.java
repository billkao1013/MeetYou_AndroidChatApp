package com.androidchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by billkao on 4/25/17.
 */

public class Knob_Unlock_Activity extends AppCompatActivity implements Fragment_FancyRotaryKnob.UnlockListener{
    final static int FANCYROTARYVIEWFRAGMENT = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fancy_knob_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.knob_container, Fragment_FancyRotaryKnob.newInstance(0))
                .commit();
    }
    @Override
    public void Unlock(){
        startActivity(new Intent(this, Main_activity.class));
    }
}
