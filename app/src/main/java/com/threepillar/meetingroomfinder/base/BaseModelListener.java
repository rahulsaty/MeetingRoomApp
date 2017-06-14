package com.threepillar.meetingroomfinder.base;


public interface BaseModelListener {
    /**
     * This method is called when model is not able to fetch data
     * @param failureResponse
     */
    void onFailure(FailureResponse failureResponse);

}
