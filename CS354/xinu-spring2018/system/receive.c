/* receive.c - receive */

#include <xinu.h>

/*------------------------------------------------------------------------
 *  receive  -  Wait for a message and return the message to the caller
 *------------------------------------------------------------------------
 */
umsg32	receive(void)
{
	intmask	mask;			/* Saved interrupt mask		*/
	struct	procent *prptr;		/* Ptr to process' table entry	*/
	umsg32	msg;			/* Message to return		*/

	mask = disable();
	prptr = &proctab[currpid];
	if (prptr->prhasmsg == FALSE) {
		//kprintf("NONE %d\n\n", currpid);
		prptr->prstate = PR_RECV;
		resched();		/* Block until message arrives	*/
	}
	//kprintf("RECEIVED");
	msg = prptr->prmsg;		/* Retrieve message		*/
	prptr->prhasmsg = FALSE;	/* Reset message flag		*/
	
	//if(blknonempty(prptr->sendqueue)) {
	if(prptr->rcpblkflag != 0) {
		pid32 remove = blkDequeue(prptr->sendqueue);
		//kprintf("DEQUEUED %d\n\n", currpid);
		msg = prptr->prmsg;
		prptr->rcpblkflag--;
		ready(remove);
	}

	restore(mask);
	return msg;
}
