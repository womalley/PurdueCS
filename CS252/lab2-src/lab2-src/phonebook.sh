#!/bin/bash 
# Phonebook, UNIX commands (PART 3)
# To display directory information

# check if proper argument passed
if [ $# -eq 1 ]; then
	echo "Usage: phonebook"
	exit 1
fi

# welcome message
echo "Welcome to MagicPhone!"
echo -n "Please enter part or all of a name to search for: "

# take user input for name search
read input

# determine number of contacts and contact information
search=$(ls | grep $input house_dir_tab.txt)

# make search an array
search=( $search )

# determines the number of contacts greped
count=$(ls | grep "$input" house_dir_tab.txt | wc -l)
#echo $count

# determine if the contact has a middle initial (line word count)
words=$(ls | grep -m 1 "$input" house_dir_tab.txt | wc -w)
#echo $words

# determine the number of loops needed to get name
nameCount=$((words-4))

# if grep returns more than one line return first persons info
if [ $count -gt 1 ]; then
	# display message to user
	echo "I found $count matches"
	echo "You might want to be more specific."
	echo "The first of these matches is:"

	# display results for the first contact
	# loop to get name
	index=0
	echo -n "Name: "
	while [ $index -ne $nameCount ]; do
		name="${search[$index]}"
		echo -n "$name "
		let index=index+1

	done # name looping done

	echo # fixes format from displaying name
	
	# display state and phone number
	echo "State: ${search[$index]}"

	let index=index+2
	echo "Phone: ${search[$index]}"

	# display end time and date
	DATE=`date +'%a %b %d %T %Z %Y'`
	echo "Search complete on $DATE"
	exit 0
else
	# displays message when search shows nothing
	if [ $count -eq 0 ]; then
		echo "Sorry, I could not finde that person."
		exit 1	
	# displays contact information for searched contact
	else
		echo "Match found!"
		index=0
		echo -n "Name: "
		while [ $index -ne $nameCount ]; do
			name="${search[$index]}"
			echo -n "$name "
			let index=index+1

		done # name looping done
	
		echo # fixes format from displaying name

		# display state and phone number
		echo "State: ${search[$index]}"

		let index=index+2
		echo "Phone: ${search[$index]}"

		# display end time and date
		DATE=`date +'%a %b %d %T %Z %Y'`
		echo "Search complete on $DATE"		
		
	fi # end inner if-else structure

fi # end if-else structure

exit 0
