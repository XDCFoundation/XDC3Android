package com.xinfin.Web;

import com.xinfin.Model.TokenResponse;
import com.xinfin.Model.TokenTransferResponse;
import com.xinfin.callback.TokenCallback;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip20.generated.ERC20;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.datatypes.Event;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Web3jClass {
    Web3j web3;
    public static Web3jClass instance;
    ERC20 javaToken;
    BigInteger allowance, decimal, totalSupply, balance;
    String symbol, name;
    TokenResponse tokenResponse;

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

    public TokenResponse getTokenoinfo(String token_address) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                javaToken = null;
                try {
                    javaToken = ERC20.load(token_address, web3, creds, new DefaultGasProvider());
                    return getinfo(javaToken, token_address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            } else {

                //Show Error
                return null;
            }
        } catch (
                Exception e) {

//Show Error

        }


        return null;
    }

    private TokenResponse getinfo(ERC20 javaToken, String token_address) {
        try {
            allowance = javaToken.allowance(token_address, "0x2e550836caaa79884f36e78626363f59ca50e96e").send();
            balance = javaToken.balanceOf(token_address).send();
            symbol = javaToken.symbol().send();
            totalSupply = javaToken.totalSupply().send();
            name = javaToken.name().send();
            decimal = javaToken.decimals().send();

            tokenResponse = new TokenResponse();
            tokenResponse.setAllowance(allowance);
            tokenResponse.setBalance(balance);
            tokenResponse.setSymbol(symbol);
            tokenResponse.setTotalSupply(totalSupply);
            tokenResponse.setName(name);
            tokenResponse.setSpender_address(token_address);
            tokenResponse.setDecimal(decimal);

            return tokenResponse;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

    }

    @SuppressWarnings("NewApi")
    public String TransferToken(String PRIVATE_KEY_TRANSACTION, String FROM_ADDRESS, String TO_ADDRESS, BigInteger value, Long gasprice, Long gaslimit, TokenCallback tokenCallback) {
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
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    tokenCallback.failure(ioException.getMessage());
                }
            } else {

                tokenCallback.failure("Failed");
            }
        } catch (
                Exception e)
        {

            tokenCallback.failure(e.getMessage());
//Show Error

        }


        //return null;
        return null;
    }


    public void tranferfrom() throws Exception {
        Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
        javaToken = null;
        javaToken = ERC20.load(AppConstants.FROM_ADDRESS, web3, creds, new DefaultGasProvider());
        TransactionReceipt receipt = javaToken.transferFrom(AppConstants.FROM_ADDRESS, AppConstants.TO_ADDRESS, BigInteger.valueOf(1000)).send();
        EthFilter ethFilter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, javaToken.getContractAddress());
        EthLog ethLog = web3.ethGetLogs(ethFilter).send();

        ethFilter.addSingleTopic(EventEncoder.encode((Event) javaToken.getTransferEvents(receipt)));
        EthLog eventLog = web3.ethGetLogs(ethFilter).send();
        List<EthLog.LogResult> logs = eventLog.getLogs();


    }
}
