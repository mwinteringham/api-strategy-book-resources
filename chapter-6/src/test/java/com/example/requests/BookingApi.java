package com.example.requests;

import com.example.payloads.Booking;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingApi extends BaseApi {

    private static final String apiUrl = baseUrl + "booking/";

    public static Response getBookingSummary(){
        return given().get(apiUrl + "summary?roomid=1");
    }

    public static Response postBooking(Booking payload) {
        return given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(apiUrl);
    }

    public static Response deleteBooking(int id, String tokenValue) {
        return given()
                .header("Cookie", "token=" + tokenValue)
                .delete(apiUrl + Integer.toString(id));
    }

}
