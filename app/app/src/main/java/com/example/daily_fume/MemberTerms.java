package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberTerms extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change, htmlAc;
    Button confirmkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_terms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("회원 약관");

        backBtn = (ImageView) findViewById(R.id.back_icon);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        htmlAc = (TextView) findViewById(R.id.htmlAc);
        String strHtml =
                "제1조(목적)\n" +
                        "이 약관은 비타민C(사업자)가 운영하는 데일리퓸 앱(APP) (이하 \"앱\"이라 한다)에서 제공하는 온라인 관련 서비스" +
                        "(이하 \"서비스\"라 한다)를 이용함에 있어 앱과 이용자의 권리·의무 및 책임사항을 규정함을 목적으로 합니다.\n\n\n"+
                "제2조(정의)\n" +
                        "1. \"앱\" 이란 비타민C가 재화 또는 용역(이하 \"재화등\"이라 함)을 이용자에게 제공하기 위하여 컴퓨터등 정보통신설비를" +
                        "이용하여 이용자에게 필요한 정보를 제공할 수 있도록 설정한 가상의 영업장을 말하며,  아울러 앱을 운영하는 사업자의 의미로도 사용합니다.\n\n"+
                        "2. \"이용자\"란 \"앱\"에 접속하여 이 약관에 따라 \"앱\"이 제공하는 서비스를 받는 회원을 말합니다.\n\n" +
                        "3. '회원'이라 함은 \"앱\"에 개인정보를 제공하여 회원등록을 한 자로서, \"앱\"의 정보를 지속적으로 제공받으며, " +
                        "\"앱\"이 제공하는 서비스를 계속적으로 이용할 수 있는 자를 말합니다.\n\n\n"+
                "제3조 (약관등의 명시와 설명 및 개정) \n" +
                        "1. \"앱\"은 이 약관의 내용과 상호 및 대표자 성명, 영업소 소재지 주소(소비자의 불만을 처리할 수 있는 곳의 주소를 포함)," +
                        "전화번호 및 전자우편주소, 사업자등록번호, 통신판매업신고번호, 개인정보관리책임자등을 이용자가 쉽게 알 수 있도록 데일리퓸 앱에 공지사항에 게시합니다.\n\n"+
                        "2. \"앱\"은 이용자가 약관에 동의하기에 앞서 약관에 정하여져 있는 내용 중 개인정보와 같은 중요한 내용을 이용자가 이해할 수 있도록 별도의 연결화면 또는 " +
                        "팝업화면 등을 제공하여 이용자의 확인을 구하여야 합니다.\n\n\n"+
                "제4조(회원가입) \n" +
                        "1. 이용자는 \"앱\"이 정한 가입 양식에 따라 회원정보를 기입한 후 이 약관에 동의한다는 의사표시를 함으로서 회원가입을 신청합니다.\n" + "\n" +
                        "2. 회원은 제15조제1항에 의한 등록사항에 변경이 있는 경우, 즉시 전자우편 기타 방법으로 \"앱\"에 대하여 그 변경사항을 알려야 합니다.\n\n\n"+
                "제5조(회원 탈퇴 및 자격 상실 등) \n" +
                        "1. 회원은 \"앱\"에 언제든지 탈퇴를 요청할 수 있으며 \"앱\"은 즉시 회원탈퇴를 처리합니다.\n" + "\n" +
                        "2. 회원이 다음 각호의 사유에 해당하는 경우, \"앱\"은 회원자격을 제한 및 정지시킬 수 있습니다.\n" +
                        "1. 가입 신청시에 허위 내용을 등록한 경우\n" +
                        "2. \"앱\"을 이용하여 얻은 정보등을 사용하여 악위적인 내용을 추가하여 허위 유포하는 경우\n" +
                        "3. 다른 사람의 \"앱\" 이용을 방해하거나 그 정보를 도용하는 등 전자상거래 질서를 위협하는 경우\n" +
                        "4. \"앱\"을 이용하여 법령 또는 이 약관이 금지하거나 공서양속에 반하는 행위를 하는 경우\n" + "\n" +
                        "3. \"앱\"이 회원 자격을 제한 또는 정지 시킨후, 동일한 행위가 2회이상 반복되거나 30일이내에 그 사유가 시정되지 아니하는 경우 \"앱\"은 회원자격을 상실시킬 수 있습니다.\n" + "\n" + "\n" +
                "제6조(개인정보보호)\n" +
                        "1. \"앱\"은 이용자의 정보수집시 필요한 최소한의 정보를 수집합니다. 다음 사항을 필수사항으로 하며 그 외 사항은 선택사항으로 합니다. \n" +
                        "1. 성명\n" +
                        "2. 연령\n" +
                        "3. 성별\n" +
                        "4. 희망닉네임\n" +
                        "5. 비밀번호(회원의 경우)\n" +
                        "6. 전자우편주소(또는 이동전화번호)\n" + "\n" +
                        "2. \"앱\"이 이용자의 개인식별이 가능한 개인정보를 수집하는 때에는 반드시 당해 이용자의 동의를 받습니다.\n" + "\n" +
                        "3. \"앱\"은 이용자가 제공한 개인정보를 당해 이용자의 동의없이 목적외의 이용이나 제3자에게 제공할 수 없습니다.\n" + "\n" + "\n" +
                "제7조(이용자의 의무)\n" +
                        "이용자는 다음 행위를 하여서는 안됩니다.\n" +
                        "1. 신청 또는 변경시 허위 내용의 등록\n" +
                        "2. 타인의 정보 도용\n" +
                        "3. \"앱\"에 게시된 정보의 변경\n" +
                        "4. \"앱\"이 정한 정보 이외의 정보(컴퓨터 프로그램 등) 등의 송신 또는 게시\n" +
                        "5. \"앱\" 기타 제3자의 저작권 등 지적재산권에 대한 침해\n" +
                        "6. \"앱\" 기타 제3자의 명예를 손상시키거나 업무를 방해하는 행위\n" +
                        "7. 외설 또는 폭력적인 메시지, 화상, 음성, 기타 공서양속에 반하는 정보를 몰에 공개 또는 게시하는 행위\n" + " \n" + "\n" +
                "제8조(저작권의 귀속 및 이용제한)\n" +
                        "1. \"앱\"이 작성한 저작물에 대한 저작권 기타 지적재산권은 \"앱\"에 귀속합니다.\n" + "\n" +
                        "2. 이용자는 \"앱\"을 이용함으로써 얻은 정보 중 \"앱\"에게 지적재산권이 귀속된 정보를 \"앱\"의 사전 승낙없이 복제, 송신, 출판, 배포, 방송 기타 방법에 의하여 영리목적으로 이용하거나 제3자에게 이용하게 하여서는 안됩니다.\n" + "\n" +
                        "3. \"앱\"은 약정에 따라 이용자에게 귀속된 저작권을 사용하는 경우 당해 이용자에게 통보하여야 합니다.\n" + "\n" + "\n" +
                        "-  본 약관은 2022년 4월 20일 부터 적용됩니다. \n" + "\n" + "\n"
                ;
        htmlAc.setText(strHtml);

        confirmkBtn = (Button) findViewById(R.id.confirmkBtn);
        confirmkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}