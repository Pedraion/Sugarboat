package com.example.sugarboat;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class MarginPageTransformer implements ViewPager.PageTransformer {
    private int margin;

    public MarginPageTransformer(int margin) {
        this.margin = margin;
    }

    @Override
    public void transformPage(View page, float position) {
        page.setTranslationX(position * (page.getWidth() + margin));
    }
}
