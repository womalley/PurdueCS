.global sub_string
sub_string:
	/*Push arguements onto stack*/
		stmfd sp!, {fp, lr}
	/*Store arguements in usable registers*/
		mov r5, r0 /*String*/
		mov r6, r1 /*Start index*/
		mov r7, r2 /*End index*/
	/*Get substring size to copy*/
		sub r7, r7, r6
		add r7, r7, #1
	/*Malloc set size of 30*/
		mov r0, #30
		bl malloc
	/*Get arguements stored for strncpy*/
		sub r6, r6, #1 /*Start substring -1 for indexing at 0*/
		add r1, r5, r6
		mov r2, r7
	/*Call strncpy*/
		bl strncpy

		ldmfd sp!, {fp, pc}
