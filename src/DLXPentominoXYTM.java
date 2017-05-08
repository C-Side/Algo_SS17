import java.util.LinkedList;

/**
 * berechnet die anzahl der passenden lösungen
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

		boolean firstElement = true;
		matrix = new DLXNode[size][subsets.size() + 1];
		DLXNode lastNode = new DLXNode();

		for (int i = 0; i <= size - 1; i++)
		{
			matrix[i][0] = new DLXNode();
			matrix[i][0].C = matrix[i][0];

			if (i > 0) {
				lastNode.R = matrix[i][0];
				matrix[i][0].L = lastNode;
			}
			lastNode = matrix[i][0];
		}

		//connect header
		lastNode.R = header;
		header.L = lastNode;
		header.R = matrix[0][0];
		matrix[0][0].L = header;

		for (int row = 0; row <= subsets.size() - 1; row++)
		{
			DLXNode firstRowEntry = new DLXNode();
			DLXNode lastRowEntry = new DLXNode();

			int[] rowArray = subsets.get(row);

			for (int column = 0; column <= rowArray.length - 1; column++) {
				int columnArray = rowArray[column] - 1;
				matrix[columnArray][row + 1] = new DLXNode();
				matrix[columnArray][row + 1].C = matrix[columnArray][0];
				if (column > 0)
				{
					matrix[columnArray][row + 1].L = lastRowEntry;
					lastRowEntry.R = matrix[columnArray][row + 1];
				} else {
					firstRowEntry = matrix[columnArray][row + 1];
				}
				lastRowEntry = matrix[columnArray][row + 1];
			}
			firstRowEntry.L = lastRowEntry;
			lastRowEntry.R = firstRowEntry;
		}

		for (int i = 0; i <= size - 1; i++)
		{
			firstElement = true;
			DLXNode letzterSpaltenEintrag = new DLXNode();
			for (int ii = 0; ii <= subsets.size() - 1; ii++)
			{
				if (matrix[i][ii + 1] instanceof DLXNode)
				{
					if (firstElement)
					{
						matrix[i][0].D = matrix[i][ii + 1];
						matrix[i][ii + 1].U = matrix[i][0];
						firstElement = false;
						letzterSpaltenEintrag = matrix[i][ii + 1];
					} else {
						letzterSpaltenEintrag.D = matrix[i][ii + 1];
						matrix[i][ii + 1].U = letzterSpaltenEintrag;
						letzterSpaltenEintrag = matrix[i][ii + 1];
					}
					matrix[i][ii + 1].C = matrix[i][0];
				}
			}
			letzterSpaltenEintrag.D = matrix[i][0];
			matrix[i][0].U = letzterSpaltenEintrag;
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
			search(0);
			System.out.println("a(" + n + ") = " + counter);
		}
	}
}
