#include <cstdio>
#include <unistd.h>
#include "shell.hh"

#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <pwd.h>
#include <fcntl.h>
#include <signal.h>

int yyparse(void);

void Shell::prompt() {

  if (isatty(0)) {
    printf("myshell> ");
    fflush(stdout);
  }
}

extern "C" void display( int sig ) {
  printf("\n");
  Shell::prompt();
}

extern "C" void zombieHandler( int sig ) {
  while(waitpid(-1, NULL, WNOHANG) > 0);
}

int main(int argc, char **cpyArg) {

  char * buff = (char*)malloc(100);
  setenv("SHELL", realpath(cpyArg[0], buff), 1);

  // Ctrl-C handler using display extern
  struct sigaction signalAction;
  signalAction.sa_handler = display;
  sigemptyset(&signalAction.sa_mask);
  signalAction.sa_flags = SA_RESTART;
  int error = sigaction(SIGINT, &signalAction, NULL );
  if ( error ) {
    perror( "sigaction" );
    exit( -1 );
  }  

  // Zombie processes handler using zombieHandler
  struct sigaction zombieAction;
  zombieAction.sa_handler = zombieHandler;
  sigemptyset(&zombieAction.sa_mask);
  zombieAction.sa_flags = SA_RESTART;
  error = sigaction(SIGCHLD, &zombieAction, NULL );
 
  Shell::prompt(); 
  yyparse();
}

Command Shell::_currentCommand;
