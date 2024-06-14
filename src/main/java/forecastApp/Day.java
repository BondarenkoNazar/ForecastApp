package forecastApp;

import java.util.Arrays;

public class Day {
    private String datetime;
    private double tempmax;
    private double tempmin;
    private String description;

    private Hour[] hours;

    @Override
    public String toString(){
        return datetime + " weather description: " + description.toLowerCase() + "\nThe temperature will be between " + tempmin + " and " + tempmax + ".";
    }

    private double tempToCelsius(double temp){
        return Math.round((temp - 32) * 5/9 * 10) / 10.0;
    }

    public void convertTempToCelsius(){
        this.tempmax = this.tempToCelsius(this.tempmax);
        this.tempmin = this.tempToCelsius(this.tempmin);
        Arrays.stream(hours).forEach(hour -> {hour.temp = tempToCelsius(hour.temp); hour.feelslike = tempToCelsius(hour.feelslike);});

    }

    public void printDetailedForecast(){
        Arrays.stream(hours).forEach(System.out::println);
    }
    
    public class Hour {
        private String datetime;
        private double temp;
        private double feelslike;
        private double windspeed;
        private double cloudcover;

        public String isCloud(){
            if (this.cloudcover < 20){
                return "clean";
            } else if (cloudcover < 50){
                return "partly cloudy";
            } else{
                return "cloudy";
            }
        }

        @Override
        public String toString(){
            return datetime + "\nTemperature: " + temp + "° (feels like " + feelslike + "°)\nThe sky is " + isCloud() + "\nWind speed: " + windspeed + " m/s";
        }
    }

}
