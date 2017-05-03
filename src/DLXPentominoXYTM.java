import java.util.LinkedList;

/**
 * [Text goes here]
 *
 * @author Klinghammer, Walk, Kroll auf Basis von Heinz
 * @version %I%, %G%
 */
public class DLXPentominoXYTM {

	private static long counter;
	private static int n;
	private static int size;
	private static DLXNode header;
	private static LinkedList<int[]> subsets;
	private static DLXNode[][] matrix;

	/**
	 * inner DLXNode class for the matrix to use
	 */
	private static class DLXNode { // represents 1 element or header
		DLXNode C; // reference to column-header
		DLXNode L, R, U, D; // left, right, up, down references

		DLXNode() {
			C = L = R = U = D = this;
		} // supports circular lists
	}

	public static void search(int k) { // finds & counts solutions
		if (header.R == header) {
			counter++;
			return;
		} // if empty: count & done
		DLXNode c = header.R; // choose next column c
		cover(c); // remove c from columns
		for (DLXNode r = c.D; r != c; r = r.D) { // forall rows with 1 in c
			for (DLXNode j = r.R; j != r; j = j.R) // forall 1-elements in row
			{
				cover(j.C); // remove column
			}
			search(k + 1); // recursion
			for (DLXNode j = r.L; j != r; j = j.L) // forall 1-elements in row
			{
				uncover(j.C); // backtrack: un-remove
			}
		}
		uncover(c); // un-remove c to columns
	}

	private static void cover(DLXNode c) { // remove column c
		c.R.L = c.L; // remove header
		c.L.R = c.R; // .. from row list
		for (DLXNode i = c.D; i != c; i = i.D) // forall rows with 1
		{
			for (DLXNode j = i.R; i != j; j = j.R) { // forall elem in row
				j.D.U = j.U; // remove row element
				j.U.D = j.D; // .. from column list
			}
		}
	}

	private static void uncover(DLXNode c) {//undo remove col c
		for (DLXNode i = c.U; i != c; i = i.U) // forall rows with 1
		{
			for (DLXNode j = i.L; i != j; j = j.L) { // forall elem in row
				j.D.U = j; // un-remove row elem
				j.U.D = j; // .. to column list
			}
		}
		c.R.L = c; // un-remove header
		c.L.R = c; // .. to row list
	}

