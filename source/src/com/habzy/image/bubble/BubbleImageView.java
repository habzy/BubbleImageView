/*
 * Copyright 2014 Habzy Huang
 */
package com.habzy.image.bubble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BubbleImageView extends ImageView {

    private Context mContext;

    public BubbleImageView(Context context) {
        super(context);
        mContext = context;
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    // Method of creating mask runtime
    public void makeMaskImageScaleFit(int bubble_id) {
        // Get mask bitmap
        Bitmap bubble = BitmapFactory.decodeResource(mContext.getResources(), bubble_id);

        int bubbleWidth = bubble.getWidth();
        int bubbleHeight = bubble.getHeight();
        // Scale imageView and it's bitmap to the size of the mask
        this.getLayoutParams().width = bubbleWidth;
        this.getLayoutParams().height = bubbleHeight;

        // Get bitmap from ImageView and store into 'original'
        this.setDrawingCacheEnabled(true);
        this.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        this.layout(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
//        this.buildDrawingCache(true);
        Bitmap original = ((BitmapDrawable) this.getDrawable()).getBitmap();
        // Bitmap original = BitmapFactory.decodeResource(getResources(), mContent);

        // Scale that bitmap
        Bitmap original_scaled =
                Bitmap.createScaledBitmap(original, this.getWidth(), this.getHeight(), false);
        this.setImageBitmap(original_scaled);
        this.setDrawingCacheEnabled(false);

        // Create result bitmap
        Bitmap result = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Config.ARGB_8888);

        // Perform masking
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original_scaled, 0, 0, null);


        Rect src = new Rect(0, 0, bubble.getWidth() / 2, bubble.getHeight() / 2);
        Rect dst = new Rect(0, 0, bubble.getWidth() / 2, bubble.getHeight() / 2);
        Rect src2 = new Rect(bubble.getWidth() / 2, 0, bubble.getWidth(), bubble.getHeight() / 2);
        Rect dst2 =
                new Rect(this.getWidth() - bubble.getWidth(), 0, this.getWidth(),
                        bubble.getHeight() / 2);
        Rect src3 = new Rect(0, bubble.getHeight() / 2, bubble.getWidth() / 2, bubble.getHeight());
        Rect dst3 =
                new Rect(0, this.getHeight() - bubble.getHeight() / 2, bubble.getWidth() / 2,
                        this.getHeight());
        Rect src4 =
                new Rect(bubble.getWidth() / 2, bubble.getHeight() / 2, bubble.getWidth(),
                        bubble.getHeight());
        Rect dst4 =
                new Rect(this.getWidth() - bubble.getWidth() / 2, this.getHeight()
                        - bubble.getHeight() / 2, this.getWidth(), this.getHeight());
        mCanvas.drawBitmap(bubble, src, dst, paint);
        mCanvas.drawBitmap(bubble, src2, dst2, paint);
        mCanvas.drawBitmap(bubble, src3, dst3, paint);
        mCanvas.drawBitmap(bubble, src4, dst4, paint);
        // mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);

        // Set imageView to 'result' bitmap
        this.setImageBitmap(result);
        this.setScaleType(ScaleType.FIT_XY);

        // Make background transparent
        this.setBackgroundResource(android.R.color.transparent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        makeMaskImageScaleFit(R.drawable.the_bubble);
        super.onDraw(canvas);
    }

}
