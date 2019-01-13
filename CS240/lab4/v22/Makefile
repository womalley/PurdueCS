mymain: main.o domainError.o httpError.o isURL.o concatSysCall.o ipToInt.o
	gcc -o mymain main.o domainError.o httpError.o isURL.o concatSysCall.o ipToInt.o -lm
main.o: main.c myHeader.h
	gcc -c main.c
domainError.o: domainError.c myHeader.h
	gcc -c domainError.c
httpError.o: httpError.c myHeader.h
	gcc -c httpError.c
isURL.o: isURL.c myHeader.h
	gcc -c isURL.c
concatSysCall.o: concatSysCall.c myHeader.h
	gcc -c concatSysCall.c
ipToInt.o: ipToInt.c myHeader.h
	gcc -c ipToInt.c
