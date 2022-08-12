package com.llbean.salestrans.rewardsresearch.api.response;

public class RewardsResponseDTO {
    ResponseSummary responseSummary;

    public ResponseSummary getResponseSummary() {
        return responseSummary;
    }

    public void setResponseSummary (ResponseSummary responseSummary) {
        this.responseSummary = responseSummary;
    }

    RewardsBalance rewardsBalance;

    public RewardsBalance getRewardsBalance() {
        return rewardsBalance;
    }

    public void setRewardsBalance(RewardsBalance rewardsBalance) {
        this.rewardsBalance = rewardsBalance;
    }
}