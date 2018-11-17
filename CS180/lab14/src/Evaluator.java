import java.util.Stack;

/**
 * Created by bomal_000 on 4/16/2016.
 */
public class Evaluator {
    static double evaluate(String args) {
        if (args.length() == 0 || args == null) {
            return 0;
        }

        String[] splitter = args.split(" ");
        Stack stack = new Stack();

        for (String i : splitter) {
            if (i.matches("[0-9]")) {
                stack.push(Double.parseDouble(i));
            }
            else {
                double input1 = (double) stack.pop();
                double input2 = (double) stack.pop();

                if (i.equals("+")) {
                    stack.push(input1 + input2);
                }
                else if (i.equals("-")) {
                    stack.push(input1 - input2);
                }
                else if (i.equals("/")) {
                    stack.push(input1 / input2);
                }
                else if (i.equals("*")) {
                    stack.push(input1 * input2);
                }
                else {
                    throw new RuntimeException("ERROR! Not an operator");
                }
            }
        }
        return (double) stack.pop();
    }
}
