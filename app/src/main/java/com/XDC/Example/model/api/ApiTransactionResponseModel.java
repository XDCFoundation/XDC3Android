package com.XDC.Example.model.api;

import com.google.gson.annotations.SerializedName;

public class ApiTransactionResponseModel {


    /**
     * jsonrpc : 2.0
     * id : 1
     * result : 0xb523dd5085d32bfbe0d8332a71f4c03ffea961e633e1f472fa5f01a26df86a38
     */

    @SerializedName("jsonrpc")
    private String jsonrpc;
    @SerializedName("id")
    private int id;
    @SerializedName("result")
    private String result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
