package com.company.M15FinalProjectPhamPhillip.WeatherResponse;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class GetWeather {
    public static WeatherResponse getWeather(String url) {
        WebClient weather = WebClient.create(url);
        WeatherResponse weatherResponse = null;
        try {
            Mono<WeatherResponse> response = weather
                    .get()
                    .retrieve()
                    .bodyToMono(WeatherResponse.class);
            weatherResponse = response.share().block();
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
        return weatherResponse;
    }
    public static void displayWeather(WeatherResponse weatherResponse) {
        if (weatherResponse != null) {
            System.out.println("The weather in " + weatherResponse.name + ", " + weatherResponse.sys.country + " is: " + weatherResponse.main.temp + " degrees fahrenheit.");
        } else {
            System.out.println("There is no city with that name.");
        }
    }
    public static void displayISSLocation(WeatherResponse weatherResponse) {
        if (weatherResponse.sys.country != null) {
            System.out.println("The ISS is currently in " + weatherResponse.name + ", " + weatherResponse.sys.country + ".");
        } else {
            System.out.println("The ISS is currently not in a country.");
        }
    }
    public static void displayISSWeather(WeatherResponse weatherResponse) {
        if (weatherResponse.sys.country == null) {
            System.out.println("The ISS is not currently in a country. The weather there is: " + weatherResponse.main.temp);
        } else if (weatherResponse != null) {
            System.out.println("The ISS is currently in " + weatherResponse.name + ", "
                    + weatherResponse.sys.country + ". The weather there is: " + weatherResponse.main.temp);
        } else {
            System.out.println("There was an error with your request.");
        }
    }
}