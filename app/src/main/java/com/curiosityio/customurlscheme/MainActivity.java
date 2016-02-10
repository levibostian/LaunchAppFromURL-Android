package com.curiosityio.customurlscheme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        final String action = intent.getAction();

        // below is how you get the path from the intent filter. so if http://curiosityio.com/about launched this activity, then segments.get(1) should return back "about"....I think....(it might actually return ?user=101 or something. I am not sure.
        if (Intent.ACTION_VIEW.equals(action)) {
            final List<String> segments = intent.getData().getPathSegments();
            if (segments.size() > 1) {
                //mUsername = segments.get(1);
            }
        }
    }
}
