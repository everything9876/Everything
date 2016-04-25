package com.everything.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.everything.BaseActivity;
import com.everything.R;
import com.everything.fragments.ForgotPasswordFragment;
import com.everything.fragments.LoginFragment;
import com.everything.fragments.RegisterFragment;
import com.everything.service.model.User;

public class LoginActivity extends BaseActivity implements LoginFragment.LoginFragmentListener {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            LoginFragment firstFragment = new LoginFragment();
            commitFragment(firstFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNextScreen() {
        goToNextScreenCommon();
    }


    @Override
    public void onNextScreen(User user) {
        if (null != user) {
            goToNextScreenCommon(user);
        } else {
            goToNextScreenCommon();
        }
    }

    private void goToNextScreenCommon() {
        goToNextScreenCommon(null);
    }

    private void goToNextScreenCommon(User user) {
        Intent nextActivity = new Intent(this, HydrantActivity.class);
        if (null != user) {
            nextActivity.putExtra("User", user);
        }
        startActivity(nextActivity);
        finish();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            fragmentManager.popBackStack();
            finish();
        }
    }

    @Override
    public void onForgotPasswordClicked() {
        Log.i("login activity", "forgot password clicked");
        commitFragment(new ForgotPasswordFragment(),AnimationType.LEFT_IN_RIGHT_OUT);
    }

    @Override
    public void onRegisterClicked() {
        Log.i("login activity", "register clicked");
        commitFragment(new RegisterFragment(),AnimationType.RIGHT_IN_LEFT_OUT);
    }
}
