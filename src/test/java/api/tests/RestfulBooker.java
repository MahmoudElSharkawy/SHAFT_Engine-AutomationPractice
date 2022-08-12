package api.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;

import api.restfulbooker.objectModels.RestfulBookerApi;
import api.restfulbooker.objectModels.RestfulBookerApiBooking;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;

@Epic("API")
@Feature("Restful-Booker")
public class RestfulBooker {
    private SHAFT.API apiObject;
    private RestfulBookerApi restfulBookerApi;
    private RestfulBookerApiBooking restfulBookerApiBooking;

    @BeforeClass
    public void beforeClass() {
	// Initialise the API Driver and the object classes objects to start from here!
	apiObject = new SHAFT.API(RestfulBookerApi.BASE_URL);
	restfulBookerApi = new RestfulBookerApi(apiObject);
	restfulBookerApiBooking = new RestfulBookerApiBooking(apiObject);
	
	restfulBookerApi.login("admin", "password123");
    }
    
    ////////////////////////// TEST CASES! ////////////////////////////

    @Test(description = "Get Booking Ids")
    @Description("Returns the ids of all the bookings that exist within the API. Can take optional query strings to search and return a subset of booking ids.")
    @Severity(SeverityLevel.MINOR)
    @Story("Booking")
    @TmsLink("Test_case")
    @Issue("Software_bug")
    public void getBookingIds() {
	restfulBookerApiBooking.getBookingIds();
    }

    @Test(description = "Get Booking")
    @Description("Returns a specific booking based upon the booking id provided")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Booking")
    @TmsLink("Test_case")
    @Issue("Software_bug")
    public void getBooking() {
	restfulBookerApiBooking.getBooking("1");
    }

    @Test(description = "Create Booking")
    @Description("Creates a new booking in the API")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Booking")
    @TmsLink("Test_case")
    @Issue("Software_bug")
    public void createBooking() {
	// Create Booking
	Response createBookingRes = restfulBookerApiBooking.createBooking("Mahmoud", "ElSharkawy", 1000, true, "2020-01-01",
		"2021-01-01", "IceCream");
	// Get the created booking id
	String bookingId = RestActions.getResponseJSONValue(createBookingRes, "bookingid");

	// Get the created booking parameters values
	restfulBookerApiBooking.getBooking(bookingId);
	apiObject.assertThatResponse().extractedJsonValue("firstname").isEqualTo("Mahmoud").perform();
	apiObject.assertThatResponse().extractedJsonValue("lastname").isEqualTo("ElSharkawy").perform();
	apiObject.assertThatResponse().extractedJsonValue("bookingdates.checkin").isEqualTo("2020-01-01").perform();
	apiObject.assertThatResponse().extractedJsonValue("bookingdates.checkout").isEqualTo("2021-01-01").perform();
	apiObject.assertThatResponse().extractedJsonValue("totalprice").isEqualTo("1000").perform();

	apiObject.assertThatResponse()
		.isEqualToFileContent(System.getProperty("testJsonFolderPath") + "RestfulBooker/booking.json")
		.perform();

    }

    @Test(description = "Delete Booking", dependsOnMethods = { "createBooking" })
    @Description("Returns the ids of all the bookings that exist within the API. Can take optional query strings to search and return a subset of booking ids.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Booking")
    @TmsLink("Test_case")
    @Issue("Software_bug")
    public void deleteBooking() {
	Response getBookingId = restfulBookerApiBooking.getBookingIds("Mahmoud", "ElSharkawy");
	String bookingId = RestActions.getResponseJSONValue(getBookingId, "$[0].bookingid");

	restfulBookerApiBooking.deleteBooking(bookingId);
	apiObject.assertThatResponse().extractedJsonValue("$").isEqualTo("Created").perform();
	
    }

}
