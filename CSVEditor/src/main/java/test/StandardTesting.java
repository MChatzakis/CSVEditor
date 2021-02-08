/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import csv.CSV;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import utils.CommonUtils;

/**
 *
 * @author manos
 */
public class StandardTesting {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        
        long estimatedTime = System.currentTimeMillis() - startTime;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(estimatedTime);
        
        System.out.println("Milliseconds: " + estimatedTime + " ms");
        System.out.println("Seconds: " + seconds + " s");
    }

    public static void parsingTest() throws FileNotFoundException, Exception {
        File[] testFiles = CommonUtils.getFilesOfDirectory("input\\test\\withHeaders");
        for (int i = 0; i < testFiles.length; i++) {
            CSV csv = new CSV(testFiles[i].getAbsolutePath(), ",", true);
            System.out.println("Parsing file: " + testFiles[i].getName());
            csv.parse();
            csv.toFile("test.csv");

            //assert (FileUtils.contentEquals(testFiles[i], new File("test.csv")));
            if (!FileUtils.contentEquals(testFiles[i], new File("test.csv"))) {
                throw new Exception("gmtx");
            }
            System.out.println("Successful parse for file: " + testFiles[i].getName());
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.println("Parsing csv files with headers is succesful.");
        System.out.println("Parsing the rest csv files of the dataset.");
        System.out.println("-----------------------------------------------------------------");

        testFiles = CommonUtils.getFilesOfDirectory("input\\test\\withoutHeaders");
        for (int i = 0; i < testFiles.length; i++) {
            CSV csv = new CSV(testFiles[i].getAbsolutePath(), ",", false);
            System.out.println("Parsing file: " + testFiles[i].getName());
            csv.parse();
            csv.toFile("test.csv");

            assert (FileUtils.contentEquals(testFiles[i], new File("test.csv")));
            System.out.println("Successful parse for file: " + testFiles[i].getName());
        }

    }
}
