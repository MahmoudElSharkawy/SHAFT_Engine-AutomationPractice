package tests;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;
import com.shaft.validation.Assertions;
import com.shaft.validation.Verifications;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestfulBooker_LinearDesign {
    private RestActions apiObject;

    @SuppressWarnings("unchecked")
    @BeforeClass
    public void beforeClass() {
	apiObject = new RestActions("https://restful-booker.herokuapp.com/");

	JSONObject authentication = new JSONObject();
	authentication.put("username", "admin");
	authentication.put("password", "password123");
	Response createToken = apiObject.performRequest(RequestType.POST, 200, "auth", authentication,
		ContentType.JSON);
//	Response createToken = apiObject.buildNewRequest("auth", RequestType.POST)
//		.setRequestBody(authentication)
//		.setContentType(ContentType.JSON)
//		.performRequest();
	String token = RestActions.getResponseJSONValue(createToken, "token");
	apiObject.addHeaderVariable("Cookie", "token=" + token);
    }

    @Test
    public void getBookingIds() {
	apiObject.performRequest(RequestType.GET, 200, "booking");
//	apiObject.buildNewRequest("booking", RequestType.GET).performRequest();
    }

    @Test
    public void getBooking() {
	apiObject.performRequest(RequestType.GET, 200, "booking/" + "1");
//	apiObject.buildNewRequest("booking/" + "1", RequestType.GET).performRequest();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void createBooking() {
	// JSON Objects
	JSONObject createBookingBody = new JSONObject();
	JSONObject bookingDates = new JSONObject();
	bookingDates.put("checkin", "2020-01-01");
	bookingDates.put("checkout", "2021-01-01");
	createBookingBody.put("firstname", "Mahmoud");
	createBookingBody.put("lastname", "ElSharkawy");
	createBookingBody.put("totalprice", 1000);
	createBookingBody.put("depositpaid", true);
	createBookingBody.put("bookingdates", bookingDates);
	createBookingBody.put("additionalneeds", "IceCream");

	// Create new booking
	Response createBookingRes = apiObject.performRequest(RequestType.POST, 200, "booking", createBookingBody,
		ContentType.JSON);
//	Response createBookingRes = apiObject.buildNewRequest("booking", RequestType.POST)
//		.setRequestBody(createBookingBody)
//		.setContentType(ContentType.JSON)
//		.performRequest();
	String bookingId = RestActions.getResponseJSONValue(createBookingRes, "bookingid");

	// Get the created booking values
	Response getBookingRes = apiObject.performRequest(RequestType.GET, 200, "booking/" + bookingId);
//	Response getBookingRes = apiObject.buildNewRequest("booking/" + bookingId, RequestType.GET).performRequest();
	String firstName = RestActions.getResponseJSONValue(getBookingRes, "firstname");
	String lastName = RestActions.getResponseJSONValue(getBookingRes, "lastname");
	String checkin = RestActions.getResponseJSONValue(getBookingRes, "bookingdates.checkin");
	String checkout = RestActions.getResponseJSONValue(getBookingRes, "bookingdates.checkout");

	// Validations
	Verifications.verifyEquals("Mahmoud", firstName);
	Verifications.verifyEquals("ElSharkawy", lastName);
	Verifications.verifyEquals("2020-01-01", checkin);
	Verifications.verifyEquals("2021-01-01", checkout);
	Assertions.assertJSONFileContent(getBookingRes,
		System.getProperty("jsonFolderPath") + "RestfulBooker/booking.json");
    }

    @Test(dependsOnMethods = { "createBooking" })
    public void deleteBooking() {
	Response getBookingId = apiObject.performRequest(RequestType.GET, 200, "booking",
		"firstname=Mahmoud&lastname=ElSharkawy");
//	Response getBookingId = apiObject.buildNewRequest("booking", RequestType.GET)
//	.setUrlArguments("firstname=Mahmoud&lastname=ElSharkawy")
//	.performRequest();
	String bookingId = RestActions.getResponseJSONValue(getBookingId, "bookingid[0]");

	Response deleteBooking = apiObject.performRequest(RequestType.DELETE, 201, "booking/" + bookingId);
//	Response deleteBooking = apiObject.buildNewRequest("booking/" + bookingId, RequestType.DELETE)
//		.setTargetStatusCode(201)
//		.performRequest();

	String deleteBookingBody = RestActions.getResponseBody(deleteBooking);
	Assertions.assertEquals("Created", deleteBookingBody);

    }

}
