import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * [Text goes here]
 *
 * @author Klinghammer
 * @version %I%, %G%
 */
public class PermRechner {

	private static int max;
	private static int n;

	private static void calc(BigDecimal perms, BigDecimal fakul) {
		fakul = fakul.multiply(BigDecimal.valueOf(n));
		perms = (perms.multiply(BigDecimal.valueOf(n))).add(BigDecimal.ONE);
		BigDecimal erg = (perms.subtract(BigDecimal.ONE)).divide(fakul,200, RoundingMode.HALF_UP);
		if (n == max) {
			printErg(fakul, perms, erg);
		} else {
			n++;
			calc(perms, fakul);
		}
	}

	private static void printErg(BigDecimal fakul, BigDecimal perms, BigDecimal erg) {
		System.out.println("Fakultaeten: " + fakul);
		System.out.println("Permutationen: " + perms);
		System.out.println("Ergebnis: " + erg);
	}


	public static void main(String args[]) {
		max = Integer.parseInt (args[0]);
		n = 1;
		calc(BigDecimal.ZERO, BigDecimal.ONE);
	}
}
