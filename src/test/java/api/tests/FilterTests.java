package api.tests;

import java.util.List;

import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;

import io.restassured.response.Response;

public class FilterTests {
    SHAFT.API apiObject;

    @Test
    public void testFilterExpression() {
	apiObject = new SHAFT.API("https://jsonplaceholder.typicode.com");
	apiObject.get("/users").perform();
	apiObject.assertThatResponse().extractedJsonValue("$[?(@.name=='Chelsey Dietrich')].id").isEqualTo("5").perform();
    }

    @Test
    public void testArrayIndex() {
	apiObject = new SHAFT.API("https://jsonplaceholder.typicode.com");
	apiObject.get("/users").perform();
	apiObject.assertThatResponse().extractedJsonValue("$[3].address.city").isEqualTo("South Elvis").perform();
    }
    
    @Test
    public void getResponseJSONValueAsList_test() {
	apiObject = new SHAFT.API("https://jsonplaceholder.typicode.com");
	Response getUsers = apiObject.get("/users").perform();

	List<Object> usersList = RestActions.getResponseJSONValueAsList(getUsers, "$");
	String userName = "";
	String userCity = "";
	for (Object user : usersList) {
	    if (RestActions.getResponseJSONValue(user, "id").equals("5")) {
		userName = RestActions.getResponseJSONValue(user, "name");
		userCity = RestActions.getResponseJSONValue(user, "address.city");
	    }
	}
	Validations.assertThat().object(userName).isEqualTo("Chelsey Dietrich").perform();
	Validations.assertThat().object(userCity).isEqualTo("Roscoeview").perform();
    }
}
