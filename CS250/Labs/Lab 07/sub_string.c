#include <stdio.h>
#include <stdlib.h>

char *sub_string(char *in, int start, int end)
{
        //Initializations
        char *out = (char*)malloc(25);
        int counter = 0;
        int i;

        //Loop for substring
        for(i = start - 1; i < end; i++)
        {
                out[counter] = in[i];
                counter++;
        }

        return out;
}
