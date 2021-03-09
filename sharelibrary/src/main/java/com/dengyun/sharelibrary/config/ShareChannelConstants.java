package com.dengyun.sharelibrary.config;

import android.text.TextUtils;

import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;

/**
 * @titile 分享的渠道常量类
 * @desc Created by seven on 2018/5/7.
 */

public class ShareChannelConstants {

    public static final String SHARE_QQ = "4";
    public static final String SHARE_WEIXIN = "1";
    public static final String SHARE_WEIXIN_CIRCLE = "2";
    public static final String SHARE_SINA = "0";
    public static final String SHARE_SMS = "13";
    public static final String SHARE_MORE1 = "100";
    public static final String SHARE_MORE2 = "101";
    public static final String SHARE_MORE3 = "102";

    /**
     * 将友盟的枚举渠道转换为我们自定义的渠道信息：SHARE_QQ转换为4；
     *
     * @param share_media 友盟的渠道
     * @return
     */
    public static String getChannelFromMedia(SHARE_MEDIA share_media) {
        String shareChannel = SHARE_WEIXIN;
        if (share_media.toString().equals("QQ")) {
            shareChannel = SHARE_QQ;
        } else if (share_media.toString().equals("WEIXIN")) {
            shareChannel = SHARE_WEIXIN;
        } else if (share_media.toString().equals("WEIXIN_CIRCLE")) {
            shareChannel = SHARE_WEIXIN_CIRCLE;
        } else if (share_media.toString().equals("SINA")) {
            shareChannel = SHARE_SINA;
        } else if (share_media.toString().equals("SMS")) {
            shareChannel = SHARE_SMS;
        } else if (share_media.toString().equals("MORE")) {
            shareChannel = SHARE_MORE1;
        }
        return shareChannel;
    }

    /**
     * 将渠道拼接的字段拆开成集合
     */
    public static ArrayList<String> getShareChannelList(String shareChannelStr) {
        ArrayList<String> shareChannelList = new ArrayList<>();
        if (TextUtils.isEmpty(shareChannelStr)) {
            //默认分享全渠道
            shareChannelList.add(SHARE_WEIXIN);
            shareChannelList.add(SHARE_WEIXIN_CIRCLE);
            shareChannelList.add(SHARE_QQ);
            shareChannelList.add(SHARE_SINA);
            shareChannelList.add(SHARE_SMS);
            return shareChannelList;
        }

        String[] shareChannels = shareChannelStr.split("_");
        for (String strChannel : shareChannels) {
            shareChannelList.add(strChannel);
        }
        return shareChannelList;
    }

    /**
     * 根据自定义的渠道信息获取友盟的渠道枚举
     * @param channel 0、1、2、4、13
     * @return
     */
    public static SHARE_MEDIA getShareMediaFromChannel(String channel){
        if (SHARE_WEIXIN.equals(channel)){
            return SHARE_MEDIA.WEIXIN;
        } else if (SHARE_WEIXIN_CIRCLE.equals(channel)){
            return SHARE_MEDIA.WEIXIN_CIRCLE;
        }else if (SHARE_QQ.equals(channel)){
            return SHARE_MEDIA.QQ;
        }else if (SHARE_SINA.equals(channel)){
            return SHARE_MEDIA.SINA;
        }else if (SHARE_SMS.equals(channel)){
            return SHARE_MEDIA.SMS;
        }
        return SHARE_MEDIA.MORE;
    }

}
