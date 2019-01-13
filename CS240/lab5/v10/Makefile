all: libmymathlib.so mymain 

mymain: libmymathlib.so main.o
	gcc -o mymain main.o -L . -l mymathlib -lm 
main.o: main.c dotheader.h
	gcc -c -fPIC main.c
read_vectors.o: read_vectors.c dotheader.h
	gcc -c -fPIC read_vectors.c
calc_dotmag.o: calc_dotmag.c dotheader.h
	gcc -c -fPIC calc_dotmag.c
calc_diff.o: calc_diff.c dotheader.h
	gcc -c -fPIC calc_diff.c
calc_power.o: calc_power.c dotheader.h
	gcc -c -fPIC calc_power.c
write_vectors.o: write_vectors.c dotheader.h
	gcc -c -fPIC write_vectors.c
libmymathlib.so: calc_diff.o calc_dotmag.o read_vectors.o calc_power.o write_vectors.o
	gcc -shared -o libmymathlib.so calc_diff.o calc_dotmag.o read_vectors.o calc_power.o write_vectors.o
clean: 
	rm *.o
