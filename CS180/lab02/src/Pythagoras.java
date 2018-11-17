/**
 * CS180 - Lab 02: Pythagoras
 * This program calculate the answer using the pythagoras thereom when given inputs for a, b, and/or c.
 * @author William O'Malley, womalley@purdue.edu, L12
 *
 * Created by womalley on 1/20/16.
 */
public class Pythagoras {
    public double getHypotenuse(int a, int b) {
        double result;
        result = Math.sqrt(a * a + b * b);
        return result;
    }
}
