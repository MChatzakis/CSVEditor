package utils;

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
}
