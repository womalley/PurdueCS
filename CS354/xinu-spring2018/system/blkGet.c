#include <xinu.h>

pid32 blkGetFirst(qid16 q) {
	if(blkisempty(q)) {
		return EMPTY;
	}

	pid32 head = blkqueuehead(q);
	pid32 temp = blkqueuetab[head].qnext;

	pid32 next = blkqueuetab[temp].qnext;
	pid32 prev = blkqueuetab[temp].qprev;
	blkqueuetab[prev].qnext = next;
	blkqueuetab[next].qprev = prev;

	return temp;
}

pid32 blkGetLast(qid16 q) {
	if(blkisempty(q)) {
		return EMPTY;
	}
	
	pid32 tail = blkqueuetail(q);
	pid32 temp = blkqueuetab[tail].qprev;

	pid32 next = blkqueuetab[temp].qnext;
	pid32 prev = blkqueuetab[temp].qprev;
	blkqueuetab[prev].qnext = next;
	blkqueuetab[next].qprev = prev;
	return temp;
}
