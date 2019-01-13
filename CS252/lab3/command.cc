/*
 * CS252: Shell project
 *
 * Template file.
 * You will need to add more code here to execute the command table.
 *
 * NOTE: You are responsible for fixing any bugs this code may have!
 *
 */

#include <cstdio>
#include <cstdlib>

#include <iostream>

#include <string.h>
#include <signal.h>
#include <pwd.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>

#include "command.hh"
#include "shell.hh"

Command::Command() {
    // Initialize a new vector of Simple Commands
    _simpleCommands = std::vector<SimpleCommand *>();

    _outFile = NULL;
    _inFile = NULL;
    _errFile = NULL;
    _background = false;
    _append = false;
}

void Command::insertSimpleCommand( SimpleCommand * simpleCommand ) {
    // add the simple command to the vector
    _simpleCommands.push_back(simpleCommand);
}

void Command::clear() {
    // deallocate all the simple commands in the command vector
    for (auto simpleCommand : _simpleCommands) {
        delete simpleCommand;
    }

    // remove all references to the simple commands we've deallocated
    // (basically just sets the size to 0)
    _simpleCommands.clear();

    int doubleFreeFlag = 0;
    if (_outFile == _errFile) {
	doubleFreeFlag = 1;
    }

    if ( _outFile ) {
        delete _outFile;
    }
    _outFile = NULL;

    if ( _inFile ) {
        delete _inFile;
    }
    _inFile = NULL;

    if ( _errFile ) {
	if (doubleFreeFlag == 1) {
	
	}
	else {
            delete _errFile;
	}
    }
    _errFile = NULL;

    _background = false;
    _ambiguous = 0;
}

void Command::print() {
    printf("\n\n");
    printf("              COMMAND TABLE                \n");
    printf("\n");
    printf("  #   Simple Commands\n");
    printf("  --- ----------------------------------------------------------\n");

    int i = 0;
    // iterate over the simple commands and print them nicely
    for ( auto & simpleCommand : _simpleCommands ) {
        printf("  %-3d ", i++ );
        simpleCommand->print();
    }

    printf( "\n\n" );
    printf( "  Output       Input        Error        Background\n" );
    printf( "  ------------ ------------ ------------ ------------\n" );
    printf( "  %-12s %-12s %-12s %-12s\n",
            _outFile?_outFile->c_str():"default",
            _inFile?_inFile->c_str():"default",
            _errFile?_errFile->c_str():"default",
            _background?"YES":"NO");
    printf( "\n\n" );
}

