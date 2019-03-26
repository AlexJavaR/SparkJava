package spark;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import spark.model.Trip;

import java.time.LocalDate;

@Service
public class TaxiServiceImpl implements TaxiService {

    private final JavaSparkContext javaSparkContext;

    @Autowired
    public TaxiServiceImpl(JavaSparkContext javaSparkContext) {
        this.javaSparkContext = javaSparkContext;
    }

    @Override
    public void getCountOfTrips(String path) {
        // Load the text into a Spark RDD, which is a distributed representation of each line of text
        JavaRDD<String> textFile = javaSparkContext.textFile(path);

        System.out.println("Total trips: " + textFile.count());
    }

    @Override
    public void getCountOfTripsBoston(String path) {
        // Load the text into a Spark RDD, which is a distributed representation of each line of text
        JavaRDD<String> textFile = javaSparkContext.textFile(path);
        Long countOfTripToBoston = textFile
                .map(WordUtils::getTrip)
                .filter(trip -> trip.getCity().toLowerCase().contains("boston"))
                .filter(trip -> trip.getDistance() > 10)
                .count();
        System.out.println("Total trips in Boston longer than 10 km: " + countOfTripToBoston);
    }

    @Override
    public void getSumDistanceForAllTripsBoston(String path) {
        // Load the text into a Spark RDD, which is a distributed representation of each line of text
        JavaRDD<String> textFile = javaSparkContext.textFile(path);
        Double sumDistanceInBoston = textFile
                .map(WordUtils::getTrip)
                .filter(trip -> trip.getCity().toLowerCase().contains("boston"))
                .mapToDouble(Trip::getDistance)
                .reduce((a, b) -> a + b);
        System.out.println("Sum of all distances trips in Boston: " + sumDistanceInBoston);
    }

    @Override
    public void getTopOfDriversByDistanceInOneDay(String driverPath, String tripPath, LocalDate day) {
        // Load the text into a Spark RDD, which is a distributed representation of each line of text
        JavaRDD<String> driverFile = javaSparkContext.textFile(driverPath);
        JavaRDD<String> tripFile = javaSparkContext.textFile(tripPath);

        JavaPairRDD<String, String> drivers = driverFile
                .map(WordUtils::getDriver)
                .mapToPair(driver -> new Tuple2<>(driver.getId(), driver.getName()));

        JavaPairRDD<String, Integer> trips = tripFile
                .map(WordUtils::getTrip)
                .filter(trip -> trip.getDate().toLocalDate().isEqual(day))
                .mapToPair(trip -> new Tuple2<>(trip.getId(), trip.getDistance()))
                .reduceByKey((a, b) -> a + b);

        drivers
                .join(trips)
                .mapToPair(x->x._2.swap())
                .sortByKey(false)
                .take(3)
                .forEach(System.out::println);
    }
}
