# Element.java

## Notes
 - The primary code for the deliverable is located in "Element.java" and "Del4Test.java"
 - Element.java contains the code written to parse the input file and generate the appropriate output
 - Del4Test.java contains unit tests in JUnit which are executed using TestRunner.java

## Execution
 - There are two necessary execution steps in this deliverable: Execution of the actual program, and execution of unit tests
 - Unit tests are executed in the standard way using required jars hamcrest and junit (note: use ';' on Windows machines):
 

```
$ javac -cp ".;./junit-4.12.jar;./hamcrest-core-1.3.jar;./mockito-core-1.10.19.jar;./objenesis-2.4.jar" *.java
$ java -cp ".;./junit-4.12.jar;./hamcrest-core-1.3.jar;./mockito-core-1.10.19.jar;./objenesis-2.4.jar" TestRunner
```

$ javac -cp .:./junit-4.12.jar:./hamcrest-core-1.3.jar:./mockito-core-1.10.19.jar:./objenesis-2.4.jar *.java

$ java -cp .:./junit-4.12.jar:./hamcrest-core-1.3.jar:./mockito-core-1.10.19.jar:./objenesis-2.4.jar TestRunner
```
 
 - The actual program is executed by compiling and running Element.java:
 
```
$ javac Element.java

$ java Element [path to text input file]
```
