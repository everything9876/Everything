package com.everything.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.everything.Configs;
import com.everything.R;
import com.everything.Utils;
import com.everything.service.RetrofitServiceHelper;
import com.everything.service.ServiceErrorCallback;
import com.everything.service.model.User;
import com.everything.utils.ActivityHelper;
import com.everything.utils.TextUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

/**
 * Created by Mirek on 2016-03-18.
 */
public class LoginFragment extends BaseFragment {

    public static final int FRAGMENT_NUMBER = -1;
    private static final String TEST = "test";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final int RC_SIGN_IN = 456;
    private static final String LOGIN_FRAGMENT = "login_fragment";
    final String FIRST_FRAGMENT_ERROR = "first_fragment_error";

    @Bind(R.id.login_field)
    EditText loginField;
    @Bind(R.id.fb_login_button)
    LoginButton fbLoginButton;
    @Bind(R.id.password_field)
    EditText passwordField;
    @Bind(R.id.sign_in_button)
    SignInButton signInButton;

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private boolean loginRunned = false;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    private long lastClickTime;
    private LoginFragmentListener loginFragmentListener;
    private String login;
    private String password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGoogleSignInOptions();
        if (savedInstanceState != null) {
            try {
                login = savedInstanceState.getString(LOGIN);
                password = savedInstanceState.getString(PASSWORD);
            } catch (Exception e) {

            }
        } else {
            initGoogleApiClient();
        }

        initAccessTokenTracker();

        if (callbackManager == null) {
            callbackManager = CallbackManager.Factory.create();
        }
    }

    private void initGoogleSignInOptions() {
        if (gso == null) {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                    .requestEmail()
                    .build();
        }
    }

    private void initAccessTokenTracker() {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, final AccessToken newToken) {
                if (!loginRunned) {
                    loginRunned = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (newToken != null) {
                                loginUsingToken(newToken);
                            }
                        }
                    }, 100);
                }
            }
        };

        accessTokenTracker.startTracking();
    }

    private void initGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String login = loginField.getText().toString();
        String password = passwordField.getText().toString();

        if (!TextUtils.isTextNullOrEmpty(login)) {
            outState.putString(LOGIN, login);
        }
        if (!TextUtils.isTextNullOrEmpty(password)) {
            outState.putString(PASSWORD, password);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, view);

        if (signInButton != null && gso != null) {
            signInButton.setSize(SignInButton.SIZE_STANDARD);
            signInButton.setColorScheme(SignInButton.COLOR_AUTO);
            signInButton.setScopes(gso.getScopeArray());
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActivityHelper.hideKeyboard(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            loginField.setText(login);
            passwordField.setText(password);
            initFblogin();
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation anim = super.onCreateAnimation(transit, enter, nextAnim);

        if (anim == null && nextAnim != 0) {
            anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        }

        if (anim != null) {
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ActivityHelper.hideKeyboard(getActivity());
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        return anim;
    }

    private void initFblogin() {
        fbLoginButton.setReadPermissions("user_friends");
        fbLoginButton.setFragment(this);
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker profileTracker;

            @Override
            public void onSuccess(final LoginResult loginResult) {
                if (!loginRunned) {
                    loginRunned = true;

                    if (Profile.getCurrentProfile() == null) {
                        createAndStartProfileTracker(loginResult);
                    } else {
                        AccessToken accessToken = loginResult.getAccessToken();
                        loginUsingToken(accessToken);
                    }
                }
            }

            private void createAndStartProfileTracker(final LoginResult loginResult) {
                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        loginUsingToken(accessToken);
                        profileTracker.stopTracking();
                    }
                };
                profileTracker.startTracking();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.loginFragmentListener = (LoginFragmentListener) switchFragmentsListener;
        } catch (ClassCastException e) {
            Log.e(LOGIN_FRAGMENT, "Cannot attach loginFragmentListener");
        }
    }

    @OnClick(R.id.login_button)
    void onLoginClicked() {

        if (preventFastClick()) return;

        if (null != super.switchFragmentsListener) {
            if (isFieldsValidationCorrect()) {
                String login = loginField.getText().toString();
                String password = passwordField.getText().toString();
                RetrofitServiceHelper.getRetrofitService()
                        .getLoginInterface()
                        .login(login, password)
                        .enqueue(new ServiceErrorCallback<User>(getContext()) {
                            @Override
                            public void onSuccess(Response<User> response) {
                                User user = (User) response.body();
                                switchScreenOnLoginSuccess(user);
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                ActivityHelper.showErrorDialog("Some request error", getContext());
                            }
                        });
            }
        }
    }

    private boolean preventFastClick() {
        if (SystemClock.elapsedRealtime() - lastClickTime < Configs.PREVENT_FAST_CLICK_THRESHOLD) {
            return true;
        }
        lastClickTime = SystemClock.elapsedRealtime();
        return false;
    }

    private void loginUsingToken(AccessToken token) {
        Profile profile = Profile.getCurrentProfile();

        final String userId = token.getUserId();
        final String accessToken = token.getToken();

        User user = new User();
        user.setFbId(userId);
        user.setFbToken(accessToken);
        if (null != profile) {
            String firstName = profile.getFirstName();
            if (null != firstName) {
                user.setFbUserName(firstName);
            }
        }
        switchScreenOnLoginSuccess(user);
    }

    private void switchScreenOnLoginSuccess(User user) {
        if (null != super.switchFragmentsListener && null == user) {
            switchFragmentsListener.onNextScreen();
        } else if (null != super.switchFragmentsListener) {
            switchFragmentsListener.onNextScreen(user);
        }
    }

    private boolean isFieldsValidationCorrect() {
        return Utils.validateEdittext(loginField, "Please enter login")
                && Utils.validateEdittext(passwordField, "Please enter password");
    }

    @OnClick(R.id.sign_in_button)
    void onSignInButtonClicked() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String googlePlusDisplayName = account.getDisplayName();
            User user = new User();
            user.setGPlusDisplayName(googlePlusDisplayName);

            switchScreenOnLoginSuccess(user);
        } else {
            //signed out, show unauthenticated UI
        }
    }

    @OnClick(R.id.forgot_password)
    void onForgotPasswordClicked() {
        if (loginFragmentListener != null) {
            loginFragmentListener.onForgotPasswordClicked();
        }
    }

    @OnClick(R.id.register)
    void onRegisterClicked() {
        if (loginFragmentListener != null) {
            loginFragmentListener.onRegisterClicked();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (accessTokenTracker != null) {
            accessTokenTracker.stopTracking();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface LoginFragmentListener extends SwitchFragmentsListener {
        void onForgotPasswordClicked();

        void onRegisterClicked();
    }
}
