package com.everything.service;

import com.everything.service.model.BaseResponse;
import com.everything.service.model.Computer;
import com.everything.service.model.ComputersContainer;
import com.everything.service.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Mirek on 2016-03-17.
 */
public interface LoginInterface {

    @FormUrlEncoded
    @POST("56efe67e1000000f008ef19d")
    Call<User> login(
            @Field("login") String login,
            @Field("password") String password
    );

    @GET("56eac2ee1300006b007377c7")
    Call<BaseResponse> logout(
    );

}
