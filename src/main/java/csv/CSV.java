package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;

import lombok.Data;
import normalization.Normalizer;

import sort.NumberColumnComparator;
import sort.SortOrder;
import sort.SortType;
import sort.StringColumnComparator;

import utils.CommonUtils;

/**
 * Main CSV class holding modification methods
 *
 * @author Manos Chatzakis (chatzakis@ics.forth.gr)
 */
@Data
public class CSV implements Cloneable {

    private String filepath;
    private String regex;

    private File csvFile;

    private ArrayList<CSVLine> parsedLines;

    private CSVLine header;
    private InputStream stream;

    private int maxCharacters;

    private Pair<Integer, Integer> columnFromTo;
    private Pair<Integer, Integer> rowFromTo;

    private boolean isSelectingSpecific;
    private boolean hasHeader;

    public CSV(InputStream stream, String regex, boolean hasHeader) throws FileNotFoundException {
        parsedLines = new ArrayList<>();

        this.regex = regex;
        this.hasHeader = hasHeader;

        this.isSelectingSpecific = false;
        this.maxCharacters = 0;
        this.rowFromTo = new Pair<Integer, Integer>(0, 0);
        this.columnFromTo = new Pair<Integer, Integer>(0, 0);

        this.stream = stream;
    }

    public CSV(String filepath, String regex, boolean hasHeader) throws FileNotFoundException {
        parsedLines = new ArrayList<>();

        this.filepath = filepath;
        this.regex = regex;
        this.hasHeader = hasHeader;

        this.isSelectingSpecific = false;
        this.maxCharacters = 0;
        this.rowFromTo = new Pair<Integer, Integer>(0, 0);
        this.columnFromTo = new Pair<Integer, Integer>(0, 0);

        csvFile = new File(filepath);

        if (!csvFile.exists()) {
            throw new FileNotFoundException("The file given does not exist!");
        }
    }

    public CSV(String filepath, String regex, int columnFrom, int columnTo, int rowFrom, int rowTo) throws FileNotFoundException {
        parsedLines = new ArrayList<>();

        this.filepath = filepath;
        this.regex = regex;

        this.rowFromTo = new Pair<Integer, Integer>(rowFrom, rowTo);
        this.columnFromTo = new Pair<Integer, Integer>(columnFrom, columnTo);

        this.isSelectingSpecific = true;
        this.maxCharacters = 0;

        csvFile = new File(filepath);

        if (!csvFile.exists()) {
            throw new FileNotFoundException("The file given does not exist!");
        }
    }

