package com.everything.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.everything.BaseActivity;
import com.everything.R;
import com.everything.fragments.BaseFragment;
import com.everything.fragments.hydrantFragments.LoginDataShowerFragment;
import com.everything.fragments.hydrantFragments.EnvironmentFragment;
import com.everything.fragments.hydrantFragments.OpenCloseExampleFragment;
import com.everything.fragments.hydrantFragments.HydrantPhotoFragment;
import com.everything.fragments.hydrantFragments.HydrantSendResultsFragment;
import com.everything.fragments.hydrantFragments.HydrantTechnicalDetailsFragment;
import com.everything.fragments.hydrantFragments.LiskovPrinciplFakeAPIFragment;
import com.everything.fragments.hydrantFragments.HydrantTechnicalCommentsFragment;
import com.everything.service.model.User;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

public class HydrantActivity extends BaseActivity implements BaseFragment.SwitchFragmentsListener {

    private static final String FRAGMENTS_LIST = "fragments_list";
    private static final String FRAGMENTS_COUNTER = "fragments_counter";
    private ArrayList<BaseFragment> fragmentsList = null;
    private int fragmentsCounter = 0;
    private final LoginDataShowerFragment loginDataShowerFragment = new LoginDataShowerFragment();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydrant);

        if (savedInstanceState == null) {
            initFragmentsList();
            Bundle bundle = getIntent().getExtras();

            Fragment fragment = fragmentsList.get(fragmentsCounter);
            fragment.setArguments(bundle);
            commitFragment(fragment);
        } else {
            try {
                fragmentsList = (ArrayList<BaseFragment>) savedInstanceState.getSerializable(FRAGMENTS_LIST);
                fragmentsCounter = savedInstanceState.getInt(FRAGMENTS_COUNTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(FRAGMENTS_LIST, fragmentsList);
        outState.putSerializable(FRAGMENTS_COUNTER,fragmentsCounter);
    }

    private void initFragmentsList() {
        fragmentsList = new ArrayList<BaseFragment>();
        fragmentsList.add(loginDataShowerFragment);
        fragmentsList.add(new OpenCloseExampleFragment());
        fragmentsList.add(new LiskovPrinciplFakeAPIFragment());
        fragmentsList.add(new HydrantPhotoFragment());
        fragmentsList.add(new EnvironmentFragment());
        fragmentsList.add(new HydrantTechnicalDetailsFragment());
        fragmentsList.add(new HydrantTechnicalCommentsFragment());
        fragmentsList.add(new HydrantSendResultsFragment());
    }

    abstract class NextScreenSwitcher {
        protected void goToNextScreen() {
            ++fragmentsCounter;
            if (fragmentsCounter == fragmentsList.size()) {
                cleanWholeFragmentsStack(0);
                fragmentsCounter = 0;
            } else {
                commitFragmentInProperWay();
            }
        }

        abstract protected void commitFragmentInProperWay();
    }

    class NextScreenWithoutData extends NextScreenSwitcher {
        @Override
        protected void commitFragmentInProperWay() {
            if (fragmentsCounter >= 0) {
                commitFragment(fragmentsList.get(fragmentsCounter));
            }
        }
    }

    class NextScreenWithData extends NextScreenSwitcher {
        private User user;

        public NextScreenWithData(User user) {
            this.user = user;
        }

        @Override
        protected void commitFragmentInProperWay() {
            Fragment fragment = fragmentsList.get(fragmentsCounter);
            Bundle args = new Bundle();
            args.putSerializable("User", user);
            fragment.setArguments(args);
            commitFragment(fragment);
        }
    }

    @Override
    public void onNextScreen() {
        new NextScreenWithoutData().goToNextScreen();
    }

    @Override
    public void onNextScreen(User user) {
        new NextScreenWithData(user).goToNextScreen();
    }

    @Override
    public void onBackPressed() {
        returnToPreviousFragment();
    }

    private void returnToPreviousFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            //TODO: Return to login or finish(like in seatsplanet)?
            returnToLoginScreen();
        }
        --fragmentsCounter;
        if (fragmentsCounter < 0) {
            fragmentsCounter = 0;
        }
    }

    private void returnToLoginScreen() {
        Intent nextActivity = new Intent(this, LoginActivity.class);
        startActivity(nextActivity);
        LoginManager.getInstance().logOut();
        finish();
    }
}
