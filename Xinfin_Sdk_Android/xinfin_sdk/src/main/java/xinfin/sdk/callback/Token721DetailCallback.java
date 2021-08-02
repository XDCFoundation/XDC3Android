package xinfin.sdk.callback;


import xinfin.sdk.Model.Token721DetailsResponse;

public interface Token721DetailCallback {
    void success(Token721DetailsResponse tokenApiModel);


    void failure(Throwable t);

    void failure(String message);
}
