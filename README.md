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
```
CSV csv = new CSV("your_directory\\filename.csv", ",");
```
You can use every possible regex (here ","), but the default CSV file has data splitted with comma.
After loading the file to the API, parsing is needed, so the data are splitted:
```
csv.parse(); /* Parses the csv file givem */
```
Now we are ready to apply operations.

The API can sort the csv contents by a specific column (numeric or alphabetical) normally or inverted:
```
csv.parse(); /* Parses the csv file givem */
```
Also, the API offers factory methods to select specific contents of the data. For example,
```
CSV c1 = csv.selectRows(0, 10); /*Select the first eleven rows of the file*/
CSV c2 = csv.selectColumns(0, 1); /*Select the first two columns of every row of the file*/
CSV c3 = csv.selectColumnsRows(0, 10, 0, 1); /*Select the first two columns of the first eleven rows of the file*/
```

## Authors

* **Manos Chatzakis** - *Basic development* - [Git Profile](https://github.com/MChatzakis)

## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for details

