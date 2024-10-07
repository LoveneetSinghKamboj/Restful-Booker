package org.NAGP.Assignment.Rest.POJO.Response;

public class Booking
{
       private String firstname;
       private String lastname;
      private int totalprice;
      private boolean depositpaid;
    private String additionalneeds;

    private bookingdates bookingdates;

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public bookingdates getBookingdates() {
        return bookingdates;
    }

   
}
