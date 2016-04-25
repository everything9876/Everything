package com.everything.activities;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.everything.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    public static final int LOGO_POSITION = 2;
    private static final String SPLASH = "splash";

    @Bind(R.id.logo_container)
    RelativeLayout logoContainer;

    private ValueAnimator logoAnimator;

    private final Animator.AnimatorListener logoAnimatorListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
            logoContainer.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            openLoginActivity();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
    }

    private void openLoginActivity() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSplashAnimation();
    }

    private void startSplashAnimation() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int logoTextSizeInPx = (int) getResources().getDimension(R.dimen.logo_text_size);

        final int logoContainerTopMargin = displayMetrics.heightPixels/LOGO_POSITION - logoTextSizeInPx;
        final RelativeLayout.LayoutParams logoContainerLayoutParams =
                (RelativeLayout.LayoutParams) logoContainer.getLayoutParams();

        logoAnimator = new ValueAnimator();
        logoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                Log.i(SPLASH,"Current value:"+ animation.getAnimatedValue());

                logoContainerLayoutParams.topMargin = (int) animation.getAnimatedValue();
                logoContainer.setLayoutParams(logoContainerLayoutParams);
            }
        });
        logoAnimator.addListener(logoAnimatorListener);
        logoAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        logoAnimator.setDuration(1000);
        logoAnimator.setIntValues(logoContainerTopMargin,getResources().getDimensionPixelSize(R.dimen.view_margin_medium));

        logoAnimator.start();
    }

}
