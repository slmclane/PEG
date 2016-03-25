package com.example.strykermclane.peg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class careguide extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    int currentSection = 0;
    String htmlAsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careguide);

        Intent myIntent = getIntent();

        getContent();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.navList);
        String[] sectionArray = {"Intro","Week 1","Week 2","Week 3","Week 4"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sectionArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    private void displayView(int position){
        switch(position){
            case 0:
                currentSection = 0;
                break;
            case 1:
                currentSection = 1;
                break;
            case 2:
                currentSection = 2;
                break;
            case 3:
                currentSection = 3;
                break;
            case 4:
                currentSection = 4;
                break;
            default:
                currentSection = 0;
                break;
        }
        getContent();

    }

    private void getContent(){

        // get our html content
        switch(currentSection) {
            case 0:
                htmlAsString = getString(R.string.careGuide0);      // used by WebView
                break;
            case 1:
                htmlAsString = getString(R.string.careGuide1);
                break;
            case 2:
                htmlAsString = getString(R.string.careGuide2);
                break;
            case 3:
                htmlAsString = getString(R.string.careGuide3);
                break;
            case 4:
                htmlAsString = getString(R.string.careGuide4);
                break;
        }
        // set the html content on a Webview;
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);

    }


}