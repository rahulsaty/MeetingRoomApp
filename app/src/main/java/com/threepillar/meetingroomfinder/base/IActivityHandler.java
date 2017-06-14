package com.threepillar.meetingroomfinder.base;

/**
 * Created by rahul.pandey on 4/28/2017.
 */

public interface IActivityHandler {

    void addBackClickListener(IActivityCallback interaction);

    void removeBackClickListener(IActivityCallback interaction);
}
