#include <xinu.h>

syscall cbreg( int (* mrecv_cb) (void) ) {
	//kprintf("beginning of cbreg\n");
	intmask mask;
	mask = disable();

	//Check if there is no callback function
	if(mrecv_cb == NULL) {
		restore(mask);
		return SYSERR;
	}	

	struct procent *prptr = &proctab[currpid];
	if(prptr->prhascb == 1)  {
		restore(mask);
		return SYSERR;
	}
	prptr->prhascb = 1;
	prptr->fptr = mrecv_cb;

	restore(mask);
	//kprintf("end of cbreg\n");
	return OK;
}
