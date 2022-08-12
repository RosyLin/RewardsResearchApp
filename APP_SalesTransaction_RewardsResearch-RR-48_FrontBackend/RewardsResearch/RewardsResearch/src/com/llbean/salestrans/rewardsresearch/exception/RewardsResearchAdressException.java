package com.llbean.salestrans.rewardsresearch.exception;

public class RewardsResearchAdressException extends Throwable{
    private static final long serialVersionUID = -6128798905418455713L;
    String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}