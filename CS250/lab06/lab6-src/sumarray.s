@ sumarray.s code
@ CS250 Lab06
@ 10/24/2017

.data
	scanIn: .asciz "%d"		@ scan in for user input numbers
	printSum: .asciz "\n%d\n\n"	@ print sum of array (stack)
	number: .word 0			@ number value set to zero
	

.text					@ prevents bus error
.global main

main:
	push {r5-r9, fp, lr}		@ push onto stack
	mov r2, #0			@ set r0 to zero
	mov r6, #0			@ set r6 to zero

whileLoop:
	ldr r0, =scanIn			@ r1 pointer to scanIn (load scanIn into r1)
	ldr r1, =number			@ r2 pointer to number (load number into r2)
	bl scanf			@ branch with link scan number in	

	ldr r2, =number			@ r0 pointer to number (load number into r0)
	ldr r3, [r2] 			@ r3 pointer to dereferenced r2
	
	add r6, r6, #1			@ r6 and 1 added to r6 and used as a loop counter (r6 = r6 + 1)
	add r4, r3, r4			@ r4 and r3 added to r4 (r4 = r3 + r4)
	cmp r6, #5			@ r6 compared to 5
	bne whileLoop			@ loop while r5 does not equal 5

	ldr r0, =printSum		@ r1 pointer to printSum (load printSum into r1)
	mov r1, r4			@ set r2 to r4
	bl printf			@ branch with link to print sum

	pop {r5-r9, fp, pc}		@ pop stack

