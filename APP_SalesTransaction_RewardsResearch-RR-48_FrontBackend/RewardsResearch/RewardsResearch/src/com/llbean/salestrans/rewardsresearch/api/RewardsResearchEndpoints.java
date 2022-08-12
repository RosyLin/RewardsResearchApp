package com.llbean.salestrans.rewardsresearch.api;
import com.llbean.salestrans.rewardsresearch.api.response.AuthorizationHistoryDTO;
import com.llbean.salestrans.rewardsresearch.exception.*;

import static com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.debug;
import static com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.error;
import static com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.generateHeaders;
import static com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.log;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.NDC;
import org.apache.wink.client.ClientResponse;

import com.google.gson.JsonObject;
import com.llbean.salestrans.rewardsresearch.RewardsResearchController;
import com.llbean.salestrans.rewardsresearch.RewardsResearchDAO;
import com.llbean.salestrans.rewardsresearch.RewardsResearchUtil;
import com.llbean.salestrans.rewardsresearch.api.response.RewardsResponseDTO;
import com.llbean.salestrans.rewardsresearch.api.response.PurchaseHistoryResponse;
import com.llbean.salestrans.rewardsresearch.api.response.SuccessfulPurchaseHistoryResponse;
import com.llbean.salestrans.rewardsresearch.exception.RewardsResearchCustomException;
import com.llbean.salestrans.rewardsresearch.exception.RewardsResearchGeneralError;
import com.llbean.salestrans.rewardsresearch.exception.PortalServiceNotFoundException;

