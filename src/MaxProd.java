import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Algorithmen und Datenstrukturen
 * Abgabe 03.04.2017
 * Aufgabe 2
 *
 * @author Klinghammer, Kroll, Walk
 * @version %I%, %G%
 */
public class MaxProd {

	private static double[] array;
	private static int minIndex;
	private static int maxIndex;

	private static void maxProdMath() {

		int n = array.length;

		minIndex = 0;
		maxIndex = 0;

		double max = 1;
		double min = 1;
		double maxProd = 1;

		for (int i = 0; i < n; i++) {

			if (array[i] > 1 && array[i] != 0) {
				max = max * array[i];
				min = Math.min(min * array[i], 1);
			} else if (array[i] == 0) {
				max = 1;
				min = 1;
			} else if (array[i] < 1 && array[i] != 0) {
				double temp = max;
				max = Math.max(min * array[i], 1);
				min = temp * array[i];
			}

			if (maxProd < max) {
				maxIndex = i;
				maxProd = max;
			}
		}
	  System.out.println("Das maximale Produkt ist: " + maxProd);
		System.out.println("Der linke Index ist : " + minIndex + ". Der rechte Index ist: " + maxIndex);
	}

	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Geben sie die gewünschte Zahlenfolge");
		System.out.println("getrennt durch Leerzeichen ein: ");
		try {
			String[] strings;
			strings = input.readLine().trim().split("\\s");
			array = new double[strings.length];
			for(int i=0; i < strings.length; i++) {
				array[i] = Double.parseDouble(strings[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		maxProdMath();
	}
}
