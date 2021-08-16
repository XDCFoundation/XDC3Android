package com.XDCAndroid.Model;

import java.io.Serializable;
import java.math.BigInteger;

public class TokenDetailsResponse implements Serializable
{

    private BigInteger allowance;
    public BigInteger getAllowance() {
        return allowance;
    }
    public void setAllowance(BigInteger param) {
        this.allowance = param;
    }

    private BigInteger decimal;
    public BigInteger getDecimal() {
        return decimal;
    }
    public void setDecimal(BigInteger param) {
        this.decimal = param;
    }

    private BigInteger totalSupply;
    public BigInteger getTotalSupply() {
        return totalSupply;
    }
    public void setTotalSupply(BigInteger param) {
        this.totalSupply = param;
    }

    private String symbol;
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String param) {
        this.symbol = param;
    }

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String param) {
        this.name = param;
    }

    private String spender_address;
    public String getSpender_address() {
        return spender_address;
    }
    public void setSpender_address(String param) {
        this.spender_address = param;
    }

    private String contract;
    public String getcontract() {
        return contract;
    }
    public void setcontract(String param) {
        this.contract = param;
    }

}
