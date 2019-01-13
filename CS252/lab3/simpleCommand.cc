#include <cstdio>
#include <cstdlib>
#include <string.h>

#include <iostream>
#include <sys/types.h>
#include <pwd.h>
#include "simpleCommand.hh"

SimpleCommand::SimpleCommand() {
  _arguments = std::vector<std::string *>();
}

SimpleCommand::~SimpleCommand() {
  // iterate over all the arguments and delete them
  for (auto & arg : _arguments) {
    delete arg;
  }
}


void SimpleCommand::insertArgument( std::string * argument ) {
  // simply add the argument to the vector
  _arguments.push_back(argument);

/*
  const char* arg = argument->c_str();
  if (arg[0] == '~') {
    printf("TILDE\n");
    
    //char *strTilde = strdup(arg);
    const char *ret;
    ret = tilde(arg);
    printf(ret);
    argument = ret;
    //std::cout << argument << std::endl; 
  }
*/
/*
  _arguments[_arguments.size()] = argument;
  _arguments[_arguments.size() + 1] = NULL;
  int x = (int)_arguments.size();
  //printf("ARGS: %d", x);
  _arguments.resize(_arguments.size() + 1); 
  x = (int)_arguments.size();
  //printf("RESIZE: %d\n", x);
*/
}

// Print out the simple command
void SimpleCommand::print() {
  for (auto & arg : _arguments) {
    std::cout << "\"" << arg << "\" \t";
  }
  // effectively the same as printf("\n\n");
  std::cout << std::endl;
}

char * SimpleCommand::tilde(char * argument) {


/*
  //printf("IN TILDE\n");
  int argLen = strlen(argument);  
  // tilde expansion
  if (argument[0] == '~') {
   //printf("EXPANSION\n"); 
    // home
    if (argLen == 1) {
      //printf("ARG LEN\n");
      argument = strdup(getenv("HOME"));
      //printf(argument);
      return argument;
    }
    else {
    
    }
  }
*/
}


char * SimpleCommand::expansion(char * argument) {


}
