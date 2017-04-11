/**
 *
 */
public class Money {
	static int array[] = {0, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10956, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155};
	// Indices 0 1 2 3 4 5 6
	static int betrag[] = {1, 2, 3, 3, 5, 7, 11};
	static int n = betrag.length; // Anzahl Muenzen
	// # Wechselarten fuer Betrag g und Muenzen mit Indices <= i
	static long table[][]; // Tabelle

	static long math (int g, int i){ // Methode
		return g < 0 ? 0 :
				i == 0 ? (g % betrag[0]==0 ? 1 : 0) :
						table [g][i] != 0 ? table [g][i] :
								(table [g][i] = math (g, i-1) + math (g-betrag[i], i)) ;
	}



	static long mathIf(int gesamt, int index) { // Methode
		if (gesamt < 0) {
			return 0;
		} else if (index == 0) {
			if (gesamt % betrag[0] == 0) {
				return 1;
			} else {
				return 0;
			}
		} else if (table[gesamt][index] != 0) {
			return table[gesamt][index];
		} else {
			return (table[gesamt][index] = mathIf(gesamt, index + 1) + mathIf(gesamt - betrag[index], index));
		}
	}

	public static void main (String[] args){
		int g = Integer.parseInt (args[0]); // g lesen
		table = new long [array[g]+1][n]; // w dimensionieren
		System.out.println ("Den Betrag "+g+" kann man auf "+
				math (array[g], n-1) + " verschiedene Arten wecheln.");
	}
}
