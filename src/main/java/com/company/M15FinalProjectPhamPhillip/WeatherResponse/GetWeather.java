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
        // If the weatherResponse object exists, then print out the country and weather. Otherwise, there is no city with the name.
        if (weatherResponse != null) {
            System.out.println("The weather in " + weatherResponse.name + ", " + weatherResponse.sys.country + " is: " + weatherResponse.main.temp + " degrees fahrenheit.");
        } else {
            System.out.println("There is no city with that name.");
        }
    }
    public static void displayISSLocation(WeatherResponse weatherResponse) {
        // If the country is not null, then print out the location of the ISS (city & country). Otherwise, it is not in a country.
        if (weatherResponse.sys.country != null) {
            System.out.println("The ISS is currently in " + weatherResponse.name + ", " + weatherResponse.sys.country + ".");
        } else {
            System.out.println("The ISS is currently not in a country.");
        }
    }
    public static void displayISSWeather(WeatherResponse weatherResponse) {
        // If the country IS null (put first because it's a specific case)
        // then notify the user that the ISS isn't in a country. Still give them the weather.
        // If the weatherResponse is not null, then print out the ISS location and weather.
        // If neither scenarios work, then there was an error.
        if (weatherResponse.sys.country == null) {
            System.out.println("The ISS is not currently in a country. It is currently " + weatherResponse.main.temp + " degrees fahrenheit where the ISS is.");
        } else if (weatherResponse != null) {
            System.out.println("The ISS is currently in " + weatherResponse.name + ", "
                    + weatherResponse.sys.country + ". It is currently " + weatherResponse.main.temp + " degrees fahrenheit there.");
        } else {
            System.out.println("There was an error with your request.");
        }
    }
}
