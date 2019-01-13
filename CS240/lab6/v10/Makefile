CC=gcc
OBJ=calc_diff.o calc_dotmag.o read_vectors.o calc_power.o write_vectors.o
OFLAG=-o
CFLAG=-c
PICFLAG=-fPIC
GCC=-g

all: libmymathlib.so mymain 

mymain: libmymathlib.so main.o
	$(CC) $(OFLAG) mymain main.o -L . -l mymathlib -lm $(GCC)
main.o: main.c dotheader.h
	$(CC) $(CFLAG) $(PICFLAG) $(GCC) main.c
read_vectors.o: read_vectors.c dotheader.h
	$(CC) $(CFLAG) $(PICFLAG) $(GCC) read_vectors.c
calc_dotmag.o: calc_dotmag.c dotheader.h
	$(CC) $(CFLAG) $(PICFLAG) $(GCC) calc_dotmag.c
calc_diff.o: calc_diff.c dotheader.h
	$(CC) $(CFLAG) $(PICFLAG) $(GCC) calc_diff.c
calc_power.o: calc_power.c dotheader.h
	$(CC) $(CFLAG) $(PICFLAG) $(GCC) calc_power.c
write_vectors.o: write_vectors.c dotheader.h
	$(CC) $(CFLAG) $(PICFLAG) $(GCC) write_vectors.c
libmymathlib.so: $(OBJ) 
	$(CC) -shared $(OFLAG) libmymathlib.so $(OBJ)
clean: 
	rm *.o
