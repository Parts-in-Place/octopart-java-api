package com.partsinplace.octopart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Octopart {

	static private Octopart theInstance = null;
	final static private String OCTOPART_URL = "http://octopart.com/api/v2/parts/search";
	static private String API_KEY = "EXAMPLE_KEY";

	private Octopart() {
		/* Singleton */
	}

	public static Octopart getInstance() {
		if (theInstance == null) {
			theInstance = new Octopart();
		}
		return theInstance;
	}

	public static Octopart getInstance(String apiKey) {
		setApiKey(apiKey);
		return getInstance();
	}

	public static void setApiKey(String apiKey) {
		API_KEY = apiKey;
	}

	public List<Part> searchParts(String query) throws IOException,
			ParseException {

		List<Part> results = null;

		query = replaceBlanks(query);

		String paramString = "?q=" + query + "&apikey=" + API_KEY;

		URL url = new URL(OCTOPART_URL + paramString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() == 400) {
			/*
			 * This means BAD_REQUEST -- our search query was malformed.
			 */
		}

		if (conn.getResponseCode() == 401) {
			/*
			 * UNAUTHORIZED -- the API key was invalid.
			 */
		}

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Octopart returned HTTP error code: "
					+ conn.getResponseCode() + " " + conn.getResponseMessage());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		StringBuilder output = new StringBuilder();
		String line = "";

		while ((line = br.readLine()) != null) {
			output.append(line);
		}

		conn.disconnect();
		results = getPartsFromJSONData(output.toString());

		return results;
	}

	private String replaceBlanks(String query) {
		return query.replaceAll("\\s+", "+");
	}

	private List<Part> getPartsFromJSONData(String jsonResponse)
			throws ParseException {

		List<Part> parts = new ArrayList<Part>();

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(jsonResponse);
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray results = (JSONArray) jsonObject.get("results");
		for (Object o : results) {
			JSONObject result = (JSONObject) o;
			JSONObject item = (JSONObject) result.get("item");
			if (item != null) {
				parts.add(new Part(item));
			}
		}

		return parts;
	}
}
