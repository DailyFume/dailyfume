package com.example.daily_fume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class pageViewA extends PagerAdapter {

    private Context context;
    private LayoutInflater mInflater;

    // Context 를 전달받아 context 에 저장하는 생성자 추가.
    public pageViewA(Context context) {
        //this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // position 값을 받아 주어진 위치에 페이지를 생성한다

        View view = null;
        // position이 0부터 시작됨

        switch (position) {
            case 0 :
                view = mInflater.inflate(R.layout.view_pager1, null);
                TextView textView1 = view.findViewById(R.id.fume_brand_01);
                textView1.setText("딥디크");
                TextView textView2 = view.findViewById(R.id.fume_brand_02);
                textView2.setText("바이레도");
                TextView textView3 = view.findViewById(R.id.fume_brand_03);
                textView3.setText("불가리");

                TextView textView11 = view.findViewById(R.id.fume_title_01);
                textView11.setText("플레르 드 뽀");
                TextView textView12 = view.findViewById(R.id.fume_title_02);
                textView12.setText("모하비 고스트");
                TextView textView13 = view.findViewById(R.id.fume_title_03);
                textView13.setText("골데아 더 로만 나이트");

                ImageView imageView1 = view.findViewById(R.id.fume_picture_01);
                imageView1.setImageResource(R.drawable.fume01);
                ImageView imageView2 = view.findViewById(R.id.fume_picture_02);
                imageView2.setImageResource(R.drawable.fume02);
                ImageView imageView3 = view.findViewById(R.id.fume_picture_03);
                imageView3.setImageResource(R.drawable.fume03);
                break;
            case 1 :
                view = mInflater.inflate(R.layout.view_pager2, null);
                TextView textView4 = view.findViewById(R.id.fume_brand_04);
                textView4.setText("샤넬");
                TextView textView5 = view.findViewById(R.id.fume_brand_05);
                textView5.setText("디올");
                TextView textView6 = view.findViewById(R.id.fume_brand_06);
                textView6.setText("샤넬");

                TextView textView14 = view.findViewById(R.id.fume_title_04);
                textView14.setText("넘버파이브");
                TextView textView15 = view.findViewById(R.id.fume_title_05);
                textView15.setText("자도르");
                TextView textView16 = view.findViewById(R.id.fume_title_06);
                textView16.setText("넘버나인틴");

                ImageView imageView4 = view.findViewById(R.id.fume_picture_04);
                imageView4.setImageResource(R.drawable.fume04);
                ImageView imageView5 = view.findViewById(R.id.fume_picture_05);
                imageView5.setImageResource(R.drawable.fume05);
                ImageView imageView6 = view.findViewById(R.id.fume_picture_06);
                imageView6.setImageResource(R.drawable.fume06);
                break;
            case 2 :
                view = mInflater.inflate(R.layout.view_pager3, null);
                TextView textView7 = view.findViewById(R.id.fume_brand_07);
                textView7.setText("조말론");
                TextView textView8 = view.findViewById(R.id.fume_brand_08);
                textView8.setText("베르사체");
                TextView textView9 = view.findViewById(R.id.fume_brand_09);
                textView9.setText("조말론");

                TextView textView17 = view.findViewById(R.id.fume_title_07);
                textView17.setText("라임바질 앤 만다린");
                TextView textView18 = view.findViewById(R.id.fume_title_08);
                textView18.setText("크리스탈 느와르");
                TextView textView19 = view.findViewById(R.id.fume_title_09);
                textView19.setText("블랙베리 앤 베이");

                ImageView imageView7 = view.findViewById(R.id.fume_picture_07);
                imageView7.setImageResource(R.drawable.fume08);
                ImageView imageView8 = view.findViewById(R.id.fume_picture_08);
                imageView8.setImageResource(R.drawable.fume09);
                ImageView imageView9 = view.findViewById(R.id.fume_picture_09);
                imageView9.setImageResource(R.drawable.fume10);
                break;

        }

//        if(context != null) {
//            // LayoutInflater 를 통해 "/res/layout/page.xml" 을 뷰로 생성.
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.view_pager1, container, false);
//
//            TextView textView1 = view.findViewById(R.id.fume_brand_01);
//            textView1.setText("1");
//            TextView textView2 = view.findViewById(R.id.fume_brand_02);
//            textView2.setText("2");
//            TextView textView3 = view.findViewById(R.id.fume_brand_03);
//            textView3.setText("3");
//
//            ImageView imageView1 = view.findViewById(R.id.fume_picture_01);
//            imageView1.setImageResource(R.drawable.fume01);
//            ImageView imageView2 = view.findViewById(R.id.fume_picture_02);
//            imageView2.setImageResource(R.drawable.fume02);
//            ImageView imageView3 = view.findViewById(R.id.fume_picture_03);
//            imageView3.setImageResource(R.drawable.fume03);
//
//            TextView textView4 = view.findViewById(R.id.fume_brand_04);
//            textView4.setText("4");
//            TextView textView5 = view.findViewById(R.id.fume_brand_05);
//            textView5.setText("5");
//            TextView textView6 = view.findViewById(R.id.fume_brand_06);
//            textView6.setText("6");
//
//            ImageView imageView4 = view.findViewById(R.id.fume_picture_04);
//            imageView4.setImageResource(R.drawable.fume04);
//            ImageView imageView5 = view.findViewById(R.id.fume_picture_05);
//            imageView5.setImageResource(R.drawable.fume05);
//            ImageView imageView6 = view.findViewById(R.id.fume_picture_06);
//            imageView6.setImageResource(R.drawable.fume06);
//
//
//            switch (position) {
//                case 1:
//                    view1 = inflater.inflate(R.layout.view_pager1, container, false);
//                case 2 :
//                    view2 = inflater.inflate(R.layout.view_pager2, container, false);
//            }
//
//
//
//            // textView.setText(""+position);// 기존
//
//        }

        // 뷰페이저에 추가
        //container.addView(view);

        //return view;
        ((ViewPager)container).addView(view, null);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // position 값을 받아 주어진 위치의 페이지를 삭제한다
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        // 사용 가능한 뷰의 개수를 return 한다
        // 전체 페이지 수는 10개로 고정한다
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // 페이지 뷰가 생성된 페이지의 object key 와 같은지 확인한다
        // 해당 object key 는 instantiateItem 메소드에서 리턴시킨 오브젝트이다
        // 즉, 페이지의 뷰가 생성된 뷰인지 아닌지를 확인하는 것
        return view == object;
    }
}