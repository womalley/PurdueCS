/* recvclr.c - recvclr */

#include <xinu.h>

/*------------------------------------------------------------------------
 *  recvclr  -  Clear incoming message, and return message if one waiting
 *------------------------------------------------------------------------
 */
umsg32	recvclr(void)
{
	intmask	mask;			/* Saved interrupt mask		*/
	struct	procent *prptr;		/* Ptr to process' table entry	*/
	umsg32	msg;			/* Message to return		*/

	mask = disable();
	prptr = &proctab[currpid];
	if (prptr->prhasmsg == TRUE) {
		msg = prptr->prmsg;	/* Retrieve message		*/
		prptr->prhasmsg = FALSE;/* Reset message flag		*/
	} else {
		msg = OK;
	}
	
	/* Addtion for lab5, remove from queue once the message has been sent */
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
