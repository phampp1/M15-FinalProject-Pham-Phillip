package com.company.M15FinalProjectPhamPhillip.CryptoResponse;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class GetCoin {
    public CryptoResponse getCrypto(String url) {
        // Using String url so that we can update what goes into the API url
        WebClient crypto = WebClient.create(url);
        CryptoResponse cryptoResponse = null;
        try {
            Mono<CryptoResponse[]> response = crypto
                    .get()
                    .retrieve()
                    .bodyToMono(CryptoResponse[].class);
            cryptoResponse = response.share().block()[0];
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
        return cryptoResponse;
    }
    public void displayCrypto(CryptoResponse cryptoResponse) {
        // If the crypto input was valid, print out the name, symbol, and price in USD. Otherwise, the entry was invalid.
        if (cryptoResponse != null) {
            System.out.println("Cryptocurrency name: " + cryptoResponse.name
                    + "\nCryptocurrency symbol: " + cryptoResponse.asset_id
                    + "\nPrice in USD: $" + String.format("%.2f", cryptoResponse.price_usd));
        } else {
            System.out.println("The symbol entered was invalid");
        }
    }
}
