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
    private BubbleParams mbubbleParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble);

        RelativeLayout mRelativeLayout5 = (RelativeLayout) findViewById(R.id.image_layout_5);
        initLeftViews(mRelativeLayout5, R.drawable.source_pick_5);
        initLeftViews((RelativeLayout) findViewById(R.id.image_layout_2), R.drawable.source_pick_2);

        mbubbleParams = new BubbleParams();
        initRightParam(mbubbleParams);

        initRightViews((RelativeLayout) findViewById(R.id.image_layout_1), R.drawable.source_pick_1);
        initRightViews((RelativeLayout) findViewById(R.id.image_layout_4), R.drawable.source_pick_4);
        initRightViews((RelativeLayout) findViewById(R.id.image_layout_6), R.drawable.source_pick_3);
    }

    private void initRightViews(RelativeLayout layout, int sourcePick4) {
        BubbleImageView bubbleImage = new BubbleImageView(this);

        LayoutParams params =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        bubbleImage.setParam(mbubbleParams);
        bubbleImage.setImageResource(sourcePick4);

        layout.addView(bubbleImage, params);
    }

    private void initLeftViews(RelativeLayout layout, int sourcePick5) {
        BubbleImageView bubbleImage = new BubbleImageView(this);
        bubbleImage.setImageResource(sourcePick5);

        LayoutParams params =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layout.addView(bubbleImage, params);
    }

    private void initRightParam(BubbleParams bubbleParams) {
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
    }
}
