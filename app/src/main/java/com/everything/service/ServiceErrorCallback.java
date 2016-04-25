package com.everything.service;

import android.content.Context;


import com.everything.R;
import com.everything.service.model.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ServiceErrorCallback<T extends BaseResponse> implements Callback<T> {

    private final Context context;

    public ServiceErrorCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(final Call<T> call, final Response<T> response) {
        String errorMessage = null;
        if (!response.isSuccess()) {
            errorMessage = getStringSafe(R.string.error_server_connection);
        } else if (!response.body().isSuccessful()) {
            errorMessage = String.format(getStringSafe(R.string.error_message), response.body().getMessage());
        }

        if (errorMessage == null) {
            onSuccess(response);
        } else {
            onFailure(errorMessage);
        }
    }

    @Override
    public void onFailure(final Call<T> call, final Throwable throwable) {
        String errorMessage = getStringSafe(R.string.error_internet_connection);
        onFailure(errorMessage);
    }

    private String getStringSafe(int resId) {
        if (context != null) {
            return context.getString(resId);
        } else {
            return "";
        }
    }

    public abstract void onSuccess(final Response<T> response);

    public abstract void onFailure(final String errorMessage);
}
