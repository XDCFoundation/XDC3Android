package com.xinfin.callback;

import com.xinfin.Model.TokenDetailsResponse;

public interface CreateAccountCallback
{
    void success( TokenDetailsResponse tokenApiModel);




    void failure(Throwable t);

    void failure(String message);
}
