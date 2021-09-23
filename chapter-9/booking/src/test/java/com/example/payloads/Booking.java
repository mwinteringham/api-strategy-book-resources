package com.example.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Optional;

@JsonIgnoreType
public class Booking {

    @JsonProperty
    private int roomid;
    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private boolean depositpaid;
    @JsonProperty
    private BookingDates bookingdates;
    @JsonProperty
    private String additionalneeds;
    @JsonProperty
    private String email;
    @JsonProperty
    private String phone;

    public Booking(int roomid, String firstname, String lastname, boolean depositpaid, BookingDates bookingdates, String additionalneeds, String email, String phone) {
        this.roomid = roomid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;
        this.email = email;
        this.phone = phone;
    }

    public int getRoomid() {
        return roomid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
