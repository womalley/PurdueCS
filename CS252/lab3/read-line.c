/*
 * CS252: Systems Programming
 * Purdue University
 * Example that shows how to read one line with simple editing
 * using raw terminal.
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <assert.h>
#include <termios.h>

#define MAX_BUFFER_LINE 2048

extern void tty_raw_mode(void);

// Buffer where line is stored
int line_length;
char line_buffer[MAX_BUFFER_LINE];

// Simple history array
// This history does not change. 
// Yours have to be updated.
int history_index = 0;
char ** history;
/*char * history [] = {
  "ls -al | grep x", 
  "ps -e",
  "cat read-line-example.c",
  "vi hello.c",
  "make",
  "ls -al | grep xxx | grep yyy"
};
*/
int history_length = 0;  //sizeof(history)/sizeof(char *);
int line_pos = 0;

// size of purdue's
int historySize = 50;


void read_line_print_usage()
{
  char * usage = "\n"
    " ctrl-?       Print usage\n"
    " Backspace    Deletes last character\n"
    " up arrow     See last command in the history\n"
    " down arrow   see the next command in the history\n"
    " left arrow   moves the cursor to the left for inserting text\n"
    " right arrow  moves the cursor to the right for inserting text\n"
    " delete       (ctrl-D) - removes the character that the cursor is over\n"
    " home         (ctrl-A) - moves the cursor to the start of the line\n"
    " end          (ctrl-E) - moves the cursor to the end of the line\n";

  write(1, usage, strlen(usage));
}

/* 
 * Input a line with some basic editing.
 */
char * read_line() {

  // Set terminal in raw mode
  struct termios orig_attr;
  tcgetattr(0, &orig_attr);
  tty_raw_mode();

  line_length = 0;
  line_pos = 0;

  // Read one line until enter is typed
  while (1) {

    // Read one character in raw mode.
    char ch;
    read(0, &ch, 1);

    if (ch>=32 && ch != 127) {
      // It is a printable character. 

      // Do echo
      write(1,&ch,1);

      // If max number of character reached return.
      if (line_length==MAX_BUFFER_LINE-2) break; 

      // add char to buffer.
      line_buffer[line_length]=ch;
      line_length++;
      line_pos++;
    }
    else if (ch==10) {
      // <Enter> was typed. Return line
     
      if (history_length == 0) {
	history = (char**)malloc(historySize * sizeof(char));
      }

      if (line_buffer[0] > 0) {
        history[history_length] = strdup(line_buffer);
	history_length++;
      }
 
      // Print newline
      write(1,&ch,1);

      break;
    }
    else if (ch == 31) {
      // ctrl-?
      read_line_print_usage();
      line_buffer[0]=0;
      break;
    }
    else if ((ch == 8 && line_length != 0) || (ch == 127 && line_length != 0)) {
      // <backspace> was typed. Remove previous character read.

      // Go back one character
      ch = 8;
      write(1,&ch,1);

      // Write a space to erase the last character read
      ch = ' ';
      write(1,&ch,1);

      // Go back one character
      ch = 8;
      write(1,&ch,1);

      // Remove one character from buffer
      line_length--;
      line_pos--;
    }
    // ctrl-A move to beginning, reseting line pos on the way
    else if (ch == 1) {
      for (int i = line_pos; i >= 1; i--) {
	line_pos--;
	ch = 8;
        write(1, &ch, 1);
        
      }
    }
    // ctrl-E move to end
    else if (ch == 5) {
      int i = line_pos;
      char * newChar = line_buffer[i];
      for (i = line_pos; i <= line_length - 2; i++) {
        line_pos++;
	newChar = line_buffer[i];
        write(1, &ch, 1);
        
      }
    }
    // ctrl-D
    else if (line_length != 0 && ch == 4) {


    }
    else if (ch==27) {
      // Escape sequence. Read two chars more
      //
      // HINT: Use the program "keyboard-example" to
      // see the ascii code for the different chars typed.
      //
      char ch1; 
      char ch2;
      read(0, &ch1, 1);
      read(0, &ch2, 1);
      
      // RIGHT arrow
      if (ch1 == 91 && ch2 == 67) {
	// already at end
        if (line_pos == line_length) {
	  continue;
	}
	// room to go right
	else if (line_length > line_pos) {
	  char charSpot = line_buffer[line_pos];
          write(1, &ch, 1);
	  line_pos++;
	}
      }
      // LEFT arrow
      else if (ch1 == 91 && ch2 == 68) {
	if (line_pos > 0) {
          line_pos--;
	  ch = 8;
          write(1, &ch, 1);
          
        }
        else {
          // already at beginning
          continue;
        }
      }


      if (ch1==91 && ch2==65) {
	// Up arrow. Print next line in history.

	// Erase old line
	// Print backspaces
	int i = 0;
	for (i =0; i < line_length; i++) {
	  ch = 8;
	  write(1,&ch,1);
	}

	// Print spaces on top
	for (i =0; i < line_length; i++) {
	  ch = ' ';
	  write(1,&ch,1);
	}

	// Print backspaces
	for (i =0; i < line_length; i++) {
	  ch = 8;
	  write(1,&ch,1);
	}	

	// Copy line from history
	strcpy(line_buffer, history[history_index]);
	line_length = strlen(line_buffer);
	history_index=(history_index+1)%history_length;

	// echo line
	write(1, line_buffer, line_length);
      }
      
    }

  }

  // Add eol and null char at the end of string
  line_buffer[line_length]=10;
  line_length++;
  line_buffer[line_length]=0;

  // reset termios
  tcsetattr(0, TCSANOW, &orig_attr);

  return line_buffer;
}

