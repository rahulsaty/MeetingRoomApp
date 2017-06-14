package com.threepillar.meetingroomfinder.network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nikhil.mehta on 6/12/2017.
 */

public interface RequestInterface {

    @GET("bins/197wz7")
    Call<JSONResponse> getJSON();

}
