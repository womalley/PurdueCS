public class CounterDriver {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter2(); //change Counter_() for different uses (i.e. 0, 1, 2)
        Thread inc = new Thread(new IncThread(counter));
        Thread dec = new Thread(new DecThread(counter));
        inc.start();
        dec.start();
        inc.join();
        dec.join();
        System.out.println(counter.get());
    }
}