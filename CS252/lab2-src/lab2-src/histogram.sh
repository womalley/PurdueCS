#!/bin/bash
# Histogram, File I/O (PART 4)
# Display statistics for a provided set of data

# check if argument passed is correct
if [ $# -eq 0 ]; then
	echo "Usage: histogram <filename>"
	echo
	exit 1
fi

# set arg val to variable
file=$1

# check if file is not readable
if [[ -r $file ]]; then
	continue
else
	echo "Error: $file is not readable!"
	echo
	exit 1
fi

# pull entry list and place in array
arr=$(ls | grep ' ' $file)
# make into array
arr=( $arr )

# storage array for recording bucket sizes
storArr=( $storArr )

# determines number of entries
numEntry=$(grep ' ' $file | wc -l)
#echo $numEntry
loopLength=$((numEntry*2))

# initialize "index" and "sum" to store total value of entries
sum=0
index=1
bucketVal=100

# print number of entries
echo "$numEntry scores total..."

# loop through all entries
while [ $index -le $loopLength ]; do
	
	# add to sum
	sum=$((sum+arr[$index]))
	#echo "NUM: ${arr[$index]}"

	# increment according bucket
	loc=$((arr[$index]/10))

	# store in storArr
	storArr[loc]=$((storArr[$loc]+1))
	#echo ${storArr[$loc]}

	let index=index+2 # iterate by 2 to skip words (turkstra#)

done # while loop done

index=10 # reset to 10 to iterate backwards for histogram

# loop to print histogram
while [ $bucketVal -ge 0 ]; do

	# determine how many loops to print histogram
	counter=${storArr[$index]}
	#echo $counter

	# print histogram values
	#echo -n "$bucketVal: "
	printf "%3s" $bucketVal 
	echo -n ": "

	# print histogram
	while [ $counter -ne 0 ]; do
		
		# print '=' symbol
		echo -n "="

		let counter=counter-1		

	done # end of printing loop

	echo # newline

	# decrement counts accordingly	
	let bucketVal=bucketVal-10
	let index=index-1

done # while loop done

# print average of all entries
avg=$((sum/numEntry))
echo "Average: $avg"
exit 0

