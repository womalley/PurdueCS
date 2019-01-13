
Part 1:
Step 3 prints 'A', 'B', and 'C' seemingly at random. 
Step 5 only prints 'C' because it never reaches the pthread calls.
The function "printC()" is called and loops forever.

Part 3:

------------------------------------------------------------------------------------------
|                                         | System (Kernel) Time | User Time | Real Time |
------------------------------------------------------------------------------------------
| pthread_mutex(count)                    |      1.964 sec       | 1.855 sec | 1.764 sec |
------------------------------------------------------------------------------------------
| spin_lock(count_spin with thr_yield     |      0.220 sec       | 0.476 sec | 0.366 sec |
------------------------------------------------------------------------------------------
| spin_lock(count_spin without thr_yield  |      0.000 sec       | 0.240 sec | 0.139 sec |
------------------------------------------------------------------------------------------

1. The difference between count_spin with and without thr_yield in user time is most likely caused by the increase in the system
   time when thr_yield is active. This would explain the near 0.22 second difference in the user times, since the thr_yield takes
   0.22 seconds. The thr_yield method can end up wasting time waiting.

2. The difference in system time between count and count_spin is caused by the fact that count uses pthread_mutex_lock which calls
   the wait function. The spin_lock used by count_spin is a lot faster than using wait(), which is seen by the chart.
