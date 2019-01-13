//sub_string.c

char* sub_string(char *in_string, int start_index, int end_index) {

	char *out_string; //= (char*)malloc(50);
	int count = 0;
	int index = 0;

	for (index = start_index; index <= end_index; index++) {
		out_string[count] = in_string[index];
		count++;
	}

	/* code to extract the sub-string */
	return out_string;

}
