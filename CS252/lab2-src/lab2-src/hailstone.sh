#!/bin/bash
# Hailstone basic bash (PART 1)
# To calculate and display a Hailstone sequence

# set variables from args
n=$1
l=$2

# check for proper arguments
if [ -z "$1" -o -z "$2" ];then
        echo "Usage: hailstone <start> <limit>"
        exit 1
fi

echo -n $n

# print space without new line
echo -n " "

# while loop for arg "l"
while [ $l -gt 1 ]; do
	# if n is even 
	if [ $(( n % 2)) -eq 0 ]; then
	        let n=n/2
		echo -n $n
		echo -n " "
	# else n is odd
	else
	        let n=3*n+1
		echo -n $n
		echo -n " "
	fi
	
	# iterate count
	let l=l-1
done

# extra
echo

