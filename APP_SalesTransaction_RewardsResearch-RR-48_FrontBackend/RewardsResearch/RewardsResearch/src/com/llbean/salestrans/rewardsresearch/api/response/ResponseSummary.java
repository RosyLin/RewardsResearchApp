package com.llbean.salestrans.rewardsresearch.api.response;

import java.lang.reflect.Array;

public class ResponseSummary {
    String registryConsumerId;
    String registryContextId;
    String originatingRequestor;
    String originatingUserName;
    int originatingTimestamp;
    String storeId;
    String terminalId;
    int requestReceivedTimestamp;
    int processingCompleteTimestamp;
    String processingTimeMilliseconds;
    String requestorAuditId;
    int serviceAuditId;
    int unitOfWorkId;
    String responseCode;
    String[] responseDetail;

    public String getRegistryConsumerId() {
        return registryConsumerId;
    }

    public void setRegistryConsumerId(String registryConsumerId) {
        this.registryConsumerId = registryConsumerId;
    }

    public String getRegistryContextId() {
        return registryContextId;
    }

    public void setRegistryContextId(String registryContextId) {
        this.registryContextId = registryContextId;
    }

    public String getOriginatingRequestor() {
        return originatingRequestor;
    }

    public void setOriginatingRequestor(String originatingRequestor) {
        this.originatingRequestor = originatingRequestor;
    }

    public String getOriginatingUserName() {
        return originatingUserName;
    }

    public void setOriginatingUserName(String originatingUserName) {
        this.originatingUserName = originatingUserName;
    }

    public int getOriginatingTimestamp() {
        return originatingTimestamp;
    }

    public void setOriginatingTimestamp(int originatingTimestamp) {
        this.originatingTimestamp = originatingTimestamp;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public int getRequestReceivedTimestamp() {
        return requestReceivedTimestamp;
    }

    public void setRequestReceivedTimestamp(int requestReceivedTimestamp) {
        this.requestReceivedTimestamp = requestReceivedTimestamp;
    }

    public int getProcessingCompleteTimestamp() {
        return processingCompleteTimestamp;
    }

    public void setProcessingCompleteTimestamp(int processingCompleteTimestamp) {
        this.processingCompleteTimestamp = processingCompleteTimestamp;
    }

    public String getProcessingTimeMilliseconds() {
        return processingTimeMilliseconds;
    }

    public void setProcessingTimeMilliseconds(String processingTimeMilliseconds) {
        this.processingTimeMilliseconds = processingTimeMilliseconds;
    }

    public String getRequestorAuditId() {
        return requestorAuditId;
    }

    public void setRequestorAuditId(String requestorAuditId) {
        this.requestorAuditId = requestorAuditId;
    }

    public int getServiceAuditId() {
        return serviceAuditId;
    }

    public void setServiceAuditId(int serviceAuditId) {
        this.serviceAuditId = serviceAuditId;
    }

    public int getUnitOfWorkId() {
        return unitOfWorkId;
    }

    public void setUnitOfWorkId(int unitOfWorkId) {
        this.unitOfWorkId = unitOfWorkId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String[] getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(String[] responseDetail) {
        this.responseDetail = responseDetail;
    }
}