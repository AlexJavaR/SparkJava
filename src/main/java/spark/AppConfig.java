package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "spark")
public class AppConfig {

    @Bean
    public JavaSparkContext javaSparkContext() {
        //Create a SparkContext to initialize
        SparkConf conf = new SparkConf().setAppName("TaxiDrivers").setMaster("local");

        // Create a Java version of the Spark Context
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
        javaSparkContext.setLogLevel("ERROR");
        return javaSparkContext;
    }
}
