package com.dengyun.sharelibrary.dialog;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.sharelibrary.R;
import com.dengyun.sharelibrary.callback.OnShareResult;
import com.dengyun.sharelibrary.config.ShareChannelConstants;
import com.dengyun.sharelibrary.utils.ShareCallbackType;
import com.dengyun.sharelibrary.utils.ShareOptions;
import com.dengyun.sharelibrary.utils.ShareUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title 自定义分享弹出框
 * @Desc: 自定义分享弹出框
 * @Author: zhoubo
 * @CreateDate: 2019-06-28 15:40
 */
public class ShareDialog extends ShareBaseDialogFragment {
    private ShareOptions shareOptions;
    private OnShareResult onShareResult;
    public static OnMoreItemClickListener onMoreItemClickListener1;
    public static OnMoreItemClickListener onMoreItemClickListener2;
    public static OnMoreItemClickListener onMoreItemClickListener3;
    public static int defultImageIcon = android.R.mipmap.sym_def_app_icon;

    public static ShareDialog newInstance(String shareChannel) {
        return new ShareDialog();
    }

    @Override
    public int intLayoutId() {
        return R.layout.sharelib_dialog_share;
    }

    @Override
    public void convertView(ShareDialogViewHolder holder, ShareBaseDialogFragment dialog) {
        if (null == shareOptions) return;

        //分享面板上方的标题
        TextView tvShare = holder.getView(R.id.tv_sharelib_share);
        if (null != tvShare){
            if (null != shareOptions.getShareBoardTitle()){
                tvShare.setText(shareOptions.getShareBoardTitle());
            }else {
                tvShare.setText("分享");
            }
        }

        TextView tvCancel = holder.getView(R.id.tv_sharelib_cancel);
        if (null!=tvCancel){
            tvCancel.setOnClickListener(v -> {
                //分享面板取消
                dismiss();
            });
        }


        /*if (shareOptions.getAddRebate() > 0) {
            //返利商品分享
            tvShare.setText(new ShareSpanUtils().append("好友下单您将得").setForegroundColor(Color.BLACK)
                    .append(shareOptions.getAddRebate() + "").setForegroundColor(Color.RED)
                    .append("元").setForegroundColor(Color.BLACK).create());
        } else tvShare.setText("分享");*/

        //设置面板
        ArrayList<String> shareChannelList = ShareChannelConstants.getShareChannelList(shareOptions.getShareChannel());

        RecyclerView rvShare = holder.getView(R.id.rv_sharelib);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        rvShare.setLayoutManager(gridLayoutManager);
        ShareDialogAdapter shareDialogAdapter = new ShareDialogAdapter(R.layout.sharelib_item_share_channel, shareChannelList);
        rvShare.setAdapter(shareDialogAdapter);

        shareDialogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String shareChannel = shareDialogAdapter.getData().get(position);
                if (ShareChannelConstants.SHARE_MORE1.equals(shareChannel) && null!=onMoreItemClickListener1){
                    onMoreItemClickListener1.onMoreItemClick(shareOptions, ShareDialog.this);
                    return;
                } else if (ShareChannelConstants.SHARE_MORE2.equals(shareChannel) && null!=onMoreItemClickListener2){
                    onMoreItemClickListener2.onMoreItemClick(shareOptions, ShareDialog.this);
                    return;
                } else if (ShareChannelConstants.SHARE_MORE3.equals(shareChannel) && null!=onMoreItemClickListener3){
                    onMoreItemClickListener3.onMoreItemClick(shareOptions, ShareDialog.this);
                    return;
                }

                //设置分享的图片（纯图片、链接卡片中的图片、纯文字中的图片）
                UMImage image = null;
                if (!TextUtils.isEmpty(shareOptions.getShareImgUrl())) {
                    image = new UMImage(shareOptions.getActivity(), shareOptions.getShareImgUrl());
                } else if (shareOptions.getShareImgRes() != 0) {
                    image = new UMImage(shareOptions.getActivity(), shareOptions.getShareImgRes());
                } else if (null != shareOptions.getShareImgBitmap()) {
                    image = new UMImage(shareOptions.getActivity(), shareOptions.getShareImgBitmap());
                } else if (null != shareOptions.getShareImgFile()) {
                    image = new UMImage(shareOptions.getActivity(), shareOptions.getShareImgFile());
                } else {
                    image = new UMImage(shareOptions.getActivity(), defultImageIcon);
                }

                UMWeb shareWeb = null;
                if (!TextUtils.isEmpty(shareOptions.getShareUrl())) {
                    //链接卡片分享
                    //分享的链接
                    shareWeb = new UMWeb(shareOptions.getShareUrl());
                    //标题
                    if (null != shareOptions.getShareTitle())
                        shareWeb.setTitle(shareOptions.getShareTitle());
                    //描述
                    if (null != shareOptions.getShareMsg())
                        shareWeb.setDescription(shareOptions.getShareMsg());
                    //卡片中的图片
                    shareWeb.setThumb(image);
                }else {
                    //纯图片分享
                    //设置缩略图（图片的缩略图，用于弹出分享之后的对话框中展示的）
                    if (!TextUtils.isEmpty(shareOptions.getShareThumbUrl())) {
                        image.setThumb(new UMImage(shareOptions.getActivity(),shareOptions.getShareThumbUrl()));
                    } else if (shareOptions.getShareThumbRes() != 0) {
                        image.setThumb(new UMImage(shareOptions.getActivity(),shareOptions.getShareThumbRes()));
                    } else if (null != shareOptions.getShareThumbBitmap()) {
                        image.setThumb(new UMImage(shareOptions.getActivity(),shareOptions.getShareThumbBitmap()));
                    } else if (null != shareOptions.getShareThumbFile()) {
                        image.setThumb(new UMImage(shareOptions.getActivity(),shareOptions.getShareThumbFile()));
                    } else {
                        image.setThumb(new UMImage(shareOptions.getActivity(),defultImageIcon));
                    }
                }

                //分享面板取消
                dismiss();

                // 友盟的渠道枚举
                SHARE_MEDIA share_media = ShareChannelConstants.getShareMediaFromChannel(shareChannel);
                //umeng 分享意图
                ShareAction shareAction = new ShareAction(shareOptions.getActivity()).setPlatform(share_media);
                //判断是分享的链接还是纯图片
                if (null != shareWeb) {
                    shareAction.withMedia(shareWeb);
                } else {
                    shareAction.withMedia(image);
                }

                //点击面板全局回调
                setGlobalOnShareSuccessByClickBoard(shareOptions, share_media);
                //点击面板回调
                setOnShareSuccessByClickBoard(shareOptions, share_media);

                //友盟的分享结果的回调
                shareAction.setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        //友盟分享成功的回调
                        setGlobalOnShareSuccessByUmeng(shareOptions, share_media);
                        setOnShareSuccessByUmeng(shareOptions, share_media);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        //友盟分享失败的回调
                        if (shareOptions.enableGlobalCallback() && null != ShareUtil.getGlobalShareCallBack()) {
                            ShareUtil.getGlobalShareCallBack().onShareError(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media), throwable);
                        }
                        if (null != onShareResult) {
                            onShareResult.onShareError(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media), throwable);
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        //友盟取消分享的回调
                        if (shareOptions.enableGlobalCallback() && null != ShareUtil.getGlobalShareCallBack()) {
                            ShareUtil.getGlobalShareCallBack().onShareCancel(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
                        }
                        if (null != onShareResult) {
                            onShareResult.onShareCancel(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
                        }
                    }
                });
                shareAction.share();

            }
        });
    }

    private void setOnShareSuccessByUmeng(ShareOptions shareOptions, SHARE_MEDIA share_media) {
        if (null != onShareResult) {
            onShareResult.onShareSuccessByUmeng(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
            if (shareOptions.getShareCallbackType() == ShareCallbackType.DEFULT_RESULT) {
                onShareResult.onShareSuccess(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
            }
        }
    }

    private void setGlobalOnShareSuccessByUmeng(ShareOptions shareOptions, SHARE_MEDIA share_media) {
        if (shareOptions.enableGlobalCallback() && null != ShareUtil.getGlobalShareCallBack()) {
            ShareUtil.getGlobalShareCallBack().onShareSuccessByUmeng(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
            if (shareOptions.getShareCallbackType() == ShareCallbackType.DEFULT_RESULT) {
                ShareUtil.getGlobalShareCallBack().onShareSuccess(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
            }
        }
    }

    private void setOnShareSuccessByClickBoard(ShareOptions shareOptions, SHARE_MEDIA share_media) {
        if (null != onShareResult) {
            onShareResult.onShareClickBoard(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
            if (shareOptions.getShareCallbackType() == ShareCallbackType.BOARD_CLICK_RESULT) {
                onShareResult.onShareSuccess(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
            }
        }
    }

    private void setGlobalOnShareSuccessByClickBoard(ShareOptions shareOptions, SHARE_MEDIA share_media) {
        if (shareOptions.enableGlobalCallback() && null != ShareUtil.getGlobalShareCallBack()) {
            ShareUtil.getGlobalShareCallBack().onShareClickBoard(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
            if (shareOptions.getShareCallbackType() == ShareCallbackType.BOARD_CLICK_RESULT) {
                ShareUtil.getGlobalShareCallBack().onShareSuccess(shareOptions, ShareChannelConstants.getChannelFromMedia(share_media));
            }
        }
    }

    public void setShareOptions(ShareOptions shareOptions) {
        this.shareOptions = shareOptions;
    }

    public void setShareCallBack(OnShareResult onShareResult) {
        this.onShareResult = onShareResult;
    }
}
