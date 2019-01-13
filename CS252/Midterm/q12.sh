#!/bin/bash
# Question 12 on homework. 
# Write a shell command to read the current user’s lab2 grade from a file organized as follows

lab2Spot=$(ls | grep "lab2" grades.txt | cut -d "2" -f1 | wc -w)

labGrade=$(ls | grep "$USER" grades.txt)

#make string an array
labGrade=( $labGrade )

echo ${labGrade[$lab2Spot-1]}

