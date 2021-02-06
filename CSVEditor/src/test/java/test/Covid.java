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
import sort.SortType;

/**
 *
 * @author manos
 */
public class Covid {

    public static void main(String[] args) throws FileNotFoundException, IOException {
             
            CSV csv = new CSV("input\\test\\covid.csv", ",", true); 
             csv.parse();
             
             int [] columns = {1,2,4};
             csv.selectColumns(columns);
             csv.sort(2,SortOrder.REVERSED,SortType.NUMERIC);
             csv.toFile("test.csv");
             
             
    }
}
