package Lib;

import java.util.*;

public class IntegerValidator {

  public static int getValidatedInteger(Scanner sc) {
    while (true) {
      try {
        return sc.nextInt();
      } catch (InputMismatchException e) {
        System.err.println("Enter a valid integer");
        sc.nextLine();
      }
    }
  }

}
