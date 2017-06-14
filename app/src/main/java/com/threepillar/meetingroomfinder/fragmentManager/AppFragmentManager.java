package com.threepillar.meetingroomfinder.fragmentManager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Parcelable;

import com.threepillar.meetingroomfinder.base.BaseFragment;
import com.threepillar.meetingroomfinder.common.utils.LogUtil;
import com.threepillar.meetingroomfinder.enums.FragmentTag;
import com.threepillar.meetingroomfinder.factory.FragmentFactory;

public class AppFragmentManager {

    private String TAG = getClass().getSimpleName();

    private FragmentManager fragmentManager;

    public AppFragmentManager(Activity activity) {
        fragmentManager = activity.getFragmentManager();
    }

    /**
     * add fragment to view passing hosting container id,
     * fragment class for fragment , FragmentFactory will generate fragment wrt
     * fragmentTAG and pass serializable object for that fragment.
     * addToBackStack true if add fragment to backstack
     *
     * @param frameLayoutId
     * @param fragmentTag
     * @param fragmentArgs
     * @param addToBackStack
     */

    public void showFragment(int frameLayoutId, FragmentTag fragmentTag, Parcelable fragmentArgs, boolean addToBackStack) {

        Fragment fragment = FragmentFactory.getFragment(fragmentTag, fragmentArgs);

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment.setRetainInstance(true);
            fragmentTransaction.replace(frameLayoutId, fragment, fragmentTag.name());

            if (addToBackStack)
                fragmentTransaction.addToBackStack(fragmentTag.name());

            fragmentTransaction.commit();
        } else {
            //TODO error message to be in some config
            LogUtil.LOGD(TAG, "Kindly implement fragment in respective module factory");
        }
    }

    /**
     * deliver activity result to top fragment
     *
     * @param frameLayoutId
     * @param requestCode
     * @param resultCode
     * @param data
     */

    public void onActivityResult(int frameLayoutId, int requestCode, int resultCode, Intent data) {

        try {
            BaseFragment baseFragment = (BaseFragment) fragmentManager.findFragmentById(frameLayoutId);
            baseFragment.onActivityResult(requestCode, resultCode, data);
        } catch (ClassCastException e) {
            LogUtil.LOGE(TAG, e.getMessage());
        } catch (NullPointerException e) {
            LogUtil.LOGE(TAG, e.getMessage());
        }

    }

    /**
     * cancel all flows of the top fragment when user opts for cancel
     *
     * @param frameLayoutId
     */


    public void cancelAction(int frameLayoutId) {

        try {
            BaseFragment baseFragment = (BaseFragment) fragmentManager.findFragmentById(frameLayoutId);
            baseFragment.cancelRequest();
        } catch (ClassCastException e) {
            LogUtil.LOGE(TAG, e.getMessage());
        } catch (NullPointerException e) {
            LogUtil.LOGE(TAG, e.getMessage());
        }

    }

    /**
     * clear all fragments from stack
     */
    public void clearBackStack() {
        int count = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fragmentManager.popBackStack();
        }
    }

    /**
     * clear frag upto some frag only
     *
     * @param fragTag
     */
    public void clearBackStackUptoFrag(FragmentTag fragTag) {
        fragmentManager.popBackStack(fragTag.name(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
