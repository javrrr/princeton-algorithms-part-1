import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF unionFind;
    private boolean[][] grid;
    private final int size;

    // create n-by-n unionFind, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        grid = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF(n * n);
        size = n;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isInvalid(row, col)) {
            throw new IllegalArgumentException();
        }
        grid[row-1][col-1] = true;
        unionNeighbors(row, col);
    }

    private void unionNeighbors(int row, int col) {
        int index = getIndex(row, col);
        int[] neighbors = { 1, -1 };
        for(int n : neighbors) {
            if (!isInvalid(row, col + n)) {
                if (isOpen(row, col + n)) {
                    unionFind.union(index, getIndex(row, col + n));
                }
            }
            if (!isInvalid(row + n, col)) {
                if (isOpen(row + n, col)) {
                    unionFind.union(index, getIndex(row + n, col));
                }
            }
        }
    }

    private int getIndex(int row, int col) {
        return row == 1 ? col - 1 : (row - 1) * size + col - 1;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(isInvalid(row, col)) {
            throw new IllegalArgumentException();
        }
        return grid[row-1][col-1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isInvalid(row, col)) {
            throw new IllegalArgumentException();
        }
        int index = getIndex(row, col);
        if (!isOpen(row, col)) return false;
        for (int i = 1; i <= size; i++) {
            if (isOpen(1, i)) {
                int topIndex = getIndex(1, i);
                if(unionFind.connected(index, topIndex)) {
                    return true;
                }
            }
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {
                if (grid[row][col]) {
                    count++;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= size; i++) {
            if (isFull(size, i)) return true;
        }
        return false;
    }

    private boolean isInvalid(int row, int col){
        return row < 1 || row > size || col < 1 || col > size;
    }
}
