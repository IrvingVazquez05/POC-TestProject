package cucumbermm.restassured;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;

public class RestAssuredPetTest {
	
	private Response response;
	private ValidatableResponse json;
	private RequestSpecification request;
	private Integer petID;
    private String ENDPOINT_GET_PET_BY_ID = "https://petstore.swagger.io/v2";
    private String SERVICE = "/pet/";
 
	@Given("a pet exists with an petId of {int}")
	public void a_pet_exists_with_petId(int petId){
		request = given().param("q",petId);
		petID = petId; //PetId saved to use in the URL
	}
	
	@When("I send a request to get petService")
	public void a_user_retrieves_the_pet_by_petId(){
		response = request.when().get(ENDPOINT_GET_PET_BY_ID+SERVICE+petID); // URL 
		System.out.println("response: " + response.prettyPrint());
	}
 
	@Then("response status code is {int}")
	public void verify_status_code(int statusCode){
		json = response.then().statusCode(statusCode);
		System.out.println(this.json);
	}
	
	@And("response contains the pet name {string}")
    public void response_equals(String petName) {
		String petN = response.jsonPath().get("name");
		System.out.println("---PetName---");
		System.out.println(petName);
		System.out.println("---petN---");
		System.out.println(petN);		
		String category = response.jsonPath().get("category.name");
		System.out.println("---Category Name---");
		System.out.println(category);	
		
		
		Assertions.assertEquals(petName,petN);
		//System.out.println(petName);
	}
}

