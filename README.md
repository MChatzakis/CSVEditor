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

## Authors

* **Manos Chatzakis** - *Basic development* - [Git Profile](https://github.com/MChatzakis)

## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for details