    private ArrayList<String> read() throws IOException {
        ArrayList<String> rawLines = new ArrayList<>();
        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                rawLines.add(line);
                counter++;
            }
        }
        return rawLines;
    }

    private ArrayList<String> readInStream() throws IOException {
        ArrayList<String> rawLines = new ArrayList<>();
        int counter = 0;
        try (InputStreamReader streamReader = new InputStreamReader(this.stream, StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                rawLines.add(line);
                counter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawLines;
    }

    public ArrayList<CSVLine> parseInStream() throws IOException {
        ArrayList<String> rawLines = readInStream();
        int counter = 0;
        int biggestLineSize = 0;
        for (String line : rawLines) {

            String[] prsLine = line.split(regex, -1);

            //for patch
            if (biggestLineSize <= prsLine.length) {
                biggestLineSize = prsLine.length;
            }
            CSVLine csvLine = new CSVLine();

            for (int i = 0; i < prsLine.length; i++) {

                /*if(prsLine[i].length() >= maxCharacters){
                    maxCharacters = prsLine[i].length(); 
                }*/
                csvLine.insertItem(prsLine[i]);
            }

            if (hasHeader && counter == 0) {
                header = csvLine;
            } else {
                parsedLines.add(csvLine);
            }

            counter++;
        }

        patchLines(biggestLineSize);
        return parsedLines;
    }

    public void selectRows(int from, int to) {
        ArrayList<CSVLine> selected = new ArrayList<>();

        for (int i = from; i <= to; i++) {
            selected.add(parsedLines.get(i));

            if (i >= parsedLines.size() - 1) {
                break;
            }
        }

        this.setParsedLines(selected);
    }

    public void selectRows(int[] columns) {
        ArrayList<CSVLine> selected = new ArrayList<>();

        for (int i = 0; i < columns.length; i++) {
            int col = columns[i];
            selected.add(parsedLines.get(col));
        }

        this.setParsedLines(selected);
    }

    public void selectColumns(int from, int to) {
        ArrayList<CSVLine> selected = new ArrayList<>();
        if (header != null) {
            header.selectColumns(from, to);
        }
        for (int i = 0; i < parsedLines.size(); i++) {
            CSVLine line = new CSVLine();
            line.setLine(parsedLines.get(i).getColumns(from, to));
            selected.add(line);
        }

        this.setParsedLines(selected);
    }

    public void selectColumns(int[] columns) {
        ArrayList<CSVLine> selected = new ArrayList<>();
        header.selectColumns(columns);
        for (int i = 0; i < parsedLines.size(); i++) {
            CSVLine line = new CSVLine();
            line.setLine(parsedLines.get(i).getColumns(columns));
            selected.add(line);
        }

        this.setParsedLines(selected);
    }

    public void selectColumnsRows(int rowFrom, int rowTo, int columnFrom, int columnTo) throws CloneNotSupportedException {
        ArrayList<CSVLine> selected = new ArrayList<>();
        for (int i = 0; i < parsedLines.size(); i++) {
            if (i >= rowFrom && i <= rowTo) {
                CSVLine line = new CSVLine();
                line.setLine(parsedLines.get(i).getColumns(columnFrom, columnTo));
                selected.add(line);
            }
            if (i >= rowTo) {
                break;
            }
        }

        this.setParsedLines(selected);
    }

    private CSV selectColumnsByValueTemp(int column, String value) throws CloneNotSupportedException {
        ArrayList<CSVLine> selected = new ArrayList<>();
        CSV csv = (CSV) this.clone();

        for (int i = 0; i < parsedLines.size(); i++) {
            CSVLine currLine = parsedLines.get(i);
            if (value.equals(currLine.getLine().get(column))) {
                selected.add(currLine);
            }
        }

        csv.setParsedLines(selected);
        return csv;
    }

    public void selectColumnsByValue(int column, String value) throws CloneNotSupportedException {
        ArrayList<CSVLine> selected = new ArrayList<>();

        for (int i = 0; i < parsedLines.size(); i++) {
            CSVLine currLine = parsedLines.get(i);
            if (value.equals(currLine.getLine().get(column))) {
                selected.add(currLine);
            }
        }

        this.setParsedLines(selected);
    }

    public Map<String, ArrayList<CSVLine>> groupCSVByColumn(int column) throws CloneNotSupportedException {

        Map<String, ArrayList<CSVLine>> csvMap = new HashMap<>();
        ArrayList<CSVLine> lines = new ArrayList<>();
        ArrayList<String> attributes = new ArrayList<>();

        for (CSVLine line : parsedLines) {
            String currAtr = line.getLine().get(column);
            if (!attributes.contains(currAtr)) {
                attributes.add(currAtr);
                //System.out.println("Adding atr: " + currAtr);
            }
        }

        for (String atr : attributes) {
            lines = selectColumnsByValueTemp(column, atr).getParsedLines();
            csvMap.put(atr, lines);

        }

        return csvMap;
    }

    public ArrayList<CSVLine> parse() throws IOException {
        ArrayList<String> rawLines = read();
        int counter = 0;
        int biggestLineSize = 0;
        for (String line : rawLines) {

            String[] prsLine = line.split(regex, -1);

            //for patch
            if (biggestLineSize <= prsLine.length) {
                biggestLineSize = prsLine.length;
            }
            CSVLine csvLine = new CSVLine();

            for (int i = 0; i < prsLine.length; i++) {

                /*if(prsLine[i].length() >= maxCharacters){
                    maxCharacters = prsLine[i].length(); 
                }*/
                csvLine.insertItem(prsLine[i]);
            }

            if (hasHeader && counter == 0) {
                header = csvLine;
            } else {
                parsedLines.add(csvLine);
            }

            counter++;
        }

        patchLines(biggestLineSize);
        return parsedLines;
    }

    public void patchLines(int maxSize) {
        for (CSVLine line : parsedLines) {
            int curSize = line.getLine().size();
            for (int i = 0; i < (maxSize - curSize); i++) {
                line.insertItem("");
            }
        }
    }

    public void sort(int compareColumn, SortOrder so, SortType st) {

        boolean reverse = false;

        if (so == SortOrder.REVERSED) {
            reverse = true;
        }

        switch (st) {
        case NUMERIC:
            Collections.sort(parsedLines, new NumberColumnComparator(compareColumn, reverse));
            break;
        case ALPHABETIC:
            Collections.sort(parsedLines, new StringColumnComparator(compareColumn, reverse));
            break;
        }
    }

    public void sort(int compareColumn, SortOrder so) {

        String s1 = parsedLines.get(0).getLine().get(compareColumn);

        boolean comparingNumbers = CommonUtils.isNumeric(s1);

        if (comparingNumbers) {
            this.sort(compareColumn, so, SortType.NUMERIC);
        } else {
            this.sort(compareColumn, so, SortType.ALPHABETIC);
        }
    }

    public ArrayList<Integer> getColumnNormalizedAsInt(int column, double normMin, double normMax, double realMin, double realMax) {
        ArrayList<Integer> ints = new ArrayList<>();
        Normalizer norm = new Normalizer(realMin, realMax, normMin, normMax);
        for (CSVLine line : parsedLines) {
            //int num = Integer.parseInt(line.getLine().get(column));
            double num = Double.parseDouble(line.getLine().get(column));
            ints.add((int) Math.round(norm.normalizeValue(num)));
            //ints.add(num);
        }

        return ints;
    }

    public ArrayList<Integer> getColumnAsInt(int column) {
        ArrayList<Integer> ints = new ArrayList<>();

        for (CSVLine line : parsedLines) {
            int num = Integer.parseInt(line.getLine().get(column));
            ints.add(num);
        }

        return ints;
    }

    public ArrayList<Double> getColumnAsDouble(int column) {
        ArrayList<Double> dd = new ArrayList<>();

        for (CSVLine line : parsedLines) {
            double num = Double.parseDouble(line.getLine().get(column));
            dd.add(num);
        }

        return dd;
    }

    public ArrayList<String> getColumnAsString(int column) {
        ArrayList<String> data = new ArrayList<>();

        for (CSVLine line : parsedLines) {
            String s = line.getLine().get(column);
            data.add(s);
        }

        return data;
    }

    public ArrayList<String> getColumn(int column) {
        return getColumnAsString(column);
    }

    public Double getSumOfColumn(int column) {
        Double sum = 0.0;
        for (CSVLine line : parsedLines) {
            String cl = line.getLine().get(column);
            Double d = 0.0;
            if (CommonUtils.isNumeric(cl)) {
                d = Double.parseDouble(cl);
            }
            sum += d;
        }
        return sum;
    }

    public String[][] to2DArray() {
        int rows = parsedLines.size();

        if (header != null) {
            rows++;
        }

        int columns = parsedLines.get(0).getLine().size();
        String[][] csvArr = new String[rows][columns];

        if (header != null) {
            for (int i = 0; i < header.getLine().size(); i++) {
                csvArr[0][i] = header.getLine().get(i);
            }
        }

        int currIndex = 0;

        for (int i = 0; i < parsedLines.size(); i++) {
            for (int k = 0; k < parsedLines.get(0).getLine().size(); k++) {
                if (header != null) {
                    currIndex = i + 1;
                } else {
                    currIndex = i;
                }
                csvArr[currIndex][k] = parsedLines.get(i).getLine().get(k);
            }
        }

        return csvArr;
    }

    public CSVLine getRow(int row) {
        return parsedLines.get(row);
    }

    public void addRow(CSVLine newRow) {
        parsedLines.add(newRow);
    }

    public String toString() {
        String output = "";

        if (hasHeader) {
            output += header.toString();
        }

        for (CSVLine cl : parsedLines) {
            output += cl.toString();
        }
        return output;
    }

    public String toFile() {
        String output = "";
        try {
            FileWriter writer = new FileWriter(filepath, false);

            output += this.toString();

            writer.write(output);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error occured while writing to csv file.");
            e.printStackTrace();
        }
        return filepath;
    }

    public String toFile(String filepath) {
        String output = "";
        try {
            FileWriter writer = new FileWriter(filepath, false);

            output += this.toString();

            writer.write(output);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error occured while writing to csv file.");
            e.printStackTrace();
        }
        return filepath;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
