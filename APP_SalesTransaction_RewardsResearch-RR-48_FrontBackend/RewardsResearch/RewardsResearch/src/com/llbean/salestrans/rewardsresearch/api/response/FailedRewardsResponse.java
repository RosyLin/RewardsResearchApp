package com.llbean.salestrans.rewardsresearch.api.response;

public class FailedRewardsResponse extends RewardsResponseDTO{

    public FailedRewardsResponse() {
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
