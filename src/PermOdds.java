/**
 * Algorithmen und Datenstrukturen
 * Abgabe 24.04.2017
 * Aufgabe 4.1
 *
 * @author Klinghammer, Kroll, Walk
 * @version %I%, %G%
 */
public class PermOdds {

	private int[] a; // a Arbeitsarray
	private int max; // maximaler Index
	private boolean mayread = false; // Kontrolle
	private int counter = 1;

	PermOdds (int n){ // Konstruktor
		a = new int[n]; // Indices: 0 .. n-1
		max = n - 1; // Maximaler Index
		for (int i=0; i<=max;i++) a[i]=1+i; // a fuellen
	} // end Konstruktor

	private void perm (int i){ // permutiere ab Index i
		if (i >= max)printArray(a); // eine Permutation fertig
		else {
			for (int j=i; j <= max; j++){ // jedes nach Vorne
				swap (i,j); // vertauschen
				perm (i+1); // und Rekursion
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

	private void printArray(int[] arr) {
		System.out.print(counter++ + "[");
		for (int i = 0; i < arr.length;i++) {
			System.out.print(" " + arr[i]);
		}
		System.out.println(" ]");
	}

	public static void main(String args[]) {
		int n = Integer.parseInt (args[0]);
		PermOdds permOdds = new PermOdds(n);
		permOdds.perm(0);
	}
}
