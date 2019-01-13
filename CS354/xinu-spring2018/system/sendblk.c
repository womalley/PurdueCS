#include <xinu.h>

/*------------------------------------------------------------------------
 *  sendblk  -  Pass a message to a process and start recipient if waiting
 *------------------------------------------------------------------------
 */
syscall sendblk(
          pid32         pid,            /* ID of recipient process      */
          umsg32        msg             /* Contents of message          */
        )
{
        intmask mask;                   /* Saved interrupt mask         */
        struct  procent *prptr;         /* Ptr to the receiver's table entry  */
	struct 	procent *prptrCurr;	/* Ptr to the sender's table entry */
	
	//PID Error checking
        mask = disable();
        if (isbadpid(pid)) {
                restore(mask);
                return SYSERR;
        }

	//Get sender and receiver
        prptr = &proctab[pid]; //Receiver
	prptrCurr = &proctab[currpid]; //Sender
	
        if ((prptr->prstate == PR_FREE) /*|| prptr->prhasmsg*/) {
                restore(mask);
                return SYSERR;
        }

	/* Added code for lab5 */
	if (prptr->prhasmsg) {  //1 word buffer is full
		//store message in sender, flag nonZero if valid, and store pid of receiver
		//kprintf("IN SENDBLK, CURRPID: %d, MESSAGE: %d\n");
		prptrCurr->sendblkmsg = msg;
		prptrCurr->sendblkflag = 1;
		prptrCurr->sendblkrcp = pid;

		//Set block state
		prptrCurr->prstate = PR_SNDBLK;

		//Store process in FIFO queue (set rcpblkflag and send in sendqueue)
		blkEnqueue(currpid, prptr->sendqueue);
		prptr->rcpblkflag++;

		//Call resched() to context switch out
		resched();

	} 
	//1 word buffer is empty, continue like send()
	prptr->prmsg = msg;	/* Deliver message */
	prptr->prhasmsg = TRUE;	/* Indicate message is waiting */
	
	/* If recipient waiting or in timed-wait make it ready */
        if (prptr->prstate == PR_RECV) {
               	ready(pid);
        } else if (prptr->prstate == PR_RECTIM) {
               	unsleep(pid);
               	ready(pid);
        }


	/* End of added code for lab5 */
        restore(mask);          /* Restore interrupts */
        return OK;
}
