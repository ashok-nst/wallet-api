package com.nst.wallet.controller;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import com.nst.wallet.controller.WalletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/wallet") // Base URL for this controller
public class WalletController {
    @Value("${NEXT_PUBLIC_CIRCLE_API_KEY}")
    private String circleApiKey;
    
    private static final Logger LOG = LoggerFactory.getLogger(WalletController.class);

    @Get("/circle-api-key")
    public String getCircleApiKey() {
        return circleApiKey; // Returns the Circle API Key
    }

    @Post("/create") 
    public HttpResponse<String> createWallet(@Body WalletRequest request) {
        String userId = request.getUserId();

        LOG.info("Creating wallet for user ID: {}", userId); // Log user ID
        // Implement your logic here (e.g., save to database)
        
        return HttpResponse.created("Wallet created for user ID: "  + getCircleApiKey());
    }
    @Get("/") // New GET endpoint to return "Hello World"
    public String helloWorld() {
        return "Hello World"; // Response text
    }
}