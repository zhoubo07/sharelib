package com.dengyun.sharelibrary.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.dengyun.sharelibrary.callback.OnShareResult;
import com.dengyun.sharelibrary.dialog.OnMoreItemClickListener;
import com.dengyun.sharelibrary.dialog.ShareDialog;
import com.dengyun.sharelibrary.dialog.ShareDialogAdapter;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

/**
 * @titile 分享的工具类
 * @desc Created by seven on 2018/5/7.
 */

public class ShareUtil {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    //分享结果的全局回调
    private static OnShareResult globalOnShareResult;

    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

    /**
     * 初始化分享组件
     *
     * @param sApplication    应用Application
     * @param defultImageIcon 默认图（分享链接的时候作为卡片上的图片）：用户设置的图片大小最好不要超过250k，缩略图不要超过18k
     *                        下面的参数是市场的应用id
     */
    public static void initShare(Application sApplication,
                                 int defultImageIcon,
                                 String wx_appid,
                                 String wx_appsecret,
                                 String qq_appid,
                                 String qq_appkey,
                                 String sina_appkey,
                                 String sina_appsecret,
                                 String sina_redirectUrl) {
        ShareUtil.sApplication = sApplication;
        ShareDialog.defultImageIcon = defultImageIcon;
        //微信 appid appsecret
        PlatformConfig.setWeixin(wx_appid, wx_appsecret);
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo(sina_appkey, sina_appsecret, sina_redirectUrl);
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone(qq_appid, qq_appkey);

    }

    /**
     * 设置自定义面板中更多按钮1的信息
     *
     * @param moreText                按钮的文案
     * @param moreIcon                按钮的图标
     * @param onMoreItemClickListener 按钮的点击监听
     */
    public static void setShareBoardMoreItem(String moreText, int moreIcon, OnMoreItemClickListener onMoreItemClickListener) {
        ShareDialogAdapter.moreText1 = moreText;
        ShareDialogAdapter.moreIcon1 = moreIcon;
        ShareDialog.onMoreItemClickListener1 = onMoreItemClickListener;
    }

    /**
     * 设置自定义面板中更多按钮2的信息
     *
     * @param moreText                按钮的文案
     * @param moreIcon                按钮的图标
     * @param onMoreItemClickListener 按钮的点击监听
     */
    public static void setShareBoardMoreItem2(String moreText, int moreIcon, OnMoreItemClickListener onMoreItemClickListener) {
        ShareDialogAdapter.moreText2 = moreText;
        ShareDialogAdapter.moreIcon2 = moreIcon;
        ShareDialog.onMoreItemClickListener2 = onMoreItemClickListener;
    }

    /**
     * 设置自定义面板中更多按钮3的信息
     *
     * @param moreText                按钮的文案
     * @param moreIcon                按钮的图标
     * @param onMoreItemClickListener 按钮的点击监听
     */
    public static void setShareBoardMoreItem3(String moreText, int moreIcon, OnMoreItemClickListener onMoreItemClickListener) {
        ShareDialogAdapter.moreText3 = moreText;
        ShareDialogAdapter.moreIcon3 = moreIcon;
        ShareDialog.onMoreItemClickListener3 = onMoreItemClickListener;
    }

    /**
     * @param globalOnShareResult 设置分享结果的全局回调，分享加分之类的可以写在这里面
     */
    public static void setGlobalShareCallBack(OnShareResult globalOnShareResult) {
        ShareUtil.globalOnShareResult = globalOnShareResult;
    }

    public static OnShareResult getGlobalShareCallBack() {
        return globalOnShareResult;
    }

    /**
     * 开始请求权限分享
     *
     * @param shareOptions  分享参数
     * @param onShareResult 分享回调
     */
    public static void shareWithPermission(final ShareOptions shareOptions, OnShareResult onShareResult) {

        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};
            AndPermission.with(shareOptions.getActivity())
                    .runtime()
                    .permission(mPermissionList)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            share(shareOptions, onShareResult);
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            Toast.makeText(shareOptions.getActivity(), "请授予读写权限", Toast.LENGTH_SHORT).show();
                        }
                    }).start();
        } else {
            share(shareOptions, onShareResult);
        }
    }

    //QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
    //注意onActivityResult不可在fragment中实现，如果在fragment中调用登录或分享，需要在fragment依赖的Activity中实现
    public static void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

    private static void share(final ShareOptions shareOptions, OnShareResult onShareResult) {
        ShareDialog shareDialog = ShareDialog.newInstance(shareOptions.getShareChannel());
        shareDialog.setShareOptions(shareOptions);
        shareDialog.setShareCallBack(onShareResult);
        shareDialog.setShowBottom(true)
                .show(shareOptions.getActivity().getSupportFragmentManager());


        //下面是使用umeng分享面板的分享
        //分享的url
        /*UMWeb web = new UMWeb(null == shareOptions.getShareUrl() ? "" : shareOptions.getShareUrl());
        //标题
        if (null != shareOptions.getShareTitle()) web.setTitle(shareOptions.getShareTitle());
        //描述
        if (null != shareOptions.getShareMsg()) web.setDescription(shareOptions.getShareMsg());
        //缩略图
        if (!TextUtils.isEmpty(shareOptions.getShareImgUrl())) {
            web.setThumb(new UMImage(shareOptions.getActivity(), shareOptions.getShareImgUrl()));
        } else {
            web.setThumb(new UMImage(shareOptions.getActivity(), android.R.mipmap.sym_def_app_icon));
        }

        final ShareAction shareAction = new ShareAction(shareOptions.getActivity());
        shareAction.setDisplayList(ShareChannelConstants.getShareMediaArr(shareOptions.getShareChannel()));
        shareAction.withMedia(web);
        if (null == shareCallBack) {
            shareAction.open();
            return;
        }
        //分享面板点击的监听
        shareAction.setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                shareCallBack.onShareClickBoard(shareOptions,ShareChannelConstants.getChannelFromMedia(share_media));
                shareAction.setPlatform(share_media);
                shareAction.share();
            }
        });
        //友盟的分享结果的回调
        shareAction.setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                shareCallBack.onShareSuccessCallBack(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                shareCallBack.onShareErrorCallBack(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media), throwable);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                shareCallBack.onShareCancelCallBack(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
            }
        });
        shareAction.open();*/
    }
}
