package api.tests;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;
import com.shaft.driver.DriverFactory;
import com.shaft.validation.Validations;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestfulBooker_LinearDesign {
    private RestActions apiObject;

    @SuppressWarnings("unchecked")
    @BeforeClass
    public void beforeClass() {
	// Initialise the API Driver object to start from here!
	apiObject = DriverFactory.getAPIDriver("https://restful-booker.herokuapp.com/");

	// Login
	JSONObject authentication = new JSONObject();
	authentication.put("username", "admin");
	authentication.put("password", "password123");
	Response createToken = apiObject.buildNewRequest("auth", RequestType.POST)
		.setRequestBody(authentication)
		.setContentType(ContentType.JSON)
		.performRequest();
	String token = RestActions.getResponseJSONValue(createToken, "token");
	apiObject.addHeaderVariable("Cookie", "token=" + token);
    }

    @Test
    public void getBookingIds() {
	apiObject.buildNewRequest("booking", RequestType.GET).performRequest();
    }

    @Test
    public void getBooking() {
	apiObject.buildNewRequest("booking/" + "1", RequestType.GET).performRequest();
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
	Response createBookingRes = apiObject.buildNewRequest("booking", RequestType.POST)
		.setRequestBody(createBookingBody)
		.setContentType(ContentType.JSON)
		.performRequest();
	String bookingId = RestActions.getResponseJSONValue(createBookingRes, "bookingid");

	// Get the created booking values
	Response getBookingRes = apiObject.buildNewRequest("booking/" + bookingId, RequestType.GET).performRequest();
	String firstName = RestActions.getResponseJSONValue(getBookingRes, "firstname");
	String lastName = RestActions.getResponseJSONValue(getBookingRes, "lastname");
	String checkin = RestActions.getResponseJSONValue(getBookingRes, "bookingdates.checkin");
	String checkout = RestActions.getResponseJSONValue(getBookingRes, "bookingdates.checkout");
	String totalprice = RestActions.getResponseJSONValue(getBookingRes, "totalprice");

	// Validations
	Validations.verifyThat().object(firstName).isEqualTo("Mahmoud").perform();
	Validations.verifyThat().response(getBookingRes).extractedJsonValue("firstname").isEqualTo("Mahmoud").perform();
	
	Validations.verifyThat().object(lastName).isEqualTo("ElSharkawy").perform();
	Validations.verifyThat().response(getBookingRes).extractedJsonValue("lastname").isEqualTo("ElSharkawy").perform();

	Validations.verifyThat().object(checkin).isEqualTo("2020-01-01").perform();
	Validations.verifyThat().response(getBookingRes).extractedJsonValue("bookingdates.checkin").isEqualTo("2020-01-01").perform();

	Validations.verifyThat().object(checkout).isEqualTo("2021-01-01").perform();
	Validations.verifyThat().response(getBookingRes).extractedJsonValue("bookingdates.checkout").isEqualTo("2021-01-01").perform();

	Validations.verifyThat().object(totalprice).isEqualTo("1000").perform();
	Validations.verifyThat().response(getBookingRes).extractedJsonValue("totalprice").isEqualTo("1000").perform();

	Validations.assertThat().response(getBookingRes)
		.isEqualToFileContent(System.getProperty("testJsonFolderPath") + "RestfulBooker/booking.json")
		.perform();
    }

    @Test(dependsOnMethods = { "createBooking" })
    public void deleteBooking() {
	Response getBookingId = apiObject.buildNewRequest("booking", RequestType.GET)
		.setUrlArguments("firstname=Mahmoud&lastname=ElSharkawy")
		.performRequest();
	String bookingId = RestActions.getResponseJSONValue(getBookingId, "bookingid[0]");

	Response deleteBooking = apiObject.buildNewRequest("booking/" + bookingId, RequestType.DELETE)
		.setTargetStatusCode(201)
		.performRequest();

	String deleteBookingBody = RestActions.getResponseBody(deleteBooking);
	
	Validations.assertThat().object(deleteBookingBody).isEqualTo("Created").perform();
    }

}
