/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import static java.lang.String.format;

/**
 *
 * @author manos
 */
public class CSVPrinter {

    private static final char BORDER_KNOT = '+';
    private static final char HORIZONTAL_BORDER = '-';
    private static final char VERTICAL_BORDER = '|';

    private static final String DEFAULT_AS_NULL = "(NULL)";

    private final String asNull;

    public CSVPrinter(String asNull) {
        this.asNull = asNull;
    }
    
    public CSVPrinter(){
        this(DEFAULT_AS_NULL);
    }

    public String getCSVRepresentation(CSV csv) {
        String output = "";
        String[][] csvArr = csv.to2DArray();

        final int[] widths = new int[getMaxColumns(csvArr)];
        adjustColumnWidths(csvArr, widths);
        output += printPreparedTable(csvArr, widths, getHorizontalBorder(widths));
        return output;
    }

    private String printPreparedTable(String[][] table, int widths[], String horizontalBorder) {
        String output = "";
        final int lineLength = horizontalBorder.length();
        //out.println(horizontalBorder);
        output += horizontalBorder+"\n";
        for (final String[] row : table) {
            if (row != null) {
                //out.println(getRow(row, widths, lineLength));
                output += getRow(row, widths, lineLength) + "\n";
                //out.println(horizontalBorder);
                output += horizontalBorder + "\n";
            }
        }
        return output;
    }

    private String getRow(String[] row, int[] widths, int lineLength) {
        final StringBuilder builder = new StringBuilder(lineLength).append(VERTICAL_BORDER);
        final int maxWidths = widths.length;
        for (int i = 0; i < maxWidths; i++) {
            builder.append(padRight(getCellValue(safeGet(row, i, null)), widths[i])).append(VERTICAL_BORDER);
        }
        return builder.toString();
    }

    private String getHorizontalBorder(int[] widths) {
        final StringBuilder builder = new StringBuilder(256);
        builder.append(BORDER_KNOT);
        for (final int w : widths) {
            for (int i = 0; i < w; i++) {
                builder.append(HORIZONTAL_BORDER);
            }
            builder.append(BORDER_KNOT);
        }
        return builder.toString();
    }

    private void adjustColumnWidths(String[][] rows, int[] widths) {
        for (final String[] row : rows) {
            if (row != null) {
                for (int c = 0; c < widths.length; c++) {
                    final String cv = getCellValue(safeGet(row, c, asNull));
                    final int l = cv.length();
                    if (widths[c] < l) {
                        widths[c] = l;
                    }
                }
            }
        }
    }

    private int getMaxColumns(String[][] table) {
        int max = 0;
        for (final String[] row : table) {
            if (row != null && row.length > max) {
                max = row.length;
            }
        }
        return max;
    }

    private static String safeGet(String[] array, int index, String defaultValue) {
        return index < array.length ? array[index] : defaultValue;
    }

    private String getCellValue(Object value) {
        return value == null ? asNull : value.toString();
    }

    private static String padRight(String s, int n) {
        return format("%1$-" + n + "s", s);
    }
}
