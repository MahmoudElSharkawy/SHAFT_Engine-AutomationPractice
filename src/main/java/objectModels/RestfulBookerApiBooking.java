package objectModels;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

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

    public Response getBookingIds() {
	return apiObject.performRequest(RequestType.GET, RestfulBookerApi.SUCCESS, booking_serviceName);
//	return apiObject.buildNewRequest(booking_serviceName, RequestType.GET).performRequest();
    }
    
    public Response getBookingIdsByNames(String firstName, String lastName) {
	return apiObject.performRequest(RequestType.GET, RestfulBookerApi.SUCCESS, booking_serviceName,
		"firstname=" + firstName + "&lastname=" + lastName);
//	return apiObject.buildNewRequest(booking_serviceName, RequestType.GET)
//	.setUrlArguments("firstname=" + firstName + "&lastname=" + lastName)
//	.performRequest();
    }

    public Response getBooking(String bookingId) {
	return apiObject.performRequest(RequestType.GET, RestfulBookerApi.SUCCESS,
		booking_serviceName + "/" + bookingId);
//	apiObject.buildNewRequest("booking/" + "1", RequestType.GET).performRequest();
    }

    public Response createBooking(String firstName, String lastName, int totalPrice, boolean depositePaid,
	    String checkIn, String checkOut, String additionalNeeds) {
	return apiObject.performRequest(RequestType.POST, RestfulBookerApi.SUCCESS, booking_serviceName,
		createBookingBody(firstName, lastName, totalPrice, depositePaid, checkIn, checkOut, additionalNeeds),
		ContentType.JSON);
//	return apiObject.buildNewRequest(booking_serviceName, RequestType.POST)
//		.setRequestBody(createBookingBody(firstName, lastName, totalPrice, depositePaid, checkIn, checkOut, additionalNeeds))
//		.setContentType(ContentType.JSON)
//		.performRequest();
    }

    public Response deleteBooking(String bookingId) {
	return apiObject.performRequest(RequestType.DELETE, RestfulBookerApi.SUCCESS_DELETE,
		booking_serviceName + "/" + bookingId);
//	return apiObject.buildNewRequest(booking_serviceName + "/" + bookingId, RequestType.DELETE)
//		.setTargetStatusCode(RestfulBookerApi.SUCCESS_DELETE)
//		.performRequest();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    private JSONObject createBookingBody(String firstName, String lastName, int totalPrice, boolean depositePaid,
	    String checkIn, String checkOut, String additionalNeeds) {
	// JSON Objects
	JSONObject body = new JSONObject();
	JSONObject bookingDates = new JSONObject();
	body.put("firstname", firstName);
	body.put("lastname", lastName);
	body.put("totalprice", totalPrice);
	body.put("depositpaid", depositePaid);
	bookingDates.put("checkin", checkIn);
	bookingDates.put("checkout", checkOut);
	body.put("bookingdates", bookingDates);
	body.put("additionalneeds", additionalNeeds);

	return body;
    }

}
