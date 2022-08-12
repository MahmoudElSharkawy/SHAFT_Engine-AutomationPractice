package api.restfulbooker.objectModels;

import org.json.simple.JSONObject;

import com.shaft.driver.SHAFT;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestfulBookerApiBooking {
    private SHAFT.API apiObject;

    // Services Names
    private String booking_serviceName = "booking/";

    // Constructor
    public RestfulBookerApiBooking(SHAFT.API apiObject) {
	this.apiObject = apiObject;
    }
      
    //////////////////////////////////////////////////////
    ////////////////////// Actions //////////////////////

    @Step("Get all Booking Ids")
    public Response getBookingIds() {
	return apiObject.get(booking_serviceName).perform();
    }
    
    @Step("Get and filter the Booking Ids By FirstName: [{firstName}] and Last Name: [{lastName}]")
    public Response getBookingIds(String firstName, String lastName) {
	return apiObject.get(booking_serviceName)
	.setUrlArguments("firstname=" + firstName + "&lastname=" + lastName)
	.perform();
    }

    @Step("Get a Booking details by the Booking Id: [{bookingId}]")
    public Response getBooking(String bookingId) {
	return apiObject.get(booking_serviceName + bookingId).perform();
    }

    @Step("Create Booking")
    public Response createBooking(String firstName, String lastName, int totalPrice, boolean depositePaid,
	    String checkIn, String checkOut, String additionalNeeds) {
	return apiObject.post(booking_serviceName)
		.setRequestBody(createBookingBody(firstName, lastName, totalPrice, depositePaid, checkIn, checkOut, additionalNeeds))
		.setContentType(ContentType.JSON)
		.perform();
    }

    @Step("Delete a Booking by Id: [{bookingId}]")
    public Response deleteBooking(String bookingId) {
	return apiObject.delete(booking_serviceName + bookingId)
		.setTargetStatusCode(RestfulBookerApi.SUCCESS_DELETE)
		.perform();
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
	
	/* NOTE!!: We use the java.util.ArrayList<Object> when we want to have an ArrayList that has a JSONObject that's inside a big JSONObject
	 * Example:
	 * ArrayList<Object> arrayObject = new ArrayList<>();
	 * arrayObject.add(JSONObject); 
	 * {
	 * 	{
	 * 	},
	 * 	[
	 * 		{
	 * 		} 
	 * 	],
	 * 	{
	 * 	}
	 * }
	 */
    }

}
