package app;

import csv.CSV;

import java.io.FileNotFoundException;
import java.io.IOException;

import sort.SortOrder;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Command Line Application
 * Example: 
 * @author Manos Chatzakis
 */
public class CommandLineΑpp {

    private static CommandLineParser parser = new DefaultParser();
    private static Options options = new Options();

    /**
     * Command line options
     */
    private static void createOptions() {
        Option file = new Option("f", "file", true, "The input csv file");
        file.setRequired(true);

        Option del = new Option("d", "delimiter", true, "The delimeter used");
        del.setRequired(true);

        Option op = new Option("o", "operation", true, "The operation to be done (sort)");
        del.setRequired(true);

        Option col = new Option("c", "sortColumn", true, "CSV sort column");
        col.setRequired(true);

        Option type = new Option("t", "sortType", true, "CSV sort type");
        type.setRequired(true);
        
        options.addOption(file)
                .addOption(del)
                .addOption(op)
                .addOption(col)
                .addOption(type);
    }

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException, Exception {
        createOptions();
        CommandLine cli = parser.parse(options, args);

        String filePath = cli.getOptionValue("f");
        String del = cli.getOptionValue("d");

        CSV csv = new CSV(filePath, del);
        csv.parse();
        switch (cli.getOptionValue("o")) {
        case "sort":
            csv.sort(0, SortOrder.NORMAL);
            break;
        }

    }

}


