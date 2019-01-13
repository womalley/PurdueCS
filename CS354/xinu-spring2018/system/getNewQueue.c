#include <xinu.h>

qid16 getNewQueue(void) {
	static qid16	nextqid=NPROC;	/* Next list in queuetab to use	*/
	qid16		q;		/* ID of allocated queue 	*/

	q = nextqid;
	if (q > blkNQENT) {		/* Check for table overflow	*/
		return SYSERR;
	}

	nextqid += 2;			/* Increment index for next call*/

	/* Initialize head and tail nodes to form an empty queue */

	blkqueuetab[blkqueuehead(q)].qnext = blkqueuetail(q);
	blkqueuetab[blkqueuehead(q)].qprev = EMPTY;
	blkqueuetab[blkqueuehead(q)].qkey  = MAXKEY;
	blkqueuetab[blkqueuetail(q)].qnext = EMPTY;
	blkqueuetab[blkqueuetail(q)].qprev = blkqueuehead(q);
	blkqueuetab[blkqueuetail(q)].qkey  = MINKEY;
	return q;	
}
