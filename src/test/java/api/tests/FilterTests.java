package api.tests;

import org.testng.annotations.Test;

import com.shaft.driver.SHAFT;

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
}
