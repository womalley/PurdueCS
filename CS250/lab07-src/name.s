@ name.s

.data

	scanFirst:	.asciz "Enter your first name: "
	scanLast:	.asciz "Enter your last name: "
	write:		.asciz "Hello, "
	readFirst:	.asciz "%s"
	readLast:	.asciz "%s"
	
	pntFirst:	.word scanFirst
	pntLast:	.word scanLast
	pntRFirst:	.word readFirst	
	pntRLast:	.word readLast

	str: .space 100

.text
.global main
main:
	
@	push {r4-r9, fp, lr}
	
	/*first name prompt*/
@	ldr r0, scanFirst
@	bl printf

	/*first name input*/
@	ldr r0, readFirst
@	ldr r1,	str 
@	bl scanf
	
	/*last name prompt*/
@	ldr r0, scanLast
@	bl printf

	/*last name input*/
@	ldr r0, readLast
@	ldr r1, str
@	bl scanf

	/*write final*/
@	write
@	bl printf

@	pop {r4-r9, fp, pc}
	

	
