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

char write_buf[MAX_BUFFER]; //Character buffer for output
char* wp; //Pointer to beginning of write_buf array
int write_buf_size; //Max size of buffer to print

/* Declare global variables for read operations here */
int fd_read;

char* rp; //Pointer that points to a location in the buffer
char read_buf[MAX_BUFFER]; //Buffer array
int read_buf_size; //The size of the input buffer
int read_count; //Tells how many bytes were read

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
		    if(write_buf_size < 1)
		    {
			printf("Invalid buffer size!\n");
			break;
		    }
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

			int back1 = myreadc();
			if(back1 == -1)
			{
				printf("\nEnd of file\n");
				break;
			}
			else
			{
				char a =(char) back1;
				printf("%c", a);
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
		
			if(read_buf_size < 1)
			{
				printf("Invalid buffer size!\n");
			}
			int back2 = mygetc();
			if(back2 == -1)
			{
				printf("\nEnd of file\n");
				break;
			}
			else
			{
				char b = (char) back2;
				printf("%c", b);
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

void mywritec(char ch)
{
	write(fd_write, &ch, 1);
}

void mywritebufsetup(int n)
{
	if(n <= MAX_BUFFER && n > 0)
	{
		write_buf_size = n;
		wp = &write_buf[0];
	}
}

void myputc(char ch)
{
	write_buf[wp - &write_buf[0]] = ch;
	wp++;
	if(wp - &write_buf[0] == write_buf_size)
	{
		write(fd_write, &write_buf[0], write_buf_size);
		wp = &write_buf[0];
	}
}

void mywriteflush(void)
{
	int inBuffer = wp - &write_buf[0];
	if(inBuffer > 0)
	{
		write(fd_write, &write_buf[0], inBuffer);
	}
	wp = &write_buf[0];
}

int myreadc(void)
{
	unsigned char toReturn;
	int check = read(fd_read, &toReturn, 1);
	
	if(check == 0)
	{
		return -1;
	}
	else if(check == -1)
	{
		printf("Error occured!\n");
		return -1;
	}
	return toReturn;
}

void myreadbufsetup(int n)
{
	if(n <= MAX_BUFFER && n > 0)
	{
		read_buf_size = n;
		read_count = 0;
	}
}

int mygetc()
{
	if(read_count <= 0)
	{
		read_count = read(fd_read, &read_buf[0], read_buf_size);
		rp = &read_buf[0];
		if(read_count == 0)
		{
			return -1;
		}
		if(read_count == -1)
		{
			printf("Error occured!\n");
			return -1;
		}
	}
	unsigned char toReturn = read_buf[rp - &read_buf[0]];
	rp++;
	read_count--;
	return toReturn;
}
