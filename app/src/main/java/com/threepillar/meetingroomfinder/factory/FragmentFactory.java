package com.threepillar.meetingroomfinder.factory;

import android.app.Fragment;
import android.os.Parcelable;

import com.threepillar.meetingroomfinder.common.utils.AppConstants;
import com.threepillar.meetingroomfinder.enums.FragmentTag;

public class FragmentFactory {

    public static Fragment getFragment(FragmentTag fragmentTag, Parcelable serializableArgs) {

        Fragment fragment = null;


        switch (fragmentTag.getModule()) {

            case AppConstants.Modules.MEETING_ROOM_MODULE:
                fragment = AppFragmentFactory.getCalenderFragment(fragmentTag, serializableArgs);
                break;
        }

        return fragment;

    }
}
