#!/bin/bash
# Compile, looping with non-integer values (PART 2)
# To compile and test a series of C source files

# check for proper argument
if [ $# -eq 1 ];then
	echo "Usage: compile"
	echo
	exit 1
fi


# finds all files with src*.c ('*' being the number)
result=$(ls | grep "src[0-9]*.c")

# setting up as array
result=( $result )

# determines how many indices in array "result"
count=${#result[@]}

index=0

# while loop to iterate through result source files to be compiled
while [ $index -ne $count ]; do
	
	inputIndex=0
	name="${result[$index]}" # src*.c file at index
	echo "C program: $name"

	# try to compile .c file
	gcc -Wall -std=c99 $name	
	
	# '$?' means last command
	if [ $? -eq 0 ]; then
		echo "Successfully compiled!"

		# finds all files with input*.data ('*' being the number)
		inputFile=$(ls | grep "input[0-9]*.data")
		# setting up as array
		inputFile=( $inputFile )
		# determines how many indices in array "inputFile"
		inputCount=${#inputFile[@]}
	
		# while loop to iterate through input files to be executed
		while [ $inputIndex -ne $inputCount ]; do
			inputName="${inputFile[$inputIndex]}" # input*.data file at index inputIndex
			echo "Input file: $inputName"

			# try to execute generated a.out on each input file
			a.out < $inputName

			# '$?' means last command
			if [ $? -eq 0 ]; then


				echo "Run successful."

			else
				
				echo "Run failed on $inputName."

			fi # end if-else statement

			let inputIndex=inputIndex+1 # iterate inputIndex

		done # input file while loop finished

	else
		echo "Compilation of $name failed!"
		
	fi # end if-else statement


	echo # newline between .c files
	let index=index+1 # iterate index
done # while loop finished
exit 0
