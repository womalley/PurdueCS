@ printinteger.s
@ CS 250
@ Lab08
@ Last modified: 11/13/2017

.text
.data

.global printx
.global printd


printx:
	push {r4-r9, fp, lr}
	cmp r0, #0 		@ Corner case (passing zero)
	beq zeroCase		@ Corner case branching (branch if equal to print zero)
	mov r5, r0 		@ Place r0 value into r4
	mov r6, #15 		@ Set r5 value to 15 (masking)
	mov r7, #0 		@ Counter
	@ Branch to looper


looper:
	cmp r7, #8 		@ Compare r6 value to value 8
	beq checker 		@ Branck to check if compare is true
	and r0, r5, r6 		@ obtain values from mask
	cmp r0, #10 		@ Compares r0 value with value 9
	blt num			@ Branch if above less than or equal (number)
	bge character		@ Else branch greater than if cmp greater than (letter)


zeroCase:
        add r0, r0, #48         @ Converts number to ascii table value
        bl putchar              @ Branch with link to putchar
        b terminate             @ Branch to terminate program


num:
	add r0, r0, #48 	@ Converts number to ascii table value
	push {r0} 		@ Adjust pointer
	mov r5, r5, ASR #4 	@ Moves right each time
	add r7, r7, #1 		@ Counter adds one each time
	b looper		@ Branch to looper


character:
	add r0, r0, #87 	@ Converts number to ascii table value
	push {r0}		@ Adjust pointer
	mov r5, r5, ASR #4	@ Moves right each time
	add r7, r7, #1		@ Counter adds one each time
	b looper		@ Branch to looper


checker:
	cmp r7, #0 		@ Check if stack printed
	bne printing		@ Branch not equal to printing 
	beq terminate		@ Branch equal to terminate


printing:
	pop {r0}		@ Adjust pointer
	bl putchar		@ Branch with link to putchar
	sub r7, r7, #1		@ Counter adds one each time
	b checker		@ Branch to checker


terminate:
	pop {r4-r9, fp, pc}



printd:
	push {r4-r9, fp, lr}
	mov r4, r0 		@ Place r0 value into r4
	cmp r4, #0 		@ Compare r4 value to value zero
	mov r6, #0 		@ Counter
	beq branchEqual		@ Branch if equal to branchEqual
	blt lessD		@ Branch if less than
	bgt greaterD	 	@ Branch if greater than


branchEqual:
	add r0, r0, #48 	@ Converts number to ascii table value (zero)
	bl putchar		@ Branch with link to putchar
	b terminate		@ Branch to terminate


lessD:
	mov r0, #45 		@ Converts number to ascii table value
	bl putchar		@ Branch with link to putchar
	mov r9, #0		@ Place zero as r9 value
	sub r4, r9, r4		@ Subtract r4 value from r9 value and store in r4
	b greaterD		@ Branch if greater than


greaterD:
	cmp r4, #0 		@ Compares r4 value to zero (checks if number divisible by 10)
	beq checkerD		@ Branch if equal to checker 
	ldr r1, =0xCCCCCCDF     @ Don't know why this works and =0xFFFFFFFF doesn't
	umull r5, r2, r1, r4 	@ Unsigned multiplication
	mov r7, r2, LSR #3 	@ Shift by 3-bits and store in r7
	mov r8, #10		@ Place value 10 in r8 (used to multiply the result of r5 in umull)
	mul r5, r7, r8		@ Multiply by r8 (10)
	sub r8, r4, r5 		@ Set r8 to mul answer minus r4 (compared value)
	push {r8} 		@ Push r8 onto the stack
	mov r4, r7 		@ Place r7 value into r4 (shifted value)
	add r6, r6, #1 		@ Counter add
	b greaterD		@ Branch if greater than


checkerD:
	cmp r6, #0		@ Compare r6 value to value zero
	bne intPrint		@ Branch not equal to intPrint
	beq terminate		@ Branch equal to to terminate


intPrint:
	pop {r0}		@ Adjust pointer
	add r0, r0, #48		@ Convert to ascii table value
	bl putchar		@ Branch with link to putchar
	sub r6, r6, #1		@ Counter subtraction
	b checkerD		@ Branch to checkerD


