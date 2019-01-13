@main.s

.data

	stringIn:	.asciz "Enter a string: "
	stringStart:	.asciz "Enter the start index: "
	stringEnd:	.asciz "Enter the end index: "
	result:		.asciz "The substring of the given string is '%s'\n"

	scanS:		.asciz "%s"
	scanD:		.asciz "%d"

	_str: 	.space 100
.text
	pntIn:		.word stringIn
	pntStart:	.word stringStart
	pntEnd:		.word stringEnd
	pntResult:	.word result
	pntScanS:	.word scanS
	pntScanD:	.word scanD

.global main
main:
	push {lr}	@push link register
	sub sp, sp, #8
	
	/*print first user input prompt*/
	ldr r0, pntIn
	bl printf

	/*scan user input string*/
	ldr r0, =scanS
	ldr r1, =_str
	bl scanf

	/*print start index user input prompt*/
	ldr r0, pntStart
	bl printf

	/*scan user input for start*/
	ldr r0, pntScanD
	mov r1, sp
	bl scanf
	ldr r4, [sp]	@dereference

	/*print end index user input prompt*/
	ldr r0, pntEnd
	bl printf

	/*scan user input for end*/
	ldr r0, pntScanD
	mov r1, sp
	bl scanf
	ldr r6, [sp]

	/*fetch sub string*/
	ldr r0, pntIn
	mov r1, r4
	mov r2, r6
	bl stringIn
	mov r7, r0

	/*final print statement*/
	ldr r0, pntResult
	mov r1, r7
	bl printf
	add sp, sp, #8
	pop {pc}

	
