## project description

*this project is to solve a problem named 'Conference Track Management' that making all received proposals fitting  into the time constraints of the day*

## how to run this project
+ Open the project source directory by any java-supported development tools just like eclipse or IDE
+ Build the project using maven build tool
+ Input should put in the file named input.txt which is located under src directory, and output is writen in the file named output.txt which is flushed when you run the project again
+ Run the main function in the java file named Main.java

## a brief explanation of project's design
+ Conference contains tracks those combines sessions, which are  including several talks and should mantain some rules according to other event's contraints
+ Those events has common attributes and their own implements from their father class, and each type of events may has their particular attributes
+ Main function in the Main class run in below steps:
   1. Add all event to the list container which there are some of necessary events like lunch or network event and some of pickable events
   2. Call the function listAllArranges recursively and then add suitable event to the conferece class via checking event's contraints itself
   3. return the recursive function when the conference object is filled up, then add the one kind of proposal to the output file 

## notice
*the project may run a long time, so you can break it down as you will*