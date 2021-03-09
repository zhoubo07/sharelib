package com.dengyun.sharelibrary.dialog;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengyun.sharelibrary.R;
import com.dengyun.sharelibrary.utils.ShareImageUtils;

import java.util.List;

import static com.dengyun.sharelibrary.config.ShareChannelConstants.SHARE_MORE1;
import static com.dengyun.sharelibrary.config.ShareChannelConstants.SHARE_MORE2;
import static com.dengyun.sharelibrary.config.ShareChannelConstants.SHARE_MORE3;
import static com.dengyun.sharelibrary.config.ShareChannelConstants.SHARE_QQ;
import static com.dengyun.sharelibrary.config.ShareChannelConstants.SHARE_SINA;
import static com.dengyun.sharelibrary.config.ShareChannelConstants.SHARE_SMS;
import static com.dengyun.sharelibrary.config.ShareChannelConstants.SHARE_WEIXIN;
import static com.dengyun.sharelibrary.config.ShareChannelConstants.SHARE_WEIXIN_CIRCLE;

/**
 * @Title 分享弹框中的渠道列表适配器
 * @Author: zhoubo
 * @CreateDate: 2019-06-28 16:21
 */
public class ShareDialogAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public static String moreText1 = "站内分享";
    public static String moreText2 = "站内分享";
    public static String moreText3 = "站内分享";
    public static int moreIcon1 = R.drawable.sharelib_custom_share_more;
    public static int moreIcon2 = R.drawable.sharelib_custom_share_more;
    public static int moreIcon3 = R.drawable.sharelib_custom_share_more;

    public ShareDialogAdapter(int layoutResId, @Nullable List<String> shareChannelList) {
        super(layoutResId, shareChannelList);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tvShare = helper.getView(R.id.tv_share_channel);
        switch (item) {
            case SHARE_QQ:
                tvShare.setText("QQ");
                ShareImageUtils.drawableTop(tvShare,R.drawable.sharelib_custom_share_qq,11);
                break;
            case SHARE_WEIXIN:
                tvShare.setText("微信");
                ShareImageUtils.drawableTop(tvShare,R.drawable.sharelib_custom_share_wechat,11);
                break;
            case SHARE_WEIXIN_CIRCLE:
                tvShare.setText("朋友圈");
                ShareImageUtils.drawableTop(tvShare,R.drawable.sharelib_custom_share_wxcircle,11);
                break;
            case SHARE_SINA:
                tvShare.setText("微博");
                ShareImageUtils.drawableTop(tvShare,R.drawable.sharelib_custom_share_sina,11);
                break;
            case SHARE_SMS:
                tvShare.setText("短信");
                ShareImageUtils.drawableTop(tvShare,R.drawable.sharelib_custom_share_sms,11);
                break;
            case SHARE_MORE1:
                tvShare.setText(moreText1);
                ShareImageUtils.drawableTop(tvShare, moreIcon1,11);
                break;
            case SHARE_MORE2:
                tvShare.setText(moreText2);
                ShareImageUtils.drawableTop(tvShare, moreIcon2,11);
                break;
            case SHARE_MORE3:
                tvShare.setText(moreText3);
                ShareImageUtils.drawableTop(tvShare, moreIcon3,11);
                break;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvShare.setVisibility(View.VISIBLE);
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(tvShare, "translationY", 300, 0);
                fadeAnim.setDuration(300);
                ShareItemEvaluator shareItemEvaluator = new ShareItemEvaluator();
                shareItemEvaluator.setDuration(150);
                fadeAnim.setEvaluator(shareItemEvaluator);
                fadeAnim.start();
            }
        }, helper.getAdapterPosition() * 100);
    }

    public static class ShareItemEvaluator implements TypeEvaluator<Float> {
        private final float s = 1.70158f;
        float mDuration = 0f;

        public void setDuration(float duration) {
            mDuration = duration;
        }

        public Float evaluate(float fraction, Float startValue, Float endValue) {
            float t = mDuration * fraction;
            float b = startValue.floatValue();
            float c = endValue.floatValue() - startValue.floatValue();
            float d = mDuration;
            float result = calculate(t, b, c, d);
            return result;
        }

        public Float calculate(float t, float b, float c, float d) {
            return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
        }
    }
}
