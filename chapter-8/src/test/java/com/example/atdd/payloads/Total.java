package com.example.atdd.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Total {

    @JsonProperty
    private int total;

    public int getTotal() {
        return total;
    }

}
