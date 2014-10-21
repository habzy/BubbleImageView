/*
 * Copyright 2014 Habzy Huang
 */
package com.habzy.image.bubble.sample;

import com.habzy.image.bubble.BubbleImageView;
import com.habzy.image.bubble.BubbleParams;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;


public class BubbleActivity extends Activity {
    RelativeLayout mRelativeLayout4;
    RelativeLayout mRelativeLayout5;
    RelativeLayout mRelativeLayout6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble);

        mRelativeLayout5 = (RelativeLayout) findViewById(R.id.image_layout_5);
        initLeftViews();

        BubbleParams bubbleParams4 = new BubbleParams();
        LayoutParams params4 =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        initRightViews(bubbleParams4, params4);

        mRelativeLayout4 = (RelativeLayout) findViewById(R.id.image_layout_4);
        BubbleImageView bubbleImage4 = new BubbleImageView(this);
        bubbleImage4.setParam(bubbleParams4);
        bubbleImage4.setImageResource(R.drawable.source_pick_4);
        mRelativeLayout4.addView(bubbleImage4, params4);

        LayoutParams params6 =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        initRightViews(bubbleParams4, params6);
        mRelativeLayout6 = (RelativeLayout) findViewById(R.id.image_layout_6);
        BubbleImageView bubbleImage6 = new BubbleImageView(this);
        bubbleImage6.setParam(bubbleParams4);
        bubbleImage6.setImageResource(R.drawable.source_pick_3);
        mRelativeLayout6.addView(bubbleImage6, params6);


    }

    private void initLeftViews() {
        // BubbleParams bubbleParams = new BubbleParams();
        // bubbleParams.bubble_top_right = R.drawable.left_bubble_top_right;

        BubbleImageView bubbleImage = new BubbleImageView(this);
        // bubbleImage.setParam(bubbleParams);

        bubbleImage.setImageResource(R.drawable.source_pick_5);

        LayoutParams params =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mRelativeLayout5.addView(bubbleImage, params);


    }

    private void initRightViews(BubbleParams bubbleParams, LayoutParams params) {
        bubbleParams.bubble_top_right = R.drawable.right_bubble_top_right;
        bubbleParams.bubble_top_left = R.drawable.right_bubble_top_left;
        bubbleParams.bubble_bottom_left = R.drawable.right_bubble_bottom_left;
        bubbleParams.bubble_bottom_right = R.drawable.right_bubble_bottom_right;
        bubbleParams.bubble_left = R.drawable.right_bubble_left;
        bubbleParams.bubble_right = R.drawable.right_bubble_right;
        bubbleParams.bubble_top = R.drawable.right_bubble_topbottom;
        bubbleParams.bubble_bottom = R.drawable.right_bubble_topbottom;

        bubbleParams.border_top_left = R.drawable.right_border_top_left;
        bubbleParams.border_top_right = R.drawable.right_border_top_right;
        bubbleParams.border_bottom_left = R.drawable.right_border_bottom_left;
        bubbleParams.border_bottom_right = R.drawable.right_border_bottom_right;
        bubbleParams.border_left = R.drawable.right_border_left;
        bubbleParams.border_right = R.drawable.right_border_left;
        bubbleParams.border_top = R.drawable.right_border_topbottom;
        bubbleParams.border_bottom = R.drawable.right_border_topbottom;

        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
    }
}
