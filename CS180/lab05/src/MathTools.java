/**
 * CS180 - Lab 05 - MathTools
 * The program has a selection display that executes different mathematical functions
 * @author William O'Malley, womalley@purdue.edu
 *
 * Created by bomal_000 on 2/9/2016.
 */
public class MathTools {
    public static double absoluteValue(double n) {

        if(n < 0) {
            return (-n);
        }
        else {
            return (n);
        }
    }
    public static double power(double base, int exponent) {
        if(exponent == 0) {
            return (1);
        }
        else {
            double ans = 1;
            double inverse;

            if(exponent < 0) {
                for (int i = 1; i <= absoluteValue(exponent); i++) {
                    ans = (ans * base);
                }

                inverse = (1 / ans);

                return (inverse);
            }
            else {
                return (ans);
            }
        }
    }
    public static double nthRoot(double value, int root) {
        double error;


        if(value < 1 || root == 0) {
            return (0);
        }

    }
    public static String scientificNotation(double n) {
        double pow = 0; //power
        double coefficient = 0; //coeffeicient of the number
        double exp = 0; //exponent


        while(absoluteValue(n) <= 0) {
            n = (n / 10);  //Moves decimal point to the roght
            pow++; //Shows the power value after moving the decimal location
        }
        while(absoluteValue(n) >= 10) {
            n = (n * 10);  //Moves decimal point to the left
            pow--; //Shows the power value after moving the decimal location
        }

        coefficient = (((int)(n * 1000000)) / 1000000.0);
        exp = (((int)absoluteValue(n) / n) * pow);

        return (coefficient + " x 10^ " + exp);
    }
}
