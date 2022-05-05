package com.company.M15FinalProjectPhamPhillip.SpaceResponse;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class GetISS {
    public SpaceResponse getSpace() {
        // We do not have to edit this URL, so leaving this as-is is okay
        WebClient space = WebClient.create("http://api.open-notify.org/iss-now.json");
        SpaceResponse spaceResponse = null;
        try {
            Mono<SpaceResponse> response = space
                    .get()
                    .retrieve()
                    .bodyToMono(SpaceResponse.class);
            spaceResponse = response.share().block();
        } catch (WebClientResponseException we) {
           int statusCode = we.getRawStatusCode();
           if (statusCode >= 400 && statusCode < 500) {
                System.out.println("Client Error");
            } else if (statusCode >= 500 && statusCode < 600) {
                System.out.println("Server Error");
            }
            System.out.println("Message: " + we.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return spaceResponse;
    }
    public void displayLocation(SpaceResponse spaceResponse) {
        // These get displayed no matter what. Included the if-else statement in case the object is ever null.
        if (spaceResponse != null) {
            System.out.println("Latitude: " + spaceResponse.iss_position.latitude);
            System.out.println("Longitude: " + spaceResponse.iss_position.longitude);
        } else {
            System.out.println("There was an error with your request.");
        }

    }
}
