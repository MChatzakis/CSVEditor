package csv;

import java.util.ArrayList;
import lombok.Data;

/**
 * This class represents a single line of a CSV line
 * 
 * @author Manos Chatzakis (chatzakis@ics.forth.gr)
 */
@Data
class CSVLine {

    ArrayList<String> line = new ArrayList<>();

    public CSVLine() {

    }

    public void insertItem(String str) {
        line.add(str);
    }

    public String toString() {
        String output = "";

        for (int i = 0; i < line.size(); i++) {
            output += line.get(i);
            if(i < line.size() - 1){
                output += ",";
            }
        }
        output += "\n";
        return output;
    }

}
