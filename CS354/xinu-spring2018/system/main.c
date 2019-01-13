/*  main.c  - main */

#include <xinu.h>
#include <stdio.h>

int32 mrecv_cb(void) {
        umsg32 mbuf;
        mbuf = receive();
        kprintf("Callback PID: %d, %d\n", currpid, mbuf);
        return(OK);
}

void sendAsync(pid32 receiver, umsg32 message) {
	if (send(receiver, message) == SYSERR) {
                kprintf("Message failed: %d\n", message);
        } else {
		kprintf("Message sent: %d\n", message);
	}
	return;
}

process	main(void)
{
	/*kprintf("\nHello World!\n");
	kprintf("\nI'm the first XINU app and running function main() in system/main.c.\n");
	kprintf("\nI was created by nulluser() in system/initialize.c using create().\n");
	kprintf("\nMy creator will turn itself into the do-nothing null process.\n");
	kprintf("\nI will create a second XINU app that runs shell() in shell/shell.c as an example.\n");
	kprintf("\nYou can do something else, or do nothing; it's completely up to you.\n");
	kprintf("\n...creating a shell\n");
	recvclr();
	resume(create(shell, 8192, 50, "shell", 1, CONSOLE));

	Wait for shell to exit and recreate it

	while (TRUE) {
		receive();
		sleepms(200);
		kprintf("\n\nMain process recreating shell\n\n");
		resume(create(shell, 4096, 20, "shell", 1, CONSOLE));
	}*/

	kprintf("\nMAIN METHOD\n\n");

	//Part 1 Testing
	int testing = create(receiveMessage, 1024, 2, "receive", 0);
	resume(testing);
	resume(create(sendMessage, 1024, 2, "send1", 2, testing, 1));
	resume(create(sendMessage, 1024, 2, "send2", 2, testing, 2));
	resume(create(sendMessage, 1024, 2, "send3", 2, testing, 3));
	resume(create(sendMessage, 1024, 2, "send4", 2, testing, 4));

	/*//Part 2 Testing
   	if (cbreg(&mrecv_cb) != OK) {
      		kprintf("cb registration failed!");
      		return 1;
   	}	
	
	//kprintf("creating tests async sending\n");
	int asyncReceive = currpid;
	int asyncSend1 = create(sendAsync, 1024, 20, "asyncSend", 2, asyncReceive, 1);
	int asyncSend2 = create(sendAsync, 1024, 20, "asyncSend", 2, asyncReceive, 2);
	int asyncSend3 = create(sendAsync, 1024, 20, "asyncSend", 2, asyncReceive, 3);
	int asyncSend4 = create(sendAsync, 1024, 20, "asyncSend", 2, asyncReceive, 4);
	//kprintf("resume async sending\n");
	resume(asyncSend1);
	resume(asyncSend2);
	resume(asyncSend3);
	resume(asyncSend4);

	while(1) {}*/
	//----------
	return OK;
}

void receiveMessage(void) {
	while(1) {
		umsg32 received = receive();
		kprintf("\nRECEIVED: %d\n", received);
		sleepms(500);
	}
}

void sendMessage(pid32 pid, umsg32 message) {
	sendblk(pid, message);
}
