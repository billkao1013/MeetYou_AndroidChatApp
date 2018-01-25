package com.androidchatapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by billkao on 4/26/17.
 */

public class Chat_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Fragment_Chat.microphoneClickListener{
    DrawerLayout drawerLayout;
    Fragment mFragment;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    EditText microinput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(UserDetails.chatWith);
        NavigationView navigationView = (android.support.design.widget.NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.header_name);
        navUsername.setText(UserDetails.username);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggler = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(toggler);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggler.syncState();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.chat_container, Fragment_Chat.newInstance()).commit();
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                Intent intent1 = new Intent(this,Login.class);
                startActivity(intent1);
                break;
            case R.id.contact_viewpager:
                startActivity(new Intent(this,Contact_ViewPager.class));
                break;
            case R.id.contact:
                startActivity(new Intent(this,Main_activity.class));
                break;
            case R.id.action_you2:
                Intent intent = new Intent(this, YouTubeActivity.class);
                intent.putExtra(YouTubeActivity.VIDEO_ID, "um6DMjCJV2Y");
                startActivity(intent);
                break;
            default:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.chat_container, Fragment_Chat.newInstance()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void speaktoText(){
        promptSpeechInput();
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtSpeechInput.setText(result.get(0));
                    //Toast.makeText(this, result.get(0), Toast.LENGTH_SHORT).show();
                    Fragment frag = getSupportFragmentManager().findFragmentById(R.id.chat_container);
                    ((EditText) frag.getView().findViewById(R.id.messageArea)).setText(result.get(0),TextView.BufferType.EDITABLE);
                    //UserDetails.microinput=(String)result.get(0);

                }
                break;
            }

        }
    }
}
