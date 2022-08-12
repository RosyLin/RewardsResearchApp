package com.llbean.salestrans.rewardsresearch;

public class CustomerAddressDetails {
	private String addressLine1, cityName, stateProvince, countryId, activityDate, auditTimestamp, 
	addressId, postalCode, webRegistered, active; 

	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}
	public String getAuditTimestamp() {
		return auditTimestamp;
	}
	public void setAuditTimestamp(String auditTimestamp) {
		this.auditTimestamp = auditTimestamp;
	}
	public String isWebRegistered() {
		return webRegistered;
	}
	public void setWebRegistered(String webRegistered) {
		this.webRegistered = webRegistered;
	}
	public String isActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	} 
}
