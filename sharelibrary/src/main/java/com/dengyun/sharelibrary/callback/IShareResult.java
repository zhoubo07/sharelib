package com.dengyun.sharelibrary.callback;

import com.dengyun.sharelibrary.utils.ShareOptions;

/**
 * @Title 分享的回调接口类
 * @Author: zhoubo
 * @CreateDate: 2020-03-12 15:40
 */
public interface IShareResult {
    /**
     * 分享成功回调wap，（已经根据 "成功类型" 判断是点击面板还是友盟回调成功）
     * @param shareOptions
     * @param shareChannel
     */
    void onShareSuccess(ShareOptions shareOptions,String shareChannel);

    /**
     * 友盟分享成功的回调
     *
     * @param shareOptions 分享的参数菜单列表
     * @param shareChannel 分享/点击 的渠道
     */
    void onShareSuccessByUmeng(ShareOptions shareOptions, String shareChannel);

    /**
     * 分享面板点击的监听
     * @param shareOptions
     * @param shareChannel
     */
    void onShareClickBoard(ShareOptions shareOptions, String shareChannel);

    /**
     * 分享取消的回调（取自友盟回调）
     *
     * @param shareOptions 分享的参数菜单列表
     * @param shareChannel 分享的渠道
     */
    void onShareCancel(ShareOptions shareOptions, String shareChannel);

    /**
     * 分享失败的回调（取自友盟回调）
     *
     * @param shareOptions 分享的参数菜单列表
     * @param shareChannel 分享的渠道
     */
    void onShareError(ShareOptions shareOptions, String shareChannel, Throwable throwable);
}
