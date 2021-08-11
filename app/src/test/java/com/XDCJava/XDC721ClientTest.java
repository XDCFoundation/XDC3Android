package com.XDCJava;

import android.util.Log;

import junit.framework.TestCase;

public class XDC721ClientTest extends TestCase {

    private static final String TAG =  "testXRC721" ;

    public void testGettokenByIndex()
    {
        String tokenid =   XDC721Client.gettokenByIndex("0xf6b4102b25756a33a90f8f192c74e53d22a9628e","0");
        assertEquals(tokenid,"19");
    }

    public void testApprove()
    {
        try {
         String txhash =   XDC721Client.approve("0xf6b4102b25756a33a90f8f192c74e53d22a9628e","0x7415687b87e1f1bcf55b26e0ddb086f9eb1d31cdc2059a296ce05de51b043258","19","0x7af2f691d78c51d0f0368a3c367634c9d76cfe7d");
            Log.e(TAG, "testApprove: "+txhash);
         assertNotNull(txhash);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testTokenOfOwnerByIndex()
    {
        String tokenid =   XDC721Client.tokenOfOwnerByIndex("0xf6b4102b25756a33a90f8f192c74e53d22a9628e","0x517b6be05e5c50df6876906909c23ad130476cc7","0");
        assertEquals(tokenid,"19");
    }

    public void testGetTokenoinfo()
    {
    }

    public void testGetTokenUri() {
    }

    public void testGetBalance() {
    }

    public void testGettotalSupply() {
    }

    public void testTestGettokenByIndex() {
    }

    public void testGetOwnerof() {
    }

    public void testGetsupportInterface() {
    }

    public void testGetApproved() {
    }

    public void testIsApprovedForAll() {
    }

    public void testSetApprovalForAll() {
    }

    public void testSafeTransferFrom() {
    }

    public void testTransferfrom() {
    }
}