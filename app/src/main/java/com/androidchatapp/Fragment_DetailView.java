package com.androidchatapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Bill Kao on 2017/2/14.
 */
public class Fragment_DetailView extends Fragment {
    private OnContactImageClickListencer mOnContactImageClickListencer;
    private static final String ARG_MOVIE="movie";
    private int total;
    private String contactname;
    public static Fragment_DetailView newInstance(String contactname) {
        Fragment_DetailView fragment = new Fragment_DetailView();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE,contactname );
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            contactname=(String)getArguments().getSerializable(ARG_MOVIE);
        }
        if(savedInstanceState!=null)
            total=savedInstanceState.getInt("Total");
        mOnContactImageClickListencer=(OnContactImageClickListencer)getContext();

    }
    /*
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("Total",total);
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
        View rootView =inflater.inflate(R.layout.detailview_frag,container,false);
        final TextView name=(TextView)rootView.findViewById(R.id.contactNmae_viewpager);
        final ImageView imageView=(ImageView)rootView.findViewById(R.id.imageView);
        name.setText(contactname);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnContactImageClickListencer.OnContactImageClick(contactname);
            }
        });

        return rootView;
    }
    public Fragment_DetailView() { }
    public interface OnContactImageClickListencer{
        public void OnContactImageClick(String contact);
    }
}
