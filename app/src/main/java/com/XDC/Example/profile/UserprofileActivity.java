package com.XDC.Example.profile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.XDC.Example.XDCmethods.AddToken;
import com.XDC.Example.XDCmethods.TransferXDCActivity;
import com.XDC.Example.XDCmethods.XDC20Details;
import com.XDC.Example.utils.SharedPreferenceHelper;
import com.XDC.Example.utils.Utility;
import com.XDC.R;
import com.XDCJava.Model.TokenDetailsResponse;
import com.XDCJava.Model.WalletData;
import com.XDCJava.XDC20Client;
import com.XDCJava.callback.TokenDetailCallback;
import com.google.gson.Gson;

import java.math.BigInteger;


public class UserprofileActivity extends AppCompatActivity {

    TextView text_accountaddress, text_XDC, transfer_XDC, text_addtoken, text_tokenbalance,text_tokensymbol;
    WalletData user_wallet;
    TokenDetailsResponse tokenDetail;
    ImageView img_copy;
    LinearLayout lin_token_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        text_accountaddress = findViewById(R.id.text_accountaddress);
        img_copy = findViewById(R.id.img_copy);
        text_XDC = findViewById(R.id.text_XDC);
        text_addtoken = findViewById(R.id.text_addtoken);
        transfer_XDC = findViewById(R.id.transfer_XDC);
        lin_token_info = findViewById(R.id.lin_token_info);
        text_tokenbalance = findViewById(R.id.text_tokenbalance);
        text_tokensymbol = findViewById(R.id.text_tokensymbol);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        user_wallet = Utility.getProfile(UserprofileActivity.this);
        tokenDetail = Utility.gettokeninfo(UserprofileActivity.this);

        text_accountaddress.setText(user_wallet.getAccountAddress());
        Log.e("walletAddress", user_wallet.getAccountAddress());
        getXDCBalance();
        displaytokenInfo();
        text_addtoken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserprofileActivity.this, AddToken.class);
                startActivity(intent);
                finish();
            }
        });


        img_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("wallet Address", user_wallet.getAccountAddress());
                clipboard.setPrimaryClip(clip);
            }
        });

        transfer_XDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserprofileActivity.this, TransferXDCActivity.class);
                startActivity(intent);
            }
        });

        lin_token_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(UserprofileActivity.this, XDC20Details.class);
                startActivity(intent);

            }
        });


    }

    private void displaytokenInfo() {

        if (tokenDetail != null && tokenDetail.getToken_address() != null && tokenDetail.getToken_address().length() > 0) {
            lin_token_info.setVisibility(View.VISIBLE);
            text_tokenbalance.setText(XDC20Client.getInstance().getBalance(tokenDetail.getToken_address(),user_wallet.getAccountAddress()));
            XDC20Client.getInstance().getTokenoinfo(tokenDetail.getToken_address(), new TokenDetailCallback() {
                @Override
                public void success(TokenDetailsResponse tokendetail)
                {
                    text_tokensymbol.setText(tokendetail.getSymbol());
                    tokendetail.setBalance(text_tokenbalance.getText().toString());
                    Gson gson = new Gson();
                    String json = gson.toJson(tokenDetail);
                    SharedPreferenceHelper.setSharedPreferenceString(UserprofileActivity.this, "tokeninfo", json);
                }

                @Override
                public void failure(Throwable t) {

                }

                @Override
                public void failure(String message) {

                }
            });





        } else {
            lin_token_info.setVisibility(View.GONE);
        }
    }

    private void getXDCBalance() {
        text_XDC.setText(XDC20Client.getInstance().getXdcBalance(user_wallet.getAccountAddress()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getXDCBalance();
    }
}