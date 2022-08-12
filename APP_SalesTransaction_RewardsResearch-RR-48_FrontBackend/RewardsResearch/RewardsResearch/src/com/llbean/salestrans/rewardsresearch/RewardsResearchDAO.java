package com.llbean.salestrans.rewardsresearch;

import static com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.debug;
import com.llbean.salestrans.rewardsresearch.api.ConnectorFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;

import com.llbean.commons.persistence.jdbc.LLBJdbcDataSource;
import com.google.gson.JsonArray;
import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.JsonObject;
import com.llbean.salestrans.rewardsresearch.exception.PortalServiceNotFoundException;
import com.llbean.salestrans.rewardsresearch.exception.PurchaseHistoryNotFoundException;
import com.llbean.salestrans.rewardsresearch.exception.RewardsAccountNotFoundException;
import com.llbean.security.oauth.OAuthTokenCache;

import com.llbean.salestrans.rewardsresearch.api.request.JSONFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RewardsResearchDAO {

	public final static String OAUTH_KEY_ALIAS = "REWARDSRESEARCH_OAUTHKEY";
	private final static String OAUTH_SECRET_ALIAS = "REWARDSRESEARCH_OAUTHSECRET";
	private final static String OAUTH_SCOPE = "customers%20purchase-history%20rewards";

	private final static OAuthTokenCache tokenCache = OAuthTokenCache.buildCacheWithAuthAliases(OAUTH_KEY_ALIAS, OAUTH_SECRET_ALIAS, OAUTH_SCOPE);
	private final static String PURCHASE_HISTORY_URL_REFERENCE = "url/v2Purchases";
	private final static String PURCHASE_HISTORY_ORDERS_REFERENCE = "url/v1Orders";
	private final static String PURCHASE_HISTORY_CUSTOMER_URL_REFERENCE = "url/v1Customers";
	private final static String APPLICATION_PORTAL_URL_REFERENCE = "url/v1PortalSession";

	private final static String REWARDS_SERVICE_URL_REFERENCE = "url/v1Rewards";

	private final static String APP_NAME_FOR_APPLICATION_PORTAL = "Rewards%20Research";

	

	/********************************************************************** */
	/********************************************************************** */
	public static JsonObject callRewardsService(String urlParameters) throws Exception {

		JsonObject jsonRequest = new JsonObject();
		JsonObject requestAuditInfo = new JsonObject();
		requestAuditInfo.addProperty("registryConsumerId",1);
		requestAuditInfo.addProperty("registryContextId", 22);
		requestAuditInfo.addProperty("employeeId", "157373");
		requestAuditInfo.addProperty("originatingUserName", "RewardsResearch");
		requestAuditInfo.addProperty("requestorAuditId", "123456");
		requestAuditInfo.addProperty("unitOfWorkId", 1);
		requestAuditInfo.addProperty("requestor", "INT");
		requestAuditInfo.addProperty("storeId", "12347");
		requestAuditInfo.addProperty("terminalId", "1");
		requestAuditInfo.addProperty("requestorIPAddress", "127.0.0.1");
		requestAuditInfo.addProperty("originatingRequestor", "Postman");
		requestAuditInfo.addProperty("originatingTimestamp", new Date().getTime());
		jsonRequest.add("requestAuditInfo", requestAuditInfo);
		jsonRequest.addProperty("customerId", urlParameters);


		String rewardsServicesEndpoint;
		try {
			Context context = new InitialContext();
			rewardsServicesEndpoint = ((URL) context.lookup(REWARDS_SERVICE_URL_REFERENCE)).toExternalForm();

			System.out.println("the url is ::: " + rewardsServicesEndpoint + " url reference: " + REWARDS_SERVICE_URL_REFERENCE );

		} catch (NamingException exc) {
			exc.printStackTrace();
			throw new RuntimeException("Exception occurred while loading URL resource", exc);
		}

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.connectTimeout(10000);
		clientConfig.readTimeout(10000);

		RestClient restClient = new RestClient(clientConfig);

		Resource resource = restClient.resource(rewardsServicesEndpoint + "/lookup");
		resource.contentType(MediaType.APPLICATION_JSON_TYPE);
		resource.accept(MediaType.APPLICATION_JSON_TYPE);
		debug("Calling order services with the URL: " + rewardsServicesEndpoint);
		String token = tokenCache.getToken();

		resource.header("Authorization", "Bearer " + token);
		resource.header("x-llbean-app", "CTM");

		ClientResponse response = resource.post(jsonRequest.toString());
		//if(response.getStatusCode() == 200) {
			// A good response. Parse the results into a JsonObject
			String json = response.getEntity(String.class);
			System.out.println("CallRewardsServices --07 and json: " + json);
			return RewardsResearchUtil.getGson().fromJson(json, JsonObject.class);
//		} else if(response.getStatusCode() == 404) {
//			// Not found. Return a special type of exception so the calling code will know
//			throw new RewardsAccountNotFoundException();
//		} else {
//			// Some other not OK response code. Throw an exception with the details.
//			throw new Exception("Calling the purchases API returned a non-200 status code of " + response.getStatusCode()
//					+ ". The URL called is " + rewardsServicesEndpoint + urlParameters);
//		}
	}

	/**
	 * Call the v2 purchases API and get the details of an order or the v1 customer API to get the customer details
	 * @param urlParameters Either the original direct channel order ID or the retail 4 part key parameters, or the customerID and the corresponding address ID
	 * @return The JSON object from Order Services
	 * @throws Exception NamingException while resolving the URL, or if a status code other than 200 is returned
	 */
	public static JsonObject callOrderServices(String urlParameters, boolean isCallToTransactionAPI) throws Exception {

		String orderServicesEndpoint;
		try {
			Context context = new InitialContext();
			if(isCallToTransactionAPI) {
				orderServicesEndpoint = ((URL) context.lookup(PURCHASE_HISTORY_URL_REFERENCE)).toExternalForm();
			} else {
				orderServicesEndpoint = ((URL) context.lookup(PURCHASE_HISTORY_CUSTOMER_URL_REFERENCE)).toExternalForm();
			}
		} catch (NamingException exc) {
			exc.printStackTrace();
			throw new RuntimeException("Exception occurred while loading URL resource", exc);
		}
		ClientConfig clientConfig = new ClientConfig();
		//Below not printing
		clientConfig.connectTimeout(RewardsResearchProperties.getInstance().getOrderServicesConnectTimeout());

		clientConfig.readTimeout(RewardsResearchProperties.getInstance().getOrderServicesReadTimeout());

		RestClient restClient = new RestClient(clientConfig);
		Resource resource = restClient.resource(orderServicesEndpoint + urlParameters);
		resource.contentType(MediaType.APPLICATION_JSON_TYPE);
		resource.accept(MediaType.APPLICATION_JSON_TYPE);
		debug("Calling order services with the URL: " + orderServicesEndpoint + urlParameters);

		String token = tokenCache.getToken();

		resource.header("Authorization", "Bearer " + token);
		resource.header("x-llbean-app", "rewards-research");
		ClientResponse response = resource.get();

		if(response.getStatusCode() == 200) {
			// A good response. Parse the results into a JsonObject
			String json = response.getEntity(String.class);
			return RewardsResearchUtil.getGson().fromJson(json, JsonObject.class);
		} else if(response.getStatusCode() == 404) {
			// Not found. Return a special type of exception so the calling code will know
			throw new PurchaseHistoryNotFoundException();
		} else {
			// Some other not OK response code. Throw an exception with the details.
			throw new Exception("Calling the purchases API returned a non-200 status code of " + response.getStatusCode()
			+ ". The URL called is " + orderServicesEndpoint + urlParameters);
		}
	}



	/********************************************************************** */
	/********************************************************************** */

	public static JsonObject getLoyaltyAuthorizationHistory(String loyaltyId) throws Exception {
		final Logger LOGGER = Logger.getLogger(RewardsResearchDAO.class.getName());
		JSONFormatter jsonFormatterForAuthRequest;
		JSONFormatter jsonFormatterForAuthResponse;
		JsonObject json = null;
		JsonObject json2 = null;
		Connection sqlConnection = ConnectorFactory.getSqlConnection();
		String AUTH_REQUEST = "{call Payment.[dbo].PMTR0087(?)}";


		//Result sets for various calls
		ResultSet resultSetForSQR = null; //Result set for SQR
		ResultSet resultSetForAuthRequest = null;
		ResultSet resultSetForAuthResponse = null;
		
		//Prepared statment for ad hoc queries
		PreparedStatement preparedStatement = null;
	    
	    try{ 		
			preparedStatement=sqlConnection.prepareCall(AUTH_REQUEST);
			preparedStatement.setString(1, String.valueOf(loyaltyId));
			

			System.out.println("inside RRDAO: 001- preparedStatement is:" + preparedStatement);
			System.out.println("auth_request is : " + AUTH_REQUEST);

			resultSetForAuthRequest = preparedStatement.executeQuery();
			jsonFormatterForAuthRequest = new JSONFormatter(resultSetForAuthRequest, resultSetForAuthRequest.getMetaData(),"AuthRequest");

			preparedStatement.getMoreResults();
			resultSetForAuthResponse = preparedStatement.getResultSet(); //return response!
			jsonFormatterForAuthResponse = new JSONFormatter(resultSetForAuthResponse, resultSetForAuthResponse.getMetaData(),"AuthResponse");

			json = jsonFormatterForAuthRequest.getJSONArray();
			json2 = jsonFormatterForAuthResponse.getJSONArray();

			json.add("AuthResponse", json2.get("AuthResponse"));

		} catch (SQLException se) {
			System.out.println("inside RRDAO: 005");
			LOGGER.error("Could not execute the Auth Request API call", se);
			System.out.println("Could not execute the Auth Request API call");
			System.out.println(se);
		} 
		finally {  // close all connections 
			System.out.println("inside RRDAO: 006");
			if(resultSetForSQR != null){
				resultSetForSQR.close();
			}
			
			if(resultSetForAuthRequest != null){
				resultSetForAuthRequest.close();
			}
			
			if(preparedStatement != null){
				preparedStatement.close();
			}

			if (sqlConnection != null) 
			{
				sqlConnection.close();
			}
		}
		return json;
	}


	public static boolean checkSessionId(String sessionId) throws Exception {
		return true;
	}
}

