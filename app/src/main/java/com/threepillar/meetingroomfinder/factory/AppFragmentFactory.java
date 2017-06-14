package com.threepillar.meetingroomfinder.factory;

import android.app.Fragment;
import android.os.Parcelable;

import com.threepillar.meetingroomfinder.enums.FragmentTag;
import com.threepillar.meetingroomfinder.fragments.CalenderFragment;
import com.threepillar.meetingroomfinder.fragments.RoomsFragment;

public class AppFragmentFactory {

    public static Fragment getCalenderFragment(FragmentTag fragmentTag, Parcelable fragArgs) {

        Fragment fragment = null;

        switch (fragmentTag) {

            case CalenderFragment:
                fragment = CalenderFragment.newInstance(fragArgs);
                break;
            case RoomsFragment:
                fragment = RoomsFragment.newInstance(fragArgs);
                break;
        }
        return fragment;
    }


}
