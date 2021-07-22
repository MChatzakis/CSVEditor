/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sort;

import csv.CSVLine;
import java.util.Comparator;

/**
 *
 * @author chatzakis
 */
public class StringColumnComparator implements Comparator<CSVLine> {

    private int compareColumn;
    private int reverse = 1;

    public StringColumnComparator(int compareColumn, boolean reversed) {
        this.compareColumn = compareColumn;
        if (reversed) {
            reverse = -1;
        }
    }

    public int compare(CSVLine c1, CSVLine c2) {
        String s1 = c1.getLine().get(compareColumn);
        String s2 = c2.getLine().get(compareColumn);
        return reverse * s1.compareTo(s2);
    }
}
