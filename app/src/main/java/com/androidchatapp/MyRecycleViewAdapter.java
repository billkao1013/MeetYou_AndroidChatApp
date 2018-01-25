package com.androidchatapp;
/**
 * Created by Bill Kao on 2017/2/13.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder>
{
    OnItemClickListener mItemClickListener;
    private Context context;
    ArrayList<String> contact;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public MyRecycleViewAdapter(Context myContext, ArrayList<String> contactlist){
        contact=contactlist;
        context=myContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView;
        contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);


        ViewHolder vh = new ViewHolder(contactView);
        return vh;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String contactname=contact.get(position);
        //Map<String,?> movie = movieData.get(position);
        //holder.bindMovieData(movie);
        holder.contactName.setText(contactname);
    }


    @Override
    public int getItemCount() {
        return contact.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView contactName;


        public ViewHolder(View itemView) {
            super(itemView);

            contactName=(TextView)itemView.findViewById(R.id.contactNmae);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(mItemClickListener!=null)
                        mItemClickListener.onItemClick(v,getPosition());
                }
            });


        }

    }
}

