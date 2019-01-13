William O'Malley

Instructions to Compile Code:
javac -cp .:stdlib.jar:algs4.jar Percolation.java
javac -cp .:stdlib.jar:algs4.jar PercolationSlow.java
javac -cp .:stdlib.jar:algs4.jar PercolationVisualizer.java
javac -cp .:stdlib.jar:algs4.jar PercolationStats.java

Instructions to Run Code:
java -cp .:stdlib.jar:algs4.jar Percolation < testCase.txt
java -cp .:stdlib.jar:algs4.jar PercolationSlow < testCase.txt
java -cp .:stdlib.jar:algs4.jar PercolationVisualizer < testCase.txt
java -cp .:stdlib.jar:algs4.jar PercolationStats <N-value> <repetitions> <type "fast" or "slow">

Extra Information:
In place of "<N-value>" and "<repetitions>" an integer value should be placed. For the third argument
of PercolationStats a string, without the quotation marks, "fast" or "slow" should be placed to 
determine whether weighted quick union (fast) or quick find (slow) will run. For my project I created 
two separate .java files for implementing the weighted quick union and quick find methods. Weighted 
quick union is within the Percolation.java file and quick find is within the PercolationSlow.java file.
I went this route as it was suggested by my TA and made it much easier for me to implement my code
without having to think how I would get both methods to work within one .java file. I plan on 
combining the two methods into one for my personal use, however I have run out of time to try and create
a .java file that can do both methods. I created a simple function within my code to check for boundaries
so that the x and y values will throw an error if the x and y values are outside the bounds. One error 
check that I did not code for was if an x or y value is given in a text file without its counterpart.
The way checked to see if a cell could be connected to another open cell near it was by taking
the 2D-array values and making them into an index spot on the virtual grid. This allowed my checks
to be simple. Furthermore, I took care of the backwash error by implementing a virtual top and bottom.
This allowed me to also easily tell if the grid percolated because if the virtual top and bottom 
were connected that meant the grid percolated.

