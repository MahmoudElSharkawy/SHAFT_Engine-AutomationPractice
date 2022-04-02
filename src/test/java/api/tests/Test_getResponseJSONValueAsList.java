package api.tests;

import java.util.List;

import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;
import com.shaft.driver.DriverFactory;
import com.shaft.validation.Validations;

import io.restassured.response.Response;

public class Test_getResponseJSONValueAsList {
    RestActions apiObject;

    @Test
    public void getResponseJSONValueAsList_test() {
	apiObject = DriverFactory.getAPIDriver("https://jsonplaceholder.typicode.com/");

	Response getUsers = apiObject.buildNewRequest("users", RequestType.GET).performRequest();

	List<Object> users = RestActions.getResponseJSONValueAsList(getUsers, "$");
	String userName = "";
	String userCity = "";
	for (Object user : users) {
	    if (RestActions.getResponseJSONValue(user, "id").equals("5")) {
		userName = RestActions.getResponseJSONValue(user, "name");
		userCity = RestActions.getResponseJSONValue(user, "address.city");
	    }
	}
	Validations.assertThat().object(userName).isEqualTo("Chelsey Dietrich").perform();
	Validations.assertThat().object(userCity).isEqualTo("Roscoeview").perform();
    }
}