void Command::execute() {
	// Don't do anything if there are no simple commands
	if ( _simpleCommands.size() == 0 ) {
        	Shell::prompt();
      		return;
    	}

	// check for exit call
	if (!strcmp(_simpleCommands[0]->_arguments[0]->c_str(), "exit")) {
		if (_simpleCommands.size() == 1) {
			printf("\nGood bye!!\n\n");
			exit(1);
		}
	}


	// COMMAND PRINT
	//print();

        // save default in, out, and error for later use
        int defaultIn = dup(0);
        int defaultOut = dup(1);
        int defaultError = dup(2);
        int fdIn = 0;
        int fdOut = 0;
        int fdError = 0;
        int ret = 0;
	int bang = 0;

        // Redirection of the input
        if (_inFile) {
                // open for read only
                fdIn = open(_inFile->c_str(), O_RDONLY);
	}
        else {
                // default input
                fdIn = dup(defaultIn);
        }

	// CASE: no error file present
	if (!_errFile) {
		fdError = dup(defaultError);
	}
	// CASE: error file present
	else {
		if (!_append) {
			fdError = open(_errFile->c_str(), O_WRONLY | O_CREAT | O_TRUNC, 0600);
		}
		else {
			fdError = open(_errFile->c_str(), O_WRONLY | O_CREAT | O_APPEND, 0600);
		}
	}

	dup2(fdError, 2);
	close(fdError);

        // Redirection of the output
       	unsigned int index = 0;
        for (index = 0; index < _simpleCommands.size(); index++) {
		

                // setenv (Make function call if time allows)
                if ( !strcmp(_simpleCommands[index]->_arguments[0]->c_str(), "setenv" )) {
                        int err = setenv(_simpleCommands[index]->_arguments[1]->c_str(), _simpleCommands[index]->_arguments[2]->c_str(), 1);
                        if (err == -1) {
                                perror("setenv");
                        }

                        Shell::_currentCommand.clear();			
                        return;
                }
		// unsetenv (Make function call if time allows)
		if ( !strcmp(_simpleCommands[index]->_arguments[0]->c_str(), "unsetenv" )) {
                        int err = unsetenv(_simpleCommands[index]->_arguments[1]->c_str());
                        if (err == -1) {
                                perror("unsetenv");
                        }

                        Shell::_currentCommand.clear();
                        return;
		}
		// cd calls
		if ( !strcmp(_simpleCommands[index]->_arguments[0]->c_str(), "cd" )) {
			
			if (_simpleCommands[index]->_arguments.size() == 1) {
				chdir(getenv("HOME"));
			}
			else {
				//REMOVE DOESN'T WORK 
				chdir(const_cast<char*>(_simpleCommands[index]->_arguments[1]->c_str()));
			}
			
			Shell::_currentCommand.clear();
			return;
		}


                dup2(fdIn, 0);
                close(fdIn);

                // last command case
                if (index == _simpleCommands.size() - 1) {

                        if (_outFile) {
				
                                if (_append) {
                                        fdOut = open(_outFile->c_str(), O_WRONLY | O_CREAT | O_APPEND, 0600);
                                }
                                else {
                                        fdOut = open(_outFile->c_str(), O_WRONLY | O_CREAT | O_TRUNC, 0600);
                                }
                        }
                        else {
                                fdOut = dup(defaultOut);
                        }
                }
                // pipe in next command / argument
                else {
			int fdpipe[2];
			pipe(fdpipe);
                        fdIn = fdpipe[0];
                        fdOut = fdpipe[1];
		}

                // redirecting output
                dup2(fdOut, 1);
                close(fdOut);

		setenv("$", std::to_string(getpid()).c_str(), 1);
		setenv("!", std::to_string(bang).c_str(), 1);
	       //setenv("_", std::to_string(lastStr).c_str(), 1);

                // executing command
                ret = fork();

		// child process	
		if (ret == 0) {	
		
			// Check printenv
			if (!strcmp(_simpleCommands[index]->_arguments[0]->c_str(), "printenv")) {
				char** env = environ;
				while (*env) {
					printf("%s\n", *env);
					env++;
				}
				exit(0);
			}
	
			int argSize = _simpleCommands[0]->_arguments.size();
			char *ptr[argSize + 1];
//			char * newStr = (char*)malloc(500);
//			int newIndex = 0;
			int flag = 0;

			for (int k = 0; k < argSize; k++) {
				ptr[k] = const_cast<char *> (_simpleCommands[index]->_arguments[k]->c_str());
				char * argStr = const_cast<char *> (_simpleCommands[index]->_arguments[k]->c_str());
				int argStrLen = strlen(argStr);
				char * newStr = (char*)malloc(argStrLen+300);
                                char * temp  = (char*)malloc(argStrLen+300);
				int newIndex = 0;

				//printf("PTR: %s\n", ptr[k]);
				// echo * case
				if (ptr[k] == "echo") {  // && ptr[k + 1] == "*") {
					k++;
	                                printf("PTR: %s\n", ptr[k]);

					if (strchr(ptr[k], '*')) {
						std::cout << "IN ECHO\n" << std::endl;
						printf("IN ECHO\n");
						printf("PTR2: %s\n", ptr[k]);
						FILE * listDirec = popen("/bin/ls -al", "r");
						char buff[1000];
						while ( !feof(listDirec) && fgets(buff, sizeof(buff), listDirec) ) {
							printf("%s ", buff);
							break;
						}
					}
					else {
						k--;
					}
				}


				for (int i = 0; i < argStrLen; i++) {
					int tempIndex = 0;
					// check expansion
					if (argStr[i] == '$' && argStr[i+1] == '{') {
						flag = 1;
						i += 2;
						//std::cout << argStr[i] << std::endl;
						
						// add env string to temp
						while (argStr[i] != '}') {
							temp[tempIndex] = argStr[i];
							tempIndex++;
							i++;
						}
						temp[tempIndex] = '\0';	
						//printf("temp: %s\n", temp);
						
						// get env string
						char * argEnv = getenv(temp);
						int argEnvLen = strlen(argEnv);
						//printf("env: %s\n\n", argEnv);

						// add env to newStr
						int envIndex = 0;
						while (envIndex < argEnvLen) {
							newStr[newIndex] = argEnv[envIndex];
							envIndex++;
							newIndex++;
						}

					}
					// add regular string characters
					else {
						newStr[newIndex] = argStr[i];
						newIndex++;
					}
					newStr[newIndex] = '\0';
					// empty temp string
					strcpy(temp, "");

				}

				//printf("NEW STRING: %s\n", newStr);
				ptr[k] = strdup(newStr);
				strcpy(newStr, "");

/*				// Check for expansion
				if (argStr[0] == '$' && argStr[1] == '{') {
					// loop for argument
					int argIndex = 2;
					int tempIndex = 0;
					int argLen = strlen(argStr);
					
					for (argIndex = 2; argIndex < argLen - 1; argIndex++) {
						std::cout << argStr[argIndex] << std::endl;
						temp[tempIndex] = argStr[argIndex];
						tempIndex++;
					}
					temp[tempIndex] = '\0';

					//getenv for replacement
					//std::cout << temp << std::endl;
					char * argEnv = getenv(temp);
					int envLen = strlen(argEnv);
					printf("env: %s\n", argEnv);
					printf("temp: %s\n", temp); 

					ptr[k] = argEnv;
*/					
					// copy old string and inject getenv string in place of ${...}
/*					char * newStr = (char*)malloc(argStrLen - argLen + envLen + 1);
					
					int i = 0;
					while (argStr[i] != '\0') {
						
						if (argStr[i] == '$' && argStr[i++] == '{') {
							
						}
					}

				}
*/
			}
                       
			ptr[argSize] = NULL;
			execvp(_simpleCommands[index]->_arguments[0]->c_str(), ptr);
                        perror("EXECVP");
                        exit(1);

       	        }
                else if (0 > ret) {
                        perror("FORK");
                        return;
                }
        }

	

	// set var for 

        dup2(defaultIn, 0);
        dup2(defaultOut, 1);
        dup2(defaultError, 2);
        close(defaultIn);
        close(defaultOut);
        close(defaultError);

        if (_background == false) {
	       int stat;
               waitpid(ret, &stat, 0);
	       bang = WEXITSTATUS(stat);
        }


        // Clear to prepare for next command
        clear();

        // Print new prompt
        Shell::prompt();

}



SimpleCommand * Command::_currentSimpleCommand;
