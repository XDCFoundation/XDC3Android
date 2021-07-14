package com.xinfin.callback;

import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.Model.TokenTransferResponse;

public interface TokenDetailCallback
{
    void success( TokenDetailsResponse tokenApiModel);




    void failure(Throwable t);

    void failure(String message);
}