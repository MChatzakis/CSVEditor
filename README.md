# CSVEditor

CSVEditor is a lightweight library with purpose to be used as an efficient data extraction tool for CSV files.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

To use this library, Java Runtime Enviroment (JRE) is required.

```
C:\Users\manos>java -version
java version "1.8.0_221"
Java(TM) SE Runtime Environment (build 1.8.0_221-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.221-b11, mixed mode)
```

## Built With

* [Java](https://www.java.com/en/) - Core Programming Language
* [Maven](https://maven.apache.org/) - Dependency Management 

## Examples
First need to do is instanciate the csv object:
``` java
String filepath = "your_dir\\filename.csv"; /* The path to the csv file */
String regex = ","; /* The selected delimeter */
Boolean hasHeader = false; /* Determine if the first line will get special treatment from the API */

CSV csv = new CSV(filepath,regex,hasHeader); /* Instanciate the object */
```
You can use every possible regex (here ","), but the default CSV file has data splitted with comma.
After loading the file to the API, parsing is needed, so the data are splitted:
```
csv.parse(); /* Parses the csv file given */
```
Now we are ready to apply operations.

The API can sort the csv contents by a specific column (numeric or alphabetical) normally or inverted:
```
int columnSortBase = 1;
SortOrder sortOrder = SortOrder.NORMAL /* or SortOrder.REVERSED */
csv.sort(columnSortBase, sortOrder); /* Sort the csv file data */
```
CSVEditor checks if the given column contains Strings (thus, alphabetical sort) or numbers (thus, numeric sort).

Also, the API offers factory methods to select specific contents of the data. For example,
```
CSV c1 = csv.selectRows(0, 10); /* Select the first eleven rows of the file */
CSV c2 = csv.selectColumns(0, 1); /* Select the first two columns of every row of the file */
CSV c3 = csv.selectColumnsRows(0, 10, 0, 1); /* Select the first two columns of the first eleven rows of the file */
```
For simple data extraction of specidfic rows, CSVEditor can produce a new CSV file containing specific value attributes, given from the user, for example:
```
String atrr = "Retail";
int correspondingColumn = 1;
CSV c4 = csv.selectColumnsBy(correspondingColumn, atrr); /* Select all data that have the value "Retail" in column 1*/
```
Note that CSVEditor requires the column of the csv to dodge ambiguity.

Any time, you can print the csv file data:
```
System.out.println(csv);
```

Suppose that you are done with these operations and you want to save the file. You can use:
```
csv.toFile(); /* Updates the initial file with the current csv object data */
csv.toFile("new_path\\filename.csv"); /* Creates new file and saves the current csv object data */
```

## Authors

* **Manos Chatzakis** - *Basic development* - [Git Profile](https://github.com/MChatzakis)

## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for details

