#ifndef blkNQENT
#define blkNQENT (NPROC + 4 + NSEM + NSEM)
#endif

extern struct qentry blkqueuetab[];

#define	blkqueuehead(q)	(q)
#define	blkqueuetail(q)	((q) + 1)
#define	blkfirstid(q)	(blkqueuetab[blkqueuehead(q)].qnext)
#define	blklastid(q)	(blkqueuetab[blkqueuetail(q)].qprev)
#define	blkisempty(q)	(blkfirstid(q) >= NPROC)
#define	blknonempty(q)	(blkfirstid(q) <  NPROC)
#define	blkfirstkey(q)	(blkqueuetab[blkfirstid(q)].qkey)
#define	blklastkey(q)	(blkqueuetab[blklastid(q)].qkey)

#define	blkisbadqid(x)	(((int32)(x) < 0) || (int32)(x) >= blkNQENT-1)
