package com.xinfin.Web;

import com.xinfin.Model.TokenResponse;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip20.generated.ERC20;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Web3jClass
 {
    Web3j web3;
    public static Web3jClass instance;
    ERC20 javaToken;
    BigInteger allowance,decimal,totalSupply,balance;
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

    public TokenResponse getTokenoinfo(String token_address)
    {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError())
            {

                web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);
                // Log.e("response", web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);


                EthGetTransactionCount ethGetTransactionCount = null;
                try {
                    ethGetTransactionCount = web3.ethGetTransactionCount(
                            AppConstants.TO_ADDRESS, DefaultBlockParameterName.LATEST).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                }

                BigInteger nonce = ethGetTransactionCount.getTransactionCount();


                RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                        nonce, BigInteger.valueOf(4000004), BigInteger.valueOf(50005), AppConstants.TO_ADDRESS, BigInteger.valueOf(1000000000000000000L));

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create(AppConstants.PRIVATE_KEY_TRANSACTION));
                String hexValue = Numeric.toHexString(signedMessage);


                EthSendTransaction ethSendTransaction = null;
                try {
                    ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                }

                String transactionHash = ethSendTransaction.getTransactionHash();
                try {
                    web3.ethGetTransactionByHash(transactionHash).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                }


                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                javaToken = null;

                try {
                    javaToken = ERC20.load(token_address, web3, creds, new DefaultGasProvider());






                  return  getinfo(javaToken,token_address);









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

    private TokenResponse getinfo(ERC20 javaToken, String token_address)
    {
        try {
            allowance = javaToken.allowance(token_address,"0x2e550836caaa79884f36e78626363f59ca50e96e").send();
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
        } catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }

    }

     @SuppressWarnings("NewApi")
     public void TransferTokenEvent()
     {
         web3 = Web3j.build(new

                 HttpService(AppConstants.BASE_URL));
         try {
             Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
             if (!clientVersion.hasError())
             {

                 web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);
                 // Log.e("response", web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);


                 EthGetTransactionCount ethGetTransactionCount = null;
                 try {
                     ethGetTransactionCount = web3.ethGetTransactionCount(
                             AppConstants.TO_ADDRESS, DefaultBlockParameterName.LATEST).sendAsync().get();
                 } catch (
                         ExecutionException e) {
                     e.printStackTrace();
                 } catch (
                         InterruptedException e) {
                     e.printStackTrace();
                 }

                 BigInteger nonce = ethGetTransactionCount.getTransactionCount();


                 RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                         nonce, BigInteger.valueOf(4000004), BigInteger.valueOf(50005), AppConstants.TO_ADDRESS, BigInteger.valueOf(1000000000000000000L));

                 byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create(AppConstants.PRIVATE_KEY_TRANSACTION));
                 String hexValue = Numeric.toHexString(signedMessage);


                 EthSendTransaction ethSendTransaction = null;
                 try {
                     ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
                 } catch (
                         ExecutionException e) {
                     e.printStackTrace();
                 } catch (
                         InterruptedException e) {
                     e.printStackTrace();
                 }

                 String transactionHash = ethSendTransaction.getTransactionHash();
                 try {
                     web3.ethGetTransactionByHash(transactionHash).sendAsync().get();
                 } catch (
                         ExecutionException e) {
                     e.printStackTrace();
                 } catch (
                         InterruptedException e) {
                     e.printStackTrace();
                 }


                 Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                 javaToken = null;

                 try {
                     javaToken = ERC20.load(AppConstants.FROM_ADDRESS, web3, creds, new DefaultGasProvider());

                     TransactionReceipt receipt  = javaToken.transferFrom(AppConstants.FROM_ADDRESS,AppConstants.TO_ADDRESS,BigInteger.valueOf(1000)).send();

                     List<ERC20.TransferEventResponse> events = javaToken.getTransferEvents(receipt);
                     events.forEach(event
                             -> System.out.println("from: " + event._from + ", to: " + event._to + ", value: " + event._value));

                     javaToken.transferEventFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
                             .subscribe(event -> {
                                 try {
                                     System.err.printf("hash=%s from=%s to=%s amount=%s%n",
                                             event.log.getTransactionHash(),
                                             event._from,
                                             event._to,
                                             event._value);
                                 }catch(Throwable e) {
                                     e.printStackTrace();
                                 }
                             });


                   //  return  getinfo(javaToken,token_address);









                 } catch (Exception exception) {
                     exception.printStackTrace();
                 }

             } else {

                 //Show Error
                // return null;
             }
         } catch (
                 Exception e) {

//Show Error

         }


         //return null;
     }
}
