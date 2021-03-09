package com.dengyun.sharelibrary.callback;

import com.dengyun.sharelibrary.utils.ShareOptions;

/**
 * @Title 分享的回调
 * @Author: zhoubo
 * @CreateDate: 2020-03-12 15:41
 */
public abstract class OnShareResult implements IShareResult {
    /**
     * 友盟分享成功的回调
     *
     * @param shareOptions 分享的参数菜单列表
     * @param shareChannel 分享/点击 的渠道
     */
    @Override
    public void onShareSuccessByUmeng(ShareOptions shareOptions, String shareChannel) {

    }

    /**
     * 分享面板点击的监听
     * @param shareOptions
     * @param shareChannel
     */
    @Override
    public void onShareClickBoard(ShareOptions shareOptions, String shareChannel){};

    /**
     * 分享取消的回调（取自友盟回调）
     *
     * @param shareOptions 分享的参数菜单列表
     * @param shareChannel 分享的渠道
     */
    @Override
    public void onShareCancel(ShareOptions shareOptions, String shareChannel) {

    }

    /**
     * 分享失败的回调（取自友盟回调）
     *
     * @param shareOptions 分享的参数菜单列表
     * @param shareChannel 分享的渠道
     */
    @Override
    public void onShareError(ShareOptions shareOptions, String shareChannel, Throwable throwable) {

    }
}
