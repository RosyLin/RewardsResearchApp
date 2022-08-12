package com.llbean.salestrans.rewardsresearch.api.response;

public class RewardsAccounts {

    String accountId;
    int rewardsBalanceAmount;
    String rewardsAvailableFlag;
    String actionCode;
    String actionStatus;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getRewardsBalanceAmount() {
        return rewardsBalanceAmount;
    }

    public void setRewardsBalanceAmount(int rewardsBalanceAmount) {
        this.rewardsBalanceAmount = rewardsBalanceAmount;
    }

    public String getRewardsAvailableFlag() {
        return rewardsAvailableFlag;
    }

    public void setRewardsAvailableFlag(String rewardsAvailableFlag) {
        this.rewardsAvailableFlag = rewardsAvailableFlag;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }



}