@Path("")
public class RewardsResearchEndpoints {
	//class used for general parameter validation
	private final RewardsResearchGeneralError generalValidationError = new RewardsResearchGeneralError();
	private static final String SESSIONID = "sessionid";
	private static final String SESSIONID_ERROR = "Your session ID is not valid. Please log out and back in";
	private static final String NO_SESSIONID_ERROR = "Your session ID was not found. Please log out and back in";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/transaction")
	public Response phByTransaction(
			final @HeaderParam(SESSIONID) String aSessionId,
			final @QueryParam("orderId") String aOrderId,
			final @QueryParam("transactionEntryDate") String aTransactionEntryDate,
			final @QueryParam("inventoryLocationId") String aInventoryLocationId,
			final @QueryParam("registerId") String aRegisterId,
			final @QueryParam("transactionId") String aTransactionId) {

		System.out.println("Hello-001___________________************");

		debug("Entered /transaction endpoint");
		// Use a request ID to track the request through the service
		NDC.clear();

		NDC.push(
				String.format(
						"[orderId: %s, date: %s, store: %s, register: %s, transaction: %s]",
				aOrderId, aTransactionEntryDate, aInventoryLocationId, aRegisterId, aTransactionId));

		log("[Request Initialization]");
		PurchaseHistoryResponse response = null;

		System.out.println("Hello-002___________________************");

		// Validate we have a session ID
		if(!validateRequestSessionID(aSessionId)) {
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(generalValidationError)).build();
		}
		try {
			if(RewardsResearchController.isSessionIdNotValid(aSessionId)) {
				generalValidationError.setErrorMessage(SESSIONID_ERROR);
				return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(generalValidationError)).build();
			}
		} catch (Exception e) {
			generalValidationError.setErrorMessage(e.toString());
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(generalValidationError)).build();
		}
		System.out.println("Hello-003___________________************");
		try {
			System.out.println("order ID: " + aOrderId);
			System.out.println(aTransactionEntryDate);
			System.out.println(aInventoryLocationId);
			System.out.println(aRegisterId);
			System.out.println(aTransactionId);

			response = RewardsResearchController.getTransactionDetails(aOrderId, aTransactionEntryDate, aInventoryLocationId, aRegisterId, aTransactionId);
		} catch(Exception e) {
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(response)).build();
		}
		System.out.println(response);
		System.out.println("Hello-004___________________************");
		if(response instanceof SuccessfulPurchaseHistoryResponse) {
			System.out.println("Hello-005___________________************");
			log("Response code: [200]");
			SuccessfulPurchaseHistoryResponse successfulPurchaseHistoryResponse = (SuccessfulPurchaseHistoryResponse) response;
			return generateHeaders(200).entity(RewardsResearchUtil.objectToPrettyJSON(successfulPurchaseHistoryResponse)).build();
		} else {
			System.out.println("Hello-006___________________************");
			log("Response code: [500]");
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(response)).build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/rewards")
	public Response getRewardsInfo(
			final @QueryParam("customerId") String aCustomerId){

		debug("Entered /rewards endpoint");
		NDC.clear();

		NDC.push(
				String.format(
						"[customerId: %s]",
						aCustomerId
				)
		);

		log("[Request Initialization]");
		RewardsResponseDTO response = null;

		if(aCustomerId == null) {
			generalValidationError.setErrorMessage("ERROR: Need to provide the CustomerId");
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(generalValidationError)).build();
		}
		try {
			response = RewardsResearchController.getRewardsDetails(aCustomerId);
		} catch(Exception e) {
			return generateHeaders(500).header("Access-Control-Allow-Origin", "*").entity(RewardsResearchUtil.objectToPrettyJSON(response)).build();
		}
		if(response instanceof RewardsResponseDTO) {
			log("Response code: [200]");
			return generateHeaders(200).header("Access-Control-Allow-Origin", "*").entity(RewardsResearchUtil.objectToPrettyJSON(response)).build();
		} else {
			log("Response code: [500]");
			return generateHeaders(500).header("Access-Control-Allow-Origin", "*").entity(RewardsResearchUtil.objectToPrettyJSON(response)).build();
		}
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/customer")
	public Response getCustomerInfo(
			final @HeaderParam(SESSIONID) String aSessionId,
			final @QueryParam("customerId") String aCustomerId,
			final @QueryParam("addressId") String aAddressId) {

		debug("Entered /customer endpoint");
		// Use a request ID to track the request through the service
		NDC.clear();

		NDC.push(
				String.format(
						"[customerId: %s, addressId: %s]",
						aCustomerId, aAddressId
				)
		);

		log("[Request Initialization]");
		PurchaseHistoryResponse response = null;

		// Validate we have a session ID
		if(!validateRequestSessionID(aSessionId)) {
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(generalValidationError)).build();
		}
		if(aCustomerId == null || aAddressId == null) {
			generalValidationError.setErrorMessage("ERROR: Need to provide both the CustomerId and the AddressId");
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(generalValidationError)).build();
		}
		try {
			if(RewardsResearchController.isSessionIdNotValid(aSessionId)) {
				generalValidationError.setErrorMessage(SESSIONID_ERROR);
				return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(generalValidationError)).build();
			}
		} catch (Exception e) {
			generalValidationError.setErrorMessage(e.toString());
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(generalValidationError)).build();
		}
		try {
			response = RewardsResearchController.getCustomerDetails(aCustomerId, aAddressId);
		} catch(Exception e) {
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(response)).build();
		}
		if(response instanceof SuccessfulPurchaseHistoryResponse) {
			log("Response code: [200]");
			return generateHeaders(200).entity(RewardsResearchUtil.objectToPrettyJSON(((SuccessfulPurchaseHistoryResponse) response).getPurchaseHistoryResponse())).build();
		} else {
			log("Response code: [500]");
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(response)).build();
		}

	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/authorizations")
	public Response getLoyaltyAuthorizationHistory(
			final @QueryParam("loyaltyId") String aLoyaltyId){

		debug("Entered /loyalty endpoint");
		NDC.clear();

		NDC.push(
				String.format(
						"[loyaltyId: %s]",
						aLoyaltyId
				)
		);

		log("[Request Initialization]");
		JsonObject response = null;
		System.out.println("inside RREndpoints!!! -001");
		System.out.println("the loyalty ID in endpoint is " + aLoyaltyId);
		if(aLoyaltyId == null) {
			System.out.println("inside RREndpoints!!! -0011");
			generalValidationError.setErrorMessage("ERROR: Need to provide the LoyaltyId");
			return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(generalValidationError)).build();
		}
		try {
			System.out.println("inside RREndpoints!!! -002");
			response = RewardsResearchController.getLoyaltyAuthorizationHistory(aLoyaltyId);
			return generateHeaders(200).header("Access-Control-Allow-Origin", "*").entity(RewardsResearchUtil.objectToPrettyJSON(response)).build();

		} catch(Exception e) {
			System.out.println("inside RREndpoints!!! -0021");
			return generateHeaders(500).header("Access-Control-Allow-Origin", "*").entity(response).build();
		}
	}







	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update")

	/**
	 * Validates common request header parameters
	 *
	 * @param aSessionId The session ID
	 *
	 */
	private boolean validateRequestSessionID(final String aSessionId) {
		// Validate input HTTP headers and request parameters
		return true;
	}
}