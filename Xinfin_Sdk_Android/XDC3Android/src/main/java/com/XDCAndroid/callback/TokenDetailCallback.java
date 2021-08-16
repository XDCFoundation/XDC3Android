package com.XDCAndroid.callback;


import com.XDCAndroid.Model.TokenDetailsResponse;

public interface TokenDetailCallback {
    void success(TokenDetailsResponse tokenApiModel);


    void failure(Throwable t);

    void failure(String message);
}
