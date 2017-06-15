package com.threepillar.meetingroomfinder.network;

import com.threepillar.meetingroomfinder.utils.AppConstants;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nikhil.mehta on 6/12/2017.
 */

public interface RequestInterface {

    @GET(AppConstants.BASE_URL_ENDPOINT)
    Call<JSONResponse> getJSON();

}
