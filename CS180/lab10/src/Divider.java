import java.util.concurrent.atomic.AtomicInteger;

/**
 * CS180 - Lab 10 - Divider
 * The program checks three threads ranging from values 1 to 3000 in search of numbers divisible by the number 7.
 * If there is an error during the join stage it will catch it and return "Big problem."
 *
 * @author William O'Malley, womalley@purdue.edu
 *
 * Created by bomal_000 on 3/21/2016.
 */
public class Divider implements Runnable {

    private static AtomicInteger counter = new AtomicInteger();


//    public static int counter;
    private int start;
    private int end;
    static Object obj = new Object();

    public Divider (int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static void main (String[] args) {
//        counter = 0;

        //Created Threads
        Thread t1 = new Thread(new Divider(1, 1000));
        Thread t2 = new Thread(new Divider(1001, 2000));
        Thread t3 = new Thread(new Divider(2001, 3000));

        //Starts the Threads
        t1.start();
        t2.start();
        t3.start();

        //Joining the Threads
        try {
            t1.join();
            t2.join();
            t3.join();

        } catch (InterruptedException e) {
            System.out.println("Big problem"); //Error statement
        }

        //Count printer
        System.out.println("Total values divisible by 7: " + counter.get());
    }

    //Checks to see if divisible by 7

    public void run() {
        int i;

        for (i = start; i <= end; i++) {
            if (i % 7 == 0) {
//                synchronized (obj) {
//                    counter = (counter + 1);
//                }
                counter.incrementAndGet();
            }
        }
    }
}
