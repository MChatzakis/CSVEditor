package test;

import csv.CSV;
import csv.CSVFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import sort.SortOrder;
import sort.SortType;

/**
 *
 * @author Manos Chatzakis
 */
public class CompleteTest {
    
    public static void main(String [] args) throws FileNotFoundException, IOException, CloneNotSupportedException{
        
        CSV csv = new CSV("input\\test\\cretanEr.csv", ",", false); /* Load the file */
        
        csv.parse(); // Parse the input 
        
        int [] columns = {1, 2, 3, 8, 11};
        CSV csv1 = CSVFactory.selectCSVColumnFactory(csv, columns); /* Select specific csv columns */
        
        csv1.selectRows(0, 99); /* Select the first 100 lines of the file */
        csv1.sort(4, SortOrder.REVERSED, SortType.NUMERIC); /* Sort by fifth column inverted */
        csv1.toFile("output\\out.csv"); /* Create a new file from the parsed data */
        
        csv.sort(1, SortOrder.REVERSED, SortType.NUMERIC);
        csv.toFile("output\\out1.csv");
        
        CSV csv2 = CSVFactory.selectCSVRowsByValueFactory(csv, 1, "1989");
        csv2.toFile("output\\out2.csv");
        
    }
    
}
