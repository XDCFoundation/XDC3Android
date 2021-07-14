package com.xinfin.Web;

import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.Model.TokenTransferResponse;
import com.xinfin.callback.TokenDetailCallback;
import com.xinfin.callback.TokenTransferCallback;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip20.generated.ERC20;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Web3jClassdemo {
    Web3j web3;
    public static Web3jClassdemo instance;
    ERC20 javaToken;
    BigInteger allowance, decimal, totalSupply, balance;
    String symbol, name;
    TokenDetailsResponse tokenResponse;

    public static Web3jClassdemo getInstance() {
        if (instance == null)
            instance = new Web3jClassdemo();

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
                Credentials creds = Credentials.create(AppConstants.PRIVATE_KEY);
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



    public void tranferfrom() throws Exception {


     /*   web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                Credentials creds = Credentials.create(AppConstants.PRIVATE_KEY);
                javaToken = null;
                try {
                    javaToken = ERC20.load(AppConstants.FROM_ADDRESS, web3, creds, new DefaultGasProvider());
                   // TransactionReceipt transfer = javaToken.transferFrom(AppConstants.FROM_ADDRESS, AppConstants.TO_ADDRESS, BigInteger.valueOf(1000000000000L)).send();
                   // ArrayList<ERC20.TransferEventResponse> responses = (ArrayList<ERC20.TransferEventResponse>) javaToken.getTransferEvents(transfer);

                } catch (Exception exception)
                {
                    exception.printStackTrace();
                    System.err.printf("hash=%s from=%s to=%s amount=%s%n",exception.getMessage());
                }

            }
        } catch (
                Exception e) {

//Show Error

        }

       *//* try {

            TransactionReceipt receipt = javaToken.transferFrom(AppConstants.FROM_ADDRESS, AppConstants.TO_ADDRESS, BigInteger.valueOf(1000)).send();

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
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    });

        } catch (Exception exception) {
            exception.printStackTrace();
        }*//*
*//*

        javaToken.transferFrom(AppConstants.FROM_ADDRESS, AppConstants.TO_ADDRESS, BigInteger.valueOf(1000)).flowable();
        javaToken.transferEventFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
                .subscribe(tx -> {
                    String toAddress = tx._to.toString();
                    String fromAddress = tx._from.toString();
                    String txHash = tx._value.toString();
                    String log = tx.log.toString();
                });
*//*

*//*

        // Event definition
           Event MY_EVENT = new Event("MyEvent", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Uint8>(false) {}));

// Event definition hash
           String MY_EVENT_HASH = EventEncoder.encode(MY_EVENT);

// Filter
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST,javaToken.getContractAddress());



// Pull all the events for this contract
        web3.ethLogFlowable(filter).subscribe(log ->
        {
            @SuppressWarnings("CheckResult") List<Type> args = FunctionReturnDecoder.decode(
                    log.getData(), MY_EVENT.getParameters());
            String eventHash = log.getTopics().get(0); // Index 0 is the event definition hash

            if(eventHash.equals(MY_EVENT_HASH)) { // Only MyEvent. You can also use filter.addSingleTopic(MY_EVENT_HASH)
                // address indexed _arg1
                Address arg1 = (Address) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(1), new TypeReference<Address>() {});
                // bytes32 indexed _arg2
                Bytes32 arg2 = (Bytes32) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(2), new TypeReference<Bytes32>() {});
                // uint8 _arg3
                Uint8 arg3 = (Uint8) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(3), new TypeReference<Uint8>() {});
            }

        });

*//*


        TransactionReceipt receipt = javaToken.transfer(toAddress, amount).send();

        javaToken.transferFrom(AppConstants.FROM_ADDRESS, AppConstants.TO_ADDRESS, BigInteger.valueOf(1000)).flowable();
        EthFilter filter = new EthFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                javaToken.getContractAddress());
        Event event = new Event("transferFrom",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        }, new TypeReference<Uint256>(false) {
                        }
                )
        );

        String topicData = EventEncoder.encode(event);
        filter.addSingleTopic(topicData);
        System.out.println(topicData);

        web3.ethLogFlowable(filter).subscribe(log -> {
            System.out.println(log.getBlockNumber());
            System.out.println(log.getTransactionHash());
            List<String> topics = log.getTopics();
            for (String topic : topics) {
                System.out.println(topic);
            }
        });
*/
    }
}
