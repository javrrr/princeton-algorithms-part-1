import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Scanner;

public class Percolation {
    private WeightedQuickUnionUF unionFind;
    private boolean[][] grid;
    private int size;

    // create n-by-n unionFind, with all sites blocked
    public Percolation(int n){
        if(n <= 0) throw new IllegalArgumentException();
        grid = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF(n * n);
        size = n;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col){
        validate(row, col);
        StdOut.println("\n\nOpening [" + row + ", " + col + "]");
        grid[row-1][col-1] = true;
        connectNeighbors(row, col);
        printGrid();
        if(percolates()){
            StdOut.println("The system percolates!");
        }
    }

    private void connectNeighbors(int row, int col) {
        int index = getIndex(row, col);
        int[][] neighbors = getNeighbors(row, col);

        for(int[] n : neighbors) {
            if(n == null) continue;
            int neighbor = getIndex(n[0], n[1]);
            if(isOpen(n[0], n[1]) && !unionFind.connected(index, neighbor)) {
                unionFind.union(index, neighbor);
                StdOut.println("Union with: {" + n[0] + ", " + n[1] + "}");
                StdOut.println("Component count: " + unionFind.count());
            }
        }
    }

    private int getIndex(int row, int col) {
        return row == 1 ? col : (row-1) * size + col-1;
    }

    public int[][] getNeighbors(int row, int col) {
        return new int[][] {
                isInvalidIndex(row-1, col) ? null : new int[] {row-1, col} ,
                isInvalidIndex(row, col+1) ? null : new int[] {row, col+1},
                isInvalidIndex(row+1, col) ? null : new int[] {row+1, col},
                isInvalidIndex(row, col-1) ? null : new int[] {row, col-1}
        };
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row-1][col-1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if(isInvalidIndex(row, col)) return false;
        validate(row, col);

        boolean isTopEdge = row == 1;
        if(isOpen(row, col) && isTopEdge) return true;
        if(isConnected(row, col)) return true;

        return false;
    }

    public boolean isConnected(int row, int col) {
        int index = getIndex(row, col);
        int[][] neighbors = getNeighbors(row, col);
        for(int[] n : neighbors) {
            if(n == null) continue;
            int nRow = n[0];
            int nCol = n[1];
            int nIndex = getIndex(nRow, nCol);
            return unionFind.connected(index, nIndex) && nIndex < size;
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid.length; col++) {
                if(grid[row][col]) {
                    count++;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for(int i=1; i<=size; i++) {
            if(isFull(size, i)) return true;
        }
        return false;
    }

    private void validate(int row, int col) throws IllegalArgumentException {
        if(isInvalidIndex(row, col)) {
            throw new IllegalArgumentException("The arguments are outside of the prescribed range!");
        }
    }

    private boolean isInvalidIndex(int row, int col){
        return row < 1 || row > size || col < 1 || col > size;
    }

    // test client (optional)
    public static void main(String[] args)   {
        StdOut.println("\n\nEnter size n of the n * n grid:");
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        boolean[][] grid = percolation.grid;
        percolation.printGrid();

        Scanner input = new Scanner(System.in);
        while(true) {
            StdOut.println("\n\nEnter coordinates of the site to open one after the next:");
            int x = input.nextInt();
            int y = input.nextInt();
            percolation.open(x, y);
            percolation.printGrid();
        }
    }


    public void printGrid() {
        for(int row = 0; row < grid.length; row++) {
            StdOut.println();
            for(int col = 0; col < grid.length; col++) {
                StdOut.print(grid[row][col] + " ");
            }
        }
    }
}
