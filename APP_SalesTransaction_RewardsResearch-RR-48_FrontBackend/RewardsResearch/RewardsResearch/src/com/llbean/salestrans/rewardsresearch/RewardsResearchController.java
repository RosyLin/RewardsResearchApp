package com.llbean.salestrans.rewardsresearch;

import static com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.debug;
import static com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.log;
import com.llbean.salestrans.rewardsresearch.exception.*;

import com.llbean.salestrans.rewardsresearch.api.response.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.llbean.salestrans.rewardsresearch.exception.PortalServiceNotFoundException;
import com.llbean.salestrans.rewardsresearch.exception.PurchaseHistoryNotFoundException;


public class RewardsResearchController {

	//This method is in charge of getting the order details, prepare the call to order services, do some basic validations and then return the response we get
	public static PurchaseHistoryResponse getTransactionDetails(String aOrderId, String aTransactionEntryDate, 
			String aInventoryLocationId, String aRegisterId, String aTransactionId) {
		JsonObject purchaseHistoryResponse;
		String urlParameters;
		final String purchaseHistoryNotFound = "Transaction was not found in purchase history: ";

		//Creating a succesful response object
		SuccessfulPurchaseHistoryResponse successfulResponse = new SuccessfulPurchaseHistoryResponse();
		//Creating a failed response object, in case something goes wrong
		FailedPurchaseHistoryResponse failedResponse = new FailedPurchaseHistoryResponse();
		//Verify we received the parameters we need to send a request

		if (aOrderId != null) {
			//Send the request with what we need
			try {
				urlParameters = String.format("/%s/items", aOrderId);
				purchaseHistoryResponse = RewardsResearchDAO.callOrderServices(urlParameters, true);
				if (purchaseHistoryResponse.get("transaction").getAsJsonObject().get("transactionType").getAsString().equalsIgnoreCase("Retail")) {
					successfulResponse.setPurchaseHistoryResponse(purchaseHistoryResponse);
				} else {
					log("Direct channel orders cannot be updated at this time");
					FailedPurchaseHistoryResponse callError = new FailedPurchaseHistoryResponse();
					callError.setErrorMessage("Direct channel orders cannot be updated at this time");
					return callError;
				}

			} catch (PurchaseHistoryNotFoundException e) {
				// If a 404 was returned, process as a manual return
				log("Calling order services for details of the order returned a 404.");
				failedResponse.setErrorMessage(purchaseHistoryNotFound + e);
				return failedResponse;
			} catch (Exception e) {
				log("Received an error when trying to reach Purchase History");
				FailedPurchaseHistoryResponse callError = new FailedPurchaseHistoryResponse();
				callError.setErrorMessage(e.toString());
				return callError;
			}
		}
		else if (aTransactionEntryDate == null || aInventoryLocationId == null || aRegisterId == null || aTransactionId == null) {
			failedResponse.setErrorMessage("ERROR: Missing the required parameters to send the request");
			return failedResponse;
		}
		else {
			try {
				urlParameters = String.format("/%s/items?date=%s&storeid=%s&registerid=%s", aTransactionId, aTransactionEntryDate, aInventoryLocationId, aRegisterId );
				purchaseHistoryResponse = RewardsResearchDAO.callOrderServices(urlParameters, true);
				successfulResponse.setPurchaseHistoryResponse(purchaseHistoryResponse);
			} catch (PurchaseHistoryNotFoundException e) {
				// If a 404 was returned, send an error message 
				log("Calling order services for details of the order returned a 404.");
				failedResponse.setErrorMessage(purchaseHistoryNotFound + e);
				return failedResponse;
			} catch (Exception e) {           
				log("Received an error when trying to reach Purchase History");
				FailedPurchaseHistoryResponse callError = new FailedPurchaseHistoryResponse();
				callError.setErrorMessage(e.toString());
				return callError;
			}
		}

		// A successful response is being returned. Now get the most recent audit record.
		// Get the common order ID. One exists for every direct and retail order
		String orderId;
		JsonObject orderDocument = successfulResponse.getPurchaseHistoryResponse();
		try {
			if(orderDocument.getAsJsonObject("transaction").get("transactionType").getAsString().equalsIgnoreCase("Retail")) {
				// Path to common order ID for a retail transaction
				orderId = orderDocument.getAsJsonObject("transaction").get("orderId").getAsString();
			} else {
				// Path to common order ID for a direct chennel transaction
				orderId = orderDocument.get("id").getAsString();
			}
		} catch (Exception e) {
			String errorMessage = "There was an error trying to get the order ID from the purchase history response: " + e.getMessage();
			log(errorMessage);
			failedResponse.setErrorMessage(errorMessage);
			return failedResponse;
		}

		return successfulResponse;
	}

