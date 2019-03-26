package spark;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "C:\\winutils");
        AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(AppConfig.class);
        TaxiService taxiService = config.getBean(TaxiService.class);

        taxiService.getCountOfTrips("files/taxi_orders.txt");
        taxiService.getCountOfTripsBoston("files/taxi_orders.txt");
        taxiService.getSumDistanceForAllTripsBoston("files/taxi_orders.txt");
        taxiService.getTopOfDriversByDistanceInOneDay("files/drivers.txt", "files/taxi_orders.txt", LocalDate.of(2016, 2, 13));
    }
}
