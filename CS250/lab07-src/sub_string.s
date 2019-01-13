@sub_string.s

.global sub_string

sub_string:

	push {r4-r9, fp, lr}	@ pushes commands to stack

	mov r6, r0		@ stores first printf string
	mov r7, r1		@ stores start index string
	mov r8, r2		@ stores end index string
	
	sub r8, r8, r7		@ obtains substring size
	add r8,	r8, #1		@ add one for correct substring size

	mov r0, #50		@ make size set to 50
	bl malloc		@ mallocs to above size (50)
	
	sub r7, r7, #1		@ index properly
	add r2, r6, r7		@ 
	mov r3, r8		@ 

	pop {r4-r9, fp, pc}
