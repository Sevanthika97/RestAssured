package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		
		StrpDefinitionClass s=new StrpDefinitionClass();
		if(StrpDefinitionClass.place_id==null) {
		s.add_place_payload_with("Sevanthika","English","Seacross street");
		s.user_calls_with_http_request("AddPlaceAPI","POST");
		s.verify_place_id_created_maps_to_using("Sevanthika", "GetPlaceAPI");
		}
	}
}
