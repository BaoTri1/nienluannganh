package com.example.shopproject.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shopproject.view.UI.Fragment.CartFragment;
import com.example.shopproject.view.UI.Fragment.ProfileFragment;
import com.example.shopproject.view.UI.Fragment.HomeFragment;
import com.example.shopproject.view.UI.Fragment.FavoriteProductFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new FavoriteProductFragment();
            case 2:
                return new CartFragment();
            case 3:
                return new ProfileFragment();
            default:
                return new HomeFragment();
        }
    }



    @Override
    public int getItemCount() {
        return 4;
    }
}
