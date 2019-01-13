
// flow.c
#include<stdio.h>
#include<unistd.h>
#define BUF_SIZE 128
int main(int argc, char ** argv) {
   char buf[BUF_SIZE];
   int * pipeFd = malloc(2 * 4);
   pipe(pipeFd);
   if (!fork()) {
       dup2(pipeFd[1], 1);
       for (int i = 0; i < 100; i++) {
           printf("Something!\n");
       }
	close(pipeFd[1]);
   } else {
       dup2(pipeFd[0], 0);
       while(fgets(buf, BUF_SIZE-1, stdin) != NULL) {
           printf("Pipeline: %s", buf);
       }
	close(pipeFd[0]);
       printf("Pipeline: Empty!\n");
   }
}
