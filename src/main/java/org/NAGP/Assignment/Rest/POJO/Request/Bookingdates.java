package org.NAGP.Assignment.Rest.POJO.Request;

public class Bookingdates
{
    public String getCheckIN() {
        return checkin;
    }

    public void setCheckIN(String checkIN) {
        this.checkin = checkIN;
    }

    public String getCheckOut() {
        return checkout;
    }

    public void setCheckOut(String checkOut) {
        this.checkout = checkOut;
    }

    private String checkin;
    private String checkout;
}
