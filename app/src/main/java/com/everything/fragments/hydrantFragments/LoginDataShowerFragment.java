package com.everything.fragments.hydrantFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.everything.FragmentsDataCollector;
import com.everything.R;
import com.everything.fragments.BaseFragment;
import com.everything.service.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Mirek on 2016-03-18.
 */
public class LoginDataShowerFragment extends BaseFragment{

    @Bind(R.id.fb_id_tv)
    TextView fbIdTv;
    @Bind(R.id.fb_token_tv)
    TextView fbTokenTv;
    @Bind(R.id.user_login_tv)
    TextView userLoginTv;
    @Bind(R.id.user_password_tv)
    TextView userPasswordTv;
    @Bind(R.id.logged_user_name_fb)
    TextView loggedUserTvFb;
    @Bind(R.id.logged_user_name_gplus)
    TextView loggedUserTvGplus;

    private User user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parseArgs(getArguments());

        View view = inflater.inflate(R.layout.enter_hydrant_fragment, container, false);
        ButterKnife.bind(this, view);

        super.initViews(view);

        if (null != user) {
            userLoginTv.setText(user.getLogin());
            userPasswordTv.setText(user.getPassword());
            fbIdTv.setText(user.getFbId());
            fbTokenTv.setText(user.getFbToken());

            String fbUserName = user.getFbUserName();
            if (fbUserName != null) {
                loggedUserTvFb.setText(fbUserName);
            }

            String gPlusDisplayName = user.getGPlusDisplayName();
            if(gPlusDisplayName!=null){
                loggedUserTvGplus.setText(gPlusDisplayName);
            }

            FragmentsDataCollector.getInstance()
                    .setUser(user);
        }

        return view;
    }

    private void parseArgs(Bundle args) {
        if (args != null) {
            user = (User) args.getSerializable("User");
        }
    }

}
