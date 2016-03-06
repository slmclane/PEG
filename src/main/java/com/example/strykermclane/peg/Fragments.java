package com.example.strykermclane.peg;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A placeholder fragment containing a simple view.
 */
public class Fragments extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragments,
                container, false);

        Button btn1 = (Button)view.findViewById(R.id.button1);
        Button btn2 = (Button)view.findViewById(R.id.button2);
        Button btn3 = (Button)view.findViewById(R.id.button3);
        Button btn4 = (Button)view.findViewById(R.id.button4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;

    }
}