package com.llbean.salestrans.rewardsresearch.exception;

public class RewardsResearchCustomException extends Exception {

    private static final long serialVersionUID = -6466450483726305909L;

    public RewardsResearchCustomException() {
        super();
    }

    String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}