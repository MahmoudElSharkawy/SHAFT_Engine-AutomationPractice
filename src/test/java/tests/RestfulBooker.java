package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.validation.Assertions;
import com.shaft.validation.Verifications;

import io.restassured.response.Response;
import objectModels.RestfulBookerApi;
import objectModels.RestfulBookerApiBooking;

public class RestfulBooker {
    private RestActions apiObject;
    private RestfulBookerApi restfulBookerApi;
    private RestfulBookerApiBooking restfulBookerApiBooking;

    @BeforeClass
    public void beforeClass() {
	apiObject = new RestActions(RestfulBookerApi.BASE_URL);
	restfulBookerApi = new RestfulBookerApi(apiObject);
	restfulBookerApiBooking = new RestfulBookerApiBooking(apiObject);
	
	restfulBookerApi.login("admin", "password123");
    }
    
    ////////////////////////// TEST CASES! ////////////////////////////

    @Test
    public void getBookingIds() {
	restfulBookerApiBooking.getBookingIds();
    }

    @Test
    public void getBooking() {
	restfulBookerApiBooking.getBooking("1");
    }

    @Test
    public void createBooking() {
	// Create Booking
	Response createBookingRes = restfulBookerApiBooking.createBooking("Mahmoud", "ElSharkawy", 1000, true, "2020-01-01",
		"2021-01-01", "IceCream");
	// Get the created booking id
	String bookingId = RestActions.getResponseJSONValue(createBookingRes, "bookingid");

	// Get the created booking values
	Response getBookingRes = restfulBookerApiBooking.getBooking(bookingId);
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
	Response getBookingId = restfulBookerApiBooking.getBookingIdsByNames("Mahmoud", "ElSharkawy");
	String bookingId = RestActions.getResponseJSONValue(getBookingId, "bookingid[0]");

	Response deleteBooking = restfulBookerApiBooking.deleteBooking(bookingId);

	String deleteBookingBody = RestActions.getResponseBody(deleteBooking);
	Assertions.assertEquals("Created", deleteBookingBody);

    }

}
