William O'Malley

Instructions to Compile Code:
javac -cp .:stdlib.jar:algs4.jar Filter.java
javac -cp .:stdlib.jar:algs4.jar Query.java

Instructions to Run Code:
java -cp .:stdlib.jar:algs4.jar Filter < testCase.txt
java -cp .:stdlib.jar:algs4.jar Query < testCase.txt

Extra Information:
Filter works well and in a timely fashion. I chose to use a Doubly Linked List in hopes of getting rid of some overhead. 
I adapted my code in Filter.java for Query.java. I had runtime issues with my first attempt in query3.txt, however
the other two cases worked fine. I created a doubly linked list and sorted it as if it was a BST, where the left child
was 2 times the parents index and the right child was 2 times plus one the parents index. I got as far as building the
tree in the right order and beginning the search for the higher x and y node values. The code described is currently
commented out as I feared I would not be able to fix it before the deadline. Unfortunately, this was the case. I coded
a recursive heavy function that stepped through a y-increasing sorted DLL (doubly linked list). Then the DLL is placed 
into a new DLL and sorted for x-increasing. The larger values are placed in the final DLL and the process is repeated. 
I described the process as the code is rather difficult to look at due to my scrambling to try and get a working program
that passed query3.txt. However, I failed to realize at the time that recursively calling the function with DLLs in the 
function would store the address and eventually cause a stack overflow. 

Notes:
I am by far not the best at coding, but I started this project the day it was released and have been woring on this project 
roughly 6-7 hours (if not more) each day until 10/16/2017. The first part of the project was suitable, however part 
two seemed to be a struggle for many people to understand from the start. I plan to complete this project the right way 
in my free time if possible, as I have put dozens of hours into part 2. I apologize for the aesthetics of Query.java.
