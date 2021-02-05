/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

/**
 *
 * @author manos
 */
public class CSVFactory {

    public static CSV selectCSVColumnFactory(CSV csv, int from, int to) throws CloneNotSupportedException {
        CSV newCsv = (CSV) csv.clone();
        newCsv.selectColumns(from, to);
        return newCsv;
    }

    public static CSV selectCSVRowFactory(CSV csv, int from, int to) throws CloneNotSupportedException {
        CSV newCsv = (CSV) csv.clone();
        newCsv.selectRows(from, to);
        return newCsv;
    }

    public static CSV selectCSVColumnRowFactory(CSV csv, int columnFrom, int columnTo, int rowFrom, int rowTo) throws CloneNotSupportedException {
        CSV newCsv = (CSV) csv.clone();
        newCsv.selectColumnsRows(rowFrom, rowTo, columnFrom, columnTo);
        return newCsv;
    }

    public static CSV selectCSVRowsByValueFactory(CSV csv, int column, String value) throws CloneNotSupportedException {
        CSV newCsv = (CSV) csv.clone();
        newCsv.selectColumnsByValue(column, value);
        return newCsv;
    }

}
