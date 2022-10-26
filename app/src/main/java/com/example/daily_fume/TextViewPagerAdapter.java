//package com.example.daily_fume;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.viewpager.widget.PagerAdapter;
//
//public class TextViewPagerAdapter extends PagerAdapter {
//
//    private Context context = null;
//
//    // Context 를 전달받아 context 에 저장하는 생성자 추가.
//    public TextViewPagerAdapter(Context context) {
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        // position 값을 받아 주어진 위치에 페이지를 생성한다
//
//        View view = null;
//
//        if(context != null) {
//            // LayoutInflater 를 통해 "/res/layout/page.xml" 을 뷰로 생성.
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.view_pager, container, false);
//
//            TextView textView = view.findViewById(R.id.fume_brand_01);
//            textView.setText("");
//            // textView.setText(""+position);// 기존
//
//        }
//
//        // 뷰페이저에 추가
//        container.addView(view);
//
//        return view;
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        // position 값을 받아 주어진 위치의 페이지를 삭제한다
//        container.removeView((View) object);
//    }
//
//    @Override
//    public int getCount() {
//        // 사용 가능한 뷰의 개수를 return 한다
//        // 전체 페이지 수는 10개로 고정한다
//        return 10;
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        // 페이지 뷰가 생성된 페이지의 object key 와 같은지 확인한다
//        // 해당 object key 는 instantiateItem 메소드에서 리턴시킨 오브젝트이다
//        // 즉, 페이지의 뷰가 생성된 뷰인지 아닌지를 확인하는 것
//        return view == object;
//    }
//}


package com.example.daily_fume;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class TextViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<FragranceData> arrayList;
    private LayoutInflater inflater;

    // Context 를 전달받아 context 에 저장하는 생성자 추가.
    public TextViewPagerAdapter(Activity context, ArrayList<FragranceData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        // this.inflater = inflater;
    }

    @Override
    public int getCount() {
        // 사용 가능한 뷰의 개수를 return 한다
        // 전체 페이지 수는 10개로 고정한다
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // 페이지 뷰가 생성된 페이지의 object key 와 같은지 확인한다
        // 해당 object key 는 instantiateItem 메소드에서 리턴시킨 오브젝트이다
        // 즉, 페이지의 뷰가 생성된 뷰인지 아닌지를 확인하는 것
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // position 값을 받아 주어진 위치에 페이지를 생성한다

        // View view = null;
        //View page = inflater.inflate(R.layout.view_pager, null);

        // if(context != null) {
        // LayoutInflater 를 통해 "/res/layout/page.xml" 을 뷰로 생성.
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_pager, container, false);

        TextView textView1 = view.findViewById(R.id.fume_brand_04);
        TextView textView2 = view.findViewById(R.id.fume_brand_05);
        TextView textView3 = view.findViewById(R.id.fume_brand_06);

        TextView textView4 = view.findViewById(R.id.fume_title_04);
        TextView textView5 = view.findViewById(R.id.fume_title_05);
        TextView textView6 = view.findViewById(R.id.fume_title_06);

        ImageView imageView1 = view.findViewById(R.id.fume_picture_04);
        ImageView imageView2 = view.findViewById(R.id.fume_picture_05);
        ImageView imageView3 = view.findViewById(R.id.fume_picture_06);

        // FragranceData item = (FragranceData) arrayList.get(position);

        imageView1.setImageBitmap(arrayList.get(position).getFragrance_image());
        imageView2.setImageBitmap(arrayList.get(position).getFragrance_image());
        imageView3.setImageBitmap(arrayList.get(position).getFragrance_image());

        textView1.setText(arrayList.get(position).getFragrance_brand());
        textView2.setText(arrayList.get(position).getFragrance_brand());
        textView3.setText(arrayList.get(position).getFragrance_brand());

        textView4.setText(arrayList.get(position).getFragrance_name());
        textView5.setText(arrayList.get(position).getFragrance_name());
        textView6.setText(arrayList.get(position).getFragrance_name());

        // textView.setText(""+position);// 기존
        // }

        // 뷰페이저에 추가
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // position 값을 받아 주어진 위치의 페이지를 삭제한다
        container.removeView((View) object);
    }


}