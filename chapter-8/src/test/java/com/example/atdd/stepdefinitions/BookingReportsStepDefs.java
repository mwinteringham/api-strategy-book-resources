package com.example.atdd.stepdefinitions;

import com.example.atdd.payloads.Booking;
import com.example.atdd.payloads.BookingDates;
import com.example.atdd.payloads.Total;
import com.example.atdd.requests.BookingApi;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingReportsStepDefs {

    private Response totalResponse;

    @Given("I have multiple bookings")
    public void i_have_multiple_bookings() {
        BookingDates dates = new BookingDates(
                LocalDate.of(2021,01, 01),
                LocalDate.of(2021,03, 01)
        );

        Booking payloadOne = new Booking(
                "Mark",
                "Winteringham",
                200,
                true,
                dates,
                "Breakfast"
        );

        Booking payloadTwo = new Booking(
                "Mark",
                "Winteringham",
                200,
                true,
                dates,
                "Breakfast"
        );

        BookingApi.postBooking(payloadOne);
        BookingApi.postBooking(payloadTwo);
    }

    @When("I ask for a report on my total earnings")
    public void i_ask_for_a_report_on_my_total_earnings() {
        totalResponse = BookingApi.getTotal();
    }

    @Then("I will receive a total amount based on all my bookings")
    public void i_will_receive_a_total_amount_based_on_all_my_bookings() {
        int total = totalResponse.as(Total.class).getTotal();

        assertEquals(total, 400);
    }

}
