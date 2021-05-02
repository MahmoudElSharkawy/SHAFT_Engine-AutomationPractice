package api.restfulbooker.objectModels;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestfulBookerApiBooking {
    private RestActions apiObject;

    // Services Names
    private String booking_serviceName = "booking";

    // Constructor
    public RestfulBookerApiBooking(RestActions apiObject) {
	this.apiObject = apiObject;
    }
      
    //////////////////////////////////////////////////////
    ////////////////////// Actions //////////////////////

    @Step("Get all Booking Ids")
    public Response getBookingIds() {
	return apiObject.buildNewRequest(booking_serviceName, RequestType.GET).performRequest();
    }
    
    @Step("Get and filter the Booking Ids By FirstName: [{firstName}] and Last Name: [{lastName}]")
    public Response getBookingIds(String firstName, String lastName) {
	return apiObject.buildNewRequest(booking_serviceName, RequestType.GET)
	.setUrlArguments("firstname=" + firstName + "&lastname=" + lastName)
	.performRequest();
    }

    @Step("Get a Booking details by the Booking Id: [{bookingId}]")
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

    @Step("Delete a Booking by Id: [{bookingId}]")
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
	createBookingBody.put("firstname", firstName);
	createBookingBody.put("lastname", lastName);
	createBookingBody.put("totalprice", totalPrice);
	createBookingBody.put("depositpaid", depositePaid);
	JSONObject bookingDates = new JSONObject();
	bookingDates.put("checkin", checkIn);
	bookingDates.put("checkout", checkOut);
	createBookingBody.put("bookingdates", bookingDates);
	createBookingBody.put("additionalneeds", additionalNeeds);

	return createBookingBody;
    }

}
