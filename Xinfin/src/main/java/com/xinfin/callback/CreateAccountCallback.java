package com.xinfin.callback;

import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.Model.WalletData;

public interface CreateAccountCallback
{
    void success( WalletData walletData);




    void failure(Throwable t);

    void failure(String message);
}
