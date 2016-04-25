package com.everything.service;

import com.everything.service.model.Computer;
import com.everything.service.model.ComputersContainer;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Mirek on 2016-04-21.
 */
public interface DataInterface {

    @GET("571a26db12000036101b7257")
    Call<ComputersContainer> getComputers(
    );

}
