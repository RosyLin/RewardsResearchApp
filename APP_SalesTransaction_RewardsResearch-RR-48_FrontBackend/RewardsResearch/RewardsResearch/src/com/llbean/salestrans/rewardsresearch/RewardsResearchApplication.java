package com.llbean.salestrans.rewardsresearch;


import com.llbean.salestrans.rewardsresearch.api.RewardsResearchEndpoints;
import com.llbean.salestrans.rewardsresearch.api.HealthCheckEndpoint;
import com.llbean.salestrans.rewardsresearch.api.RefreshPropertiesEndpoint;
import org.apache.log4j.xml.DOMConfigurator;

import javax.ws.rs.core.Application;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


/**
 * Setup class for the webservice. Connects the endpoint class to the application.
 */
public class RewardsResearchApplication extends Application {

	/**
	 * Initialize the application. Load logging properties and application properties
	 */
	public RewardsResearchApplication() {

		super();

		// Logging setup
		//final URL url = this.getClass().getResource("//RewardsResearch_log4j.xml");
		//DOMConfigurator.configure(url); // uncomment this once your properties file is created
		RewardsResearchProperties.getInstance().logProperties();
	}

	/**
	 * Register the endpoints
	 * @return A set of endpoint classes
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(RewardsResearchEndpoints.class);
		classes.add(RefreshPropertiesEndpoint.class);
		classes.add(HealthCheckEndpoint.class);

		return classes;
	}

}