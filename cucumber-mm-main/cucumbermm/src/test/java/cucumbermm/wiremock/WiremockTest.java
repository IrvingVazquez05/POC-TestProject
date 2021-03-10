package cucumbermm.wiremock;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockTest {
	private final int PORT= 8060; 

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT); // If no-args constructor defaults to port 8080


    @BeforeEach
    public void startWiremock(){
        wireMockRule.start();
    }

    @AfterEach
    public void closeWiremock(){
        wireMockRule.stop();
    }
    
    @Test
    public void exampleTest() throws IOException {

        configureFor("localhost", PORT);
        stubFor(get(urlEqualTo("/baeldung"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Welcome to Baeldung!")));

        
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Send request to service
        HttpGet request = new HttpGet("http://localhost:" + PORT + "/baeldung");
        HttpResponse httpResponse = httpClient.execute(request);

        String responseString = convertResponseToString(httpResponse);
        
        // Verify content of response
        verify(getRequestedFor(urlEqualTo("/baeldung")));
        Assertions.assertEquals("Welcome to Baeldung!", responseString);
        System.out.println(responseString);

    }

    private String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }

}
