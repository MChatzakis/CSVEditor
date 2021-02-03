package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

import lombok.Data;
import sort.SortOrder;
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

    private Pair<Integer, Integer> columnFromTo;
    private Pair<Integer, Integer> rowFromTo;

    private boolean isSelectingSpecific;
    private boolean hasHeader;

    public CSV(String filepath, String regex, boolean hasHeader) throws FileNotFoundException {
        parsedLines = new ArrayList<>();

        this.filepath = filepath;
        this.regex = regex;
        this.hasHeader = hasHeader;
        this.isSelectingSpecific = false;

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

    public CSV selectRows(int from, int to) throws CloneNotSupportedException {
        ArrayList<CSVLine> selected = new ArrayList<>();

        CSV csv = (CSV) this.clone();

        for (int i = 0; i < parsedLines.size(); i++) {

            if (i >= from && i < to) {
                selected.add(parsedLines.get(i));
            }
            if (i >= to) {
                break;
            }
        }
        csv.setParsedLines(selected);
        return csv;
    }

    public CSV selectColumns(int from, int to) throws CloneNotSupportedException {
        ArrayList<CSVLine> selected = new ArrayList<>();
        CSV csv = (CSV) this.clone();

        for (int i = 0; i < parsedLines.size(); i++) {
            CSVLine line = new CSVLine();
            line.setLine(parsedLines.get(i).getColumns(from, to));
            selected.add(line);
        }

        csv.setParsedLines(selected);
        return csv;
    }

    public CSV selectColumnsRows(int rowFrom, int rowTo, int columnFrom, int columnTo) throws CloneNotSupportedException {
        ArrayList<CSVLine> selected = new ArrayList<>();
        CSV csv = (CSV) this.clone();
        for (int i = 0; i < parsedLines.size(); i++) {
            if (i >= rowFrom && i < rowTo) {
                CSVLine line = new CSVLine();
                line.setLine(parsedLines.get(i).getColumns(columnFrom, columnTo));
                selected.add(line);
            }
            if (i >= rowTo) {
                break;
            }
        }

        csv.setParsedLines(selected);
        return csv;
    }

    public CSV selectColumnsBy(int column, String value) throws CloneNotSupportedException {
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

    //ongoing
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
            lines = selectColumnsBy(column, atr).getParsedLines();
            csvMap.put(atr, lines);

        }

        return csvMap;
    }

    public ArrayList<CSVLine> parse() throws IOException {
        ArrayList<String> rawLines = read();
        int counter = 0;
        for (String line : rawLines) {
            String[] prsLine = line.split(regex);
            CSVLine csvLine = new CSVLine();

            for (int i = 0; i < prsLine.length; i++) {
                csvLine.insertItem(prsLine[i]);
            }

            if (hasHeader && counter == 0) {

                header = csvLine;
            } else {
                parsedLines.add(csvLine);
            }

            counter++;
        }

        return parsedLines;
    }

    public ArrayList<CSVLine> sort(int compareColumn, SortOrder so) {

        String s1 = parsedLines.get(0).getLine().get(compareColumn);
        String s2 = parsedLines.get(0).getLine().get(compareColumn);

        boolean comparingNumbers = CommonUtils.isNumeric(s1) && CommonUtils.isNumeric(s2);

        if (comparingNumbers) {
            Collections.sort(parsedLines, new Comparator<CSVLine>() {
                @Override
                public int compare(CSVLine c1, CSVLine c2) {

                    String s1 = c1.getLine().get(compareColumn);
                    if (!CommonUtils.isNumeric(s1)) {
                        s1 = "0";
                    }
                    String s2 = c2.getLine().get(compareColumn);
                    if (!CommonUtils.isNumeric(s2)) {
                        s2 = "0";
                    }

                    Double d1 = Double.parseDouble(s1);
                    Double d2 = Double.parseDouble(s2);

                    return Double.compare(d1, d2);
                }
            });

        } else {
            Collections.sort(parsedLines, new Comparator<CSVLine>() {
                @Override
                public int compare(CSVLine c1, CSVLine c2) {
                    String s1 = c1.getLine().get(compareColumn);
                    String s2 = c2.getLine().get(compareColumn);
                    return s1.compareTo(s2);
                }
            });
        }

        if (so == SortOrder.REVERSED) {
            Collections.reverse(parsedLines);
        }

        return parsedLines;
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

    public ArrayList<String> getColumn(int column) {
        ArrayList<String> data = new ArrayList<>();

        for (CSVLine line : parsedLines) {
            String s = line.getLine().get(column);
            data.add(s);
        }

        return data;
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
