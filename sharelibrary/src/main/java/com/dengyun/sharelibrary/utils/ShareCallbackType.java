package com.dengyun.sharelibrary.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Title 分享回调类型
 * @Author: zhoubo
 * @CreateDate: 2019-06-29 11:57
 */
@IntDef({ShareCallbackType.NONE,ShareCallbackType.DEFULT_RESULT,ShareCallbackType.BOARD_CLICK_RESULT})
@Retention(RetentionPolicy.SOURCE)
public @interface ShareCallbackType {
    int NONE = 0;
    int DEFULT_RESULT = 1;
    int BOARD_CLICK_RESULT = 2;
}
