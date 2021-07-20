package com.xinfin.Web;

import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.Model.TokenTransferResponse;
import com.xinfin.Model.TransactionResponse;
import com.xinfin.Model.WalletData;
import com.xinfin.callback.CreateAccountCallback;
import com.xinfin.callback.TokenDetailCallback;
import com.xinfin.callback.TokenTransferCallback;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip20.generated.ERC20;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip20.generated.HumanStandardToken;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;


public class Web3jTokenTransfer {
    Web3j web3;
    public static Web3jTokenTransfer instance;
    ERC20 javaToken;
    HumanStandardToken humanStandardToken;
    BigInteger allowance, decimal, totalSupply, balance;
    String symbol, name;
    TokenDetailsResponse tokenResponse;
    private WalletFile wallet;

    public static Web3jTokenTransfer getInstance() {
        if (instance == null)
            instance = new Web3jTokenTransfer();

        return instance;
    }


    @SuppressWarnings("NewApi")
    public void TransferToken() {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {

                // web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);
                // Log.e("response", web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);


                EthGetTransactionCount ethGetTransactionCount = null;
                try {
                    ethGetTransactionCount = web3.ethGetTransactionCount(
                            "0x5543f72F0bDB8B38453478403148Fb5E4AF49B23", DefaultBlockParameterName.LATEST).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                   // tokenCallback.failure(e.getMessage());
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                   // tokenCallback.failure(e.getMessage());
                }

                BigInteger nonce = ethGetTransactionCount.getTransactionCount();




                RawTransaction rawTransaction = RawTransaction.createTransaction(
                        nonce, BigInteger.valueOf(4000004), BigInteger.valueOf(50005), "0x63b32225813a3f2b877d77094d25f7ce6653b4b5", "0");

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create("0xbd6b2f02f90e4fd438af6b3fb636cc6912a8b384bb4767487d191c3dfe9713ae"));
                String hexValue = Numeric.toHexString(signedMessage);


                EthSendTransaction ethSendTransaction = null;
                try {
                    ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                  //  tokenCallback.failure(e.getMessage());
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                  //  tokenCallback.failure(e.getMessage());
                }



//                web3.transf
//
//                web3.methods.transfer(toAddress, 123).send({from: txSenderAddress}, (error, transactionHash) => {
//
//                });


                try {
                    String transactionHash = ethSendTransaction.getTransactionHash();
                    EthGetTransactionReceipt transactionReceipt =
                            web3.ethGetTransactionReceipt(transactionHash).send();


                    if (transactionHash != null && transactionHash.length() > 0) {
                        if (transactionReceipt.getTransactionReceipt().isPresent()) {
                            TransactionReceipt receipt_data = transactionReceipt.getResult();
                            TokenTransferResponse tokenTransferResponse = new TokenTransferResponse();
                            tokenTransferResponse.setBlockHash(receipt_data.getBlockHash());
                            tokenTransferResponse.setBlockNumber(receipt_data.getBlockNumberRaw());
                            tokenTransferResponse.setTransactionHash(receipt_data.getTransactionHash());
                            tokenTransferResponse.setStatus(receipt_data.getStatus());
                            tokenTransferResponse.setFrom(receipt_data.getFrom());
                            tokenTransferResponse.setTo(receipt_data.getTo());
                            tokenTransferResponse.setContractAddress(receipt_data.getContractAddress());
                            tokenTransferResponse.setGasUsed(receipt_data.getGasUsedRaw());


                          //  tokenCallback.success(tokenTransferResponse);
                        } else {
                           // tokenCallback.success(transactionHash);
                            //tokenCallback.success("Token has been transfered and Transation has been approved ");
                        }

                    } else {
                        //tokenCallback.failure("Failed");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                  //  tokenCallback.failure(ioException.getMessage());
                }
            } else {

               // tokenCallback.failure("Failed");
            }
        } catch (
                Exception e) {

            //tokenCallback.failure(e.getMessage());
//Show Error

        }




        //return null;

    }


}
