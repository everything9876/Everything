package com.everything.fragments.hydrantFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.everything.FragmentsDataCollector;
import com.everything.R;
import com.everything.fragments.BaseFragment;
import com.everything.service.model.Computer;

import java.util.ArrayList;

/**
 * Created by Mirek on 2016-03-18.
 */
public class HydrantSendResultsFragment extends BaseFragment{

    private static final String TEST = "test";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.fragmentLayout = R.layout.hydrant_send_results_fragment;

        Log.i(TEST,FragmentsDataCollector.getInstance().getUser().getLogin());
        ArrayList<Computer> computers = FragmentsDataCollector.getInstance().getComputers();

        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
