package com.company.M15FinalProjectPhamPhillip;

import static com.company.M15FinalProjectPhamPhillip.SpaceResponse.GetISS.displayLocation;
import static com.company.M15FinalProjectPhamPhillip.SpaceResponse.GetISS.getSpace;
import com.company.M15FinalProjectPhamPhillip.CryptoResponse.GetCoin;
import com.company.M15FinalProjectPhamPhillip.SpaceResponse.SpaceResponse;
import com.company.M15FinalProjectPhamPhillip.WeatherResponse.WeatherResponse;
import com.company.M15FinalProjectPhamPhillip.WeatherResponse.GetWeather;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class M15FinalProjectPhamPhillipApplication {
	public static void main(String[] args) {
		// Create a Scanner object and initialize other required variables
		Scanner scanner = new Scanner(System.in);
		boolean doLoop = true;

		// Instruct user on how to use the CLI
		System.out.println("\nHello! Welcome to my final project."
				+ "\nThis project will run a number of API requests and return the requested information through the Command Line Interface. "
				+ "\n \nTo begin, please choose one of the following options: "
				+ "\n\t    1. Weather in a City"
				+ "\n\t    2. Location of the International Space Station (ISS)"
				+ "\n\t    3. Weather in the Location of the International Space Station (ISS)"
				+ "\n\t    4. Current Cryptocurrency Prices"
				+ "\n\t    5. Exit");
		do {
			System.out.println("Please enter 1, 2, 3, 4, or 5 below: ");
			String choice = scanner.nextLine();
			switch(choice) {
				case "1":
					System.out.println("Please enter the name of a city below: ");
					// Use the .trim() function to get rid of any extraneous spaces
					String cityInput = scanner.nextLine().trim();
					// Call to the Weather API using the city that the user enters
					WeatherResponse weatherResponse = GetWeather.getWeather(
							String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=1aca49340b58c05776e08f3ef1b97c65&units=imperial",cityInput));
					// Print out the weather of the chosen city
					GetWeather.displayWeather(weatherResponse);
					break;
				case "2":
					System.out.println("Here are details of the current ISS location: ");
					// Call to the ISS API
					SpaceResponse spaceResponse = getSpace();
					// Print out the ISS location
					displayLocation(spaceResponse);
					// Retrieve the city and country from the Weather API using the instance of the ISS location above.
					GetWeather.displayISSLocation(GetWeather.getWeather(
							String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=1aca49340b58c05776e08f3ef1b97c65&units=imperial",
									spaceResponse.iss_position.latitude,spaceResponse.iss_position.longitude)));
					break;
				case "3":
					System.out.println("Here are details of the current ISS location: ");
					// Call to the ISS API
					SpaceResponse spaceResponse2 = getSpace();
					// Print out the ISS location
					displayLocation(spaceResponse2);
					// Retrieve the city and country from the Weather API using the instance of the ISS location above
					// and retrieve the weather of where the ISS location currently is.
					GetWeather.displayISSWeather(GetWeather.getWeather(
							String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=1aca49340b58c05776e08f3ef1b97c65&units=imperial",
									spaceResponse2.iss_position.latitude,spaceResponse2.iss_position.longitude)));
					break;
				case "4":
					System.out.println("Please enter a valid symbol for a cryptocurrency below: ");
					// Take in user input:
					String coinInput = scanner.nextLine().trim();
					// Call to CoinAPI and display crypto information
					GetCoin.displayCrypto(GetCoin.getCrypto(
									String.format("https://rest.coinapi.io/v1/assets/%s/?apikey=1249EB50-E736-4298-8E80-5552DCF56A72",coinInput)
									));
					break;
				case "5":
					doLoop = false;
					break;
				default:
					System.out.println("\nYour entry was invalid.");
			}
		} while(doLoop);
	}
}
