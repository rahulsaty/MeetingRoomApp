package com.threepillar.meetingroomfinder.network;

import com.threepillar.meetingroomfinder.common.util.AppConstants;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET(AppConstants.BASE_URL_ENDPOINT)
    Call<JSONResponse> getJSON();

}
