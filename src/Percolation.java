import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int[][] systemGrid; //systemGrid[i] = 0 : site is blocked // systemGrid[i] = 1 : site is open
	private int openCount = 0;
	private int gridDimension = 0;
	private WeightedQuickUnionUF weightedQuickUnionUF;
	private int top;
	private int bottom;
	
	public Percolation(int n) {
		if (n <= 0) throw new java.lang.IndexOutOfBoundsException();
		gridDimension = n;
		systemGrid = new int[n][n];
		bottom = n*n;
		top = n*n + 1;
		weightedQuickUnionUF = new WeightedQuickUnionUF(gridDimension*gridDimension + 2);
		for (int i=0 ; i<n ; i++) {
			for (int j=0 ; j<n ; j++) {
				systemGrid[i][j] = 0;
			}
		}
	}
	
	public void open(int row, int col) {
		if (!isValid(row, col)) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		systemGrid[row-1][col-1] = 1;
		openCount++;
		connectOpenNeighbours(row , col);
	}
	
	private void connectOpenNeighbours(int row, int col) {
		int leftNeighbour = col - 1;
		int rightNeighbour = col + 1;
		int upperNeighbour = row - 1;
		int downNeighbour = row + 1;
		
		if (isValid(row, leftNeighbour) && isOpen(row, leftNeighbour)) {
			weightedQuickUnionUF.union(((row-1)*gridDimension)+col-1, ((row-1)*gridDimension)+leftNeighbour-1);
			if (row == 1 ) { weightedQuickUnionUF.union(top, ((row-1)*gridDimension)+leftNeighbour-1);};
			if (row == gridDimension) { weightedQuickUnionUF.union(bottom, ((row-1)*gridDimension)+leftNeighbour-1);};

		}
		
		if (isValid(row, rightNeighbour) && isOpen(row, rightNeighbour)) {
			weightedQuickUnionUF.union(((row-1)*gridDimension)+col-1, ((row-1)*gridDimension)+rightNeighbour-1);
			if (row == 1) { weightedQuickUnionUF.union(top, ((row-1)*gridDimension)+rightNeighbour-1);};
			if (row == gridDimension) { weightedQuickUnionUF.union(bottom, ((row-1)*gridDimension)+rightNeighbour-1);};
		}
		
		if (isValid(upperNeighbour, col) && isOpen(upperNeighbour, col)) {
			weightedQuickUnionUF.union(((row-1)*gridDimension)+col-1, ((upperNeighbour-1)*gridDimension));
			if (row == 1) { weightedQuickUnionUF.union(top, (upperNeighbour-1)*gridDimension);}
			if (row == gridDimension) { weightedQuickUnionUF.union(bottom, (upperNeighbour-1)*gridDimension);};

		}
		
		if (isValid(downNeighbour, col) && isOpen(downNeighbour, col)) {
			weightedQuickUnionUF.union(((row-1)*gridDimension)+col-1, ((downNeighbour-1)*gridDimension));
			if (row == 1) { weightedQuickUnionUF.union(top, (downNeighbour-1)*gridDimension);}
			if (row == gridDimension) { weightedQuickUnionUF.union(bottom, (downNeighbour-1)*gridDimension);};
		}
		
		
	}
	
	private boolean isValid(int row, int col) {
		if ((row <= 0 || row > gridDimension) || (col <= 0 || col > gridDimension)) {
			return false;
		}
		return true;
	}
	
	public boolean isOpen(int row, int col) {
		if (!isValid(row, col)) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		return systemGrid[row-1][col-1] == 1;
	}
	
	public boolean isFull(int row, int col) {
		if (!isValid(row, col)) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		return systemGrid[row-1][col-1] == 0;
	}
	
	public int numberOfOpenSites() {
		return openCount;
	}
	
	public boolean percolates() {
		return weightedQuickUnionUF.connected(top, bottom);
	}
	
	public static void main(String[] args) {
		Percolation test = new Percolation(3);
		int[][] fuck = new int[3][3];
		fuck[0][1] = 1;
		fuck[0][2] = 1;
	}
	
}
