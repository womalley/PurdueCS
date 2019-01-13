#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>

#define READABLE_FILE "file_to_read.txt" /* File to be read during read operations */
#define BYTES_TO_READ_WRITE 819200 /* 800 KB */
#define MAX_BUFFER  1048576 /* 1 MB*/

/* Function for write without buffer */
void mywritec(char);

/* Functions for write with buffer */
void mywritebufsetup(int);
void myputc(char);
void mywriteflush(void);

/* Function for read without buffer */
int myreadc(void);

/* Functions for read with buffer */
void myreadbufsetup(int);
int mygetc(void);

/* Declare global variables for write operations here */
int fd_write = 1;
char write_buf[MAX_BUFFER]; //char array for output buffer
char *wp; //pointer to start of write_buf[] (out buffer)
int write_buf_size = 0; //size of output buffer in use at the current time

/* Declare global variables for read operations here */
int fd_read;
char read_buf[MAX_BUFFER]; //char array for input buffer
char *rp; //pointer to start of read_buf[] (in buffer)
int read_buf_size = 0; //size of input buffer
int read_count = 0; //counter for number of bytes read thus far

/* Main function starts */
int main()
{
    clock_t begin, end;
    int option, n, temp;
    const char *a="Writing byte by byte\n";
    const char *b="Writing using buffers\n";
    unsigned long i, bytes_to_write = BYTES_TO_READ_WRITE, bytes_to_read = BYTES_TO_READ_WRITE;
    char ch;

    while(1)
    {
        printf("\n 1 - Write without buffering \n 2 - Write with buffering");
        printf("\n 3 - Read without buffering \n 4 - Read with buffering");
        printf(("\n 5 - Exit \n Enter the option: "));
        scanf("%d", &option);
        switch(option)
        {
            case 1: /* Write without buffer */
                begin = clock();
                for (i=0;i<bytes_to_write;i++)
                {
                    ch = a[i%strlen(a)];
                    mywritec(ch);
                }
                end = clock();
                printf("\n Time to write without buffering: %f secs\n",(double)(end - begin)/CLOCKS_PER_SEC);
                break;

            case 2:  /* Write with buffer */
                printf("\n Enter the buffer size in bytes: ");
                scanf("%d", &n);
                mywritebufsetup(n);
                begin = clock();
                for (i=0;i<bytes_to_write;i++)
                {
                    ch = b[i%strlen(b)];
                    myputc(ch);
                }
                mywriteflush();
                end = clock();
                printf("\n Time to write with buffering: %f secs\n",(double)(end - begin)/CLOCKS_PER_SEC);
                break;

            case 3:  /* Read without buffer */
                fd_read = open(READABLE_FILE, O_RDONLY);
                if(fd_read < 0)
                {
                    printf("\n Error opening the readable file \n");
                    return 1;
                }
                begin = clock();
                for (i=0;i<bytes_to_read;i++)
                {  /* you may need to modify this slightly to print the character received and also check for end of file */
                    if(myreadc() == -1)
                    {
                        printf("\n End of file \n");
                        break;
                    }
                }
                end = clock();
                if(close(fd_read))
                {
                    printf("\n Error while closing the file \n ");
                }
                printf("\n Time to read without buffering: %f secs\n",(double)(end - begin)/CLOCKS_PER_SEC);
                break;

            case 4:  /* Read with buffer */
                printf("\n Enter the buffer size in bytes: ");
                scanf("%d", &n);
                myreadbufsetup(n);
                fd_read = open(READABLE_FILE, O_RDONLY);
                if(fd_read < 0)
                {
                    printf("\n Error opening the readable file \n");
                    return 1;
                }
                begin = clock();
                for (i=0;i<bytes_to_read;i++)
                { /* you may need to modify this slightly to print the character received and also check for end of file */
                    if(mygetc() == -1)
                    {
                        printf("\n End of file \n");
                        break;
                    }
                }
                end = clock();
                if(close(fd_read))
                {
                    printf("\n Error while closing the file \n ");
                }
                printf("\n Time to read with buffering: %f secs\n",(double)(end - begin)/CLOCKS_PER_SEC);
                break;

            default:
                return 0;
        }
    }
}


//write char to standard output
void mywritec(char ch) {
	write (fd_write, &ch, 1);
}


//verify 'n' is a positive int less than/equal to MAX_BUFFER and store in write_buf_size
void mywritebufsetup(int n) {
	if (n > 0 && n <= MAX_BUFFER) {
		//set n to write_buf_size
		write_buf_size = n;
		//initialize wp to point to first byte of buffer
		wp = &write_buf[0];
	}
}


//myputc() called several times before buffer written out
void myputc(char ch) {
	//store 'ch' in location given by wp and increment
	int loc = (wp - &write_buf[0]);
	write_buf[loc] = ch;
	wp++;

	//if buffer full write it to StdOut and reset wp to initial location
	if (write_buf_size == loc) {
		write (fd_write, &write_buf[0], write_buf_size);
		wp = &write_buf[0];
	}
}


//mywriteflush() called after all calls to myputc() have been made
void mywriteflush(void) {
	if ((wp - write_buf[0]) >= 1) {
		//write remaining chars to StdOut
		write (fd_write, &write_buf[0], (wp - write_buf[0]));
	}
	
	//reset pointer location
	wp = &write_buf[0];
}


//reads a char from the file descriptor specified by 'fd_read'
int myreadc(void) {
	
	unsigned char charVal; //unsigned to avoid sign extension
	int readChar = read(fd_read, &charVal, 1);
	
	//error or read is zero
	if (readChar == -1 || readChar == 0) {
		if (readChar == -1) {
			printf("ERROR: Read equals -1 !\n");
		}
		return (-1);
	}
	
	//return char taht was read in the low-order byte
	return (charVal);
}


//verify 'n' is a positive int less than/equal to MAX_BUFFER and store in read_buf_size
void myreadbufsetup(int n) {
        if (n > 0 && n <= MAX_BUFFER) {
                //set n to write_buf_size
                read_buf_size = n;
                //reset read_count to zero
                read_count = 0;
        }
}


//mygetc function
int mygetc() {
	
	unsigned char charVal;
	if (read_count < 1) {
		read_count = read(fd_read, &read_buf[0], read_buf_size);
		rp = &read_buf[0];

		if (read_count == -1 || read_count == 0) {
			if (read_count == -1) {
				printf("ERROR: read_count equal to -1 !\n");
			}
			return (-1);
		}
	}
	
	int loc = (rp - &read_buf[0]);
	charVal = read_buf[loc];
	read_count--;
	rp++;
	
	return (charVal);
}


