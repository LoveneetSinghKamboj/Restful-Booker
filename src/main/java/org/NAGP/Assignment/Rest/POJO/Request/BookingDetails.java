package org.NAGP.Assignment.Rest.POJO.Request;

public class BookingDetails
{
    private String firstname;
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getTotalPrice() {
        return totalprice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalprice = totalPrice;
    }

    public boolean isDepositPaid() {
        return depositpaid;
    }

    public void setDepositPaid(boolean depositPaid) {
        this.depositpaid = depositPaid;
    }

    public String getAdditionalNeeds() {
        return additionalneeds;
    }

    public void setAdditionalNeeds(String additionalNeeds) {
        this.additionalneeds = additionalNeeds;
    }

    public Bookingdates getDates() {
        return bookingdates;
    }

    public void setDates(Bookingdates dates) {
        this.bookingdates = dates;
    }

    private int totalprice;
    private boolean depositpaid;
    private String additionalneeds;
    private Bookingdates bookingdates;
}

