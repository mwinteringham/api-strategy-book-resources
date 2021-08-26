package com.example.checks;

import com.example.payloads.Booking;
import com.example.payloads.BookingDates;
import com.example.payloads.BookingResponse;
import com.example.requests.BookingApi;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingApiIT {

    private static WireMockServer authMock;

    @BeforeAll
    public static void setupMock(){
         authMock = new WireMockServer(options().port(3004));
         authMock.start();
    }

    @AfterAll
    public static void killMock(){
        authMock.stop();
    }

    @Test
    public void deleteBookingReturns202(){
        authMock.stubFor(post("/auth/validate")
                .withRequestBody(equalToJson("{ \"token\": \"abc123\" }"))
                .willReturn(aResponse().withStatus(200)));

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

        Response deleteResponse = BookingApi.deleteBooking(
                createdBookingResponse.getBookingid(),
                "abc123");


        assertEquals(202, deleteResponse.getStatusCode());
    }

}
