octopart-java-api
=================

Java wrapper to the Octopart API (http://www.octopart.com)

The goal of this project is to provide a set of Java classes that you can "drop into" your project and
quickly access the Octopart API.

This Octopart Java Wrapper is being developed as part of the Parts-in-Place system. http://partsinplace.com

Example Use
===========

```
......
/*
 * INSERT YOUR OCTOPART API KEY HERE
 * 
 * You can get one at http://octopart.com/api/register
 */
Octopart.setApiKey("<your_api_key>");

Octopart octopart = Octopart.getInstance();

/* 
 * You can use any search query here.
 */
List<Part> searchResults = octopart.searchParts("Ceramic capacitor 0603");

for (Part part : searchResults) {
	......
	String manufacturer = part.getManafacturer();
	......
}

.......
```

Refer to <a href="src/com/partsinplace/octopart/OctopartTest.java">OctopartTest.java</a> for a more complete example.

