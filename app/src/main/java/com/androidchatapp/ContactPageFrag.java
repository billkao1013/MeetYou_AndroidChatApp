package com.androidchatapp;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

/**
 * Created by billkao on 4/20/17.
 */

public class ContactPageFrag extends Fragment{
    private OnCardItemClickedListener onCardItemClickedListener;
    RecyclerView recyclerView;
    MyRecycleViewAdapter recyclerViewAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<String> al = new ArrayList<>();
    ProgressDialog pd;
    int totalUsers;
    String url;
    public static ContactPageFrag newInstance() {
        ContactPageFrag fragment = new ContactPageFrag();
        Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public ContactPageFrag(){

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        onCardItemClickedListener = (OnCardItemClickedListener) getContext();
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        //ArrayList<String> al = new ArrayList<>();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.show();
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

        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);

        System.out.println("" + al.size());
        final View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
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
        recyclerViewAdapter = new MyRecycleViewAdapter(getActivity(), al);
        recyclerView.setAdapter(recyclerViewAdapter);

        pd.dismiss();
        recyclerViewAdapter.setOnItemClickListener(new MyRecycleViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                onCardItemClickedListener.onCardItemClicked(al.get(position));
            }
        });
    }
    public interface OnCardItemClickedListener{
        public void onCardItemClicked(String name);
    }

}
