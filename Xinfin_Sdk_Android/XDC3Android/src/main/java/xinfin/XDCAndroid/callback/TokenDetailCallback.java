package xinfin.XDCAndroid.callback;


import xinfin.XDCAndroid.Model.TokenDetailsResponse;

public interface TokenDetailCallback {
    void success(TokenDetailsResponse tokenApiModel);


    void failure(Throwable t);

    void failure(String message);
}
