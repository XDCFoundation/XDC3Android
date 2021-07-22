package xinfin.sdk.Web;



import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
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
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import xinfin.sdk.Model.TokenDetailsResponse;
import xinfin.sdk.Model.TokenTransferResponse;
import xinfin.sdk.contracts.src.main.java.org.web3j.contracts.eip20.generated.ERC20;
import xinfin.sdk.contracts.src.main.java.org.web3j.contracts.eip20.generated.HumanStandardToken;


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
                            "0x32f158af29c171392a1ff35a7387583ff4959053", DefaultBlockParameterName.LATEST).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                  //  tokenCallback.failure(e.getMessage());
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                   // tokenCallback.failure(e.getMessage());
                }

                BigInteger nonce = ethGetTransactionCount.getTransactionCount();


                RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                        nonce, BigInteger.valueOf(4000004), BigInteger.valueOf(50005), "0x73585ae0c1aa818db5f360ed734ffad68d9b2ef8",BigInteger.valueOf(105));

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create("0xe258e6b92739236070ca1baf6ee4ae63203237fec29249a96d122f24f18f7ded"));
                String hexValue = Numeric.toHexString(signedMessage);


                EthSendTransaction ethSendTransaction = null;
                try {
                    ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                   // tokenCallback.failure(e.getMessage());
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                    //tokenCallback.failure(e.getMessage());
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


                            //tokenCallback.success(tokenTransferResponse);
                        } else {
                           // tokenCallback.success(transactionHash);
                            //tokenCallback.success("Token has been transfered and Transation has been approved ");
                        }

                    } else {
                      //  tokenCallback.failure("Failed");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                   // tokenCallback.failure(ioException.getMessage());
                }
            } else {

               // tokenCallback.failure("Failed");
            }
        } catch (
                Exception e) {

           // tokenCallback.failure(e.getMessage());
//Show Error

        }




        //return null;

    }





    public static String transferERC20Token() throws ExecutionException, InterruptedException, IOException {
        Web3j web3j = Web3j.build(new HttpService(AppConstants.BASE_URL));
                 //Load the required documents for the transfer, with the private key
        Credentials credentials = Credentials.create("0xe258e6b92739236070ca1baf6ee4ae63203237fec29249a96d122f24f18f7ded");
                 // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount("0x32f158af29c171392a1ff35a7387583ff4959053", DefaultBlockParameterName.LATEST).sendAsync().get();
        if (ethGetTransactionCount == null) {
            return null;
        }
        nonce = ethGetTransactionCount.getTransactionCount();
        //gasPrice and gasLimit can be set manually
        BigInteger gasPrice;
        EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
        if (ethGasPrice == null) {
            return null;
        }
        gasPrice = ethGasPrice.getGasPrice();
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //ERC20 token contract method
       // value = value.multiply(value);
        Function function = new Function(
                "transfer",
                Arrays.asList(new Address("0x73585ae0c1aa818db5f360ed734ffad68d9b2ef8"), new Uint256(BigInteger.valueOf(10))),
                Collections.singletonList(new TypeReference<Type>() {
                }));
                 //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                "0xd3d1ea96362d2660d38c749c196370b5619a3620", encodedFunction);

        //Signature Transaction
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //Send the transaction
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        String hash = ethSendTransaction.getTransactionHash();
        if (hash != null) {
            return hash;
        }
        return null;
    }


    public  String approveERC20Token() throws ExecutionException, InterruptedException, IOException {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
        Credentials credentials = Credentials.create("0xe258e6b92739236070ca1baf6ee4ae63203237fec29249a96d122f24f18f7ded");
        // Get nonce, the number of transactions
        BigInteger nonce;
        //token owner - wallet
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount("0x32f158af29c171392a1ff35a7387583ff4959053", DefaultBlockParameterName.LATEST).sendAsync().get();
        if (ethGetTransactionCount == null) {
            return null;
        }
        nonce = ethGetTransactionCount.getTransactionCount();
        //gasPrice and gasLimit can be set manually
        BigInteger gasPrice;
        EthGasPrice ethGasPrice = web3.ethGasPrice().sendAsync().get();
        if (ethGasPrice == null) {
            return null;
        }
        gasPrice = ethGasPrice.getGasPrice();
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //ERC20 token contract method
        // value = value.multiply(value);
        //receiver wallet address
        Function function = new Function(
                "approve",
                Arrays.asList(new Address("0x73585ae0c1aa818db5f360ed734ffad68d9b2ef8"), new Uint256(BigInteger.valueOf(10))),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        //token address
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                "0xd3d1ea96362d2660d38c749c196370b5619a3620", encodedFunction);

        //Signature Transaction
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //Send the transaction
        EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
        String hash = ethSendTransaction.getTransactionHash();
        if (hash != null) {
            return hash;
        }
        return null;
    }


    public  String increaseAllownce() throws Exception {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
        Credentials credentials = Credentials.create("0xe258e6b92739236070ca1baf6ee4ae63203237fec29249a96d122f24f18f7ded");
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount("0x32f158af29c171392a1ff35a7387583ff4959053", DefaultBlockParameterName.LATEST).sendAsync().get();
        if (ethGetTransactionCount == null) {
            return null;
        }
        nonce = ethGetTransactionCount.getTransactionCount();
        //gasPrice and gasLimit can be set manually
        BigInteger gasPrice;
        EthGasPrice ethGasPrice = web3.ethGasPrice().sendAsync().get();
        if (ethGasPrice == null) {
            return null;
        }
        gasPrice = ethGasPrice.getGasPrice();
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //ERC20 token contract method
        // value = value.multiply(value);

        ERC20 javaToken = ERC20.load("0xd3d1ea96362d2660d38c749c196370b5619a3620", web3, credentials, new DefaultGasProvider());
       BigInteger allowance = javaToken.allowance("0x32f158af29c171392a1ff35a7387583ff4959053", "0x73585ae0c1aa818db5f360ed734ffad68d9b2ef8").send();

        allowance = allowance.add( BigInteger.valueOf(1610)) ;
        Function function = new Function(
                "approve",
                Arrays.asList(new Address("0x73585ae0c1aa818db5f360ed734ffad68d9b2ef8"), new Uint256(allowance)),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                "0xd3d1ea96362d2660d38c749c196370b5619a3620", encodedFunction);

        //Signature Transaction
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //Send the transaction
        EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
        String hash = ethSendTransaction.getTransactionHash();
        if (hash != null) {
            return hash;
        }
        return null;
    }


    public  String decreaseAllownce() throws Exception {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
        Credentials credentials = Credentials.create("0xe258e6b92739236070ca1baf6ee4ae63203237fec29249a96d122f24f18f7ded");
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount("0x32f158af29c171392a1ff35a7387583ff4959053", DefaultBlockParameterName.LATEST).sendAsync().get();
        if (ethGetTransactionCount == null) {
            return null;
        }
        nonce = ethGetTransactionCount.getTransactionCount();
        //gasPrice and gasLimit can be set manually
        BigInteger gasPrice;
        EthGasPrice ethGasPrice = web3.ethGasPrice().sendAsync().get();
        if (ethGasPrice == null) {
            return null;
        }
        gasPrice = ethGasPrice.getGasPrice();
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //ERC20 token contract method
        // value = value.multiply(value);

        ERC20 javaToken = ERC20.load("0xd3d1ea96362d2660d38c749c196370b5619a3620", web3, credentials, new DefaultGasProvider());
        BigInteger allowance = javaToken.allowance("0x32f158af29c171392a1ff35a7387583ff4959053", "0x73585ae0c1aa818db5f360ed734ffad68d9b2ef8").send();

        allowance = allowance.subtract( BigInteger.valueOf(123)) ;
        Function function = new Function(
                "approve",
                Arrays.asList(new Address("0x73585ae0c1aa818db5f360ed734ffad68d9b2ef8"), new Uint256(allowance)),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                "0xd3d1ea96362d2660d38c749c196370b5619a3620", encodedFunction);

        //Signature Transaction
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //Send the transaction
        EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
        String hash = ethSendTransaction.getTransactionHash();
        if (hash != null) {
            return hash;
        }
        return null;
    }

}
