package xinfin.sdk;



import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;

import xinfin.sdk.Model.Token721DetailsResponse;
import xinfin.sdk.Model.TokenDetailsResponse;
import xinfin.sdk.callback.Token721DetailCallback;
import xinfin.sdk.contracts.src.main.java.XRC165;
import xinfin.sdk.contracts.src.main.java.XRC721;
import xinfin.sdk.contracts.src.main.java.XRC721Enumerable;
import xinfin.sdk.contracts.src.main.java.XRC721Metadata;


public class XDC721Client {
    Web3j web3;
    public static XDC721Client instance;
    XRC721 javaToken;
    BigInteger allowance, decimal, totalSupply, balance;
    String symbol, name;
    TokenDetailsResponse tokenResponse;
    private WalletFile wallet;

    public static XDC721Client getInstance() {
        if (instance == null)
            instance = new XDC721Client();

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

    public void getTokenoinfo(String tokenAddress, Token721DetailCallback tokenDetailCallback) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                javaToken = null;
                try {
                    XRC721Metadata javaToken = XRC721Metadata.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    getinfo(javaToken, tokenAddress, tokenDetailCallback);
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




    public void getinfo(XRC721Metadata javaToken, String tokenAddress, Token721DetailCallback tokenDetailCallback) {
        try {

            symbol = javaToken.symbol().send();
            name = javaToken.name().send();

            Token721DetailsResponse tokenResponse = new Token721DetailsResponse();
            tokenResponse.setSymbol(symbol);
            tokenResponse.setName(name);
            tokenResponse.setTokenAddress(tokenAddress);
            tokenDetailCallback.success(tokenResponse);

        } catch (Exception exception) {
            exception.printStackTrace();
            tokenDetailCallback.failure(exception.getMessage());
        }

    }

    public String getTokenUri(String tokenAddress, String tokenid) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                try {
                    XRC721Metadata javaToken1 = XRC721Metadata.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    String balance = javaToken1.tokenURI(BigInteger.valueOf(Long.parseLong(tokenid))).send();


                    return String.valueOf(balance);

                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return exception.getMessage();
                }

            } else {

                //Show Error
                return "Connection has been failed";
            }
        } catch (
                Exception e) {
            return e.getMessage();
//Show Error

        }


    }

    public String getBalance(String tokenAddress, String ownerAddress) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                try {
                    XRC721 javaToken1 = XRC721.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    BigInteger balance = javaToken1.balanceOf(ownerAddress).send();


                    return String.valueOf(balance);

                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return exception.getMessage();
                }

            } else {

                //Show Error
                return "Connection has been failed";
            }
        } catch (
                Exception e) {
            return e.getMessage();
//Show Error

        }


    }

    public String gettotalSupply(String tokenAddress) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                try {
                    XRC721Enumerable javaToken1 = XRC721Enumerable.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    BigInteger totalSupply = javaToken1.totalSupply().send();


                    return String.valueOf(totalSupply);

                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return exception.getMessage();
                }

            } else {

                //Show Error
                return "Connection has been failed";
            }
        } catch (
                Exception e) {
            return e.getMessage();
//Show Error

        }


    }

    public String gettokenByIndex(String tokenAddress, String index) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                try {
                    XRC721Enumerable javaToken1 = XRC721Enumerable.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    BigInteger token = javaToken1.tokenByIndex(new BigInteger(index)).send();


                    return String.valueOf(token);

                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return exception.getMessage();
                }

            } else {

                //Show Error
                return "Connection has been failed";
            }
        } catch (
                Exception e) {
            return e.getMessage();
//Show Error

        }


    }

    public String tokenOfOwnerByIndex(String tokenAddress, String ownerAddress,String index) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                try {
                    XRC721Enumerable javaToken1 = XRC721Enumerable.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    BigInteger token = javaToken1.tokenOfOwnerByIndex(ownerAddress,new BigInteger(index)).send();


                    return String.valueOf(token);

                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return exception.getMessage();
                }

            } else {

                //Show Error
                return "Connection has been failed";
            }
        } catch (
                Exception e) {
            return e.getMessage();
//Show Error

        }


    }


    public String getOwnerof(String tokenAddress, String tokenid) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                try {
                    XRC721 javaToken1 = XRC721.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                     String ownerOf =   javaToken1.ownerOf(BigInteger.valueOf(Long.parseLong(tokenid))).send();


                    return ownerOf;

                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return exception.getMessage();
                }

            } else {

                //Show Error
                return "Connection has been failed";
            }
        } catch (
                Exception e) {
            return e.getMessage();
//Show Error

        }


    }

    public boolean getsupportInterface(String tokenAddress,String interfaceID) {

        web3 = Web3j.build(new
                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                try {
                    XRC165 javaToken2 = XRC165.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    ByteBuffer b = ByteBuffer.allocate(4);
//                    0x80ac58cd
                    b.putInt(0x80ac58cd);
                    byte[] result = b.array();
                    Boolean supportInterface = javaToken2.supportsInterface(result).send();
                    return supportInterface;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return false;
                }
            } else {
                //Show Error
                return false;
            }
        } catch (
                Exception e) {
            return false;
//Show Error
        }

    }


    public String approve(String tokenAddress,String privatekey,String tokenid,String receiverAddress) throws Exception {
        web3 = Web3j.build(new
                HttpService(AppConstants.BASE_URL));
        //spender privatekey
        Credentials credentials = Credentials.create(privatekey);
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
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
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //receiver key - token id
        final Function function = new Function(
                "approve",
                Arrays.<Type>asList(new Address(receiverAddress),
                        new Uint256(BigInteger.valueOf(Long.parseLong(tokenid)))),
                Collections.<TypeReference<?>>emptyList());

        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        //token address
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                tokenAddress, encodedFunction);
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

    public String getApproved(String tokenAddress, String tokenId) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                try {
                    XRC721 javaToken1 = XRC721.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    String getApproved = javaToken1.getApproved(BigInteger.valueOf(Long.parseLong(tokenId))).send();
                    return getApproved;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return (exception.getMessage());
                }

            } else {

                //Show Error
                return "Connection has been failed";
            }
        } catch (
                Exception e) {
            return e.getMessage();

        }


    }


    public boolean isApprovedForAll(String tokenAddress,String ownerAddress,String OperatorAddress) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                try {
                    XRC721  javaToken = XRC721.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    Boolean isApproved = javaToken.isApprovedForAll(ownerAddress,OperatorAddress).send();

                    return isApproved;

                } catch (Exception exception) {
                    exception.printStackTrace();
                    return false;
                }

            } else {

                //Show Error
                return false;
            }
        } catch (
                Exception e) {
            return false;
//Show Error

        }

    }

    public String setApprovalForAll(String tokenAddress,String privatekey,String OperatorAddress,String booleanvalue) throws Exception {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //spender privatekey
        Credentials credentials = Credentials.create(privatekey);
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
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
        final Function function = new Function(
                "setApprovalForAll",
                Arrays.<Type>asList(new Address(OperatorAddress),
                        new Bool(Boolean.parseBoolean(booleanvalue))),
                Collections.<TypeReference<?>>emptyList());


        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                tokenAddress, encodedFunction);

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


    public String safeTransferFrom(String tokenAddress,String privatekey,String receiverAddress,String tokenid) throws Exception {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
        //spender privatekey
        Credentials credentials = Credentials.create(privatekey);
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
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
//        gasPrice = ethGasPrice.getGasPrice();
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(3000000L);
        final Function function = new Function(
                "safeTransferFrom",
                Arrays.<Type>asList(new Address(credentials.getAddress()),
                        new Address(receiverAddress),
                        new Uint256(Long.parseLong(tokenid))),
                Collections.<TypeReference<?>>emptyList());
        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, BigInteger.valueOf(3000000L), gasLimit,
                tokenAddress, encodedFunction);

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


    public String transferfrom(String tokenAddress,String privatekey,String receiverAddress,String tokenid ) throws Exception {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //spender privatekey
        Credentials credentials = Credentials.create(privatekey);
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
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
//        gasPrice = ethGasPrice.getGasPrice();
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(3000000L);
        final Function function = new Function(
                "transferFrom",
                Arrays.<Type>asList(new Address(credentials.getAddress()),
                        new Address(receiverAddress),
                        new Uint256(Long.parseLong(tokenid))),
                Collections.<TypeReference<?>>emptyList());


        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, BigInteger.valueOf(3000000L), gasLimit,
                tokenAddress, encodedFunction);

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
