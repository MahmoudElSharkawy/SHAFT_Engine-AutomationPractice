package objectModels;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestfulBookerApiBooking {
    // Variables
    private RestActions apiObject;

    // Service Names
    private String booking_serviceName = "booking";

    // Constructor
    public RestfulBookerApiBooking(RestActions apiObject) {
	this.apiObject = apiObject;
    }

    @Step("Get Booking Ids")
    public Response getBookingIds() {
	return apiObject.buildNewRequest(booking_serviceName, RequestType.GET).performRequest();
    }
    
    @Step("Get Booking Ids By Names")
    public Response getBookingIdsByNames(String firstName, String lastName) {
	return apiObject.buildNewRequest(booking_serviceName, RequestType.GET)
	.setUrlArguments("firstname=" + firstName + "&lastname=" + lastName)
	.performRequest();
    }

    @Step("Get Booking")
    public Response getBooking(String bookingId) {
	return apiObject.buildNewRequest("booking/" + bookingId, RequestType.GET).performRequest();
    }

    @Step("Create Booking")
    public Response createBooking(String firstName, String lastName, int totalPrice, boolean depositePaid,
	    String checkIn, String checkOut, String additionalNeeds) {
	return apiObject.buildNewRequest(booking_serviceName, RequestType.POST)
		.setRequestBody(createBookingBody(firstName, lastName, totalPrice, depositePaid, checkIn, checkOut, additionalNeeds))
		.setContentType(ContentType.JSON)
		.performRequest();
    }

    @Step("Delete Booking")
    public Response deleteBooking(String bookingId) {
	return apiObject.buildNewRequest(booking_serviceName + "/" + bookingId, RequestType.DELETE)
		.setTargetStatusCode(RestfulBookerApi.SUCCESS_DELETE)
		.performRequest();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    private JSONObject createBookingBody(String firstName, String lastName, int totalPrice, boolean depositePaid,
	    String checkIn, String checkOut, String additionalNeeds) {
	JSONObject createBookingBody = new JSONObject();
	JSONObject bookingDates = new JSONObject();
	createBookingBody.put("firstname", firstName);
	createBookingBody.put("lastname", lastName);
	createBookingBody.put("totalprice", totalPrice);
	createBookingBody.put("depositpaid", depositePaid);
	bookingDates.put("checkin", checkIn);
	bookingDates.put("checkout", checkOut);
	createBookingBody.put("bookingdates", bookingDates);
	createBookingBody.put("additionalneeds", additionalNeeds);

	return createBookingBody;
    }

}
