#!/bin/bash
# Question 13 for midterm
#Given two versions of a file, write a command to send an email containing the differences to a specified user.
# Usage: changelog <file1> <file2> <send_to>

file1=$1
file2=$2
sendTo=$3

difference=$(diff $file1 $file2)
echo "$difference" > tmp-message
echo >> tmp-message
/usr/bin/mailx -s "File Differences" $sendTo < tmp-message
