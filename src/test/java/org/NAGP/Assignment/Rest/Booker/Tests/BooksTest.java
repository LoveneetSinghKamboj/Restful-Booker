package org.NAGP.Assignment.Rest.Booker.Tests;


import com.aventstack.extentreports.ExtentTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.NAGP.Assignment.Rest.Booker.Hooks.hooks;
import org.NAGP.Assignment.Rest.Booker.Utils.Logs.Log;
import org.NAGP.Assignment.Rest.POJO.Request.BookingDetails;
import org.NAGP.Assignment.Rest.POJO.Request.Bookingdates;
import org.NAGP.Assignment.Rest.POJO.Response.BookingDetailsResponse;
import org.NAGP.Assignment.Rest.Utilities.apitest.baseAPI.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;


import static org.NAGP.Assignment.Rest.Booker.Utils.ExtentReports.ExtentTestManager.*;


public class BooksTest extends hooks {

    BookingDetailsResponse bookingDetailsResponse;
    String token;
    private ExtentTest extentTest;
    private final String sheetName = properties.getProperty("busHirePageSheetName");

    @Test(priority = 1,description = "Test Case used to validate Credentials")
    public void testCase10_CreateToken(Method method) throws InterruptedException {
        extentTest = startTest(method.getName(), "Test Case used to validate Credentials");
        Log.info("Properties File Loaded");
       // extentTest.log(LogStatus.INFO, "Asserting response code");
        login.setUsername(helper.loadProperties("username"));
        Log.info("Set Username");
        login.setPassword(helper.loadProperties("password"));
        Log.info("Set Password");
        Auth auth = new Auth();
        ValidatableResponse response = auth.getLoginToken(login);
        Log.info("Login Api Called");
        Assert.assertEquals(response.extract().statusCode(), 200);
        Log.info("validate Status code");
        JsonPath jsonPath = response.extract().jsonPath();
        Log.info("validate main response");
        token = jsonPath.get("token");
        Log.info("Main token set to variable");

    }

    @Test(priority = 1,description = "Test Case used to validate with Invalid Credentials")
    public void testCase2_InvalidCreateToken(Method method) throws InterruptedException {
        extentTest = startTest(method.getName(), "Test Case used to validate with Invalid Credentials");
        Log.info("Properties File Loaded");
        login.setUsername(helper.loadProperties("incorrectUsername"));
        Log.info("Set Username");
        login.setPassword(helper.loadProperties("incorrectPassword"));
        Log.info("Set Password");
        Auth auth = new Auth();
        ValidatableResponse response = auth.getLoginToken(login);
        Log.info("Login Api Called");
        Assert.assertEquals(response.extract().statusCode(), 200);
        Log.info("validate Status code");
        JsonPath jsonPath = response.extract().jsonPath();
        assert jsonPath.get("reason").equals("Bad credentials");
        Log.info("Validate response as Bad Credentials");
    }

    @Test(dataProvider = "User Details",description = "Test Case used to Create a New Booking")
    public void testCase3_CreateBookingWithDataProvider(Method method,String firstName, String lastName, boolean depositPaid, int totalPrice, String additionalNeeds, String checkIn, String checkOut) throws InterruptedException {
        extentTest = startTest(method.getName(), "Test Case used to Create a New Booking");
        BookingDetails bookingDetails = new BookingDetails();
        Log.info("Booking Object Created");
        bookingDetails.setFirstname(firstName);
        Log.info("Set FirstName");
        bookingDetails.setLastname(lastName);
        Log.info("Set LastName");
        bookingDetails.setDepositPaid(depositPaid);
        Log.info("Set Deposit Paid");
        bookingDetails.setTotalPrice(totalPrice);
        Log.info("Set Total Price");
        bookingDetails.setAdditionalNeeds(additionalNeeds);
        Log.info("Set Additional Needs");
        Bookingdates bookingDates = new Bookingdates();
        Log.info("Booking Dates Object Created");
        bookingDates.setCheckIN(checkIn);
        Log.info("Set Checkin Date");
        bookingDates.setCheckOut(checkOut);
        Log.info("Set Checkout Date");
        bookingDetails.setDates(bookingDates);
        Log.info("Booking Object Created");
        Auth auth = new Auth();
        ValidatableResponse response = auth.CreateBooking(bookingDetails);
        Log.info("Create Api Called");
        Assert.assertEquals(response.extract().statusCode(), 200);
        Log.info("Assert Status Code");
        bookingDetailsResponse = response.extract().as(BookingDetailsResponse.class);
        Log.info("Assert Booking Response");
    }



    @Test(description = "Test Case used to get all Booking Ids")
    public void testCase5_GetBookingIDS(Method method) throws InterruptedException {
        extentTest = startTest(method.getName(), "Test Case used to get all Booking Ids");
        Auth auth = new Auth();
        ValidatableResponse response = auth.GetBookingIDS();
        Log.info("GetBooking ID Api Called");
        Assert.assertEquals(response.extract().statusCode(), 200);
        Log.info("Assert Status Code");
    }


    @Test(priority = 2)
    public void testCase9_DeleteBooking() throws InterruptedException {
        Auth auth = new Auth();
        ValidatableResponse response = auth.DeleteBooking(bookingDetailsResponse.getBookingid(), token);
        Log.info("Delete Booking Api Call");
        Assert.assertEquals(response.log().all().extract().statusCode(), 201);
        Log.info("Assert Status Code");
    }

    @DataProvider(name = "User Details")
    public Object[][] getData() {
        return new Object[][]
                {{"Loveneet", "Singh", true, 100, "Breakfast", "2024-10-20", "2024-10-22"}};
    }


}



