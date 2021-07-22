package xinfin.sdk.callback;



import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.Optional;

import xinfin.sdk.Model.TokenTransferResponse;

public interface TokenTransferCallback {
    void success( TokenTransferResponse tokenApiModel);
    void success(String message);
    void failure(Throwable t);

    void failure(String message);
}
