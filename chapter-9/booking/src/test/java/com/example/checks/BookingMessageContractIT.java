package com.example.checks;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.api.BookingApplication;
import com.example.payloads.Booking;
import com.example.payloads.BookingDates;
import com.example.payloads.MessagePayload;
import com.example.requests.BookingApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "Message API", port = "3006")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BookingApplication.class)
@ActiveProfiles("dev")
public class BookingMessageContractIT {

    @Pact(consumer="Booking API", provider="Message API")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        MessagePayload message = new MessagePayload(
                "Mark Winteringham",
                "test@example.com",
                "012456789156",
                "You have a new booking!",
                "You have a new booking from Mark Winteringham. They have booked a room for the following dates: 2021-01-01 to 2021-01-03");

        return builder
                .uponReceiving("Message")
                .path("/message/")
                .method("POST")
                .body(message.toString())
                .willRespondWith()
                .status(201)
                .toPact();
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
                "Breakfast",
                "test@example.com",
                "012456789156"
        );

        Response response = BookingApi.postBooking(payload);

        assertEquals(201, response.getStatusCode());
    }



}
