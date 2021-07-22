package csv;

import java.util.ArrayList;
import lombok.Data;
import utils.CommonUtils;

/**
 * This class represents a single line of a CSV line
 *
 * @author Manos Chatzakis (chatzakis@ics.forth.gr)
 */
@Data
public class CSVLine {

    ArrayList<String> line = new ArrayList<>();

    public CSVLine() {

    }

    public void insertItem(String str) {
        line.add(str);
    }

    public void selectColumns(int from, int to){
        ArrayList<String> selected = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            selected.add(line.get(i));
            if (i >= line.size() - 1) {
                break;
            }
        }
        line = selected;
    }
    
    public void selectColumns(int [] columns){
        ArrayList<String> selected = new ArrayList<>();
        for (int i = 0; i < columns.length; i++) {
            int col = columns[i];
            selected.add(line.get(col));
        }
        line = selected;
    }
    
    public ArrayList<String> getColumns(int from, int to) {
        ArrayList<String> selected = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            selected.add(line.get(i));
            if (i >= line.size() - 1) {
                break;
            }
        }
        return selected;
    }

    public ArrayList<String> getColumns(int[] columns) {
        ArrayList<String> selected = new ArrayList<>();
        for (int i = 0; i < columns.length; i++) {
            int col = columns[i];
            selected.add(line.get(col));
        }
        return selected;
    }

    public String toString() {
        String output = "";

        for (int i = 0; i < line.size(); i++) {
            output += line.get(i);
            if (i < line.size() - 1) {
                output += ",";
            }
        }

        output += "\n";
        return output;
    }

    public String toString(int maxChars) {
        String output = "|";
        for (int i = 0; i < line.size(); i++) {
            output += line.get(i);
            if (i < line.size() - 1) {
                output += CommonUtils.getSpaces(10);
                output += "|";
                output += CommonUtils.getSpaces(10);
            }

        }

        output += "|\n";
        return output;
    }

}
