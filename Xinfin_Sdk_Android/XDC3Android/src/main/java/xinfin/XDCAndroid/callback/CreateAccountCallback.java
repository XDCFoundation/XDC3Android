package xinfin.XDCAndroid.callback;


import xinfin.XDCAndroid.Model.WalletData;

public interface CreateAccountCallback
{
    void success( WalletData walletData);




    void failure(Throwable t);

    void failure(String message);
}
