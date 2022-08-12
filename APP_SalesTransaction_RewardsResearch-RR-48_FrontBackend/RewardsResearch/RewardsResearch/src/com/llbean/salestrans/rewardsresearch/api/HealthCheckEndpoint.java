package com.llbean.salestrans.rewardsresearch.api;

import static com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.debug;
import static com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.generateHeaders;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/healthCheck")
public class HealthCheckEndpoint {

	@GET
	public Response healthCheck() {
		debug("Entered /healthCheck endpoint");
		return generateHeaders(200).build();
	}
}