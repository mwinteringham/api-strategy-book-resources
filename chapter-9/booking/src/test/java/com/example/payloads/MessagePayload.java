package com.example.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessagePayload {

    private String name;
    private String email;
    private String phone;
    private String subject;
    private String description;

    public MessagePayload(String name, String email, String phone, String subject, String description) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\"name\": \"" + name + "\",\n" +
                "\t\"email\": \"" + email + "\",\n" +
                "\t\"phone\": \"" + phone + "\",\n" +
                "\t\"subject\": \"" + subject + "\",\n" +
                "\t\"description\": \"" + description + "\"\n" +
                "}";
    }
}
