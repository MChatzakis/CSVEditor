package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import csv.CSVLine;
import java.io.File;

/**
 * Class to hold multiple methods used from all packets
 *
 * @author Manos Chatzakis (chatzakis@ics.forth.gr)
 */
public class CommonUtils {

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void printMapOfArraylist(Map<String, ArrayList<CSVLine>> mp) {
        Iterator mpIt = mp.entrySet().iterator();
        while (mpIt.hasNext()) {
            Map.Entry mapElement = (Map.Entry) mpIt.next();
            ArrayList<CSVLine> csvLine = (ArrayList<CSVLine>) mapElement.getValue();
            System.out.println("======" + mapElement.getKey() + "======");
            for (CSVLine cc : csvLine) {
                System.out.print(cc);
            }
        }
    }

    public static File[] getFilesOfDirectory(String directory) {
        File path = new File(directory); //

        if (!path.exists()) {
            System.out.println("Wrong directory given!");
            return null;
        }

        File[] files = path.listFiles();
      
        return files;
    }

    public static String getSpaces(int num) {
        String out = "";
        for (int i = 0; i < num; i++) {
            out += " ";
        }
        return out;
    }

}