	/**
	 * calculates all the possible ways to place the pieces in the matrix
	 */
	private static void calculateSubsets() {
		subsets = new LinkedList<int[]>();
		LinkedList<int[]> temp = new LinkedList<int[]>();

		//add 1x1 to the subsets
		for (int i = 1; i <= size; i++) {
			subsets.add(new int[] {i});
		}

		//the size has to be wider then 1 for the 2x4
		if (n > 1) {
			int lastRow = 1;

			//create 2x4 matrix to rotate the L
			for (int number = 1; number <= size - 4; number++) {
				if (number % n != 0 && lastRow < 4) {
					int a = number;
					int b = a + 1;
					int c = b + (n - 1);
					int d = c + 1;
					int e = d + (n - 1);
					int f = e + 1;
					int g = f + (n - 1);
					int h = g + 1;
					temp.add(new int[] {a, b, c, d, e, f, g, h}); //add the 2x4 to the temp
				} else {
					lastRow++;
				}
			}

			//rotate L through the temp 2x4
			int tempSize = temp.size();
			for (int i = 0; i <= tempSize - 1; i++) {
				int[] lastSquare = temp.get(i); //gets the next 2x4
				for (int j = 0; j <= 3; j++)//go through all the possible rotations
				{
					switch (j) {
						case 0:
							subsets.add(new int[] {lastSquare[1], lastSquare[2], lastSquare[3], lastSquare[5], lastSquare[7]});
							break;
						case 1:
							subsets.add(new int[] {lastSquare[0], lastSquare[2], lastSquare[4], lastSquare[5], lastSquare[6]});
							break;
						case 2:
							subsets.add(new int[] {lastSquare[0], lastSquare[2], lastSquare[3], lastSquare[4], lastSquare[6]});
							break;
						case 3:
							subsets.add(new int[] {lastSquare[1], lastSquare[3], lastSquare[4], lastSquare[5], lastSquare[7]});
							break;
					}
				}
			}
		}

		temp.clear();

		//the size has to be wider then 2 for the 3x3
		if (n > 2) {
			int lastRow = 1;

			//create 3x3 matrix to rotate the T pieces and add X to the subsets
			for (int number = 1; number <= size - 4; number++) {
				if ((number % n) - 1 != 0 && lastRow < 5) {
					int a = number;
					int b = a + 1;
					int c = b + 1;
					int d = c + (n - 2);
					int e = d + 1;
					int f = e + 1;
					int g = f + (n - 2);
					int h = g + 1;
					int i = h + 1;
					temp.add(new int[] {a, b, c, d, e, f, g, h, i}); //add the 3x3 to the temp
					subsets.add(new int[] {b, d, e, f, h}); //add the X to the subsets
				} else {
					lastRow++;
				}
			}

			//rotate T through the temp 3x3
			int tempSize = temp.size();
			for (int i = 0; i <= tempSize - 1; i++) {
				int[] lastSquare = temp.get(i); //gets the next 3x3
				for (int j = 0; j <= 3; j++)//go through all the possible rotations
				{
					switch (j) {
						case 0:
							subsets.add(new int[] {lastSquare[0], lastSquare[1], lastSquare[2], lastSquare[4], lastSquare[7]});
							break;
						case 1:
							subsets.add(new int[] {lastSquare[0], lastSquare[3], lastSquare[4], lastSquare[5], lastSquare[6]});
							break;
						case 2:
							subsets.add(new int[] {lastSquare[1], lastSquare[4], lastSquare[6], lastSquare[7], lastSquare[8]});
							break;
						case 3:
							subsets.add(new int[] {lastSquare[2], lastSquare[3], lastSquare[4], lastSquare[5], lastSquare[8]});
							break;
					}
				}
			}
		}

		temp.clear();

		//the size has to be wider then 1 for the 4x2
		if (n > 3) {
			int lastRow = 1;

			//create 4x2 matrix to rotate the L
			for (int number = 1; number <= size - 4; number++) {
				if (number % n != 0 && lastRow < 4) {
					int a = number;
					int b = a + 1;
					int c = b + 1;
					int d = c + 1;
					int e = d + (n - 3);
					int f = e + 1;
					int g = f + 1;
					int h = g + 1;
					temp.add(new int[] {a, b, c, d, e, f, g, h}); //add the 4x2 to the temp
				} else {
					lastRow++;
				}
			}

			//rotate L through the temp 4x2
			int tempSize = temp.size();
			for (int i = 0; i <= tempSize - 1; i++) {
				int[] lastSquare = temp.get(i); //gets the next 4x2
				for (int j = 0; j <= 3; j++)//go through all the possible rotations
				{
					switch (j) {
						case 0:
							subsets.add(new int[] {lastSquare[0], lastSquare[1], lastSquare[2], lastSquare[3], lastSquare[6]});
							break;
						case 1:
							subsets.add(new int[] {lastSquare[2], lastSquare[4], lastSquare[5], lastSquare[6], lastSquare[7]});
							break;
						case 2:
							subsets.add(new int[] {lastSquare[0], lastSquare[1], lastSquare[2], lastSquare[3], lastSquare[5]});
							break;
						case 3:
							subsets.add(new int[] {lastSquare[1], lastSquare[4], lastSquare[5], lastSquare[6], lastSquare[7]});
							break;
					}
				}
			}
		}
	}

