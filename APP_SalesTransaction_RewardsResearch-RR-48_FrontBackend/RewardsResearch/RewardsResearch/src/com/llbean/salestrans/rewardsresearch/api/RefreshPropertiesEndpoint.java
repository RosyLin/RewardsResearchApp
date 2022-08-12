package com.llbean.salestrans.rewardsresearch.api;

import static  com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.debug;
import static  com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.generateHeaders;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.llbean.salestrans.rewardsresearch.RewardsResearchProperties;
import com.llbean.salestrans.rewardsresearch.RewardsResearchUtil;

@Path("/refreshProperties")
public class RefreshPropertiesEndpoint {

	// This will also load the properties
	private final static RewardsResearchProperties properties = RewardsResearchProperties.getInstance();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response refreshProperties() {
		debug("Entered /refreshProperties endpoint");

		try {
			RewardsResearchProperties.getInstance().refresh();
			return generateSuccessfulResponse();
		} catch (Exception e) {
			return generateFailedResponse(e);
		}
	}

	private Response generateSuccessfulResponse() {
		JsonObject responseObject = new JsonObject();
		JsonArray array = new JsonArray();
		responseObject.add("properties", array);

		for(String key : properties.properties.getAllKeys()) {
			JsonObject propertyObject = new JsonObject();
			propertyObject.addProperty("key", key);
			propertyObject.addProperty("value", properties.properties.getValueForKey(key).toString());
			array.add(propertyObject);
		}
		return generateHeaders(200).entity(RewardsResearchUtil.objectToPrettyJSON(responseObject)).build();
	}

	private Response generateFailedResponse(Exception e) {
		JsonObject responseObject = new JsonObject();
		responseObject.addProperty("message", "There was an exception while refreshing properties: " + e.getMessage());
		return generateHeaders(500).entity(RewardsResearchUtil.objectToPrettyJSON(responseObject)).build();
	}

}
