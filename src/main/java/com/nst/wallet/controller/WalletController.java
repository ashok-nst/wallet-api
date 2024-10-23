package com.nst.wallet.controller;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import com.nst.wallet.controller.WalletRequest;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
// import io.micronaut.http.client.annotation.Get;
// import io.micronaut.http.client.annotation.Post;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/wallet")
public class WalletController {
    @Inject
    @Client("https://api.circle.com/v1/w3s")
    HttpClient httpClient;

    @Post("/getUserToken")
    @ExecuteOn(TaskExecutors.BLOCKING)
    public HttpResponse<String> getUserToken(@Body WalletRequest request) {
    String userId = request.getUserId();
    String url = "/users/token";

    HttpRequest<String> httpRequest = HttpRequest.POST(url, "{\"userId\":\"" + userId + "\"}")
            .header("Authorization", "Bearer " + circleApiKey)
            .header("Content-Type", "application/json")
            .header("User-Agent", "PW-TEST-SERVER");

    HttpResponse<String> response = httpClient.toBlocking().exchange(httpRequest, String.class);
    
    if (response.getStatus().getCode() == 200) {
        return HttpResponse.ok(response.body());
    } else {
        return HttpResponse.serverError("Failed to fetch user token");
    }

    // return HttpResponse.ok("User token fetched for user ID: " + userId);
}

    @Value("${NEXT_PUBLIC_CIRCLE_API_KEY}")
    private String circleApiKey;
    
    private static final Logger LOG = LoggerFactory.getLogger(WalletController.class);

    @Post("/create") 
    public HttpResponse<String> createWallet(@Body WalletRequest request) {
        String userId = request.getUserId();
        LOG.info("Creating wallet for user ID: {}", userId);        
        return HttpResponse.created("Wallet created for user ID: "  + userId + circleApiKey);
    }
    @Get("/") 
    public String helloWorld() {
        return "Hello World";
    }
}