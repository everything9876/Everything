package com.everything.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everything.R;

/**
 * Created by Mirek on 2016-04-20.
 */
public class RegisterFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.fragmentLayout= R.layout.register_fragment;
        View view = inflater.inflate(fragmentLayout,container,false);
        return view;
    }
}
