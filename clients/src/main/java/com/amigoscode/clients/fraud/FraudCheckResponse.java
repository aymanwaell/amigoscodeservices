package com.amigoscode.clients.fraud;

public class FraudCheckResponse {
    private Boolean isFraudster;

    public FraudCheckResponse() {
    }

    public FraudCheckResponse(Boolean isFraudster) {
        this.isFraudster = isFraudster;
    }
    public boolean isFraudster() {
        return isFraudster;
    }
}
