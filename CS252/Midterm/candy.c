#include <stdio.h>
#include <unistd.h>
int main(int argc, char **argv) {
	const char * str = "M|Ms";
	if (fork() != 0) {
		str = "Skittlz";
	} else {
	sleep(1);
	printf("%s are the best\n", str);
	}
}

