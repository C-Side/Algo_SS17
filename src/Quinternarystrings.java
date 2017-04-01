import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Algorithmen und Datenstrukturen
 * Abgabe 03.04.2017
 * Aufgabe 1
 *
 * @author Klinghammer, Kroll, Walk
 * @version %I%, %G%
 */
public class Quinternarystrings {

  private static int counter;
  private static int n = 3;
  private static int[] zahlen = new int[n];

  private static BigInteger math() {

    counter = 0;

    if (n == 0) {
      return BigInteger.ZERO;
    }
    if (n == 1) {
      return BigInteger.valueOf(5);
    }
    if (n >= 2) {
      for (int index = zahlen.length - 1; index >= 1; index--) {
        for (; zahlen[index - 1] <= 4; zahlen[index - 1]++) {
          for (; zahlen[index] <= 4; zahlen[index]++) {
            if (zahlen[index] - zahlen[index - 1] != 1 && zahlen[index] - zahlen[index - 1] != 2) {
              System.out.println(Arrays.toString(zahlen));
              counter++;
            }
          }
          zahlen[index] = 0;

        }
      }
    }
    return BigInteger.valueOf(counter);
  }

  private static void rekursion(int n) {

    BigInteger counter = BigInteger.ZERO;

    if (n == 0) {
      System.out.println(0);
    } else if (n == 1) {
      System.out.println(5);
    } else if (n >= 1 && zahlen[n] <= 4) {
      if (zahlen[n + 1] - zahlen[n] != 1 && zahlen[n + 1] - zahlen[n] != 2) {
        counter.add(BigInteger.ONE);
        zahlen[n]++;
        rekursion(zahlen[n]);
      }
    } else if (zahlen[n] == 4) {
      System.out.println(counter);
    }

  }

  public static void main(String[] args) {
    System.out.println(math());
  }
}
