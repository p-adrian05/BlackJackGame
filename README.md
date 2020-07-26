# Blackjack Game

The Blackjack (Twenty-One) card game in JavaFX.

## Requirements

Building the project requires JDK 11 or later and [Apache Maven](https://maven.apache.org/).

mvn clover:setup test clover:aggregate clover:clover
mvn clean site site:stage
mvn package
java -jar ./FinalModule/target/FinalModule-1.0-shaded.jar
