//package com.example.daily_fume;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.media.MediaSession2;
//import android.os.Bundle;
//import android.util.Log;
//import com.kakao.auth.ApiErrorCode;
//import com.kakao.auth.ISessionCallback;
//import com.kakao.auth.Session;
//import com.kakao.network.ErrorResult;
//import com.kakao.usermgmt.UserManagement;
//import com.kakao.usermgmt.callback.MeV2ResponseCallback;
//import com.kakao.usermgmt.response.MeV2Response;
//import com.kakao.util.exception.KakaoException;
//import com.kakao.util.helper.log.Logger;
//
//public class KakaoCallBack extends Activity {
//
//    /**
//     * LoginActivity.java
//     */
//
//    private SessionCallback callback;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_login);
//
//        callback = new SessionCallback();       // 이 두개의 함수 중요함
//        Session.getCurrentSession().addCallback(callback);
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
//        return;
//    }
//
//    super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Session.getCurrentSession().removeCallback(callback);
//    }
//
//    private class SessionCallback implements ISessionCallback {
//        @Override
//        public void onSessionOpened() {
//            redirectSignupActivity();  // 세션 연결성공 시 redirectSignupActivity() 호출
//            }
//
//        @Override
//        public void onSessionOpenFailed(KakaoException exception) {
//            if(exception != null) {
//                Logger.e(exception);
//            }
//            setContentView(R.layout.activity_login); // 세션 연결이 실패했을때
//        }                                        // 로그인화면을 다시 불러옴
//    }
//
//        protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
//            final Intent intent = new Intent(this, KakaoSignupActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(intent);
//            finish();
//    }
//}