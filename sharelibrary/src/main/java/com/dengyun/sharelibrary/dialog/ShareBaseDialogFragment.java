package com.dengyun.sharelibrary.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.dengyun.sharelibrary.R;
import com.dengyun.sharelibrary.utils.ShareSizeUtils;


/**
 * @titile  自定义dialog的基类，可以自定义view继承此类，按照fragment的方式操作
 *          此类需要实现使用，适合复杂布局
 * @desc Created by seven on 2018/3/8.
 */
public abstract class ShareBaseDialogFragment extends DialogFragment {
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String BOTTOM = "show_bottom";
    private static final String TOP = "show_top";
    private static final String CANCEL = "out_cancel";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";

    private int margin;//左右边距
    private int width;//宽度
    private int height;//高度
    private float dimAmount = 0.5f;//灰度深浅
    private boolean showBottom;//是否底部显示
    private boolean showTop;//是否顶部显示
    private boolean outCancel = true;//是否点击外部取消
    @StyleRes
    private int animStyle;
    @LayoutRes
    protected int layoutId;

    public abstract int intLayoutId();

    public abstract void convertView(ShareDialogViewHolder holder, ShareBaseDialogFragment dialog);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ShareBaseDialog);
        layoutId = intLayoutId();

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM);
            showBottom = savedInstanceState.getBoolean(BOTTOM);
            showTop = savedInstanceState.getBoolean(TOP);
            outCancel = savedInstanceState.getBoolean(CANCEL);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId, container, false);
        convertView(ShareDialogViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmount);
        outState.putBoolean(BOTTOM, showBottom);
        outState.putBoolean(TOP, showTop);
        outState.putBoolean(CANCEL, outCancel);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            //调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount;
            //是否在底部显示
            if (showBottom) {
                lp.gravity = Gravity.BOTTOM;
                if (animStyle == 0) {
                    animStyle = R.style.share_window_bottom_animation;
                }
            }
            //是否在顶部显示
            if(showTop){
                lp.gravity = Gravity.TOP;
                if (animStyle == 0) {
                    animStyle = R.style.share_window_top_animation;
                }
            }
            //设置dialog宽度
            if (width == 0) {
                lp.width = ShareSizeUtils.getScreenWidth() - 2 * ShareSizeUtils.dp2px(margin);
            } else if (width == -1) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.width = ShareSizeUtils.dp2px(width);
            }

            //设置dialog高度
            if (height == 0) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.height = ShareSizeUtils.dp2px(height);
            }

            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle);
            window.setAttributes(lp);
        }
        setCancelable(outCancel);
    }

    /**
     * 设置dialog的左右边距
     * @param margin
     */
    public ShareBaseDialogFragment setMarginDp(int margin) {
        this.margin = margin;
        return this;
    }

    /**
     * 设置dialog的宽度
     * @param width
     */
    public ShareBaseDialogFragment setWidthDp(int width) {
        this.width = width;
        return this;
    }

    /**
     * 设置dialog的高度
     * @param height
     */
    public ShareBaseDialogFragment setHeightDp(int height) {
        this.height = height;
        return this;
    }

    /**
     * 调节灰色背景透明度[0-1]，默认0.5f
     * @param dimAmount
     */
    public ShareBaseDialogFragment setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    /**
     * 设置是否从下方划出
     * @param showBottom
     */
    public ShareBaseDialogFragment setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
        return this;
    }

    /**
     * 设置是否从上方划出
     * @param showTop
     */
    public ShareBaseDialogFragment setShowTop(boolean showTop) {
        this.showTop = showTop;
        return this;
    }


    /**
     * 设置点击外面不消失true/false
     * @param outCancel
     */
    public ShareBaseDialogFragment setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }


    /**
     * 设置动画
     * @param animStyle
     */
    public ShareBaseDialogFragment setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);

    }

    public ShareBaseDialogFragment show(FragmentManager manager) {
        show(manager,String.valueOf(System.currentTimeMillis()));

        return this;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}