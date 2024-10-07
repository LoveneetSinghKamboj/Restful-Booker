package org.NAGP.Assignment.Rest.Utilities.apitest.baseAPI;


import io.restassured.response.ValidatableResponse;
import org.NAGP.Assignment.Rest.POJO.Request.BookingDetails;
import org.NAGP.Assignment.Rest.POJO.Request.Login;
import org.NAGP.Assignment.Rest.Utilities.apitest.actions.HttpOperation;
import org.NAGP.Assignment.Rest.Utilities.apitest.restassuredFuntions.API;

public class Auth extends API {

	public ValidatableResponse getLoginToken(Login login) {
		initBase("uri");
		setHeader("Content-Type","application/json");
		setBody(login);
		return methodCall("/auth", HttpOperation.POST);
	}

	public ValidatableResponse CreateBooking(BookingDetails bookingDetails) {
		initBase("uri");
		setHeader("Content-Type","application/json");
		setHeader("accept","application/json");
		setBody(bookingDetails);
		return methodCall("/booking", HttpOperation.POST);
	}
	public ValidatableResponse GetBookingIDS() {
		initBase("uri");
		setHeader("Content-Type","application/json");
		setHeader("accept","application/json");
		return methodCall("/booking", HttpOperation.GET);
	}
	public ValidatableResponse GetBookingID(int bookingId) {
		initBase("uri");
		setHeader("Content-Type","application/json");
		setHeader("accept","application/json");
		return methodCall("/booking/"+bookingId, HttpOperation.GET);
	}
	public ValidatableResponse UpdateBooking(BookingDetails bookingDetails,int bookingId,String token) {
		initBase("uri");
		setHeader("Content-Type","application/json");
		setHeader("accept","application/json");
		setHeader("Cookie","token="+token);
		setBody(bookingDetails);
		return methodCall("/booking/"+bookingId, HttpOperation.PUT);
	}
	public ValidatableResponse PartialUpdateBooking(BookingDetails bookingDetails,int bookingId,String token) {
		initBase("uri");
		setHeader("Content-Type","application/json");
		setHeader("accept","application/json");
		setHeader("Cookie","token="+token);
		setBody(bookingDetails);
		return methodCall("/booking/"+bookingId, HttpOperation.PATCH);
	}
	public ValidatableResponse DeleteBooking(int bookingId,String token) {
		initBase("uri");
		setHeader("Content-Type","application/json");
		setHeader("accept","application/json");
		setHeader("Cookie","token="+token);
		return methodCall("/booking/"+bookingId, HttpOperation.DELETE);
	}

	
	
}
