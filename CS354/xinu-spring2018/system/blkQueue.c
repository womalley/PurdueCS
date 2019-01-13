#include <xinu.h>

struct qentry blkqueuetab[blkNQENT];

pid32 blkEnqueue(pid32 pid, qid16 q) {
	if(isbadpid(pid) || blkisbadqid(q)) {
		kprintf("error\n");
		return SYSERR;
	}

	int tail = blkqueuetail(q);
	int previous = blkqueuetab[tail].qprev;

	blkqueuetab[pid].qnext = tail;
	blkqueuetab[pid].qprev = previous;
	blkqueuetab[previous].qnext = pid;
	blkqueuetab[tail].qprev = pid;
	return pid;
}

pid32 blkDequeue(qid16 q) {
	if(blkisbadqid(q)) {
		kprintf("error\n");
		return SYSERR;
	} else if(blkisempty(q)) {
		kprintf("error\n");
		return EMPTY;
	}

	pid32 pid = blkGetFirst(q);
	blkqueuetab[pid].qprev = EMPTY;
	blkqueuetab[pid].qnext = EMPTY;
	return pid;
}
