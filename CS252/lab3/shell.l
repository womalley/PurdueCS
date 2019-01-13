/*
 *
 * CS-252 Fall 2017
 * shell.l: lexical analyzer for shell
 * You have to extend it.
 *
 */

%{

#include <cstring>
#include "y.tab.hh"
#include <sys/wait.h>
#include "command.hh"
#include <unistd.h>
#include <string.h>

extern "C" char * read_line();


int mygetc(FILE * f) {
 static char *p;
 char ch;

 if (!isatty(0)) {
 // stdin is not a tty. Call real getc
   return getc(f);
 }

 // stdin is a tty. Call our read_line.
 if (p==NULL || *p == 0) {
   char * s = read_line();
   p = s;
 }

 ch = *p;
 p++;

 return ch;
}

#undef getc
#define getc(f) mygetc(f)



static  void yyunput (int c,char *buf_ptr  );

void myunputc(int c) {
  unput(c);
}

%}

%%

\n {
  return NEWLINE;
}

[ \t] {
  /* Discard spaces and tabs */
}

">" {
  return GREAT;
}
">>" {
  return GREATGREAT;
}
"|" {
  return PIPE;
}
"<" {
  return LESS;
}

"2>" {
  return TWO_GREAT;
}

"&" {
  return AMPERSAND;
}

">&" {
  return GREAT_AMPERSAND;
}

">>&" {
  return GREATGREAT_AMPERSAND;
}

\$\([^\t\n]*\) {
  /* subshell */


  char * temp = strdup(yytext + 2);
  int tempSize = strlen(temp);
  char * sub = (char*)malloc(100);

  int j = 0;
  for (j = 0; j < tempSize - 1; j++) {
    sub[j] = temp[j];
  }

  sub[j] = '\0';


  // initialize pipes
  int in = dup(0);
  int out = dup(1);
  int parent[2];
  int child[2];

  // setup the pipes for parent a child
  pipe(parent);
  pipe(child);

  // check parent child pipes
  if (pipe(parent) < 0 || pipe(child) < 0) {
    perror("PIPE");
    exit(1);
  }

  int subLen = strlen(sub);
  write(parent[1], sub, subLen);
  write(parent[1], "\n", 1);

  close(parent[1]);

  // dup and close pipes
  dup2(parent[0], 0);
  dup2(child[1], 1);
  close(parent[0]);
  close(child[1]);

  // setup/run subshell
  int forked = fork();
  if (0 > forked) {
    perror("FORKED");
    exit(1);
  }
  else if (forked == 0) {
    char* tempArgv[2];
    tempArgv[0] = "/proc/self/exe";
    tempArgv[1] = NULL;
    execvp("/proc/self/exe", tempArgv);
    _exit(1);
  }

  // dup and close
  dup2(in, 0);
  dup2(out, 1);
  close(in);
  close(out);


  char character;
  char* buffArr = (char*)malloc(1000);
  int i = 0;

  while (read(child[0], &character, 1)) {
  
    if (character != '\n') {
      buffArr[i] = character;
    }
    else {
      buffArr[i] = ' ';
    }
    i++;
  }


  buffArr[i]='\0';

  int k = strlen(buffArr) - 1;
  while (k >= 0) {
    unput(buffArr[k]);
    k--;
  }
}


[^ \t\n<>$()/|&]*\\[^ \t\n]* {

  int i = 0;
  int k = 0;
  int size = strlen(yytext);
  //char temp1[size];
  char * temp1 = (char *)malloc(100);
  int escapeCount = 0;

  /* take out escapes */
  for (i = 0; i < size; i++) {
  
    if (yytext[i] == '\\') {
      /* check for repeating '\' */
      if (yytext[i+2] == '\\') {
        //check next pair of '\' 
      }
      else {
        temp1[k] = yytext[i + 1];
        i++;
        k++;
      }
    }
    else if (yytext[i] != '\\') {
        temp1[k] = yytext[i];
        k++;
        escapeCount++;
    }
  }

  if(0 < k) {
    yytext[k] = '\0';
  }


  yylval.cpp_string = new std::string(temp1);
  return WORD;

}


\"[^\n\"]*\" {
  /* quote support */
  int size = strlen(yytext)-2;
  int j = 0;
  char temp[size];
  for (int i = 0; i < size; i++) {
    j = i + 1;
    temp[i] = yytext[j];
  }

  temp[size] = '\0';
  yylval.cpp_string = new std::string(temp);
  return WORD;

}


[^ \t\n<>|&][^ \t\n<>|&]*  {
  /* Assume that file names have only alpha chars */
   //<>|
   yylval.cpp_string = new std::string(yytext);
  return WORD;
}






