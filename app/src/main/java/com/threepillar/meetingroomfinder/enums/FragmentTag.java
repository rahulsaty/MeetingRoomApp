package com.threepillar.meetingroomfinder.enums;

import com.threepillar.meetingroomfinder.common.util.AppConstants;

public enum FragmentTag {

    //meeting room fragments
    CalenderFragment(AppConstants.Modules.MEETING_ROOM_MODULE),
    RoomsFragment(AppConstants.Modules.MEETING_ROOM_MODULE);

    // for further module fragments please specify here.

    private String module;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    FragmentTag(String module) {
        this.module = module;
    }

}
