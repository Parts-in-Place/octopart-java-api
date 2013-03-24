package com.partsinplace.octopart;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

public class OctopartTest {

	private Octopart octopart = Octopart.getInstance();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		/*
		 * INSERT YOUR OCTOPART API KEY HERE
		 * 
		 * You can get one at http://octopart.com/api/register
		 */
		Octopart.setApiKey("<your_api_key>");
	}

	@Test
	public void test() throws IOException, ParseException {
		List<Part> searchResults = octopart
				.searchParts("Ceramic capacitor 0603");

		assertTrue(searchResults != null && !searchResults.isEmpty());

		for (Part part : searchResults) {
			System.out.println("===========================================");
			System.out.println("Manufacturer: " + part.getManufacturer());
			System.out.println("Manufacturer P/N: " + part.getMpn());
			System.out.println("Market Status: " + part.getMarket_status());
		}
	}
}
