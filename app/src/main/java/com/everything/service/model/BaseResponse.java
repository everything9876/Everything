package com.everything.service.model;

import com.everything.service.RetrofitServiceHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mirek on 2016-03-17.
 */
public class BaseResponse implements Serializable {
    private String message;
    private int code;

    public String getMessage(){
        return message;
    }

    public boolean isSuccessful(){
        return code == RetrofitServiceHelper.SUCCESS_CODE;
    }

}
