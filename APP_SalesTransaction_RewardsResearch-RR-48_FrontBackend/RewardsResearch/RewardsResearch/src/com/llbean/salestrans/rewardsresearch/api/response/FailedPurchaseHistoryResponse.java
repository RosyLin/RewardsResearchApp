package com.llbean.salestrans.rewardsresearch.api.response;

public class FailedPurchaseHistoryResponse extends PurchaseHistoryResponse{

	public FailedPurchaseHistoryResponse() {
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