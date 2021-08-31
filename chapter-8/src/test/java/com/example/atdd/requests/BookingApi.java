package com.example.atdd.requests;

import com.example.atdd.payloads.Booking;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingApi {

    private static final String apiUrl = "http://localhost:3000/booking/";

    public static Response postBooking(Booking payload) {
        return given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(apiUrl);
    }

    public static Response getTotal() {
        return given()
                .get(apiUrl + "report");
    }

}
