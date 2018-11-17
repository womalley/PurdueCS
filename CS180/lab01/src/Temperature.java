import java.util.Scanner;

/**
 * Created by sblomber on 8/25/15.
 *
 * CS 180 - Lab 01
 * Converts temp from F to C or vise versa
 * @author Scott BLomberg, sblomber@purdue.edu, 813
 */

import java.util.Scanner;

public class Temperature {
    public static void main(String args[]){

        // vars
        double fahrenheit;
        double celsius;
        String temp;
        Scanner in = new Scanner(System.in);

        // input
        System.out.println("Choose temperature scale (F for fahrenhiet, C for celsius).\n");
        temp = in.next();
        if(temp.equals("F")){
            System.out.println("Enter temperature in fahrenheit.");
            fahrenheit = in.nextDouble();
            celsius = ((fahrenheit - 32) * 5) / 9.0;
        }else{
            System.out.println("Enter temperature in celsius.");
            celsius = in.nextDouble();
            fahrenheit = ((celsius * 9) / 5.0) + 32;
        }

        //print
        System.out.println("Fahrenheit: " + fahrenheit);
        System.out.println("Celsius: " + celsius);
    }
}
