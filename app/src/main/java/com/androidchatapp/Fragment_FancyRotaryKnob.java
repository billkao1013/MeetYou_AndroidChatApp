package com.androidchatapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kevin on 7/6/2014.
 */
public class Fragment_FancyRotaryKnob extends Fragment {
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    UnlockListener unlock;
    public interface UnlockListener{
        public void Unlock();
    }
    public static Fragment_FancyRotaryKnob newInstance(int sectionNumber) {
        Fragment_FancyRotaryKnob fragment = new Fragment_FancyRotaryKnob();
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unlock=(UnlockListener)getContext();
        //t3Listener= (OnTask3ClickedListener) getContext();

    }

    public Fragment_FancyRotaryKnob() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fancyrotaryknob, container, false);
        final TextView status = (TextView) rootView.findViewById(R.id.status);

        FancyRotaryKnobView rotaryKnobView =
                 (FancyRotaryKnobView) rootView.findViewById(R.id.fancyRotaryKnobView);
        rotaryKnobView.setKnobListener(new FancyRotaryKnobView.RotaryKnobListener() {
            @Override
            public void onKnobChanged(int arg) {
                status.setText(Integer.toString(arg));
                if(arg==300) {
                    unlock.Unlock();
                    Toast.makeText(getContext(), "Unlock Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return rootView;
    }
}