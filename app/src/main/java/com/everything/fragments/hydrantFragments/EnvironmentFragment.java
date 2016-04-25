package com.everything.fragments.hydrantFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.everything.R;
import com.everything.fragments.BaseFragment;

/**
 * Created by Mirek on 2016-03-18.
 */
public class EnvironmentFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.fragmentLayout = R.layout.environment_fragment;
        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
