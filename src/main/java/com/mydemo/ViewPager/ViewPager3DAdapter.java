package com.mydemo.ViewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mydemo.R;

import java.util.ArrayList;

/**
 * Created by HaoPz on 2017/12/21.
 */

public class ViewPager3DAdapter extends PagerAdapter {
    private ArrayList<Integer> arraylist;
    private Context context;

    public ViewPager3DAdapter(ArrayList<Integer> arraylist, Context context) {
        this.arraylist = arraylist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemViewPager = LayoutInflater.from(context).inflate(R.layout.item_viewpager, null);
        ImageView mImageView = (ImageView) itemViewPager.findViewById(R.id.itemViewPager3D);

        mImageView.setImageResource(arraylist.get(position));

        container.addView(itemViewPager);

        return itemViewPager;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
