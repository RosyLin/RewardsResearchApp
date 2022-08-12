package com.llbean.salestrans.rewardsresearch;

import static com.llbean.salestrans.rewardsresearch.RewardsResearchUtil.log;

import com.llbean.common.properties.utilities.applproperties.ApplPropertiesInfo;
import com.llbean.common.properties.utilities.applproperties.ApplPropertiesSourceEnum;

public class RewardsResearchProperties {

	private static final String ORDER_SERVICES_READ_TIMEOUT = "ORDER_SERVICES_READ_TIMEOUT";
	private static final String ORDER_SERVICES_CONNECT_TIMEOUT = "ORDER_SERVICES_CONNECT_TIMEOUT";

	private static final String APPLICATION_PORTAL_READ_TIMEOUT = "APPLICATION_PORTAL_READ_TIMEOUT";
	private static final String APPLICATION_PORTAL_CONNECT_TIMEOUT = "APPLICATION_PORTAL_CONNECT_TIMEOUT";

	private static final String LOG_REQUESTS = "LOG_REQUESTS";
	private static final String LOG_RESPONSES = "LOG_RESPONSES";

	private static final RewardsResearchProperties INSTANCE
	= new RewardsResearchProperties();

	public ApplPropertiesInfo properties;

	private RewardsResearchProperties() {
		this.properties = new ApplPropertiesInfo("Customer Transaction", true, false, ApplPropertiesSourceEnum.LLB_SQL_GEN);
		log("Created new ApplPropertiesInfo object using SQL Server. Loaded %d properties", this.properties.getAllKeys().size());
	}

	public void logProperties() {
		for(String key : this.properties.getAllKeys()) {
			log(String.format("Property : [%s, %s]", key, this.properties.getValueForKey(key)));
		}
	}

	public void refresh() {
		log("Refreshing properties...");
		this.properties.forceRefresh();
		log("Refresh of properties complete");

		this.logProperties();
	}

	public static RewardsResearchProperties getInstance() {
		System.out.println("RRP getInstance");
		return INSTANCE;
	}

	public int getOrderServicesReadTimeout() {
		System.out.println("RRP getOrderServicesReadTimeout" + this.properties.getValueForKey(ORDER_SERVICES_READ_TIMEOUT));

		return Integer.parseInt(this.properties.getValueForKey(ORDER_SERVICES_READ_TIMEOUT).toString());
	}

	public int getOrderServicesConnectTimeout() {
		System.out.println("RRP getOrderServicesConnectTimeout" + this.properties.getValueForKey(ORDER_SERVICES_CONNECT_TIMEOUT));

		return Integer.parseInt(this.properties.getValueForKey(ORDER_SERVICES_CONNECT_TIMEOUT).toString());
	}

	public int getApplicationPortalReadTimeout() {
		return Integer.parseInt(this.properties.getValueForKey(APPLICATION_PORTAL_READ_TIMEOUT).toString());
	}

	public int getApplicationPortalConnectTimeout() {
		return Integer.parseInt(this.properties.getValueForKey(APPLICATION_PORTAL_CONNECT_TIMEOUT).toString());
	}

	public boolean shouldLogRequests() {
		return Boolean.parseBoolean(this.properties.getValueForKey(LOG_REQUESTS).toString());
	}

	public boolean shouldLogResponses() {
		return Boolean.parseBoolean(this.properties.getValueForKey(LOG_RESPONSES).toString());
	}
}
