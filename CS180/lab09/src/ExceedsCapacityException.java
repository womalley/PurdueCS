/**
 * Created by bomal_000 on 3/7/2016.
 */
public class ExceedsCapacityException extends Exception {
    public ExceedsCapacityException (String message) {
        super(message);
    }
    public ExceedsCapacityException () {
        super();
    }
}
