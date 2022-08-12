package com.llbean.salestrans.rewardsresearch.api.request;

import com.llbean.salestrans.rewardsresearch.exception.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * JSONFormatter.java, 2016/06/24
 * @author treis
 * 
 * A class to format result sets as JSON arrays
 */
public class JSONFormatter {
	
	private final transient JsonArray jsonArray;
	private final transient JsonObject wrapperObject;
	
	/**
	 * Default constructor
	 * @param resSet (Result Set)
	 * @param rsmd (Result Set Meta Data)
	 * @throws SQLException
	 * @throws Exception 
	 */
	public JSONFormatter(final ResultSet resSet, final ResultSetMetaData rsmd, final String wrapper) throws SQLException, Exception {
		this.jsonArray = new JsonArray();
		this.wrapperObject = new JsonObject();
		fillJSONArray(resSet, rsmd, wrapper);
	}
	
	/**
	 * Fill the private jsonArray member with values provided by the resultset and resultsetmetadata
	 * 
	 * @param resSet
	 * @param rsmd
	 * @throws SQLException
	 * @throws Exception 
	 */
	private void fillJSONArray(final ResultSet resSet, final ResultSetMetaData rsmd, final String wrapper) throws SQLException, Exception {
		
		final int colLength = rsmd.getColumnCount();
		
		while (resSet.next()) {
			
			final JsonObject jsonObject = new JsonObject();
			
			for (int i = 0; i < colLength; i++) {
				final String colName = rsmd.getColumnLabel(i + 1);
				if(resSet.getString(i+1) != null){
					jsonObject.addProperty(colName, resSet.getString(i + 1).trim());
				}
			}
			
			this.jsonArray.add(jsonObject);
		}
		
		wrapperObject.add(wrapper,this.jsonArray);
	}
	
	/**
	 * Get the JSON Array
	 * @return JSON Array
	 */
	public JsonObject getJSONArray() {
		return this.wrapperObject;
	}

}