	public static RewardsResponseDTO getRewardsDetails(String aCustomerId){
		JsonObject rewardsResponse;
		String urlParameters;
		Gson gson = new Gson();
		final String rewardsNotFound = "Customer was not found in rewards: ";

		try {
			urlParameters = String.format(aCustomerId);
			rewardsResponse = RewardsResearchDAO.callRewardsService(urlParameters);

			RewardsResponseDTO rewardsResponseDTO = RewardsResearchUtil.getGson()
					.fromJson(rewardsResponse, RewardsResponseDTO.class);

			return rewardsResponseDTO;
		}
		catch (Exception e) {
			log("Received an error when trying to reach Purchase History");
			FailedRewardsResponse callError = new FailedRewardsResponse();
			callError.setErrorMessage(e.toString());
			e.printStackTrace();
			return callError;
		}
	}


	public static JsonObject getLoyaltyAuthorizationHistory(String aLoyaltyId) throws Exception{
		JsonObject authorizationHistory;
		String urlParameters;
		Gson gson = new Gson();
		final String rewardsNotFound = "Authorization was not found: ";

			System.out.println("inside RRController- 001");
			System.out.println("loyaltyId is : " + aLoyaltyId);
			urlParameters = String.format(aLoyaltyId);
			authorizationHistory = RewardsResearchDAO.getLoyaltyAuthorizationHistory(urlParameters);

			System.out.println("inside RRController- 002");

			System.out.println("inside RRController- 003");

			return authorizationHistory;
	}

	//This method is in charge of getting the customer details, prepare the call to order services, do some basic validations and then return the response we get
	public static PurchaseHistoryResponse getCustomerDetails(String aCustomerId, String aAddressId) {
		JsonObject purchaseHistoryResponse;
		String urlParameters;
		Gson gson = new Gson();
		final String purchaseHistoryNotFound = "Customer was not found in purchase history: ";

		//Creating a successful response object
		SuccessfulPurchaseHistoryResponse successfulResponse = new SuccessfulPurchaseHistoryResponse();
		//Creating a failed response object, in case something goes wrong
		FailedPurchaseHistoryResponse failedResponse = new FailedPurchaseHistoryResponse();
		//Verify we received the parameters we need to send a request
		try {
			urlParameters = String.format("/%s?expand=address;phone", aCustomerId);
			purchaseHistoryResponse = RewardsResearchDAO.callOrderServices(urlParameters, false);		
			JsonElement jsonElement = gson.toJsonTree(setResponseParameters(purchaseHistoryResponse, aAddressId));
			JsonObject jsonObject = (JsonObject) jsonElement;
			successfulResponse.setPurchaseHistoryResponse(jsonObject);
			return successfulResponse;
		} catch (PurchaseHistoryNotFoundException e) {
			// If a 404 was returned, process as a manual return
			log("Calling order services for customer details returned a 404.");
			failedResponse.setErrorMessage(purchaseHistoryNotFound + e);
			e.printStackTrace();
			return failedResponse;
		} catch (RewardsResearchAdressException e) {
			// If a 404 was returned, process as a manual return
			log("Address did not match the customer");
			failedResponse.setErrorMessage(e.getErrorMessage() + " with ID = " + aCustomerId);
			e.printStackTrace();
			return failedResponse;
		} catch (Exception e) {
			log("Received an error when trying to reach Purchase History");
			FailedPurchaseHistoryResponse callError = new FailedPurchaseHistoryResponse();
			callError.setErrorMessage(e.toString());
			e.printStackTrace();
			return callError;
		}
	}

