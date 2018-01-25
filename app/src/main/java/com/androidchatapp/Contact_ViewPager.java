package com.androidchatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
//import com.eftimoff.viewpagertransformers;
/**
 * Created by billkao on 4/22/17.
 */

public class Contact_ViewPager extends AppCompatActivity implements Fragment_DetailView.OnContactImageClickListencer{
    MyFragmentStatePagerAdapter myPagerAdapter;
    ViewPager mViewPager;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.viewerpager);
        //pd = new ProgressDialog(this);
        //pd.setMessage("Loading...");
        //pd.show();
        String url = "https://mychatapp-dcb19.firebaseio.com/users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(this);
        rQueue.add(request);

    }
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(UserDetails.username)) {
                    al.add(key);
                }
                totalUsers++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myPagerAdapter=new MyFragmentStatePagerAdapter(getSupportFragmentManager(),al);
        mViewPager=(ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(myPagerAdapter);

        mViewPager.setCurrentItem(2);
        mViewPager.setPageTransformer(true, new FlipHorizontalTransformer());
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //pd.dismiss();
    }
    @Override
    public void OnContactImageClick(String contact)
    {
        UserDetails.chatWith = contact;
        startActivity(new Intent(this, Chat_Activity.class));
    }
}


class MyFragmentStatePagerAdapter extends FragmentPagerAdapter {
    int count;
    ArrayList<String> al ;
    public MyFragmentStatePagerAdapter(FragmentManager fm, ArrayList<String> input) {
        super(fm);
        al=input;
        count=al.size();
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment_DetailView.newInstance(al.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position){
        Locale l=Locale.getDefault();
        String name=al.get(position);
        return name.toUpperCase(l);
    }
    @Override
    public int getCount() {
        return count;
    }
}
