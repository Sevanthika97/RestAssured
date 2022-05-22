Feature: Validating Place API's
@AddPlace
Scenario Outline: Verify if Place is being successfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When User calls "AddPlaceAPI" with "POST" http request
	Then the API call got success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "App" 
	And verify place_id created maps to "<name>" using "getPlaceAPI"
	
Examples:
	|name       | language | address         |
	|Sevanthika | English  |World CrossCenter|
#	|Praveen    | Kannada  | Seacross center |

@DeletePlace
Scenario: Verify if Delete Place functionality is working

	Given DeletePlace payload
	When User calls "deletePlaceAPI" with "POST" http request
	Then the API call got success with status code 200
	And "status" in response body is "OK"
		

