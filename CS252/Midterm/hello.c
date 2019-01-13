#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#define MSG_LEN 128
int main(int argc, char ** argv) {
	char msg[MSG_LEN];

	int * pipeFd = malloc(2 * 4);
	pipe(pipeFd);
	dup2(pipeFd[0], 0);
	dup2(pipeFd[1], 1);

	if (fork() == 0) {
		puts("Hello?"); 
		fflush(stdout);
		fgets(msg, MSG_LEN, stdin);
	} else {
		fgets(msg, MSG_LEN, stdin);
		puts("Can you hear me now?");
		fflush(stdout);
	}
	fprintf(stderr, "%s\n", msg);
}

