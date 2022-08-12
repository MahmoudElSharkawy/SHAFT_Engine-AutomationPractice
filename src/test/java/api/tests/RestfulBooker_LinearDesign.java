package api.tests;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestfulBooker_LinearDesign {
    private SHAFT.API apiObject;

    @SuppressWarnings("unchecked")
    @BeforeClass
    public void beforeClass() {
	// Initialise the API Driver object to start from here!
	apiObject = new SHAFT.API("https://restful-booker.herokuapp.com/");

	// Login
	JSONObject authentication = new JSONObject();
	authentication.put("username", "admin");
	authentication.put("password", "password123");
	Response createToken = apiObject.post("auth")
		.setRequestBody(authentication)
		.setContentType(ContentType.JSON)
		.perform();
	String token = RestActions.getResponseJSONValue(createToken, "token");
	apiObject.addHeader("Cookie", "token=" + token);
    }

    @Test
    public void getBookingIds() {
	apiObject.get("booking").perform();
    }

    @Test
    public void getBooking() {
	apiObject.get("booking/" + "1").perform();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void createBooking() {
	// JSON Objects
	JSONObject createBookingBody = new JSONObject();
	JSONObject bookingDates = new JSONObject();
	createBookingBody.put("firstname", "Mahmoud");
	createBookingBody.put("lastname", "ElSharkawy");
	createBookingBody.put("totalprice", 1000);
	createBookingBody.put("depositpaid", true);
	bookingDates.put("checkin", "2020-01-01");
	bookingDates.put("checkout", "2021-01-01");
	createBookingBody.put("bookingdates", bookingDates);
	createBookingBody.put("additionalneeds", "IceCream");

	// Create new booking
	Response createBookingRes = apiObject.post("booking")
		.setRequestBody(createBookingBody)
		.setContentType(ContentType.JSON)
		.perform();
	String bookingId = RestActions.getResponseJSONValue(createBookingRes, "bookingid");

	// Get the created booking values
	apiObject.get("booking/" + bookingId).perform();
	apiObject.assertThatResponse().extractedJsonValue("firstname").isEqualTo("Mahmoud").perform();
	apiObject.assertThatResponse().extractedJsonValue("lastname").isEqualTo("ElSharkawy").perform();
	apiObject.assertThatResponse().extractedJsonValue("bookingdates.checkin").isEqualTo("2020-01-01").perform();
	apiObject.assertThatResponse().extractedJsonValue("bookingdates.checkout").isEqualTo("2021-01-01").perform();
	apiObject.assertThatResponse().extractedJsonValue("totalprice").isEqualTo("1000").perform();

	apiObject.assertThatResponse()
		.isEqualToFileContent(System.getProperty("testJsonFolderPath") + "RestfulBooker/booking.json")
		.perform();
    }

    @Test(dependsOnMethods = { "createBooking" })
    public void deleteBooking() {
	Response getBookingId = apiObject.get("booking")
		.setUrlArguments("firstname=Mahmoud&lastname=ElSharkawy")
		.perform();
	String bookingId = RestActions.getResponseJSONValue(getBookingId, "$[0].bookingid");

	Response deleteBooking = apiObject.delete("booking/" + bookingId)
		.setTargetStatusCode(201)
		.perform();

	RestActions.getResponseBody(deleteBooking);
	
	apiObject.assertThatResponse().extractedJsonValue("$").isEqualTo("Created").perform();
    }

}