	//This method grabs the response from the customer API, and maps the existent values to a Java Object so it can be used in the code
	public static CustomerResponseDetail setResponseParameters(JsonObject response, String addressId) throws RewardsResearchAdressException  {
		CustomerResponseDetail customerResponseDetail = new CustomerResponseDetail();
		JsonObject customerProperties = response.get("properties").getAsJsonObject();
		
		if(customerProperties.getAsJsonObject().has("addresses")) {
			customerResponseDetail.getAddresses().add(buildCustomerAddress(customerProperties.get("addresses").getAsJsonArray(), addressId)) ;
			buildCustomerPhones(customerResponseDetail, customerProperties);
			customerResponseDetail.setAddressId(customerProperties.get("addressId").getAsString());
			customerResponseDetail.setAddressLine1(customerProperties.get("addressLine1").getAsString());
			customerResponseDetail.setCity(customerProperties.get("city").getAsString());
			customerResponseDetail.setState(customerProperties.get("state").getAsString());
			customerResponseDetail.setZipCode(customerProperties.get("zipCode").getAsString());
			customerResponseDetail.setCountry(customerProperties.get("country").getAsString());
		}
		if(customerProperties.getAsJsonObject().has("addressLine2")) {
			customerResponseDetail.setAddressLine2((customerProperties.get("addressLine2").getAsString()));
		}

		customerResponseDetail.setCustomerId(customerProperties.get("customerId").getAsString());

		if(customerProperties.has("firstName")) {
			customerResponseDetail.setFirstName(customerProperties.get("firstName").getAsString());
		} else {
			customerResponseDetail.setFirstName("");
		}

		if(customerProperties.has("lastName")) {
			customerResponseDetail.setLastName(customerProperties.get("lastName").getAsString());
		} else {
			customerResponseDetail.setLastName("");
		}

		if(customerProperties.getAsJsonObject().has("isOAP")) {
			customerResponseDetail.setIsOAP((customerProperties.get("isOAP").getAsString()));
		}
		if(customerProperties.getAsJsonObject().has("personTitle")) {
			customerResponseDetail.setPersonTitle(customerProperties.get("personTitle").getAsString());
		}
		if(customerProperties.getAsJsonObject().has("gender")) {
			customerResponseDetail.setGender(customerProperties.get("gender").getAsString());
		}
		if(customerProperties.getAsJsonObject().has("email2")) {
			customerResponseDetail.setEmail2(customerProperties.get("email2").getAsString());
		}
		if(customerProperties.getAsJsonObject().has("phone1")) {
			customerResponseDetail.setPhone1(customerProperties.getAsJsonObject().get("phone1").getAsString());
		}
		if(customerProperties.getAsJsonObject().has("honorific")) {
			customerResponseDetail.setHonorific(customerProperties.getAsJsonObject().get("honorific").getAsString());
		}
		if(customerProperties.getAsJsonObject().has("middleName")) {
			customerResponseDetail.setMiddleName(customerProperties.getAsJsonObject().get("middleName").getAsString());
		}
		if(customerProperties.getAsJsonObject().has("nameSuffix")) {
			customerResponseDetail.setNameSuffix(customerProperties.getAsJsonObject().get("nameSuffix").getAsString());
		}
		if(customerProperties.getAsJsonObject().has("email1")) {
			customerResponseDetail.setEmail1(customerProperties.get("email1").getAsString());
		}
		return customerResponseDetail;

	}
	// This method is in charge of checking the addresses returned from the customer API, finding which one matches the Adress ID passed
	// And formats the response according to the matching address. If there's no address, and error is returned
	public static com.llbean.salestrans.rewardsresearch.CustomerAddressDetails buildCustomerAddress(JsonArray addresses, String addressId) throws RewardsResearchAdressException{
		com.llbean.salestrans.rewardsresearch.CustomerAddressDetails matchingAddress = null;
		for(JsonElement address : addresses) {
			if(address.getAsJsonObject().get("addressId").getAsString().equals(addressId)) {
				matchingAddress = new com.llbean.salestrans.rewardsresearch.CustomerAddressDetails();
				matchingAddress.setAddressId(address.getAsJsonObject().get("addressId").getAsString());
				matchingAddress.setAddressLine1(address.getAsJsonObject().get("addressLine1").getAsString());
				matchingAddress.setCityName(address.getAsJsonObject().get("cityName").getAsString());
				matchingAddress.setStateProvince(address.getAsJsonObject().get("stateProvince").getAsString());
				matchingAddress.setPostalCode(address.getAsJsonObject().get("postalCode").getAsString());
				matchingAddress.setCountryId(address.getAsJsonObject().get("countryId").getAsString());
				matchingAddress.setWebRegistered(address.getAsJsonObject().get("webRegistered").getAsString());
				matchingAddress.setActive(address.getAsJsonObject().get("active").getAsString());
				matchingAddress.setActivityDate(address.getAsJsonObject().get("activityDate").getAsString());
				matchingAddress.setAuditTimestamp(address.getAsJsonObject().get("auditTimestamp").getAsString());
			}
		}
		if(matchingAddress == null) {
			RewardsResearchAdressException addressException = new RewardsResearchAdressException();
			addressException.setErrorMessage("Address ID " + addressId + " not found or does not belong to the customer provided");
			throw addressException;
		}
		return matchingAddress;

	}

