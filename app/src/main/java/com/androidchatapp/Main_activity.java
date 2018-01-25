package com.androidchatapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by billkao on 4/20/17.
 */

public class Main_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ContactPageFrag.OnCardItemClickedListener{
    DrawerLayout drawerLayout;
    Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_frag);
        Toolbar toolBar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Contact");

        NavigationView navigationView = (android.support.design.widget.NavigationView) findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.header_name);
        navUsername.setText(UserDetails.username);

        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle toggler = new ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.drawer_open, R.string.drawer_close){
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
        if (savedInstanceState != null) {
            if (getSupportFragmentManager().getFragment(savedInstanceState, "currFrag") != null)
                mFragment = getSupportFragmentManager().getFragment(savedInstanceState, "currFrag");
            else
                mFragment =ContactPageFrag.newInstance();
        } else
            mFragment =ContactPageFrag.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mFragment)
                .commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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
                mFragment = ContactPageFrag.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        mFragment).addToBackStack(null).commit();
                break;
            case R.id.action_you2:
                Intent intent = new Intent(this, YouTubeActivity.class);
                intent.putExtra(YouTubeActivity.VIDEO_ID, "um6DMjCJV2Y");
                startActivity(intent);
                break;
            default:
                mFragment = ContactPageFrag.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        mFragment).addToBackStack(null).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onCardItemClicked (String name)
    {
        UserDetails.chatWith = name;
        startActivity(new Intent(this, Chat_Activity.class));
    }

}
