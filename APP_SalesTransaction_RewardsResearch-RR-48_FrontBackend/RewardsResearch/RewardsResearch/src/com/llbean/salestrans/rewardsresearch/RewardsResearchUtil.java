package com.llbean.salestrans.rewardsresearch;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RewardsResearchUtil {

	private static Gson gson;
	private static final Logger LOGGER = Logger.getLogger("RewardsResearch");
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	/**
	 * Format an object in Gson with pretty printing
	 * @param object The object to format
	 * @return A formatted JSON representation of object
	 */

	public static String objectToPrettyJSON(Object object) {
		return getGson().toJson(object);
	}

	/**
	 * Format a date
	 * @param date The date
	 * @return A string date with format yyyyMMdd
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * Get a Gson instance
	 * @return A Gson instance with pretty printing on
	 */
	public static Gson getGson() {
		if(gson == null) {
			gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd")
					.setPrettyPrinting() // Easy to read
					.create();
		}
		return gson;
	}

	/**
	 * Log a message with Log4j at the info level. Accepts String.format() arguments such as %s, %d, etc. Handles a
	 * MissingFormatArgumentException and logs the message un-formatted.
	 * @param s The message
	 * @param args The format arguments
	 */
	public static void log(String s, Object...args) {
		try {
			LOGGER.info(String.format(s, args));
		} catch (Exception e) {
			LOGGER.info("Could not log message due to exception: " + e.getMessage() + ". Message was " + s + " and arguments were " + Arrays.toString(args));
		}
	}

	/**
	 * Log a message with Log4j at the debug level. Accepts String.format() arguments such as %s, %d, etc. Handles a
	 * MissingFormatArgumentException and logs the message un-formatted.
	 * @param s The message
	 * @param args The format arguments
	 */
	public static void debug(String s, Object...args) {
		try {
			LOGGER.debug(String.format(s, args));
		} catch (Exception e) {
			LOGGER.debug("Could not log message due to exception: " + e.getMessage() + ". Message was " + s + " and arguments were " + Arrays.toString(args));
		}
	}

	/**
	 * Log a message with Log4j at the error level. Accepts String.format() arguments such as %s, %d, etc. Handles a
	 * MissingFormatArgumentException and logs the message un-formatted.
	 * @param s The message
	 * @param args The format arguments
	 */
	public static void error(String s, Object...args) {
		try {
			LOGGER.error(String.format(s, args));
		} catch (Exception e) {
			LOGGER.error("Could not log message due to exception: " + e.getMessage() + ". Message was " + s + " and arguments were " + Arrays.toString(args));
		}
	}

	/** Generate HTTP response headers
	 *
	 * @param status HTTP response status code
	 * @return A ResponseBuilder object with the base headers added
	 */
	public static Response.ResponseBuilder generateHeaders(int status) {
		return Response.status(status).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "POST")
				.header("Access-Control-Max-Age", "1209600");
	}
}
