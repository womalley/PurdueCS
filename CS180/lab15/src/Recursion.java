import java.io.File;

/**
 * Created by bomal_000 on 4/26/2016.
 */
public class Recursion {

    /**
     * Counts the total number of files in the given directory recursively.
     *
     * @param f The current file or directory
     * @return The total number of files in f
     */
    public static int filecount(File f) {
        // TODO: implement this method
        if (f.isDirectory()) {
            int total =  0;

            for (File contain : f.listFiles()) {
                total += filecount(contain);
            }
            return (total);
        }
        else {
            return (1);
        }
    }
}
