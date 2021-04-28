package objectModels;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestfulBookerApi {
    // Variables
    private RestActions apiObject;

    public static final String BASE_URL = System.getProperty("baseUrl");

    // Status Codes
    public static final int SUCCESS = 200;
    public static final int SUCCESS_DELETE = 201;

    // Service Names
    private String auth_serviceName = "auth";

    // Constructor
    public RestfulBookerApi(RestActions apiObject) {
	this.apiObject = apiObject;
    }

    @SuppressWarnings("unchecked")
    public void login(String username, String password) {
	JSONObject authentication = new JSONObject();
	authentication.put("username", username);
	authentication.put("password", password);
	Response createToken = apiObject.performRequest(RequestType.POST, SUCCESS, auth_serviceName, authentication,
		ContentType.JSON);
//	Response createToken = apiObject.buildNewRequest(auth_serviceName, RequestType.POST)
//		.setRequestBody(authentication)
//		.setContentType(ContentType.JSON)
//		.performRequest();
	String token = RestActions.getResponseJSONValue(createToken, "token");
	apiObject.addHeaderVariable("Cookie", "token=" + token);
    }

}
