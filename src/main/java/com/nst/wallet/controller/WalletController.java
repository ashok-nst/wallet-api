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
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.HttpRequest;

import jakarta.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller("/wallet")
public class WalletController {
    @Inject
    @Client("https://api.circle.com/v1/w3s")
    HttpClient httpClient;

    @Value("${NEXT_PUBLIC_CIRCLE_API_KEY}")
    private String circleApiKey;

    private static final Logger LOG = LoggerFactory.getLogger(WalletController.class);

    // Hello World GET route
    @Get("/hello")
    public HttpResponse<String> helloWorld() {
        return HttpResponse.ok("Hello get, World!");
    }

    // Hello World POST route
    @Post("/hello")
    public HttpResponse<String> helloWorldPost(@Body String message) {
        return HttpResponse.ok("Received post message: " + message);
    }

    @Post("/wallets")
    @ExecuteOn(TaskExecutors.BLOCKING)
    public HttpResponse<String> wallets(@Body WalletRequest request) {
        String userToken = request.getUserToken();
        String url = "/wallets";

        LOG.info("Fetching wallets for user token: {}", userToken);
        
        // Ensure you are sending the correct payload expected by Circle API
        String requestBody = "{\"userId\":\"" + userToken + "\"}";

        HttpRequest<String> httpRequest = HttpRequest.POST(url, requestBody)
                .header("Authorization", "Bearer " + "circleApiKey")
                .header("Content-Type", "application/json")
                .header("User-Agent", "PW-TEST-SERVER")
                .header("X-User-Token", userToken);

        
        try {
            HttpResponse<String> response = httpClient.toBlocking().exchange(httpRequest, String.class);
            return HttpResponse.ok("Wallets post message is: " + request.getUserToken());
            // LOG.info("Response status: {}", response.getStatus());
            // LOG.info("Response body: {}", response.body());

            // if (response.getStatus().getCode() == 200) {
            //     return HttpResponse.ok(response.body());
            // }

            // LOG.error("Failed to fetch wallets: {}", response.body());
            // return HttpResponse.serverError("Failed to fetch wallets: " + response.body());
        } catch (Exception e) {
            LOG.error("Error fetching wallets: {}", e.getMessage(), e);
            return HttpResponse.serverError("Internal Server Error");
        }                        
    }


    // Other existing methods...
}

// @Controller("/wallet")
// public class WalletController {
    // @Inject
    // @Client("https://api.circle.com/v1/w3s")
    // HttpClient httpClient;

    // @Value("${NEXT_PUBLIC_CIRCLE_API_KEY}")
    // private String circleApiKey;

    // private static final Logger LOG = LoggerFactory.getLogger(WalletController.class);

//     @Post("/getWallets")
//     @ExecuteOn(TaskExecutors.BLOCKING)
//     public HttpResponse<String> getWallets(@Body WalletRequest request) {
//         String userToken = request.getUserToken();
//         String url = "/wallets";

//         LOG.info("Fetching wallets for user token: {}", userToken);
        
//         // Ensure you are sending the correct payload expected by Circle API
//         String requestBody = "{\"userId\":\"" + userToken + "\"}";

//         HttpRequest<String> httpRequest = HttpRequest.POST(url, requestBody)
//                 .header("Authorization", "Bearer " + "circleApiKey")
//                 .header("Content-Type", "application/json")
//                 .header("User-Agent", "PW-TEST-SERVER")
//                 .header("X-User-Token", userToken);

//         try {
//             HttpResponse<String> response = httpClient.toBlocking().exchange(httpRequest, String.class);
//             LOG.info("Response status: {}", response.getStatus());
//             LOG.info("Response body: {}", response.body());

//             if (response.getStatus().getCode() == 200) {
//                 return HttpResponse.ok(response.body());
//             }

//             LOG.error("Failed to fetch wallets: {}", response.body());
//             return HttpResponse.serverError("Failed to fetch wallets: " + response.body());
//         } catch (Exception e) {
//             LOG.error("Error fetching wallets: {}", e.getMessage(), e);
//             return HttpResponse.serverError("Internal Server Error");
//         }
//     }
    
//     @Post("/getUserToken")
//     @ExecuteOn(TaskExecutors.BLOCKING)
//     public HttpResponse<String> getUserToken(@Body WalletRequest request) {
//         String userId = request.getUserId();
//         String url = "/users/token";

//         HttpRequest<String> httpRequest = HttpRequest.POST(url, "{\"userId\":\"" + userId + "\"}")
//                 .header("Authorization", "Bearer " + circleApiKey)
//                 .header("Content-Type", "application/json")
//                 .header("User-Agent", "PW-TEST-SERVER");

//         try {
//             HttpResponse<String> response = httpClient.toBlocking().exchange(httpRequest, String.class);
//             if (response.getStatus().getCode() == 200) {
//                 return HttpResponse.ok(response.body());
//             } 
//             LOG.error("Failed to fetch user token: {}", response.body());
//             return HttpResponse.serverError("Failed to fetch user token");
//         } catch (Exception e) {
//             LOG.error("Error fetching user token: {}", e.getMessage(), e);
//             return HttpResponse.serverError("Internal Server Error");
//         }
//     }

//     @Get("/hello")
//     public HttpResponse<String> helloWorld() {
//         return HttpResponse.ok("Hello, World!");
//     }

//     // Hello World POST route
//     @Post("/hello")
//     @ExecuteOn(TaskExecutors.BLOCKING)
//     public HttpResponse<String> helloWorldPost(@Body String message) {
//         return HttpResponse.ok("Received message: " + message);
//     }
// }
// }