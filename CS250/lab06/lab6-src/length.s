@ length.s code
@ CS250 Lab06
@ 10/22/2017

.data
	writeLen: .asciz "%d\n"	@ for printing string length
        readStr: .asciz "%s"    @ for reading string in
	str: .word 0		@ initialize string 

.text				@ prevents bus error
.global main			@ makes main global for ldr and so on

main:
	push {r5-r9, fp, lr}	@ push onto stack (discussed during lab)

	ldr r0, =readStr	@ pointer to writeStr for r0 (load readStr into r0)
	ldr r1, =str		@ pointer to str for r1 (load str into r1)
	bl scanf		@ branch with link to scan in string

	ldr r2, =str		@ pointer to string for r2 (load str into r2)
	mov r6, #0		@ write value zero to r6

whileLoop:
	add r2, r2, #1		@ iterate r2 (string read in from user)
	add r6, r6, #1		@ iterate r6 (use as a counter for length) 
	ldr r3, [r2]		@ set r3 to value of r2 (load dereferenced r2 into r3)
	cmp r3, #0		@ compare r3 to zero (loop if not null, zero = null)
	bne whileLoop		@ branch not equal (whileLoop if r3 not equal to zero)

	ldr r0, =writeLen	@ pointer to writeLen for r0 (load writeLen into r0)
	mov r1, r6		@ set rl to r6 (length, moved to r1 since r6 is on stack)
	bl printf		@ print length
	pop {r5-r9, fp, pc}	@ pop off of the stack (discussed during lab)



