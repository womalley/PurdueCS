import java.util.Scanner;

/**
 * Created by bomal_000 on 4/16/2016.
 */
public class EvaluatorTest {
    public static void main(String[] args) {

        Scanner scanIn = new Scanner(System.in);

        String test = ("5 1 2 + 4 * + 3 -");
        System.out.println(test);
        System.out.println(Evaluator.evaluate(test));
    }
}
