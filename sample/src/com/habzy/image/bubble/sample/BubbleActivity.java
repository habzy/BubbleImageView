/*
 * Copyright 2014 Habzy Huang
 */
package com.habzy.image.bubble.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


public class BubbleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble);
    }
}
