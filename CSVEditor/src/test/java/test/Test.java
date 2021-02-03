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

/**
 *
 * @author manos
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

        CSV csv = new CSV("input\\cities.csv", ",");

        csv.parse();
        csv.sort(1, SortOrder.REVERSED);
        csv.toFile();
//        System.out.println(csv);
        CSV c1 = csv.selectRows(0, 10);//System.out.println();
        CSV c2 = csv.selectColumns(0, 1);//System.out.println();
        CSV c3 = csv.selectColumnsRows(0, 10,0,1);
        
        //System.out.println(c1);
        System.out.println("=================");
       // System.out.println(c2);
        System.out.println("=================");
        System.out.println(c3);

    }
}
