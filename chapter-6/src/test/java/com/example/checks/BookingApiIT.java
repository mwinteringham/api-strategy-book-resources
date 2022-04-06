package com.example.checks;

import com.example.payloads.Auth;
import com.example.payloads.Booking;
import com.example.payloads.BookingDates;
import com.example.payloads.BookingResponse;
import com.example.requests.AuthApi;
import com.example.requests.BookingApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingApiIT {

    @Test
    public void getBookingSummaryShouldReturn200(){
        Response response = BookingApi.getBookingSummary();

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void postBookingReturns201(){
        BookingDates dates = new BookingDates(
                LocalDate.of( 2021 , 1 , 1 ),
                LocalDate.of( 2021 , 1 , 3 )
        );

        Booking payload = new Booking(
                1,
                "Mark",
                "Auto",
                true,
                dates,
                "Breakfast"
        );

        Response response = BookingApi.postBooking(payload);

        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void deleteBookingReturns202(){
        BookingDates dates = new BookingDates(
                LocalDate.of( 2021 , 2 , 1 ),
                LocalDate.of( 2021 , 2 , 3 )
        );

        Booking payload = new Booking(
                1,
                "Mark",
                "Winteringham",
                true,
                dates,
                "Breakfast"
        );

        Response bookingResponse = BookingApi.postBooking(payload);
        BookingResponse createdBookingResponse = bookingResponse.as(BookingResponse.class);

        Auth auth = new Auth("admin", "password");

        Response authResponse = AuthApi.postAuth(auth);
        String authToken = authResponse.getCookie("token");

        Response deleteResponse = BookingApi.deleteBooking(
                createdBookingResponse.getBookingid(),
                authToken);

        assertEquals(202, deleteResponse.getStatusCode());
    }

}
