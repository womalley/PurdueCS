.data
	final:		.asciz "The substring of the given string is '%s'\n"
	prompt1:        .asciz "Enter a string: "
	prompt2:        .asciz "Enter the start index: "
	prompt3:	.asciz "Enter the end index: "
	scantype:       .asciz "%d"
	scanString:	.asciz "%s"
	.lcomm string, 128

.text
	addr_prompt1:   .word prompt1
	addr_prompt2:   .word prompt2
	addr_prompt3:	.word prompt3
	addr_scantype:  .word scantype
	addr_scanStr:	.word scanString
	addr_string:	.word string
	addr_final:	.word final

.global main
main:
		push {LR}
		sub sp, sp, #8
	/*Print string prompt*/
		ldr r0, addr_prompt1
		bl printf

	/*Scan string*/
		ldr r0, addr_scanStr
		ldr r1, addr_string
		bl scanf

	/*Print start prompt*/
		ldr r0, addr_prompt2    
		bl printf               

	/*Scan start index*/
		ldr r0, addr_scantype   
		mov r1, sp
		bl scanf
		ldr r4, [sp]
		
	/*Print end prompt*/
		ldr r0, addr_prompt3
		bl printf

	/*Scan end index*/
		ldr r0, addr_scantype
		mov r1, sp
		bl scanf
		ldr r5, [sp]

	/*Call C sub_string*/
		ldr r0, addr_string
		mov r1, r4
		mov r2, r5
		bl sub_string
		mov r6, r0

	/*Final print result*/
		ldr r0, addr_final
		mov r1, r6
		bl printf

		add sp, sp, #8
		pop {PC}
