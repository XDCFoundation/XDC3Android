package com.xinfin.callback;

import com.xinfin.Model.Token721DetailsResponse;
import com.xinfin.Model.TokenDetailsResponse;

public interface Token721DetailCallback {
    void success(Token721DetailsResponse tokenApiModel);


    void failure(Throwable t);

    void failure(String message);
}
