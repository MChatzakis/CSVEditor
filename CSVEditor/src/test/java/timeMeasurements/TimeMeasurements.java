package timeMeasurements;

import csv.CSV;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import sort.SortOrder;
import utils.CommonUtils;

/**
 * Class to measure efficiency
 *
 * @author Manos Chatzakis
 */
public class TimeMeasurements {

    public static void main(String[] args) throws FileNotFoundException, IOException, CloneNotSupportedException {
        test();
        //indicativeTests();
        //randomTests();
    }

    public static void indicativeTests() throws FileNotFoundException, IOException, CloneNotSupportedException {
        long startTime = System.currentTimeMillis();

        CSV csv = new CSV("input\\cities.csv", ",", false);

        csv.parse();
        csv.sort(8, SortOrder.REVERSED);

        //csv.groupCSVByColumn(1);
        //CommonUtils.printMapOfArraylist(csv.groupCSVByColumn(1));
        //CSV c1 = csv.selectColumns(0, 2);
        csv.sort(1, SortOrder.NORMAL);

        long estimatedTime = System.currentTimeMillis() - startTime;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(estimatedTime);

        System.out.println("Read and parsed " + csv.getParsedLines().size() + " lines");
        System.out.println("Milliseconds: " + estimatedTime + " ms");
        System.out.println("Seconds: " + seconds + " s");

        System.out.println("==== Printing Data ====");
        System.out.println(csv);
    }

    public static void randomTests() throws FileNotFoundException, IOException, CloneNotSupportedException {
        CSV csv = new CSV("input\\EarthquakesCrete_1922-2019_withMonthYearBigOnes.csv", ",", false);
        csv.parse();

        ArrayList<Integer> asInt = csv.getColumnAsInt(2);
        System.out.println(asInt);

        ArrayList<Double> asDouble = csv.getColumnAsDouble(1);
        System.out.println(asDouble);

        ArrayList<String> asString = csv.getColumnAsString(0);
        System.out.println(asString);

    }

    public static void test() throws FileNotFoundException, FileNotFoundException, IOException, CloneNotSupportedException {
        CSV csv = new CSV("input\\test\\cities.csv", ",", false);

        csv.parse();
        csv.sort(1, SortOrder.REVERSED);

        csv.sort(0, SortOrder.NORMAL);
        
        csv.selectColumns(0, 0);
        csv.selectRows(0,10);
        
        System.out.println(csv);
    }

}
