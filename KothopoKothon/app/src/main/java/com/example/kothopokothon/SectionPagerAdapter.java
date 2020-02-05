package com.example.kothopokothon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class SectionPagerAdapter extends FragmentPagerAdapter {



    public SectionPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                RequestFragment requestFragment=new RequestFragment();
                return  requestFragment;
            case 1:
                ChatFragment chatFragment= new ChatFragment();
                return chatFragment;
            case 2:
                FriendFragment friendFragment=new FriendFragment();
                return friendFragment;

                default:
                    return  null;


        }

    }

    @Override
    public int getCount() {
        return 3;
    }


    public  CharSequence getPageTitle(int pos){

        switch (pos)
        {
            case 0:
                return "Request";
            case 1:
                return "Kotha";
            case 2:
                return "Bondhu";
                default:
                    return null;


        }
    }


}
