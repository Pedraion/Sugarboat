package com.example.sugarboat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class premiumFragment extends Fragment {

    public premiumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_premium, container, false);

        HeightWrappingViewPager viewPager = view.findViewById(R.id.view_pager);
        PremiumPagerAdapter adapter = new PremiumPagerAdapter(getContext());
        viewPager.setAdapter(adapter);

        int margin = getResources().getDimensionPixelOffset(R.dimen.page_margin); // Define your margin in dimens.xml
        viewPager.setPageTransformer(false, new MarginPageTransformer(margin));

        return view;
    }
}
