package xinfin.sdk.callback;


import xinfin.sdk.Model.WalletData;

public interface CreateAccountCallback
{
    void success( WalletData walletData);




    void failure(Throwable t);

    void failure(String message);
}
