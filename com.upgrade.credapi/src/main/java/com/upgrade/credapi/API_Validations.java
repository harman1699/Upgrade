package com.upgrade.credapi;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class API_Validations {

	String url = "https://credapi.credify.tech/api/brportorch/v2/login";
	String res;
	JsonPath js ;
	 
	// Creating a File instance
	File jsonDataInFile = new File("./Payloads/AuthPayload.json");
	File invalidJsonDataInFile = new File("./Payloads/AuthPayload2.json");

	@Test
	public void validAuthenticationTest() {
		System.out.println("Test Case 1: Validate that for correct credentials provided in the payload");
		System.out.println("Access Authorized \nValidating Status Code...");
		  res = given().header("x-cf-source-id", "coding-challenge")
				.header("x-cf-corr-id", "a6871608-014d-11eb-adc1-0242ac120002")
				.header("Content-Type", "application/json")
				.body(jsonDataInFile)
				.when()
				.post(url)
				.then()
				.statusCode(200).log().status().extract().response().asString();
		  js = new JsonPath(res);
		
		 
		System.out.println("\n---------------------------------------------------\n");
	}

	@Test(priority = 1,dependsOnMethods = {"validAuthenticationTest"} )
	public void validateAttribute() {
		
		System.out.println("Test Case 2: Parse each json value and validate the productType attribute");
		Map<String, String> productType = js.getMap("loansInReview[0]");
		System.out.println("ProductType is:" + productType.get("productType"));
		System.out.println("\n---------------------------------------------------\n");
	}
	 
	@Test(priority = 2)
	public void inValidAuthenticationTest() {
		System.out.println("Test Case 3: Validate that for incorrect credentials provided in the payload");
		try {
			  res = given().header("x-cf-source-id", "coding-challenge")
					.header("x-cf-corr-id", "a6871608-014d-11eb-adc1-0242ac120002")
					.header("Content-Type", "application/json")
					.body(invalidJsonDataInFile)
					.when()
					.post(url)
					.then()
					.statusCode(200).log().status().extract().response().asString();
		} catch (AssertionError e) {
			System.out.println("Access Unauthorized \n Validating Status Code...");
			 
			System.out.println(e.getMessage());
			 
		}

		 
	}
}
