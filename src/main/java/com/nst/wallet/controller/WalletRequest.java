package com.nst.wallet.controller;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable // Add this annotation
@Introspected // Keep this if you also need introspection
public class WalletRequest {
    private String userId;
    private String userToken;
    private String message;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getMesage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }   
    public String getUserToken() {
        return userToken;
    }
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}