package com.example.checks;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import com.example.api.MessageApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Provider("Message API")
@PactBroker(url = "https://restful-booker-platform.pactflow.io", authentication = @PactBrokerAuth(token = "X0Abbdzyd2FZEI93BLTp9w"))
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = MessageApplication.class)
@ActiveProfiles("dev")
public class MessageBookingVerifyIT {

    @BeforeEach
    void before(PactVerificationContext context) {
        System.setProperty("pact.verifier.publishResults", "true");
        
        context.setTarget(new HttpTestTarget("localhost", 3006, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

}
