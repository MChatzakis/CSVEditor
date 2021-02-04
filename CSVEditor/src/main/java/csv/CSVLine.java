package csv;

import java.util.ArrayList;
import lombok.Data;

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

    public ArrayList<String> getColumns(int from, int to) {
        ArrayList<String> selected = new ArrayList<>();
        for (int i = 0; i < line.size(); i++) {
            if (i >= from && i <= to) {
                selected.add(line.get(i));
            }
            if (i >= to) {
                break;
            }
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

}
