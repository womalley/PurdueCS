/**
 * Created by bomal_000 on 3/7/2016.
 */
public class VolumeUnderFlowException extends Exception {
    public VolumeUnderFlowException (String message) {
        super(message);
    }
    public VolumeUnderFlowException () {
        super();
    }
}
