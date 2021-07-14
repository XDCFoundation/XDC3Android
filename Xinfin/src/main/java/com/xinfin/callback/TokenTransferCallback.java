package com.xinfin.callback;

import com.xinfin.Model.TokenTransferResponse;

import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.Optional;

public interface TokenTransferCallback {
    void success( TokenTransferResponse tokenApiModel);
    void success(String message);
    void failure(Throwable t);

    void failure(String message);
}
