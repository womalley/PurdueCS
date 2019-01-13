
/*
 * CS-252
 * shell.y: parser for shell
 *
 * This parser compiles the following grammar:
 *
 *	cmd [arg]* [> filename]
 *
 * you must extend it to understand the complete shell grammar
 *
 */

%code requires 
{
#include <string>

#if __cplusplus > 199711L
#define register      // Deprecated in C++11 so remove the keyword
#endif
}

%union
{
  char        *string_val;
  // Example of using a c++ type in yacc
  std::string *cpp_string;
}

%token <cpp_string> WORD
%token NOTOKEN GREAT NEWLINE GREATGREAT PIPE LESS TWO_GREAT AMPERSAND GREAT_AMPERSAND GREATGREAT_AMPERSAND

%{
//#define yylex yylex
#include <cstdio>
#include "shell.hh"

void yyerror(const char * s);
int yylex();

%}

%%

goal:
  commands
  ;

commands:
  command
  | commands command
  ;

command: simple_command
       ;

simple_command:	
  pipe_list iomodifier_list background_opt NEWLINE {
    /* printf("   Yacc: Execute command\n"); */
    Shell::_currentCommand.execute();
  }
  | NEWLINE 
  | error NEWLINE { yyerrok; }
  ;

pipe_list:
  pipe_list PIPE command_and_args
  | command_and_args
  ;

command_and_args:
  command_word argument_list {
    Shell::_currentCommand.
    insertSimpleCommand( Command::_currentSimpleCommand );
  }
  ;

argument_list:
  argument_list argument
  | /* can be empty */
  ;

argument:
  WORD {
   /* printf("   Yacc: insert argument \"%s\"\n", $1->c_str()); */
    Command::_currentSimpleCommand->insertArgument( $1 );\
  }
  ;

command_word:
  WORD {
   /* printf("   Yacc: insert command \"%s\"\n", $1->c_str()); */
    Command::_currentSimpleCommand = new SimpleCommand();
    Command::_currentSimpleCommand->insertArgument( $1 );
  }
  ;

iomodifier:
  GREAT WORD
  | GREATGREAT WORD
  | LESS WORD
  | TWO_GREAT WORD
  | GREAT_AMPERSAND WORD
  | GREATGREAT_AMPERSAND WORD
  ; 

iomodifier_list:
  iomodifier_list iomodifier
    | iomodifier_opt
    | /* can be empty*/
  ;

iomodifier_opt:
  GREAT WORD {
    /* printf("   Yacc: insert output \"%s\"\n", $2->c_str()); */
    Shell::_currentCommand._outFile = $2;
  }
  | GREATGREAT WORD {
     /* printf("   Yacc: insert argument \"%s\"\n", $2->c_str()); */
      Shell::_currentCommand._append = 1;
      Shell::_currentCommand._outFile = $2;
  }
  | LESS WORD {
     /* printf("   Yacc: insert argument \"%s\"\n", $2->c_str()); */
      Shell::_currentCommand._inFile = $2;
  }
  | TWO_GREAT WORD {
      /* printf("   Yacc: insert argument \"%s\"\n", $2->c_str()); */
      Shell::_currentCommand._outFile = $2;
      Shell::_currentCommand._errFile = $2;
  }
  | GREAT_AMPERSAND WORD {
     /* printf("   Yacc: insert argument \"%s\"\n", $2->c_str()); */
      Shell::_currentCommand._outFile = $2;
      Shell::_currentCommand._errFile = $2;
  }
  | GREATGREAT_AMPERSAND WORD {
     /* printf("   Yacc: insert argument \"%s\"\n", $2->c_str()); */
      Shell::_currentCommand._append = 1;
      Shell::_currentCommand._outFile = $2;
      Shell::_currentCommand._errFile = $2;
  }
  | /*can be empty*/
  ;

background_opt:
  AMPERSAND {
    Shell::_currentCommand._background = true;
  }
  | /*can be empty*/
  ;


%%

void
yyerror(const char * s)
{
  fprintf(stderr,"%s", s);
}

#if 0
main()
{
  yyparse();
}
#endif
