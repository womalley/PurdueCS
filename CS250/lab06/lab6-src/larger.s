@ Larger.s code
@ CS250 Lab06
@ 10/22/2017

.data

	num1: .word 0			@ initializes first number to value zero
	num2: .word 0			@ initializes second number to value zero
	
	readInt: .asciz "%d"		@ read integer value (num1 and num2)
	writeInt: .asciz "%d\n"		@ write larger of the two integer values (num1 or num2)

.text					@ prevents bus error
.global main				@ makes global for loader

main:

        push {fp, lr}                   @ pushes number onto stack (stays aligned)

	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@		 scanf for the numbers			 @
	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	ldr r0, =readInt		@ r0 pointer to readInt (loads readInt into r0)
	ldr r1, =num1			@ r1 pointer to num1 (loads num1 into r1)
	bl scanf			@ branch with link to scan in number

	ldr r0, =readInt		@ r0 pointer to readInt (loads readInt into r0)
	ldr r1, =num2			@ r1 pointer to num2 (loads num2 into r1)
	bl scanf			@ branch with link to scan in number

        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        @             load numbers into registers                @
        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	ldr r1, =num1			@ ref first number (r1 pointer to num1)
	ldr r2, [r1]			@ loads value memory contents of r2 (register indirect or dereference)	
	ldr r1, =num2			@ ref second number (r1 pointer to num2)
	ldr r3, [r1]			@ loads value memory contents of r3 (register indirect or dereference)

        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        @           compare numbers and print larger             @
        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	cmp r2, r3			@ compares the two number values
	blt lessThan			@ checks if less than (redirects to lessThan below)
	
	ldr r0, =writeInt		@ r0 pointer to writeInt (print of large number)
	ldr r1, =num1			@ r1 pointer to num1 (loads num1 into r1)
	ldr r1, [r1]			@ sets r1 to value of r1 (dereference)
	bl printf			@ branch with link to printf to print number

	b popStack			@ branch to popStack function

	@@@@@@@@@@@@@@@@@@@@@ CALLS BELOW @@@@@@@@@@@@@@@@@@@@@@@@
	lessThan:
	ldr r0, =writeInt		@ r0 pointer to writeInt (print of large number)
	ldr r1, =num2			@ r1 pointer to num2 (loads num2 into r1)
	ldr r1, [r1]			@ sets r1 to value of r1 (dereference)
	bl printf			@ branch with link to printf to print number

	popStack:			
	pop {fp, pc}			@ pops number from stack

