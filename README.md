# CSVEditor

CSVEditor is a lightweight library with purpose to be used as an efficient data extraction tool for CSV files.
This project is still under construction.

## Getting Started

Before you proceed, make sure that your local machine fulfills the following prerequisites.

### Prerequisites

To use this library, Java Runtime Enviroment (JRE) is required.

```
C:\Users\manos>java -version
java version "1.8.0_221"
Java(TM) SE Runtime Environment (build 1.8.0_221-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.221-b11, mixed mode)
```
If JRE is installed, the project can be imported in any OS/IDE, both as jar and java project.

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
``` java
csv.parse(); /* Parses the csv file given */
```
Now we are ready to apply operations.

The API can sort the csv contents by a specific column (numeric or alphabetical) normally or inverted:
``` java
int columnSortBase = 1; /* The sorting column */
SortOrder sortOrder = SortOrder.NORMAL; /* SortOrder.REVERSED can be used instead */
SortType sortType = SortOrder.NUMERIC; /* SortOrder.ALPHABETIC can be used instead */

csv.sort(columnSortBase, sortOrder, sortType); /* Sort the data */
```
A automatic sort detection method is offered, which takes the first value of the column and sorts the file based of its type, but it is unsafe for files that contain both numbers and strings. You may use it properly:
``` java
int columnSortBase = 1; /* The sorting column */
SortOrder sortOrder = SortOrder.NORMAL; /* SortOrder.REVERSED can be used instead */

csv.sort(columnSortBase, sortOrder); /* Sort the data */
```

Also, the API offers factory methods to select specific contents of the data. These may be used to get specific csv row/column projection. For example:
``` java
csv.selectRows(0, 10); /* Select the first eleven rows of the file */
csv.selectColumns(0, 1); /* Select the first two columns of every row of the file */
csv.selectColumnsRows(0, 10, 0, 1); /* Select the first two columns of the first eleven rows of the file */
```
CSVEditor has Factory methods to select rows/columns which can be used to select columns/rows from the file without changing it:
``` java
CSV c1 = CSVFactory.selectCSVRowFactory(csv, 0, 10); /* Select the first eleven rows of the file for the new CSV */
CSV c2 = CSVFactory.selectCSVColumnFactory(csv, 0, 1); /* Select the first two columns of every row of the file for the new CSV */
CSV c3 = CSVFactory.selectCSVColumnRowFactory(csv, 0, 1, 0, 10);  /* Select the first two columns of the first eleven rows of the file for the new CSV */
```

For simple data extraction of specidfic rows, CSVEditor can produce a new CSV file containing specific value attributes, given from the user, for example:
``` java
String atrr = "Retail"; /* The selected attribute */
int correspondingColumn = 1; /* The lookup column */

csv.selectColumnsBy(correspondingColumn, atrr); /* Select all data that have the value "Retail" in column 1*/
```
Note that CSVEditor requires the column of the csv to dodge ambiguity.
Also, there are also factory methods to group the columns:
``` java
String atrr = "Retail"; /* The selected attribute */
int correspondingColumn = 1; /* The lookup column */

CSV c4 = CSVFactory.selectCSVRowsByValueFactory(csv, 1, "Retail"); /* Select all data that have the value "Retail" in column 1 and put it to a new CSV */
```

Any time, you can print the csv file data:
``` java
System.out.println(csv);
```

Suppose that you are done with these operations and you want to save the file. You can use:
``` java
csv.toFile(); /* Updates the initial file with the current csv object data */
csv.toFile("new_path\\filename.csv"); /* Creates new file and saves the current csv object data */
```

To make some operations easier for the user, CSVEditor supports methods to get columns of the file converted to the demanded type, but these methods should be used carefully. For Example:
``` java
int column = 0; /* Column of the file containing Strings */
ArrayList<String>asString = csv.getColumnAsString(column); /* Use this method as the default column getter */
System.out.println(asString);

int doublesColumn = 1; /* Column of the file containing Doubles */
ArrayList<Double>asDouble = csv.getColumnAsDouble(doublesColumn); /* Use this to get the double column representation */
System.out.println(asDouble);

int integersColumn = 2; ; /* Column of the file containing Ints */
ArrayList<Integer>asInt = csv.getColumnAsInt(integersColumn); /* Use this to get the int column representation */
System.out.println(asInt);
```

The API can also return a strucure holding the csv data grouped by every attribute of a column, but its usage requires more complex API knowledge. In any case feel free to check the implementation of the CSVLine class:
``` java
int column = 1;
Map<String, ArrayList<CSVLine>> mp = csv.groupCSVByColumn(column); /* Map mp is filled by the data of every value of the column 1 */
```

This library has test packages, in which multiple examples of its usage can be seen. Also, more snippets will be added soon.

## Authors

* **Manos Chatzakis** - *Basic development* - [Git Profile](https://github.com/MChatzakis)

## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for details

