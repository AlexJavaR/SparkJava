package spark;

import spark.model.Driver;
import spark.model.Trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WordUtils {

    public static Trip getTrip(String line) {
        Trip trip;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
        String[] fields = line.split(" ");

        String date = fields[8] + "-" + fields[4] + "-" + fields[5] + " " + fields[6];
        trip = Trip.builder().id(fields[0]).city(fields[1]).distance(Integer.valueOf(fields[2])).date(LocalDateTime.parse(date, formatter)).build();

        return trip;
    }

    public static Driver getDriver(String line) {
        Driver driver;
        String[] fields = line.split(", ");

        driver = Driver.builder().id(fields[0]).name(fields[1]).address(fields[2]).email(fields[3]).build();

        return driver;
    }
}
