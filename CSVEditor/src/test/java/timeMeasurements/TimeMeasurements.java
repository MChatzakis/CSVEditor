package timeMeasurements;

import csv.CSV;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import sort.SortOrder;

/**
 * Class to measure efficiency
 *
 * @author Manos Chatzakis
 */
public class TimeMeasurements {

    public static void main(String[] args) throws FileNotFoundException, IOException, CloneNotSupportedException {

        long startTime = System.currentTimeMillis();

        CSV csv = new CSV("input\\trans.csv", ",");

        csv.parse();
        csv.sort(2, SortOrder.REVERSED);
        //csv.toFile("test.csv");
        
        long estimatedTime = System.currentTimeMillis() - startTime;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(estimatedTime);
        
        System.out.println("Read and parsed " + csv.getParsedLines().size() +" lines");
        System.out.println("Milliseconds: " + estimatedTime +  " ms");
        System.out.println("Seconds: " + seconds + " s");
        
        System.out.println(csv.selectRows(0, 10));

    }

}
