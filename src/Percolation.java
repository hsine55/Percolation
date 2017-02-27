/*
 * Fiducia IT AG, All rights reserved. Use is subject to license terms.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private int[][] systemGrid; //systemGrid[i] = 0 : site is blocked // systemGrid[i] = 1 : site is open
    private int openCount = 0;
    private int gridDimension = 0;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private	WeightedQuickUnionUF weightedQuickUnionUFBackSlash; //needed to keep track of the actually full nodes.
    private int fullTop;													//So that we don't get full nodes that aren't connected to the top.
    private int top;
    private int bottom;
    
    public Percolation(int n) {
        if (n <= 0) throw new java.lang.IllegalArgumentException();
        gridDimension = n;
        systemGrid = new int[n][n];
        bottom = n*n;
        top = n*n + 1;
        weightedQuickUnionUF = new WeightedQuickUnionUF(gridDimension*gridDimension + 2);
        fullTop = n*n;
        weightedQuickUnionUFBackSlash = new WeightedQuickUnionUF(gridDimension*gridDimension + 1);
    }
    
    public void open(int row, int col) {
        if (!isValid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) return; //already open
        systemGrid[row-1][col-1] = 1;
        openCount++;
        if(row == 1) {
            weightedQuickUnionUF.union(top, getWUFIndex(row, col));
            weightedQuickUnionUFBackSlash.union(fullTop, getWUFIndex(row, col));
        } 
        if (row == gridDimension) {
            weightedQuickUnionUF.union(bottom, getWUFIndex(row, col));
        }
        connectOpenNeighbours(row , col);
    }
    
    private void connectOpenNeighbours(int row, int col) {
        int leftNeighbour = col - 1;
        int rightNeighbour = col + 1;
        int upperNeighbour = row - 1;
        int downNeighbour = row + 1;
        
        if (isValid(row, leftNeighbour) && isOpen(row, leftNeighbour)) {
            weightedQuickUnionUF.union(getWUFIndex(row, col), getWUFIndex(row, leftNeighbour));
            weightedQuickUnionUFBackSlash.union(getWUFIndex(row, col), getWUFIndex(row, leftNeighbour));
        }
        
        if (isValid(row, rightNeighbour) && isOpen(row, rightNeighbour)) {
            weightedQuickUnionUF.union(getWUFIndex(row, col), getWUFIndex(row, rightNeighbour));
            weightedQuickUnionUFBackSlash.union(getWUFIndex(row, col), getWUFIndex(row, rightNeighbour));
        }
        
        if (isValid(upperNeighbour, col) && isOpen(upperNeighbour, col)) {
            weightedQuickUnionUF.union(getWUFIndex(row, col), getWUFIndex(upperNeighbour, col));
            weightedQuickUnionUFBackSlash.union(getWUFIndex(row, col), getWUFIndex(upperNeighbour, col));
        }
        
        if (isValid(downNeighbour, col) && isOpen(downNeighbour, col)) {
            weightedQuickUnionUF.union(getWUFIndex(row, col), getWUFIndex(downNeighbour, col));
            weightedQuickUnionUFBackSlash.union(getWUFIndex(row, col), getWUFIndex(downNeighbour, col));
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
        return weightedQuickUnionUFBackSlash.connected(fullTop, getWUFIndex(row, col));
    }
    
    public int numberOfOpenSites() {
        return openCount;
    }
    
    public boolean percolates() {
        return weightedQuickUnionUF.connected(top, bottom);
    }
    
    private int getWUFIndex(int gridIndX, int gridIndY) {
        return (gridIndX-1)*gridDimension + gridIndY - 1;
    }
    
    public static void main(String[] args) {

    }
    
}