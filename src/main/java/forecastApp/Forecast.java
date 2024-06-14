package forecastApp;

import java.util.Arrays;

public class Forecast {
    private String address;
    private Day[] days;
    public void printDay(int x){
        this.days[x].printDetailedForecast();
    }

    public void printForecast() {
        Arrays.stream(this.days).forEach(System.out::println);
    }

    public void convertTempToCelsius(){
        Arrays.stream(this.days).forEach(Day::convertTempToCelsius);
    }
}
