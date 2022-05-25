package com.XDC.Example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.XDC.Example.utils.Utility;
import com.XDC.Example.wallet.ImportorCreateActivity;
import com.XDC.R;
import com.XDCJava.Model.WalletData;


public class SplashScreen extends Activity {

    private static final int SPLASH_TIME_OUT = 500;
    private WalletData user_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        user_wallet = Utility.getProfile(SplashScreen.this);
        new Handler().postDelayed(() -> {
            if (user_wallet != null && user_wallet.getAccountAddress() != null && user_wallet.getAccountAddress().length() > 0) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashScreen.this, ImportorCreateActivity.class));
            }
            finish();
        }, SPLASH_TIME_OUT);

    }


}