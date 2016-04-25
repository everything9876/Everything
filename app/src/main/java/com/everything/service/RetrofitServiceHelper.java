package com.everything.service;

import com.everything.Configs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mirek on 2016-03-17.
 */
public class RetrofitServiceHelper {

    public static final int SUCCESS_CODE = 200;

    private static RetrofitServiceHelper instance;
    private final Retrofit retrofit;
    private LoginInterface loginInterface;
    private DataInterface dataInterface;


    private RetrofitServiceHelper() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Configs.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitServiceHelper getRetrofitService() {
        if (instance == null) {
            instance = new RetrofitServiceHelper();
        }
        return instance;
    }

    public LoginInterface getLoginInterface() {
        if (loginInterface == null) {
            loginInterface = retrofit.create(LoginInterface.class);
        }
        return loginInterface;
    }

    public DataInterface getDataInterface() {
        if (dataInterface == null) {
            dataInterface = retrofit.create(DataInterface.class);
        }
        return dataInterface;
    }
}
