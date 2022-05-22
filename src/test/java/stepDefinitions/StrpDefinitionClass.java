package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StrpDefinitionClass extends Utils{
	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data=new TestDataBuild();
	static String place_id; //will be shared across all the test cases in a single run
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException  {
	 
		res=given().spec(requestSpecification())
				.body(data.addPlacePayload(name,language,address));//request
		
	}	
	
	@When("User calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource,String method) {
		
		APIResources resourceAPI= APIResources.valueOf(resource);//valueOf method invokes constructor of enum class with value of resource you pass
		System.out.println(resourceAPI.getResource());
		
		resspec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if(method.equalsIgnoreCase("POST"))
			response=res.when().post(resourceAPI.getResource());//sending to API
		else if(method.equalsIgnoreCase("GET"))
			response=res.when().get(resourceAPI.getResource());//sending to API
//			
	}
	
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
//	  
		assertEquals(response.getStatusCode(),200);

	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedValue) {
	   
		assertEquals(getJsonPath(response,keyValue),ExpectedValue);

	}
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedname, String resource) throws IOException {
	    
		//hit getAPI call
		place_id=getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id",place_id);
		user_calls_with_http_request(resource,"GET");
		String actualname=getJsonPath(response,"name");
		assertEquals(actualname,expectedname);
	}
	

	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException {
	   res=given().spec(requestSpecification())
	   .body(data.deletePlacePayload(place_id));
	}
}
