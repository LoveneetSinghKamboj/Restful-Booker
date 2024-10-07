package org.NAGP.Assignment.Rest.Booker.Tests;


import com.aventstack.extentreports.ExtentTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.NAGP.Assignment.Rest.Booker.Hooks.hooks;
import org.NAGP.Assignment.Rest.Booker.Utils.Logs.Log;
import org.NAGP.Assignment.Rest.POJO.Request.BookingDetails;
import org.NAGP.Assignment.Rest.POJO.Request.Bookingdates;
import org.NAGP.Assignment.Rest.POJO.Response.BookingDetailsResponse;
import org.NAGP.Assignment.Rest.POJO.Response.BookingId;
import org.NAGP.Assignment.Rest.Utilities.apitest.baseAPI.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

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

    @Test(description = "Test Case used to Create a New Booking")
    public void testCase1_CreateBooking(Method method) throws InterruptedException {
        extentTest = startTest(method.getName(), "Test Case used to Create a New Booking");
        HashMap<String, String> testData = new HashMap<String, String>();
        testData = reader.getRowTestData(sheetName, "Createbooking");
        BookingDetails bookingDetails = new BookingDetails();
        Log.info("Booking Object Created");
        bookingDetails.setFirstname(testData.get("Firstname"));
        Log.info("Set FirstName");
        bookingDetails.setLastname(testData.get("LastName"));
        Log.info("Set LastName");
        bookingDetails.setDepositPaid(Boolean.getBoolean( testData.get("DepositPaid")));
        Log.info("Set Deposit Paid");
        bookingDetails.setTotalPrice(Integer.parseInt(testData.get("TotalPrice")));
        Log.info("Set Total Price");
        bookingDetails.setAdditionalNeeds(testData.get("AdditionalNeeds"));
        Log.info("Set Additional Needs");
        Bookingdates bookingDates = new Bookingdates();
        Log.info("Booking Dates Object Created");
        bookingDates.setCheckIN(testData.get("CheckIn"));
        Log.info("Set Checkin Date");
        bookingDates.setCheckOut(testData.get("CheckOut"));
        Log.info("Set Checkout Date");
        bookingDetails.setDates(bookingDates);
        Log.info("Booking Object Created");
        Auth auth = new Auth();
        ValidatableResponse response = auth.CreateBooking(bookingDetails);
        Log.info("Create Api Called");
        Assert.assertEquals(response.extract().statusCode(), 200);
        Log.info("Assert Status Code");
        bookingDetailsResponse = response.extract().as(BookingDetailsResponse.class);
        JsonPath jsonPath = new JsonPath(response.extract().asString());
        Log.info("Assert Booking Response");
    }

    @Test(dependsOnMethods = "testCase1_CreateBooking",description = "Test Case used to get all Booking Ids")
    public void testCase5_GetBookingIDS(Method method) throws InterruptedException {
        extentTest = startTest(method.getName(), "Test Case used to get all Booking Ids");
        Auth auth = new Auth();
        ValidatableResponse response = auth.GetBookingIDS();
        Log.info("GetBooking ID Api Called");
        Assert.assertEquals(response.extract().statusCode(), 200);
        Log.info("Assert Status Code");
        List<BookingId> bookingId = List.of(response.log().all().extract().as(BookingId[].class));
        Assert.assertTrue(bookingId.stream().anyMatch(o -> o.getBookingid() == (bookingDetailsResponse.getBookingid())));
        Log.info("Assert Our Booking Present in Response");
    }

    @Test(dependsOnMethods = "testCase1_CreateBooking",description = "Test Case used to Particular Booking Id")
    public void testCase6_GetBookingID(Method method) throws InterruptedException {
        extentTest = startTest(method.getName(), "Test Case used to Particular Booking Id");
        Auth auth = new Auth();
        ValidatableResponse response = auth.GetBookingID(bookingDetailsResponse.getBookingid());
        Log.info("GetBooking ID Api Called");
        Assert.assertEquals(response.extract().statusCode(), 200);
        Log.info("Assert Status Code");
        JsonPath jsonPath = new JsonPath(response.extract().asString());
        Assert.assertEquals(jsonPath.get("firstname"), bookingDetailsResponse.getBooking().getFirstname());
        Log.info("Assert First Name");
        Assert.assertEquals(jsonPath.get("lastname"), bookingDetailsResponse.getBooking().getLastname());
        Log.info("Assert Last Name");
        Assert.assertEquals(jsonPath.getInt("totalprice"), bookingDetailsResponse.getBooking().getTotalprice());
        Log.info("Assert Total Price");
        Assert.assertEquals(jsonPath.get("depositpaid"), bookingDetailsResponse.getBooking().isDepositpaid());
        Log.info("Assert Deposit Paid");
        Assert.assertEquals(jsonPath.get("additionalneeds"), bookingDetailsResponse.getBooking().getAdditionalneeds());
        Log.info("Assert Additional Needs");
        Assert.assertEquals(jsonPath.get("bookingdates.checkin"), bookingDetailsResponse.getBooking().getBookingdates().getCheckin());
        Log.info("Assert CheckIn Date");
        Assert.assertEquals(jsonPath.get("bookingdates.checkout"), bookingDetailsResponse.getBooking().getBookingdates().getCheckout());
        Log.info("Assert CheckOut Date");
    }

    @Test(dependsOnMethods = {"testCase1_CreateBooking","testCase10_CreateToken","testCase8_PartialUpdateBooking"},description = "Test Case used to Update Booking")
    public void testCase7_UpdateBooking(Method method) throws InterruptedException {
        extentTest = startTest(method.getName(), "Test Case used to Update Booking");
        BookingDetails bookingDetails = new BookingDetails();
        Log.info("Booking Object Created");
        bookingDetails.setFirstname("Love");
        Log.info("Set New First Name");
        bookingDetails.setLastname("Kamboj");
        Log.info("Set New Last Name");
        bookingDetails.setDepositPaid(true);
        Log.info("Set Deposit Paid");
        bookingDetails.setTotalPrice(100);
        Log.info("Set Total Price");
        bookingDetails.setAdditionalNeeds("Breakfast");
        Log.info("Set Additional Needs");
        Bookingdates bookingDates = new Bookingdates();
        Log.info("Booking Dates Object Created");
        bookingDates.setCheckIN("2024-10-20");
        Log.info("Set Checkin Date");
        bookingDates.setCheckOut("2024-10-22");
        Log.info("Set Checkout Date");
        bookingDetails.setDates(bookingDates);
        Auth auth = new Auth();
        ValidatableResponse response = auth.UpdateBooking(bookingDetails, bookingDetailsResponse.getBookingid(), token);
        Log.info("Update Booking Api Call");
        Assert.assertEquals(response.log().all().extract().statusCode(), 200);
        Log.info("Assert Status Code");
        JsonPath jsonPath = new JsonPath(response.extract().asString());
        Assert.assertNotEquals(jsonPath.get("firstname"), bookingDetailsResponse.getBooking().getFirstname());
        Log.info("Assert Not Equal old First Name Value");
        Assert.assertNotEquals(jsonPath.get("lastname"), bookingDetailsResponse.getBooking().getLastname());
        Log.info("Assert Not Equal old Last Name Value");
        Assert.assertEquals(jsonPath.get("firstname"), "Love");
        Log.info("Assert First Name");
        Assert.assertEquals(jsonPath.get("lastname"), "Kamboj");
        Log.info("Assert Last Name");
        Assert.assertEquals(jsonPath.getInt("totalprice"), bookingDetailsResponse.getBooking().getTotalprice());
        Log.info("Assert Total Price");
        Assert.assertEquals(jsonPath.get("depositpaid"), bookingDetailsResponse.getBooking().isDepositpaid());
        Log.info("Assert Deposit Paid");
        Assert.assertEquals(jsonPath.get("additionalneeds"), bookingDetailsResponse.getBooking().getAdditionalneeds());
        Log.info("Assert Additional Needs");
        Assert.assertEquals(jsonPath.get("bookingdates.checkin"), bookingDetailsResponse.getBooking().getBookingdates().getCheckin());
        Log.info("Assert CheckIn Date");
        Assert.assertEquals(jsonPath.get("bookingdates.checkout"), bookingDetailsResponse.getBooking().getBookingdates().getCheckout());
        Log.info("Assert CheckOut Date");
    }

    @Test(dependsOnMethods = {"testCase1_CreateBooking","testCase10_CreateToken"})
    public void testCase8_PartialUpdateBooking() throws InterruptedException {
        BookingDetails bookingDetails = new BookingDetails();
        Log.info("Booking Object Created");
        bookingDetails.setDepositPaid(false);
        Log.info("Set Deposit Paid");
        bookingDetails.setTotalPrice(1000);
        Log.info("Set Total Price");
        Auth auth = new Auth();
        ValidatableResponse response = auth.PartialUpdateBooking(bookingDetails, bookingDetailsResponse.getBookingid(), token);
        Log.info("Partial Update Booking Api Call");
        Assert.assertEquals(response.log().all().extract().statusCode(), 200);
        Log.info("Assert Status Code");
        JsonPath jsonPath = new JsonPath(response.extract().asString());
        Assert.assertNotEquals(jsonPath.get("totalprice"), bookingDetailsResponse.getBooking().getTotalprice());
        Log.info("Assert Not Equal old Total Price Value");
        Assert.assertNotEquals(jsonPath.get("depositpaid"), bookingDetailsResponse.getBooking().isDepositpaid());
        Log.info("Assert Not Equal old Deposit Paid Value");
        Assert.assertEquals(jsonPath.getInt("totalprice"), 1000);
        Log.info("Assert Total Price");
        Assert.assertEquals(jsonPath.get("depositpaid"), false);
        Log.info("Assert Deposit Paid");
        Assert.assertEquals(jsonPath.get("firstname"), bookingDetailsResponse.getBooking().getFirstname());
        Log.info("Assert First Name");
        Assert.assertEquals(jsonPath.get("lastname"), bookingDetailsResponse.getBooking().getLastname());
        Log.info("Assert Last Name");
        Assert.assertEquals(jsonPath.get("additionalneeds"), bookingDetailsResponse.getBooking().getAdditionalneeds());
        Log.info("Assert Additional Needs");
        Assert.assertEquals(jsonPath.get("bookingdates.checkin"), bookingDetailsResponse.getBooking().getBookingdates().getCheckin());
        Log.info("Assert CheckIn Date");
        Assert.assertEquals(jsonPath.get("bookingdates.checkout"), bookingDetailsResponse.getBooking().getBookingdates().getCheckout());
        Log.info("Assert CheckOut Date");    }

    @Test(dependsOnMethods = "testCase1_CreateBooking",priority = 2)
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



