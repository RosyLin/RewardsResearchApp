package com.llbean.salestrans.rewardsresearch.api.response;

public class RewardsBalance {
    int rewardsTotalAmount;
    RewardsAccounts[] rewardsAccounts;
    String customerId;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getRewardsTotalAmount() {
        return rewardsTotalAmount;
    }

    public void setRewardsTotalAmount(int rewardsTotalAmount) {
        this.rewardsTotalAmount = rewardsTotalAmount;
    }

    public RewardsAccounts[] getRewardsAccounts() {
        return rewardsAccounts;
    }

    public void setRewardsAccounts(RewardsAccounts[] rewardsAccounts) {
        this.rewardsAccounts = rewardsAccounts;
    }








}