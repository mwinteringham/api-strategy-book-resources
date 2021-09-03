package com.example.mocking.checks;

import com.example.api.BookingApplication;
import com.example.mocking.payloads.Booking;
import com.example.mocking.payloads.BookingDates;
import com.example.mocking.payloads.BookingResponse;
import com.example.mocking.requests.BookingApi;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BookingApplication.class)
@ActiveProfiles("dev")
public class BookingApiIT {

    private static WireMockServer authMock;

    @BeforeAll
    public static void setupMock() throws InterruptedException {
//        waitForApi("http://localhost:3000/booking/actuator/health", 20);

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

    private static void waitForApi(String url, int timeoutLimit) throws InterruptedException {
        while(true){
            if(timeoutLimit == 0){
                fail("Unable to connect to Web API");
            }

            try{
                Response response = given()
                        .get(url);

                if(response.statusCode() != 200){
                    timeoutLimit--;
                    Thread.sleep(1000);
                } else {
                    break;
                }
            } catch(Exception exception){
                timeoutLimit--;
                Thread.sleep(1000);
            }
        }
    }

}
