/*
 * Copyright 2014 Habzy Huang
 */
package com.habzy.image.bubble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BubbleImageView extends ImageView {

    private Context mContext;
    private BubbleParams mParam;

    public BubbleImageView(Context context) {
        super(context);
        mContext = context;
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        Bitmap original = ((BitmapDrawable) this.getDrawable()).getBitmap();
        Bitmap result = makeMaskImageScaleFit(original);
        // Set imageView to 'result' bitmap
        Drawable drawable = new BitmapDrawable(mContext.getResources(), result);
        super.setImageDrawable(drawable);
    }

    // Method of creating mask runtime
    public Bitmap makeMaskImageScaleFit(Bitmap original) {

        // Get bitmap from ImageView and store into 'original'
        int image_width = original.getWidth();
        int image_height = original.getHeight();
        int maxPix = null != mParam ? mParam.maxPix : BubbleParams.MAX_PIX;
        int minPix = null != mParam ? mParam.minPix : BubbleParams.MIN_PIX;
        if (image_width > image_height) {
            if (image_width > maxPix) {
                int temp = image_width;
                image_width = maxPix;
                image_height = image_height * image_width / temp;
            } else if (image_width < minPix) {
                int temp = image_width;
                image_width = minPix;
                image_height = image_height * image_width / temp;
            }
        } else {
            if (image_height > maxPix) {
                int temp = image_height;
                image_height = maxPix;
                image_width = image_width * image_height / temp;
            } else if (image_height < minPix) {
                int temp = image_height;
                image_height = minPix;
                image_width = image_width * image_height / temp;
            }
        }

        // Scale that bitmap
        Bitmap original_scaled =
                Bitmap.createScaledBitmap(original, image_width, image_height, false);
        Drawable drawable = new BitmapDrawable(mContext.getResources(), original_scaled);
        super.setImageDrawable(drawable);

        // Create result bitmap
        Bitmap result = Bitmap.createBitmap(image_width, image_height, Config.ARGB_8888);

        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Perform the bubble
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original_scaled, 0, 0, null);

        Bitmap bubble_TL =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.bubble_top_left : R.drawable.bubble_top_left);
        Rect src_TL = new Rect(0, 0, bubble_TL.getWidth(), bubble_TL.getHeight());
        Rect dst_TL = new Rect(0, 0, bubble_TL.getWidth(), bubble_TL.getHeight());
        mCanvas.drawBitmap(bubble_TL, src_TL, dst_TL, paint);

        Bitmap bubble_BL =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.bubble_bottom_left : R.drawable.bubble_bottom_left);
        Rect src_BL = new Rect(0, 0, bubble_BL.getWidth(), bubble_BL.getHeight());
        Rect dst_BL =
                new Rect(0, image_height - bubble_BL.getHeight(), bubble_BL.getWidth(),
                        image_height);
        mCanvas.drawBitmap(bubble_BL, src_BL, dst_BL, paint);

        Bitmap bubble_L =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.bubble_left : R.drawable.bubble_left);
        Rect src_left = new Rect(0, 0, bubble_L.getWidth(), bubble_L.getHeight());
        Rect dst_left =
                new Rect(0, bubble_TL.getHeight(), bubble_L.getWidth(), image_height
                        - bubble_BL.getHeight());
        mCanvas.drawBitmap(bubble_L, src_left, dst_left, paint);

        Bitmap bubble_TR =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.bubble_top_right : R.drawable.bubble_top_right);
        Rect src_TR = new Rect(0, 0, bubble_TR.getWidth(), bubble_TR.getHeight());
        Rect dst_TR =
                new Rect(image_width - bubble_TR.getWidth(), 0, image_width, bubble_TR.getHeight());
        mCanvas.drawBitmap(bubble_TR, src_TR, dst_TR, paint);

        Bitmap bubble_BR =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.bubble_bottom_right
                                : R.drawable.bubble_bottom_right);
        Rect src_BR = new Rect(0, 0, bubble_BR.getWidth(), bubble_BR.getHeight());
        Rect dst_BR =
                new Rect(image_width - bubble_BR.getWidth(), image_height - bubble_BR.getHeight(),
                        image_width, image_height);
        mCanvas.drawBitmap(bubble_BR, src_BR, dst_BR, paint);

        Bitmap bubble_R =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.bubble_right : R.drawable.bubble_right);
        Rect src_right = new Rect(0, 0, bubble_R.getWidth(), bubble_R.getHeight());
        Rect dst_right =
                new Rect(image_width - bubble_R.getWidth(), bubble_TR.getHeight(), image_width,
                        image_height - bubble_BR.getHeight());
        mCanvas.drawBitmap(bubble_R, src_right, dst_right, paint);

        // Draw border
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        Bitmap border_TL =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.border_top_left : R.drawable.border_top_left);
        Rect border_src = new Rect(0, 0, border_TL.getWidth(), border_TL.getHeight());
        Rect image_dst = new Rect(0, 0, border_TL.getWidth(), border_TL.getHeight());
        mCanvas.drawBitmap(border_TL, border_src, image_dst, paint);

        Bitmap border_BL =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.border_bottom_left : R.drawable.border_bottom_left);
        Rect border_src_BL = new Rect(0, 0, border_BL.getWidth(), border_BL.getHeight());
        Rect border_dst_BL =
                new Rect(0, image_height - border_BL.getHeight(), border_BL.getWidth(),
                        image_height);
        mCanvas.drawBitmap(border_BL, border_src_BL, border_dst_BL, paint);

        Bitmap border_Line_L =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.border_left : R.drawable.border_left);
        Rect border_src_left = new Rect(0, 0, border_Line_L.getWidth(), border_Line_L.getHeight());
        Rect border_dst_left =
                new Rect(0, border_TL.getHeight(), border_Line_L.getWidth(), image_height
                        - border_BL.getHeight());
        mCanvas.drawBitmap(border_Line_L, border_src_left, border_dst_left, paint);

        Bitmap border_TR =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.border_top_right : R.drawable.border_top_right);
        Rect border_src_TR = new Rect(0, 0, bubble_TR.getWidth(), bubble_TR.getHeight());
        Rect border_dst_TR =
                new Rect(image_width - border_TR.getWidth(), 0, image_width, border_TR.getHeight());
        mCanvas.drawBitmap(border_TR, border_src_TR, border_dst_TR, paint);

        Bitmap border_BR =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.border_bottom_right
                                : R.drawable.border_bottom_right);
        Rect border_src_BR = new Rect(0, 0, bubble_BR.getWidth(), bubble_BR.getHeight());
        Rect border_dst_BR =
                new Rect(image_width - border_BR.getWidth(), image_height - border_BR.getHeight(),
                        image_width, image_height);
        mCanvas.drawBitmap(border_BR, border_src_BR, border_dst_BR, paint);

        Bitmap border_Line_R =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.border_right : R.drawable.border_right);
        Rect border_src_Right = new Rect(0, 0, border_Line_R.getWidth(), border_Line_R.getHeight());
        Rect border_dst_Rignt =
                new Rect(image_width - border_Line_R.getWidth(), border_TR.getHeight(),
                        image_width, image_height - border_BR.getHeight());
        mCanvas.drawBitmap(border_Line_R, border_src_Right, border_dst_Rignt, paint);

        Bitmap border_Line_TB =
                BitmapFactory.decodeResource(mContext.getResources(),
                        null != mParam ? mParam.border_top : R.drawable.border_topbottom);
        Rect border_src_tb = new Rect(0, 0, border_Line_TB.getWidth(), border_Line_TB.getHeight());
        Rect border_dst_top =
                new Rect(border_TL.getWidth(), 0, image_width - border_TR.getWidth(),
                        border_Line_TB.getHeight());
        mCanvas.drawBitmap(border_Line_TB, border_src_tb, border_dst_top, paint);

        Rect border_dst_bottom =
                new Rect(border_BL.getWidth(), image_height - border_Line_TB.getHeight(),
                        image_width - border_BR.getWidth(), image_height);
        mCanvas.drawBitmap(border_Line_TB, border_src_tb, border_dst_bottom, paint);

        paint.setXfermode(null);

        // Make background transparent
        // this.setBackgroundResource(android.R.color.transparent);
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        Bitmap result = makeMaskImageScaleFit(bm);
        super.setImageBitmap(result);
    }

    @Override
    public void setImageResource(int resId) {
        Drawable drawable = mContext.getResources().getDrawable(resId);
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            bitmap =
                    Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
        } else {
            return;
        }
        Bitmap result = makeMaskImageScaleFit(bitmap);
        setImageBitmap(result);
    }

    public void setParam(BubbleParams param) {
        this.mParam = param;
    }

}
