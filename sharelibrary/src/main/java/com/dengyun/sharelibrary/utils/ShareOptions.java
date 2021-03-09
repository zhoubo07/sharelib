package com.dengyun.sharelibrary.utils;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;

import java.io.File;

/**
 * @titile
 * @desc Created by seven on 2018/5/7.
 */

public class ShareOptions {
    private final FragmentActivity activity;
    private final String shareTitle;
    private final String shareMsg;
    private final String shareUrl;
    private final String shareImgUrl;         //分享的图片url（如果是单纯的图片分享，作为图片，如果是链接分享，作为卡片上的图片）默认为logo
    private final int shareImgRes;            //分享的图片资源id（如果是单纯的图片分享，作为图片，如果是链接分享，作为卡片上的图片）默认为logo
    private final Bitmap shareImgBitmap;      //分享的图片bitmap（如果是单纯的图片分享，作为图片，如果是链接分享，作为卡片上的图片）默认为logo
    private final File shareImgFile;          //分享的本地图片File（如果是单纯的图片分享，作为图片，如果是链接分享，作为卡片上的图片）默认为logo
    private final String shareThumbUrl;     //分享的缩略图，用于弹出分享之后的对话框中展示的（图片分享必须设置）默认为logo
    private final int shareThumbRes;        //分享的缩略图，用于弹出分享之后的对话框中展示的（图片分享必须设置）默认为logo
    private final Bitmap shareThumbBitmap;  //分享的缩略图，用于弹出分享之后的对话框中展示的（图片分享必须设置）默认为logo
    private final File shareThumbFile;      //分享的缩略图，用于弹出分享之后的对话框中展示的（图片分享必须设置）默认为logo

    private final String shareChannel;  //分享的渠道：默认全渠道，0：新浪，1：微信，2：朋友圈，4：qq，13：短信，100：更多按钮1，101：更多按钮2，102：更多按钮3
    private final @ShareCallbackType int shareCallbackType;    //wap是否要回调：0：不要回调，1：点击面板成功回调，2：友盟成功回调；
    private final boolean enableGlobalCallback;//是否允许全局的callback处理，默认为true
    private final CharSequence shareBoardTitle;//分享面板的标题文案（可以是富文本，使用SpannableString或者SpannableStringBuilder）
    private final String extra1;  // 附加信息1，可作为更多1里的携带的附加数据，也可以作为自定义附加的数据
    private final String extra2;  // 附加信息2，可作为更多2里的携带的附加数据，也可以作为自定义附加的数据
    private final String extra3;  // 附加信息3，可作为更多3里的携带的附加数据，也可以作为自定义附加的数据

    private ShareOptions(Builder builder) {
        activity = builder.activity;
        shareTitle = builder.shareTitle;
        shareMsg = builder.shareMsg;
        shareUrl = builder.shareUrl;
        shareImgUrl = builder.shareImgUrl;
        shareImgRes = builder.shareImgRes;
        shareImgBitmap = builder.shareImgBitmap;
        shareImgFile = builder.shareImgFile;

        shareThumbUrl = builder.shareThumbUrl;
        shareThumbRes = builder.shareThumbRes;
        shareThumbBitmap = builder.shareThumbBitmap;
        shareThumbFile = builder.shareThumbFile;

        shareChannel = builder.shareChannel;
        shareCallbackType = builder.shareCallbackType;
        enableGlobalCallback = builder.enableGlobalCallback;
        shareBoardTitle = builder.shareBoardTitle;

        extra1 = builder.extra1;
        extra2 = builder.extra2;
        extra3 = builder.extra3;
    }

