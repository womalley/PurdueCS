myshell: eesh.o lck.o cprt.o lineCount.o prevStr.o
	gcc -g -o myshell eesh.o lck.o cprt.o lineCount.o prevStr.o
esh.o: eesh.c
	gcc -g -c eesh.c
lck.o: lck.c
	gcc -g -c lck.c
cprt.o: cprt.c
	gcc -g -c cprt.c
lineCount.o: lineCount.c
	gcc -g -c lineCount.c
prevStr.o: prevStr.c
	gcc -g -c prevStr.c
clean:
	rm *.o
