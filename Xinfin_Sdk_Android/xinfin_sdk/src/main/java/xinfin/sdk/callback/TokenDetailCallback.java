package xinfin.sdk.callback;


import xinfin.sdk.Model.TokenDetailsResponse;

public interface TokenDetailCallback {
    void success(TokenDetailsResponse tokenApiModel);


    void failure(Throwable t);

    void failure(String message);
}