    public static Builder newBuilder(FragmentActivity activity) {
        return new Builder(activity);
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public String getShareMsg() {
        return shareMsg;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public String getShareImgUrl() {
        return shareImgUrl;
    }

    public int getShareImgRes() {
        return shareImgRes;
    }

    public Bitmap getShareImgBitmap() {
        return shareImgBitmap;
    }

    public File getShareImgFile() {
        return shareImgFile;
    }

    public String getShareThumbUrl() {
        return shareThumbUrl;
    }

    public int getShareThumbRes() {
        return shareThumbRes;
    }

    public Bitmap getShareThumbBitmap() {
        return shareThumbBitmap;
    }

    public File getShareThumbFile() {
        return shareThumbFile;
    }

    public String getShareChannel() {
        return shareChannel;
    }

    public boolean enableGlobalCallback(){
        return enableGlobalCallback;
    }

    public CharSequence getShareBoardTitle(){
        return shareBoardTitle;
    }

    public @ShareCallbackType int getShareCallbackType() {
        return shareCallbackType;
    }

    public String getExtra1() {
        return extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public String getExtra3() {
        return extra3;
    }

    public static final class Builder {
        private FragmentActivity activity;
        private String shareTitle;
        private String shareMsg;
        private String shareUrl;
        private String shareImgUrl;         //分享的图片链接（如果是单纯的图片分享，作为图片，如果是链接分享，作为卡片上的图片）默认为logo
        private int shareImgRes;            //分享的图片资源id（如果是单纯的图片分享，作为图片，如果是链接分享，作为卡片上的图片）默认为logo
        private Bitmap shareImgBitmap;      //分享的图片bitmap（如果是单纯的图片分享，作为图片，如果是链接分享，作为卡片上的图片）默认为logo
        private File shareImgFile;          //分享的本地图片File（如果是单纯的图片分享，作为图片，如果是链接分享，作为卡片上的图片）默认为logo
        private String shareThumbUrl;     //分享的缩略图，用于弹出分享之后的对话框中展示的（图片分享必须设置）默认为logo
        private int shareThumbRes;        //分享的缩略图，用于弹出分享之后的对话框中展示的（图片分享必须设置）默认为logo
        private Bitmap shareThumbBitmap;  //分享的缩略图，用于弹出分享之后的对话框中展示的（图片分享必须设置）默认为logo
        private File shareThumbFile;      //分享的缩略图，用于弹出分享之后的对话框中展示的（图片分享必须设置）默认为logo

        private String shareChannel;      //分享的渠道：默认全渠道，0：新浪，1：微信，2：朋友圈，4：qq，13：短信，100：更多按钮
        private @ShareCallbackType int shareCallbackType = ShareCallbackType.DEFULT_RESULT;//默认值是友盟的成功回调
        private boolean enableGlobalCallback = true;//是否允许全局的callback处理，默认为true
        private CharSequence shareBoardTitle;//分享面板的标题文案（可以是富文本，使用SpannableString或者SpannableStringBuilder）
        private String extra1;  // 附加信息1，可作为更多1里的携带的附加数据，也可以作为自定义附加的数据
        private String extra2;  // 附加信息2，可作为更多2里的携带的附加数据，也可以作为自定义附加的数据
        private String extra3;  // 附加信息3，可作为更多3里的携带的附加数据，也可以作为自定义附加的数据

        private Builder(FragmentActivity activity) {
            this.activity = activity;
        }

        public Builder activity(FragmentActivity val) {
            activity = val;
            return this;
        }

        public Builder shareTitle(String val) {
            shareTitle = val;
            return this;
        }

        public Builder shareMsg(String val) {
            shareMsg = val;
            return this;
        }

        public Builder shareUrl(String val) {
            shareUrl = val;
            return this;
        }

        public Builder shareImgUrl(String val) {
            shareImgUrl = val;
            return this;
        }

        public Builder shareImgRes(int val) {
            shareImgRes = val;
            return this;
        }

        public Builder shareImgBitmap(Bitmap val) {
            shareImgBitmap = val;
            return this;
        }

        public Builder shareImgFile(File val) {
            shareImgFile = val;
            return this;
        }

        public Builder shareThumbUrl(String val) {
            shareThumbUrl = val;
            return this;
        }

        public Builder shareThumbRes(int val) {
            shareThumbRes = val;
            return this;
        }

        public Builder shareThumbBitmap(Bitmap val) {
            shareThumbBitmap = val;
            return this;
        }

        public Builder shareThumbFile(File val) {
            shareThumbFile = val;
            return this;
        }

        public Builder shareChannel(String val) {
            shareChannel = val;
            return this;
        }

        public Builder shareCallbackType(@ShareCallbackType int val) {
            shareCallbackType = val;
            return this;
        }

        public Builder enableGlobalCallback(boolean enableGlobalCallback) {
            this.enableGlobalCallback = enableGlobalCallback;
            return this;
        }

        public Builder shareBoardTitle(CharSequence shareBoardTitle) {
            this.shareBoardTitle = shareBoardTitle;
            return this;
        }

        public Builder extra1(String extra1) {
            this.extra1 = extra1;
            return this;
        }

        public Builder extra2(String extra2) {
            this.extra2 = extra2;
            return this;
        }

        public Builder extra3(String extra3) {
            this.extra3 = extra3;
            return this;
        }

        public ShareOptions build() {
            return new ShareOptions(this);
        }
    }
}
