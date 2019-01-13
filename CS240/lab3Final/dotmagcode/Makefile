dotmag.exe: main.o read_vectors.o calc_dotmag.o
	gcc -o dotmag.exe main.o read_vectors.o calc_dotmag.o -lm
main.o: main.c dotheader.h
	gcc -c main.c
read_vectors.o: read_vectors.c dotheader.h
	gcc -c read_vectors.c
calc_dotmag.o: calc_dotmag.c dotheader.h
	gcc -c calc_dotmag.c
