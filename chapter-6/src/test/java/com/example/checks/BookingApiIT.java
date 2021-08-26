package com.example.checks;

import com.example.payloads.*;
import com.example.requests.AuthApi;
import com.example.requests.BookingApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingApiIT {

    @Test
    public void getBookingShouldReturn200(){
        Response response = BookingApi.getBookings();

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
                "Winteringham",
                true,
                dates,
                "Breakfast"
        );

        Response response = BookingApi.postBooking(payload);
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

        AuthResponse authResponse = AuthApi.postAuth(auth).as(AuthResponse.class);

        Response deleteResponse = BookingApi.deleteBooking(
                createdBookingResponse.getBookingid(),
                authResponse.getToken());

        assertEquals(202, deleteResponse.getStatusCode());
    }

}
