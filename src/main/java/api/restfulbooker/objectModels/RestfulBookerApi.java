package api.restfulbooker.objectModels;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestfulBookerApi {
    private RestActions apiObject;

    // Base URL
    public static final String BASE_URL = System.getProperty("baseUrl");

    // Status Codes
    public static final int SUCCESS = 200;
    public static final int SUCCESS_DELETE = 201;

    // Services Names
    private String auth_serviceName = "auth";

    // Constructor
    public RestfulBookerApi(RestActions apiObject) {
	this.apiObject = apiObject;
    }
    
    //////////////////////////////////////////////////////
    ////////////////////// Actions //////////////////////
    
    @SuppressWarnings("unchecked")
    @Step("Login with Username: {username} and Password: {password}")
    public void login(String username, String password) {
	JSONObject authentication = new JSONObject();
	authentication.put("username", username);
	authentication.put("password", password);

	Response createToken = apiObject.buildNewRequest(auth_serviceName, RequestType.POST)
		.setRequestBody(authentication)
		.setContentType(ContentType.JSON)
		.performRequest();
	String token = RestActions.getResponseJSONValue(createToken, "token");
	apiObject.addHeaderVariable("Cookie", "token=" + token);
    }

}
