package com.xinfin.Web;

import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.Model.TokenTransferResponse;
import com.xinfin.callback.TokenDetailCallback;
import com.xinfin.callback.TokenTransferCallback;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip20.generated.ERC20;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Flowables;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;


public class Web3jClass {
    Web3j web3;
    public static Web3jClass instance;
    ERC20 javaToken;
    BigInteger allowance, decimal, totalSupply, balance;
    String symbol, name;
    TokenDetailsResponse tokenResponse;

    public static Web3jClass getInstance() {
        if (instance == null)
            instance = new Web3jClass();

        return instance;
    }


    public Boolean isWeb3jConnected() {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                //Connected
                return true;
            } else {
                //Show Error
                return false;
            }
        } catch (
                Exception e) {
            //Show Error
            return false;
        }
    }

    public void getTokenoinfo(String token_address, TokenDetailCallback tokenDetailCallback) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                javaToken = null;
                try {
                    javaToken = ERC20.load(token_address, web3, creds, new DefaultGasProvider());
                    getinfo(javaToken, token_address, tokenDetailCallback);
                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    tokenDetailCallback.failure(exception.getMessage());
                }

            } else {

                //Show Error
                tokenDetailCallback.failure("Connection has been failed");
            }
        } catch (
                Exception e) {
            tokenDetailCallback.failure(e.getMessage());
//Show Error

        }


    }

    public void getinfo(ERC20 javaToken, String token_address, TokenDetailCallback tokenDetailCallback) {
        try {
            allowance = javaToken.allowance(token_address, "0x2e550836caaa79884f36e78626363f59ca50e96e").send();
            balance = javaToken.balanceOf(token_address).send();
            symbol = javaToken.symbol().send();
            totalSupply = javaToken.totalSupply().send();
            name = javaToken.name().send();
            decimal = javaToken.decimals().send();
            String contract = javaToken.getContractAddress();

            int transfer = gettransfercount(javaToken);


            tokenResponse = new TokenDetailsResponse();
            tokenResponse.setAllowance(allowance);
            tokenResponse.setBalance(balance);
            tokenResponse.setSymbol(symbol);
            tokenResponse.setTotalSupply(totalSupply);
            tokenResponse.setName(name);
            tokenResponse.setSpender_address(token_address);
            tokenResponse.setDecimal(decimal);
            tokenResponse.setcontract(contract);
            tokenDetailCallback.success(tokenResponse);

        } catch (Exception exception) {
            exception.printStackTrace();
            tokenDetailCallback.failure(exception.getMessage());
        }

    }

    private int gettransfercount(ERC20 javaToken) {


        return 0;
    }

    @SuppressWarnings("NewApi")
    public void TransferToken(String PRIVATE_KEY_TRANSACTION, String FROM_ADDRESS, String TO_ADDRESS, BigInteger value, Long gasprice, Long gaslimit, TokenTransferCallback tokenCallback) {
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
                            FROM_ADDRESS, DefaultBlockParameterName.LATEST).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                    tokenCallback.failure(e.getMessage());
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                    tokenCallback.failure(e.getMessage());
                }

                BigInteger nonce = ethGetTransactionCount.getTransactionCount();


                RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                        nonce, BigInteger.valueOf(gasprice), BigInteger.valueOf(gaslimit), TO_ADDRESS, value);

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create(PRIVATE_KEY_TRANSACTION));
                String hexValue = Numeric.toHexString(signedMessage);


                EthSendTransaction ethSendTransaction = null;
                try {
                    ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                    tokenCallback.failure(e.getMessage());
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                    tokenCallback.failure(e.getMessage());
                }

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


                            tokenCallback.success(tokenTransferResponse);
                        } else {
                            tokenCallback.success(transactionHash);
                            //tokenCallback.success("Token has been transfered and Transation has been approved ");
                        }

                    } else {
                        tokenCallback.failure("Failed");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    tokenCallback.failure(ioException.getMessage());
                }
            } else {

                tokenCallback.failure("Failed");
            }
        } catch (
                Exception e) {

            tokenCallback.failure(e.getMessage());
//Show Error

        }


        //return null;

    }


    public void tranferfrom() {


        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                javaToken = null;
                try {
                    javaToken = ERC20.load(AppConstants.FROM_ADDRESS, web3, creds, new DefaultGasProvider());
                    TransactionReceipt transfer = javaToken.transferFrom(AppConstants.FROM_ADDRESS, AppConstants.TO_ADDRESS, BigInteger.valueOf(1000000000000L)).send();
                        ;
                        // ArrayList<ERC20.TransferEventResponse> responses = (ArrayList<ERC20.TransferEventResponse>) javaToken.getTransferEvents(transfer);





                } catch (Exception exception) {
                    exception.printStackTrace();
                    System.err.printf("hash=%s from=%s to=%s amount=%s%n", exception.getMessage());
                }

            }
        } catch (
                Exception e) {

//Show Error

        }

    }

}