	/**
	 * connects the nodes with each other
	 */
	private static void calculateMatrix() {

		boolean erstesElement = true;
		matrix = new DLXNode[size][subsets.size() + 1];
		DLXNode letzterSpaltenkopf = new DLXNode();//Referenz auf letzten Spaltenkopf

		for (int i = 0; i <= size - 1; i++)//Spaltenköpfe erstellen und verknüpfen
		{
			matrix[i][0] = new DLXNode();
			matrix[i][0].C = matrix[i][0];
			//Wenn i>0 dann ist letzter Spaltenkopf bereits bekannt
			if (i > 0) {
				letzterSpaltenkopf.R = matrix[i][0];
				matrix[i][0].L = letzterSpaltenkopf;
			}
			letzterSpaltenkopf = matrix[i][0];
		}
		//Anker verknüpfen
		letzterSpaltenkopf.R = header;
		header.L = letzterSpaltenkopf;
		header.R = matrix[0][0];
		matrix[0][0].L = header;

		for (int i = 0; i <= subsets.size() - 1; i++)//Zeilen erstellen + verknüpfen
		{
			DLXNode ersterZeilenEintrag = new DLXNode();//erster Zeileneintrag
			DLXNode letzterZeilenEintrag = new DLXNode();//letzter Zeileneintrag
			int[] reihe = subsets.get(i);
			//zugehörige Spalte bestimmen
			for (int j = 0; j <= reihe.length - 1; j++) {
				int spalte = reihe[j] - 1;//-1 da spalten von 0 bis felgröße -1 gehen
				matrix[spalte][i + 1] = new DLXNode();
				matrix[spalte][i + 1].C = matrix[spalte][0];//Stimmt das?
				//verknüpfen
				if (j > 0)//Wenn i>0 dann ist letzterZeilenEintrag bereits bekannt
				{
					matrix[spalte][i + 1].L = letzterZeilenEintrag;
					letzterZeilenEintrag.R = matrix[spalte][i + 1];
				} else {
					ersterZeilenEintrag = matrix[spalte][i + 1];
				}
				letzterZeilenEintrag = matrix[spalte][i + 1];
			}
			//erste und letzte Zeile verknüpfen
			ersterZeilenEintrag.L = letzterZeilenEintrag;
			letzterZeilenEintrag.R = ersterZeilenEintrag;
		}

		for (int i = 0; i <= size - 1; i++)//Spalten verküpfen
		{
			erstesElement = true;
			DLXNode letzterSpaltenEintrag = new DLXNode();
			for (int ii = 0; ii <= subsets.size() - 1; ii++)//Alle Elemene einer Spalte durchgehen
			{
				if (matrix[i][ii + 1] instanceof DLXNode)//Wenn Element in der Matrix vom Typ DLX Node ist
				{
					if (erstesElement)//und erstes Element
					{
						matrix[i][0].D = matrix[i][ii + 1];
						matrix[i][ii + 1].U = matrix[i][0];
						erstesElement = false;
						letzterSpaltenEintrag = matrix[i][ii + 1];
					} else {
						letzterSpaltenEintrag.D = matrix[i][ii + 1];
						matrix[i][ii + 1].U = letzterSpaltenEintrag;
						letzterSpaltenEintrag = matrix[i][ii + 1];
					}
					matrix[i][ii + 1].C = matrix[i][0];//Referenz auf Spaltenkopf
				}
			}
			letzterSpaltenEintrag.D = matrix[i][0];
			matrix[i][0].U = letzterSpaltenEintrag;
		}
	}

	public static void ma()
	{
		String spalten2 = "";
		for (int i = 1; i<= size;i++)//Beschriftet die Spalten
		{
			if(i==size/2)
			{
				spalten2 = spalten2 +"Spaltenzahl = " + size;
			}
			else{
				spalten2 = spalten2 + "----";
			}
		}
		System.out.println(spalten2);
		for(int ii = 0;ii<=subsets.size(); ii++)//Für jede Zeile
		{
			String zeile = "| ";
			for (int i = 0;i<=size-1;i++)//gehe die Spalten durch
			{

				if (matrix[i][ii]!=null)//Setze eine 1 wenn die Matrix an dieser Stelle schon einen Node hat
				{
					zeile = zeile + 1 + " | ";
				}
				else //Ansonsten 0
				{
					zeile = zeile + 0 + " | ";
				}

			}
			if(ii==0)//1 Reihe wird beschriftet
			{
				System.out.println(zeile + "- " + ii + " Zeilen = Anzahl Teilmengen");
			}
			else
			{
				System.out.println(zeile + "- " + ii + "");
			}

		}
	}

	public static void main(String[] args) {
		n = Integer.parseInt (args[0]);;
		if (n == 0) {
			System.out.println("Keine Lösung für n = 0");
		} else {
			header = new DLXNode();
			counter = 0;
			size = 6 * n;
			calculateSubsets();
			calculateMatrix();
			ma();
			search(0);
			System.out.println("a(" + n + ") = " + counter);
		}
	}
}
