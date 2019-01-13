William O'Malley

Instructions to Compile Code:
javac -cp .:stdlib.jar:algs4.jar Quick3wayBM.java
javac -cp .:stdlib.jar:algs4.jar Quick3wayBM_V.java
javac -cp .:stdlib.jar:algs4.jar Rhyming.java

Instructions to Run Code:
java -cp .:stdlib.jar:algs4.jar Quick3wayBM < testCase.txt
java -cp .:stdlib.jar:algs4.jar Quick3wayBM_V < testCase.txt
java -cp .:stdlib.jar:algs4.jar Rhyming < testCase.txt

Extra Information:
Quick3wayBM begins to deviate from the proper output after the entire left side is sorted (up to and including the value 8)
for numbers1.txt. Deviates after the fourth sort of numbers2.txt and the sixth sort of numbers3.txt. Quick3wayBM_V has a 
similar issue, however for numbers2.txt and numbers3.txt the final array of numbers is sorted properly. For numbers1.txt
there is one number (17) which is not in the proper locaton. For Rhyming.java, the code will not compile, since the file
is empty. I put all of my time into trying to understand Part 1 of this project and ran out of time to try and complete
part 2.
