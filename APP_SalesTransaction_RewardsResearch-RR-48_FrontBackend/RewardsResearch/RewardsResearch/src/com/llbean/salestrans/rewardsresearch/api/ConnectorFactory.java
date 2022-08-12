package com.llbean.salestrans.rewardsresearch.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class ConnectorFactory {
	
		private static final String JNDI_PREFIX = "jdbc/";
		private static final String RewardsResearch_Sql = "rewards_research_payments";

		/**@deprecated
		 * Gets an instance of the static connection object for the caller. Always checks to see if the connection
		 * has been closed and reopens one if so.
		 * 
		 * @return connection
		 * @throws SQLException
		 * @throws IOException
		 * @throws NamingException 
		 */

		
	    public static String getJNDINameForDatasource(String dsName) {
	    	return JNDI_PREFIX+dsName;
	    }
		
	    public static Connection getConnectionFromDatasource(final String dsName) throws NamingException, SQLException 
	    {
	    	InitialContext ctx = new InitialContext();
	    	DataSource dataSource = (DataSource)ctx.lookup(getJNDINameForDatasource(dsName)); 
	    	return dataSource.getConnection();
	    }
	    
	    public static Connection getSqlConnection() throws SQLException, IOException, NamingException 
	    {
	    	return getConnectionFromDatasource(RewardsResearch_Sql);
	    }
			
}