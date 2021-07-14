package xinfin.sdk.model.api;

import com.google.gson.annotations.SerializedName;

public class ResultDataResponseModel {

    @SerializedName("_id")
    private String id;
    @SerializedName("hash")
    private String hash;
    @SerializedName("__v")
    private int v;
    @SerializedName("blockNumber")
    private int blockNumber;
    @SerializedName("contract")
    private String contract;
    @SerializedName("from")
    private String from;
    @SerializedName("method")
    private String method;
    @SerializedName("timestamp")
    private int timestamp;
    @SerializedName("to")
    private String to;
    @SerializedName("value")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
