This framework is designed using Selenium 3.0 and Java with Maven and testNG. Approach of this framework is based on data driven
technique. Selenium layer is implemented in base class. Every test case is extending base class. Test case can be executed by testng.xml
or mvn test .Surefire report will be generated at the end of test execution.Using pom.xml this project can be integrated with Jenkins also.


Package :- UIAutomation/src/test/resources/
File :- or.properties [ Object repository ]
project.properties [ read configuration data ]

/UIAutomation/tree/master/src/test/java/drivers "Contains the driver executables for different browsers"

Class :- UIAutomation/src/test/java/com/assignment/ui/base/baseTest.java
baseTest.java "Is a selenium layer.It has all the function for driver initialization and UI interaction."

Class :- UIAutomation/src/test/java/com/assignment/ui/testcase/
newuserScreen.java " This is a test case. "

