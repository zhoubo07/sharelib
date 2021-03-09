package com.dengyun.sharelibrary.utils;

import android.graphics.drawable.Drawable;
import android.widget.TextView;


/**
 * @titile  图片 相关
 * @desc Created by seven on 2018/2/24.
 */
public final class ShareImageUtils {

    private ShareImageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void drawableLeft(TextView view, int left_pic_res,int drawablePaddingPx){
        if(left_pic_res!=0){
            Drawable drawable = ShareUtil.getApp().getResources().getDrawable(left_pic_res);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            view.setCompoundDrawablePadding(drawablePaddingPx);
            view.setCompoundDrawables(drawable, null, null, null);
        }
    }

    public static void drawableTop(TextView view, int top_pic_res,int drawablePaddingPx){
        if(top_pic_res!=0){
            Drawable drawable = ShareUtil.getApp().getResources().getDrawable(top_pic_res);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            view.setCompoundDrawablePadding(drawablePaddingPx);
            view.setCompoundDrawables(null, drawable, null, null);
        }
    }

    public static void drawableRight(TextView view, int right_pic_res,int drawablePaddingPx){
        if(right_pic_res!=0){
            Drawable drawable = ShareUtil.getApp().getResources().getDrawable(right_pic_res);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            view.setCompoundDrawablePadding(drawablePaddingPx);
            view.setCompoundDrawables(null, null, drawable, null);
        }
    }

    public static void drawableBottom(TextView view, int bottom_pic_res,int drawablePaddingPx){
        if(bottom_pic_res!=0){
            Drawable drawable = ShareUtil.getApp().getResources().getDrawable(bottom_pic_res);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            view.setCompoundDrawablePadding(drawablePaddingPx);
            view.setCompoundDrawables(null, null, null, drawable);
        }
    }
}
