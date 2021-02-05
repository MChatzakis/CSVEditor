/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import csv.CSV;
import java.io.FileNotFoundException;
import java.io.IOException;
import sort.SortOrder;
import utils.CommonUtils;

/**
 *
 * @author manos
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

        CSV csv = new CSV("input\\companies.csv", ",",true);

        csv.toFile();
        /* Updates the initial file with the current csv object data */
        csv.toFile("new_path\\filename.csv");
        /* Creates new file and saves the current csv object data */

        csv.parse();
        csv.sort(2, SortOrder.REVERSED);
        //CSV csv1 = csv.selectColumnsBy(1, "Retail");
        //System.out.println(csv1);
        CommonUtils.printMapOfArraylist(csv.groupCSVByColumn(1));

        //csv.toFile();
        //System.out.println(csv);
        csv.selectRows(0, 10);
        csv.selectColumns(0, 1);
        csv.selectColumnsRows(0, 10, 0, 1);

    }
}
