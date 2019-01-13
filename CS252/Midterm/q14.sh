#!/bin/bash
# Question 14 midterm homework
#Given the name of a program, kill an instance of the program the current user has running
# Usage: killByName <name>

name=$1

pkill -f $name

