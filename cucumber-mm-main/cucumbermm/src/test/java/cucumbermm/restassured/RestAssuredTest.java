package cucumbermm.restassured;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class RestAssuredTest {

    private final int PORT = 9075;
    private Response response;    

    @Rule
    public WireMockRule wiremock = new WireMockRule(PORT);
    
    /*
     * Stop WireMock by the end of the test
     */
    @After
    public void closeWiremock() {
    	wiremock.stop();
    }
    
    /*
     * Start and configure WireMock for port: {portNumber}
     */
    @Before
    public void setWiremock() 
    {  	
    	wiremock.start();
    	WireMock.configureFor(PORT);
    	WireMock.reset();
    	
    	// Set base URI for services
    	RestAssured.baseURI = "http://localhost:" + PORT;

    }
    
    @Given("the services are running")
    public void serviceRunning() {
    	
    	//Create mocked endpoint 
    	wiremock.stubFor(get(urlEqualTo("/baeldung"))
	            .willReturn(aResponse()
	            		.withHeader("Content-Type","application/json; charset=\"UTF-8\"")
	                    .withStatus(200)
	                    .withBody("Welcome to Baeldung!")));
    }

    @When("I send a get request to endpoint {string}")
    public void sendRequest(String endpoint) {
    	
    	response = RestAssured.given()
    			.when()
    			.get(RestAssured.baseURI + endpoint)
    			.then().extract().response();
    }
    
    @Then("response status code should be {int}")
    public void checkResponseStatusCode(int code) {
    	//Assert status code with RestAssure
    	response.then().assertThat().statusCode(200);
    	
    	//Assert with JUnit Assertions 
        Assertions.assertEquals(code, response.getStatusCode());
    }

    @And("response is {string}")
    public void verifyResponse(String string) {    	
    	Assertions.assertEquals(string, response.getBody().asString());
    }
}