package com.example.herupachi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.herupachi.fragments.Fragment_KeepTidy;
import com.example.herupachi.fragments.Fragment_LittleHelper;
import com.example.herupachi.fragments.Fragment_Shop;
import com.example.herupachi.fragments.Fragment_StayProductive;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new Fragment_LittleHelper();
            case 1: return new Fragment_StayProductive();
            case 2: return new Fragment_KeepTidy();
            case 3: return new Fragment_Shop();
            default: return new Fragment_LittleHelper();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
