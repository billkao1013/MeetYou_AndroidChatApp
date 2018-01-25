package com.androidchatapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by billkao on 4/26/17.
 */

public class Fragment_Chat extends Fragment {
    Firebase reference1, reference2;
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    microphoneClickListener mMicclick;

    public interface microphoneClickListener{
        public void speaktoText();
    }
    public static Fragment_Chat newInstance() {
        Fragment_Chat fragment = new Fragment_Chat();
        Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public Fragment_Chat(){
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mMicclick=(microphoneClickListener)getContext();
        //onCardItemClickedListener = (ContactPageFrag.OnCardItemClickedListener) getContext();
        //setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.activity_chat,container,false);
        layout = (LinearLayout)rootView.findViewById(R.id.layout1);
        sendButton = (ImageView)rootView.findViewById(R.id.sendButton);
        messageArea = (EditText)rootView.findViewById(R.id.messageArea);
        scrollView = (ScrollView)rootView.findViewById(R.id.scrollView);
        final ImageView micophone=(ImageView)rootView.findViewById(R.id.microphone);
        Firebase.setAndroidContext(getContext());
        reference1 = new Firebase("https://mychatapp-dcb19.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://mychatapp-dcb19.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);
        micophone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mMicclick.speaktoText();
                //messageArea.setText(UserDetails.microinput, TextView.BufferType.EDITABLE);
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                messageArea.setText("");
                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(UserDetails.username)){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return rootView;
    }
    public void addMessageBox(String message, int type){
        TextView textView = new TextView(getContext());
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }
        textView.setTextIsSelectable(true);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
