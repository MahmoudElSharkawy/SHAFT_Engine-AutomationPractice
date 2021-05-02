package api.tests;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.testng.annotations.Test;

import com.shaft.api.RestActions.ParametersType;
import com.shaft.api.RestActions.RequestType;
import com.shaft.driver.DriverFactory;

import io.restassured.http.ContentType;

public class PHPTRAVELS_FirstRequest {
    Date date = new Date();
    String email = "test" + date.getTime() + "@test.com";
    
    @Test
    public void firstRequest() {
	List<List<Object>> formParams = Arrays.asList(
		Arrays.asList("firstname", "mahmoud"),
		Arrays.asList("lastname", "elsharkawy"), 
		Arrays.asList("phone", "12345678901"),
		Arrays.asList("email", email), 
		Arrays.asList("password", "12345678"),
		Arrays.asList("confirmpassword", "12345678"));

	DriverFactory.getAPIDriver("https://www.phptravels.net/")
		.buildNewRequest("account/signup", RequestType.POST)
		.setTargetStatusCode(200)
		.setParameters(formParams, ParametersType.FORM)
		.setContentType(ContentType.URLENC)
		.performRequest();
    }
}
