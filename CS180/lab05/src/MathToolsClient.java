/**
 * Created by bomal_000 on 2/9/2016.
 */

import java.util.Scanner;

public class MathToolsClient {
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        MathTools mth = new MathTools();
        String menuSelect = "";

        do {
            System.out.println("Select a number from the menu choices:");
            System.out.println("1 - Absolute Value");
            System.out.println("2 - Power");
            System.out.println("3 - Nth Root");
            System.out.println("4 - Scientific Notation");

            int option = 0;
            option = in.nextInt();

            if(option > 4 || option <= 0) {
                System.out.println("Invalid option.");
                System.out.println("Return to menu? (yes/no)");

                menuSelect = in.next();
            }



        }while(menuSelect.equals("yes"));
        System.out.printf("Exiting MathToolsClient...");
    }
}
