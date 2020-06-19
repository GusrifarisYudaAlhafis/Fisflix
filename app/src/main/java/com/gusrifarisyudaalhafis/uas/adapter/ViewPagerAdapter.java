package com.gusrifarisyudaalhafis.uas.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    //inisialisasi
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> titleList = new ArrayList<>();

    //constructor
    public ViewPagerAdapter(FragmentManager manager){
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    //untuk menambah fragment
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    //mendapatkan item
    @NotNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    //mendapatkan jumlah
    @Override
    public int getCount() {
        return fragmentList.size();
    }

    //mendapatkan title halaman
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
