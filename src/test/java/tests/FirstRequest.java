package tests;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.ParametersType;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;


public class FirstRequest {
    @Test
    public void test() {
	RestActions apiObject = new RestActions("https://www.phptravels.net/");
	List<List<Object>> formParams = Arrays.asList(
		Arrays.asList("firstname", "mahmoud"), 
		Arrays.asList("lastname", "elsharkawy"),
		Arrays.asList("phone", "12345678901"),
		Arrays.asList("email", "test01@test.com"),
		Arrays.asList("password", "12345678"),
		Arrays.asList("confirmpassword", "12345678"));
	
	apiObject.performRequest(RequestType.POST, 200, "account/signup", formParams, ParametersType.FORM,
		ContentType.URLENC);

//	apiObject.buildNewRequest("account/signup", RequestType.POST)
//		.setTargetStatusCode(200)
//		.setParameters(formParams).setParametersType(ParametersType.FORM)
//		.setContentType(ContentType.URLENC)
//		.performRequest();
    }
}
