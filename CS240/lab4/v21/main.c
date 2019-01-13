/*
	PROBLEM 2 LAB 4
	MAIN
	Reads an integer of type unsigned int from stdin and uses bitwise operators.
	Inspect the four least significant bits of the variable using a mask. Output
	the value in decimal and hexadecimal form. Shift through all 32 bits.
*/

#include <stdio.h>
#include <string.h>
#define BITLEN 32

unsigned int binRep(unsigned int);
unsigned int power(unsigned int, unsigned int);
char hexa(unsigned int);

void main() {

	unsigned int num; //input number
	unsigned int maskVal = 4; //controls what numbers are masked
	unsigned int maskBin = 1; //mask set to 1 for creating binary representation
	unsigned int mask; //mask
	unsigned int bitVal;
	unsigned int binNum;
	char hexVal = '0';
	int index;

	//prompts user for input
	printf("Enter a number:\n");

	//read in number from user
	scanf("%u", &num);

	//set mask
	mask = ~(~0 << maskVal);
	
	//prints binary representation
	binRep(num);
	
//	printf("Binary representation: %032u\n", binNum); //prints 32 places (for 32-bit int)

	//loop through number
	for (index = 0; index < BITLEN; index = index + 4) {
		bitVal = num & mask; //integer from 4-bit conversion
		hexVal = hexa(bitVal); //hexadecimal value from 4-bit value

		printf("%u, 0%c\n", bitVal, hexVal);
		for (unsigned int k = 0; k < maskVal; k++) {
							
			bitVal = power(2, k) + bitVal; 
			bitVal >> 1;
		}
		num = num >> maskVal;
	}

}

//Converts user input number to binary representation
unsigned int binRep(unsigned int num) {

	int index = 0;
	unsigned int m = 1 << (BITLEN -1); //m for mask

	for (index = 0; index < BITLEN; index++) {
		printf("%u", (num & (m >> index)) != 0);
	}
	printf("\n");

}

//Converts 4-bit value to integer value
unsigned int power(unsigned int base, unsigned int exponent) {
	
	unsigned int ans = 1;
	while (exponent) { //while true
		if (exponent & 1) {
			ans = ans * base;
		}
		exponent >>= 1;
		base = base * base;
		
	}
	return (ans);
}

//Converts integer (from 4-bit value) to hexadecimal value
char hexa(unsigned int binToHex) {
	char letter = '0';
	if (binToHex <= 9 && binToHex >= 0) {
		return (binToHex += '0');
	}
	else {
		if (binToHex == 10) {
			return (letter = 'a');
		}
		else if (binToHex == 11) {
			return (letter = 'b');
		}
		else if (binToHex == 12) {
			return (letter = 'c');
		}
                else if (binToHex == 13) {
                        return (letter = 'd');
                }
                else if (binToHex == 14) {
                        return (letter = 'e');
                }
                else if (binToHex == 15) {
                        return (letter = 'f');
                }
	}
}

