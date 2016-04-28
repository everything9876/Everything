package com.everything.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.everything.R;
import com.everything.service.model.User;
import com.facebook.Profile;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * Created by Mirek on 2016-03-18.
 */
public class BaseFragment extends Fragment implements Serializable, View.OnClickListener {

    private static final String BASE_FRAGMENT = "base_fragment";
    protected SwitchFragmentsListener switchFragmentsListener = null;
    private Button nextFragmentButton;
    protected int fragmentLayout;

    protected void initViews(View view) {
            nextFragmentButton = ButterKnife.findById(view,R.id.next_fragment_button);
            nextFragmentButton.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(fragmentLayout,container,false);
        initViews(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            switchFragmentsListener = (SwitchFragmentsListener) context;
        } catch (ClassCastException exception) {
            Log.e(BASE_FRAGMENT, "Cannot attach switchFragmentsListener");
        }
    }

    @Override
    public void onClick(View v) {
        if (R.id.next_fragment_button == v.getId()) {
            if (null != switchFragmentsListener) {
                switchFragmentsListener.onNextScreen();
            }
        }
    }

    public interface SwitchFragmentsListener {
        void onNextScreen();
        void onNextScreen(User user);
    }
}
