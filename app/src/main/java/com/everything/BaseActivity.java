package com.everything;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mirek on 2016-03-18.
 */
public class BaseActivity extends AppCompatActivity {



    protected void commitFragment(Fragment fragment){
        commitFragment(fragment,AnimationType.FADE_IN_FADE_OUT);
    }

    protected void commitFragment(Fragment fragment,AnimationType animationType) {
//        ActivityHelper.hideKeyboard(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        chooseProperAnimation(animationType, fragmentTransaction);
        fragmentTransaction.replace(R.id.login_fragment_container, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    private void chooseProperAnimation(AnimationType animationType, FragmentTransaction fragmentTransaction) {
        switch(animationType){
            case FADE_IN_FADE_OUT:
                fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                break;
            case LEFT_IN_RIGHT_OUT:
                fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out, R.anim.right_in,R.anim.left_out);
                break;
            case RIGHT_IN_LEFT_OUT:
                fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);
                break;
            default:
        }
    }

    protected void cleanWholeFragmentsStack(int numberOfFragmentsLeavingOnStack){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 1; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public enum AnimationType{
        FADE_IN_FADE_OUT, LEFT_IN_RIGHT_OUT, RIGHT_IN_LEFT_OUT
    }

}
