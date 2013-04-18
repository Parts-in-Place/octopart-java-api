package com.partsinplace.octopart;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Part {
	private String uid;
	private String mpn;
	private String manufacturer;
	private String shortDescription;
	private String datasheet;
	private String marketStatus;

	protected Part(JSONObject json) {
		uid = checkForNullAndSetString(json, "uid");
		mpn = checkForNullAndSetString(json, "mpn");

		JSONObject manuf = (JSONObject) json.get("manufacturer");
		manufacturer = checkForNullAndSetString(manuf, "displayname");

		shortDescription = checkForNullAndSetString(json, "short_description");
		marketStatus = checkForNullAndSetString(json, "market_status");

		JSONArray datasheets = (JSONArray) json.get("datasheets");
		if (datasheets != null && !datasheets.isEmpty()) {
			datasheet = checkForNullAndSetString(
					(JSONObject) datasheets.get(0), "url");
		}
	}

	private String checkForNullAndSetString(JSONObject json, String property) {
		if (json == null) {
			return null;
		}
		if (json.get(property) == null) {
			return null;
		}
		return json.get(property).toString();
	}

	public String getMarket_status() {
		return marketStatus;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getDatasheet() {
		return datasheet;
	}

	public String getMpn() {
		return mpn;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getUid() {
		return uid;
	}
}