	/**
	 * Assemble CustomerPhoneDetail objects and add them to the CustomerResponseDetail object for use in the business logic
	 * @param customerResponseDetail The response detail object to update
	 * @param customerProperties The Json Object from the customer API
	 */
	private static void buildCustomerPhones(CustomerResponseDetail customerResponseDetail, JsonObject customerProperties) {
		if(customerProperties.has("phones") && customerProperties.get("phones").isJsonArray()) {
			JsonArray array = customerProperties.getAsJsonArray("phones");
			for(JsonElement element : array) {
				JsonObject object = element.getAsJsonObject();

				CustomerPhoneDetails phoneDetails = new CustomerPhoneDetails();
				phoneDetails.setPhoneNumber(object.get("phoneNumber").getAsString());
				phoneDetails.setAreaCode(object.get("areaCode").getAsString());
				phoneDetails.setExtension(object.get("extension").getAsString());
				phoneDetails.setActive(Boolean.parseBoolean(object.get("active").getAsString()));
				customerResponseDetail.getPhones().add(phoneDetails);
			}
		}
	}

	// The orders API needs a specific type of request for the update, and this is where that request is built, from different responses and data
	public static JsonObject buildOCSRequest(PurchaseHistoryResponse custResponse, JsonObject portalServiceResponse) {
		debug("portalServiceResponse: " + portalServiceResponse.toString());

		JsonObject ocsRequest = new JsonObject();
		JsonObject custResponseObject = new JsonObject();
		if (custResponse != null) {
			custResponseObject = ((SuccessfulPurchaseHistoryResponse) custResponse).getPurchaseHistoryResponse();
		}

		// Lookup attributes
		JsonObject lookupAttributes = new JsonObject();
		// End lookup attributes

		// New identifiers
		JsonObject newIdentifiers = new JsonObject();
		JsonObject buyer = new JsonObject();
		if (custResponseObject.has("customerId")) buyer.addProperty("customerId", custResponseObject.get("customerId").getAsString()); else buyer.addProperty("customerId", "");
		if (custResponseObject.has("addressId")) buyer.addProperty("addressId", custResponseObject.get("addressId").getAsString()); else buyer.addProperty("addressId", "");
		if (custResponseObject.has("gender")) buyer.addProperty("gender", custResponseObject.get("gender").getAsString()); else buyer.addProperty("gender", "");
		JsonObject name = new JsonObject();
		if (custResponseObject.has("firstName")) name.addProperty("firstName", custResponseObject.get("firstName").getAsString()); else name.addProperty("firstName", "");
		if (custResponseObject.has("middleName")) name.addProperty("middleName", custResponseObject.get("middleName").getAsString()); else name.addProperty("middleName", "");
		if (custResponseObject.has("lastName")) name.addProperty("lastName", custResponseObject.get("lastName").getAsString()); else name.addProperty("lastName", "");
		if (custResponseObject.has("businessName")) name.addProperty("businessName", custResponseObject.get("businessName").getAsString()); else name.addProperty("businessName", "");
		buyer.add("name", name);

		// Phone number section
		// Phone number object
		JsonObject phoneNumber = new JsonObject();

		// Default field values
		String phoneNbr = "", areaCode = "", extension = "";

		if (custResponseObject.has("phones") && custResponseObject.get("phones").isJsonArray()) {
			JsonArray phoneNumberArray = custResponseObject.getAsJsonArray("phones");

			// Loop through the phone objects and use the first active one
			for(JsonElement phoneElement : phoneNumberArray) {
				JsonObject phoneObject = phoneElement.getAsJsonObject();
				if(phoneObject.get("active").getAsBoolean()) {
					// Set values and exit the loop
					phoneNbr = phoneObject.get("phoneNumber").getAsString();
					areaCode = phoneObject.get("areaCode").getAsString();
					extension = phoneObject.get("extension").getAsString();
					break;
				}
			}
		}

		phoneNumber.addProperty("phoneNbr", phoneNbr);
		phoneNumber.addProperty("areaCode", areaCode);
		phoneNumber.addProperty("extension", extension);
		log("Set phone values. phoneNbr='%s', areaCode='%s', extension='%s'", phoneNbr, areaCode, extension);

		buyer.add("phoneNumber", phoneNumber);
		JsonObject email = new JsonObject();
		if (custResponseObject.has("email1")) email.addProperty("emailAddr", custResponseObject.get("email1").getAsString());
		if (custResponseObject.has("email2")) email.addProperty("emailAddr", custResponseObject.get("email2").getAsString()); else email.addProperty("emailAddr", "");
		buyer.add("email", email);        
		JsonObject address = new JsonObject();
		JsonObject phResponseAddress = new JsonObject();
		if(custResponseObject.has("addresses")) {
			phResponseAddress = custResponseObject.get("addresses").getAsJsonArray().get(0).getAsJsonObject();
		}
		if (phResponseAddress.has("addressLine1")) address.addProperty("line1", phResponseAddress.get("addressLine1").getAsString()); else address.addProperty("line1", "");
		if (phResponseAddress.has("addressLine2")) address.addProperty("line2", phResponseAddress.get("addressLine2").getAsString()); else address.addProperty("line2", "");
		if (phResponseAddress.has("addressLine3")) address.addProperty("line3", phResponseAddress.get("addressLine3").getAsString()); else address.addProperty("line3", "");
		if (phResponseAddress.has("cityName")) address.addProperty("city", phResponseAddress.get("cityName").getAsString()); else address.addProperty("city", "");
		if (phResponseAddress.has("stateProvince")) address.addProperty("jurisdictionCode", phResponseAddress.get("stateProvince").getAsString()); else address.addProperty("jurisdictionCode", "");
		if (phResponseAddress.has("postalCode")) address.addProperty("postalCode", phResponseAddress.get("postalCode").getAsString()); else address.addProperty("postalCode", "");
		if (phResponseAddress.has("countryId")) address.addProperty("countryCode", phResponseAddress.get("countryId").getAsString()); else address.addProperty("countryCode", "");
		buyer.add("address", address);
		newIdentifiers.add("buyer", buyer);
		// End new identifiers

		// Audit information
		JsonObject auditInformation = new JsonObject();
		auditInformation.addProperty("opId", portalServiceResponse.get("employeeNumber").getAsString());
		// End audit information
		ocsRequest.add("lookupAttributes", lookupAttributes);
		ocsRequest.add("newIdentifiers", newIdentifiers);
		ocsRequest.add("auditInformation", auditInformation);    

		return ocsRequest;
	}

	//This validates the sessionID we get is not valid
	public static boolean isSessionIdNotValid(String aSessionId) {
		try {
			return !RewardsResearchDAO.checkSessionId(aSessionId);
		} catch (PortalServiceNotFoundException e) {
			// If a 404 was returned, send an error message 
			log("Calling portal service for customer details returned a 404.");
		} catch (Exception e) {
			log("Received an error when trying to reach Portal services. Error: " + e);
		}

		return true;
	}
}