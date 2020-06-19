package com.gusrifarisyudaalhafis.uas.fragment.tv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gusrifarisyudaalhafis.uas.R;
import com.gusrifarisyudaalhafis.uas.adapter.ViewPagerAdapter;
import com.gusrifarisyudaalhafis.uas.fragment.tv.airingToday.TVAiringTodayFragment;
import com.gusrifarisyudaalhafis.uas.fragment.tv.onTv.OnTVFragment;

public class TVFragment extends Fragment {

    //constructor kosong
    public TVFragment() {
    }

    //buat View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        setHasOptionsMenu(true);
        //binding komponen yang digunakan
        Toolbar toolbar = view.findViewById(R.id.toolbar_main);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return view;
    }

    //set View Pager untuk memberikan tab layout
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OnTVFragment(), getString(R.string.on_tv));
        adapter.addFragment(new TVAiringTodayFragment(), getString(R.string.airing_today));
        viewPager.setAdapter(adapter);
    }
}
