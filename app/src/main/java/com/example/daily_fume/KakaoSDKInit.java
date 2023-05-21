package com.example.daily_fume;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoSDKInit extends Application {

    /**
     * GlobalApplication.java
     */

    private static KakaoSDKInit instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 네이티브 앱 키로 초기화
        KakaoSdk.init(this, "2d66d7f18521bf353781f2ea7af034b6");
    }
}
