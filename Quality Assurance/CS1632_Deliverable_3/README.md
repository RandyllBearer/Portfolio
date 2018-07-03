# CS1632_Deliverable_3

## Notes
 - The primary code for the deliverable is located in "Del3Test.java"
 - All unit tests written with JUnit and Selenium are written in this file
 - The "TestRunner.java" is unchanged from the normal template except for the addition of "Del3Test.class" as the class to be tested
 - The tests are delayed 2s to wait for page load and may take more time than normal tests due to the necessity of establishing a connection to the webpage
 
## Execution
 - Standard execution is used for this project
 - Jars required include hamcrest, junit, selenium, and the selenium server standalone
 - All required jars are included in this repository
 - It is compiled and the tests run using the following (with ';' for Windows machines):
 
 ```
$ javac -cp .:./junit-4.12.jar:./hamcrest-core-1.3.jar:./selenium-java-2.52.0.jar:./selenium-server-standalone-2.52.0.jar *.java

$ java -cp .:./junit-4.12.jar:./hamcrest-core-1.3.jar:./selenium-java-2.52.0.jar:./selenium-server-standalone-2.52.0.jar TestRunner
```
