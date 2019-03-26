package spark;

import java.time.LocalDate;

public interface TaxiService {
    void getCountOfTrips(String path);

    void getCountOfTripsBoston(String path);

    void getSumDistanceForAllTripsBoston(String path);

    void getTopOfDriversByDistanceInOneDay(String driverPath, String tripPath, LocalDate day);
}
