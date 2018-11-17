import java.util.concurrent.atomic.AtomicInteger; //For AtomicInteger libraries

/**
 * CS180 - Lab 10 - Counter2
 * The program uses the AtomicInteger library to increment, decrement, and return the value of 'value.'
 * @author William O'Malley, womalley@purdue.edu
 *
 * Created by bomal_000 on 3/21/2016.
 */

public class Counter2 implements Counter {
    private AtomicInteger value = new AtomicInteger(); //Initial new AtomicInteger

    public void inc() {
        value.incrementAndGet();
    }

    public void dec() {
        value.decrementAndGet();
    }

    public int get() {
        return value.intValue();
    }
}
