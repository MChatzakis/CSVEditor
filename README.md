# CSVEditor

CSVEditor is a lightweight library with purpose to be used as an efficient data extraction tool for CSV files.

## General
To use this library, make sure that your machine fulfills the following prerequisites:
* [Java](https://www.java.com/en/) (>=8) - Core Programming Language
* [Maven](https://maven.apache.org/) - Dependency Management 

## Download
### Without Maven
To use this library without Maven, you can download this project directly and add it as a dependency to your project using your IDE.
### Maven
Add the following to your pom.xml file:
``` xml
<repositories>
  <repository>
    <id>FORTH-ISL-snapshots</id>
    <name>FORTH ISL Nexus repository - Snapshots</name>
    <url>https://isl.ics.forth.gr/maven-snapshots/</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>gr.forth.ics.isl</groupId>
    <artifactId>CSVEditor</artifactId>
    <version>1.0-SNAPSHOT</version>
    <type>jar</type>
  </dependency>
</dependencies>
```

## Usage

### Useful Methods:
``` java
/* Constructors */
CSV(InputStream stream, String regex, boolean hasHeader) throws FileNotFoundException { ... }
CSV(String filepath, String regex, boolean hasHeader) throws FileNotFoundException { ... }

/* Parsing */
ArrayList<CSVLine> parse() throws IOException { ... }

/* Sorting */
void sort(int compareColumn, SortOrder so, SortType st) { ... }

/* Data Extraction */
Map<String, ArrayList<CSVLine>> groupCSVByColumn(int column) { ... }
ArrayList< {String, double, int} > getColumnAs{String, double, int}(int column) { ... }

/* Factories */
static CSV selectCSVColumnFactory(CSV csv, int[] columns) throws CloneNotSupportedException { ... }
static CSV selectCSVColumnFactory(CSV csv, int from, int to) throws CloneNotSupportedException { ... }
static CSV selectCSVRowFactory(CSV csv, int from, int to) throws CloneNotSupportedException { ... }
static CSV selectCSVRowFactory(CSV csv, int[] columns) throws CloneNotSupportedException { ... }

```

### Example:
``` java
CSV csv = new CSV(input\\test\\withoutHeaders\\cities.csv, ",", false);
csv.parse();
csv.sort(0, SortOrder.REVERSED, SortType.ALPHABETIC);

CSVPrinter printer = new CSVPrinter();
String prettyOutput = printer.getCSVRepresentation(csv);

System.out.println(prettyOutput);
```


## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for details.


Copyright 2020, FOUNDATION FOR RESEARCH & TECHNOLOGY - HELLAS, All rights reserved. 
