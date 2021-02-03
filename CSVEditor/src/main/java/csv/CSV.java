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
public class CSV {

    private String filepath;
    private String regex;

    private File csvFile;

    private ArrayList<CSVLine> parsedLines;

    private Pair<Integer, Integer> columnToFrom;
    private Pair<Integer, Integer> rowToFrom;

    public CSV(String filepath, String regex) throws FileNotFoundException {
        parsedLines = new ArrayList<>();

        this.filepath = filepath;
        this.regex = regex;

        csvFile = new File(filepath);

        if (!csvFile.exists()) {
            throw new FileNotFoundException("The file given does not exist!");
        }
    }

    public CSV(String filepath, String regex, int columnFrom, int columnTo, int rowFrom, int rowTo) throws FileNotFoundException {
        parsedLines = new ArrayList<>();

        this.filepath = filepath;
        this.regex = regex;

        csvFile = new File(filepath);

        if (!csvFile.exists()) {
            throw new FileNotFoundException("The file given does not exist!");
        }
    }

    private ArrayList<String> read() throws IOException {
        ArrayList<String> rawLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                //line = line.replace(" ", "_");
                rawLines.add(line);
            }
        }
        return rawLines;
    }

    public ArrayList<CSVLine> parse() throws IOException {
        ArrayList<String> rawLines = read();

        for (String line : rawLines) {

            String[] prsLine = line.split(regex);
            CSVLine csvLine = new CSVLine();

            for (int i = 0; i < prsLine.length; i++) {
                csvLine.insertItem(prsLine[i]);
            }

            parsedLines.add(csvLine);
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
                    Double d1 = Double.parseDouble(c1.getLine().get(compareColumn));
                    Double d2 = Double.parseDouble(c2.getLine().get(compareColumn));
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

    public void updateFile() {
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
    }

    public void cerateNewFile(String filepath) {
        String output = "";
        try {
            FileWriter writer = new FileWriter(filepath);

            output += this.toString();

            writer.write(output);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error occured while writing to csv file.");
            e.printStackTrace();
        }
    }

    public String toString() {
        String output = "";
        for (CSVLine cl : parsedLines) {
            output += cl.toString();
        }
        return output;
    }
}
