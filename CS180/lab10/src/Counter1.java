/**
 * CS180 - Lab 10 - Counter1
 * The program uses synchronized to increment, decrement, and return the value of 'value.'
 * @author William O'Malley, womalley@purdue.edu
 *
 * Created by bomal_000 on 3/21/2016.
 */
public class Counter1 implements Counter {
    private int value = 0;

    public synchronized void inc() {
        value = value + 1;
    }

    public synchronized void dec() {
        value = value - 1;
    }

    public synchronized int get() {
        return value;
    }
}
