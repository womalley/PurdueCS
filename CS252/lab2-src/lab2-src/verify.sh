#!/bin/bash
# Verify, File I/O and Patterns (PART 5)
# To display validation information regarding data

# checks if argument passed and displays message if it is not passed
if [ $# -eq 0 ]; then
	echo "Usage: verify <filename>"
	exit 1
fi

# set command argument (file) to variable
file=$1

# set flags for formatting errors
nameError=0
addressError=0

# check if file is readable
if [[ -r $file ]]; then # file is readable

	# grep to get full name
	name=$(grep "" $file | cut -d ";" -f1)

	# determine if there is a middle initial (word count)
	wordCount=$(grep "" $file | cut -d ";" -f1 | wc -w)

	# determine white space count, used with "wordCount" to determine if format is correct
	whiteSpace=$(grep -o " " <<<"$name" | wc -l)

	# set flag if extra whitespace found (fails formatting)
	if [ $whiteSpace -ge $wordCount ]; then
		let nameError=1
	fi

	# check name format for no middle initial
	if [ $wordCount -eq 2 ]; then
		result=$(grep -o [A-Z][a-z][a-z]*[,][' '][A-Z][a-z][a-z]* $file | wc -l)	
		#echo "No Middle: $result"
		
		if [ $result -eq 0 ]; then
			let nameError=1
		fi

	# check name format for middle initial
	else
		result=$(grep -o [A-Z][a-z][a-z]*[,][' '][A-Z][a-z][a-z]*[' '][A-Z][.] $file | wc -l)
                #echo "Middle: $result"

		 if [ $result -eq 0 ]; then
                        let nameError=1
                 fi

	fi

	# grep to get full address
        address=$(grep "" $file | cut -d ";" -f2)

	# determine words in address
	addressWord=$(grep "" $file | cut -d ";" -f2 | wc -w)
	# determine white space count for "addressWord"
        whiteSpace=$(grep -o " " <<<"$address" | wc -l)

        # set flag if extra whitespace found (fails formatting)
        if [ $whiteSpace -ge $addressWord ]; then
                let addressError=1
        fi

	addressCount=0
	correctFormat=0 # counts all correct words in format for comparision later

	# checks if there is extra white space
	if [ $whiteSpace -ge $addressWord ]; then
		let addressError=1
	fi

	# check if address format is correct
	# check if first part of address is only numbers
	addressCount=$(grep "" $file | cut -d ";" -f2 | cut -d " " -f1 | grep "[A-Z]" | wc -l)

	if [ $addressCount -eq 1 ]; then
		let addressError=1
	else
		let correctFormat=correctFormat+1
	fi

	# check if the rest of the address is formatted correctly
	addressCount=$(grep "" $file | cut -d ";" -f2 | grep -o "[A-Z][a-z]*[.]*" | wc -l)

	# add correctly formatted words to count
	correctFormat=$((correctFormat+addressCount))

	# check case for numbered street name (i.e. 7th)
	addressCount=$(grep "" $file | cut -d ";" -f2 | grep -o "[0-9][0-9]*[a-z]*[.]*" | wc -l)
	let addressCount=$((addressCount-1)) # offset for address number

	# add correctly formatted words to count
        correctFormat=$((correctFormat+addressCount))

	if [ $correctFormat -ne $addressWord ]; then
		let addressError=1	
	fi
	
	# display appropriate message
	if [ $nameError -eq 1 -a $addressError -eq 1 ]; then
		echo "Invalid name and address!"
	elif [ $nameError -eq 1 ]; then
		echo "Invalid name!"
	elif [ $addressError -eq 1 ]; then
		echo "Invalid address!"
	else
		echo "Data is valid."
	fi # end else-if structure
	exit 0

else
	# file is not readable
	echo "Error: $file is not readable!"
	exit 2

fi


