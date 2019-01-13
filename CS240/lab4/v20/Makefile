all: mymain

mymain: main.o libmymath.a
	gcc -o mymain main.o -static -L . -l mymath -lm 
main.o: main.c dotheader.h
	gcc -c main.c
read_vectors.o: read_vectors.c dotheader.h
	gcc -c read_vectors.c
calc_dotmag.o: calc_dotmag.c dotheader.h
	gcc -c calc_dotmag.c
calc_diff.o: calc_diff.c dotheader.h
	gcc -c calc_diff.c
libmymath.a: calc_diff.o calc_dotmag.o read_vectors.o
	ar rcs libmymath.a calc_diff.o
	ar rcs libmymath.a calc_dotmag.o
	ar rcs libmymath.a read_vectors.o
