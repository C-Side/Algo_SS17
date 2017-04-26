/**
 * Algorithmen und Datenstrukturen
 * Abgabe 24.04.2017
 * Aufgabe 4.1
 *
 * @author Klinghammer, Kroll, Walk, auf Basis von Prof. Heinz
 * @version %I%, %G%
 */
public class PermOdds {

	private static int[] a; // a Arbeitsarray
	private int max; // maximaler Index
	private static double counter = 0;
	private static double totCounter = 0;
	private static double altCounter = 0;

	PermOdds (int n){ // Konstruktor
						 // Indices: 0 .. n-1
		max = n - 1; // Maximaler Index
		for (int i=0; i<=max;i++) a[i]=1+i; // a fuellen
	} // end Konstruktor

	private void perm (int i, int[] b){ // permutiere ab Index i
		//printArray(a);
		if (i >= max) counter++; // Permutation auf Bedingung überprüfen
		else {
			for (int j=i; j <= max; j++){ // jedes nach Vorne
				swap (i,j); // vertauschen
				perm (i+1, a); // und Rekursion
				if (a.equals(b)) altCounter++;
			}
			int h = a[i]; // restauriere Array
			System.arraycopy (a,i+1,a,i,max-i); // shift links
			a[max] = h;
		}
	} // end perm

	private void swap (int i, int j){ // tausche a[i] <-> a[j]
		if (i != j)
		{ int h = a[i]; a[i] = a[j]; a[j] = h; }
	} // end swap

	private void checkUneven(int[] a) {
		boolean print = true;
		for (int i = 0; i < a.length - 1; i++) { //gehe durch array
			int one = (a[i] + i + 1) %2;	//erste zahl durch modulo teilen
			int two = (a[i + 1] + i + 2) %2;	//zweite zahl durch modulo teilen
			if (one == 1 && two == 1) { //vergleiche ob beide ungerade sind
				print = false; //fals ja printe nicht und verlasse for schleife
				break;
			}
		}
		if (print) counter++; //printe das array
	}

	private void printArray(int[] arr) {
		System.out.print("[");
		for (int i = 0; i < arr.length;i++) {
			System.out.print(" " + arr[i]);
		}
		System.out.println(" ]");
		totCounter++;
	}

	public static void main(String args[]) {
		int n = Integer.parseInt (args[0]);
		a = new int[n];
		PermOdds permOdds = new PermOdds(n);
		permOdds.perm(0, a);
		System.out.println("Es gab genau " + counter + " Permutationen der verlangten Art!");
		System.out.println("Es gab insgesamt " + totCounter + " Perm Aufrufe!");
		System.out.println("Es gab insgesamt " + altCounter + " entfallene Perms!");
		double zahl = altCounter/counter;
		System.out.println("Es gab insgesamt " + zahl + " Perm Aufrufe!");
	}
}
