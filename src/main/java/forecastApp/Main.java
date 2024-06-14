package forecastApp;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String key = "PCZ9PAR4WJ44SBM774YFZBG9Z";

    public static void main(String[] args) {
        LocalDate todayDate = LocalDate.now();
        LocalDate dateAfterSevenDays = todayDate.plusDays(7);

        String date1 = todayDate.toString();
        String date2 = dateAfterSevenDays.toString();

        Map<Integer, String> places = new HashMap<>();
        fillMapWithPossibleOptions(places);

        boolean shouldLoop = true;
        Scanner scanner = new Scanner(System.in);
        String location = null;

        while (shouldLoop){
            System.out.println("Enter number of the city");
            places.forEach((k, v) -> System.out.println(k + ") " + v));

            int number = scanner.nextInt();

            location = places.get(number);

            if (location != null){
                shouldLoop = false;
            } else {
                System.out.println("Option have not found");
            }
        }

        try {
            URI uri = new URI("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + location + "/" + date1 + "/" + date2 + "?key=" + key);

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> httpResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            Forecast forecast = gson.fromJson(httpResponse.body(), Forecast.class);
            forecast.convertTempToCelsius();
            forecast.printForecast();

            while (true){
                System.out.println();
                System.out.println("Would you like to view more detailed information for a specific day?");
                System.out.println("If so, please enter the day number (1 for today, up to 7). Any other input will exit the program.");

                scanner.nextLine();
                String input = scanner.nextLine();

                try{
                    int numberOfTheDay = Integer.parseInt(input) - 1;

                    if (numberOfTheDay < 0 || numberOfTheDay >7){
                        System.exit(0);
                    } else {
                        forecast.printDay(numberOfTheDay);
                    }
                } catch (Exception e){
                    System.exit(0);
                }
            }


        }  catch (URISyntaxException e) {
            System.out.println("The URI used in the GET request is incorrect.");
        } catch (InterruptedException e) {
            System.out.println("The operation was interrupted during the GET request.");
        } catch (IOException e) {
            System.out.println("An I/O error occurred during the GET request.");
        }
    }

    public static void fillMapWithPossibleOptions(@NotNull Map<Integer, String> places){
        places.put(1, "Vilnius");
        places.put(2, "Tokyo");
        places.put(3, "Helsinki");
        places.put(4, "London");
        places.put(5, "Brussels");
        places.put(6, "Copenhagen");
        places.put(7, "Dublin");
    }
}