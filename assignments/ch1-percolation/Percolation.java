import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF unionFind;
    private final int size;
    private boolean[][] grid;

    // create n-by-n unionFind, with all sites blocked
    public Percolation(int n) {
        if(n <= 0) throw new IllegalArgumentException();
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
        int index = getIndex(row, col);

        if (row - 1 > 0) {
            if (isOpen(row - 1, col)) {
                unionFind.union(index, getIndex(row - 1, col));
            }
        }
        if (row + 1 <= size ) {
            if (isOpen(row + 1, col)) {
                unionFind.union(index, getIndex( row + 1, col));
            }
        }
        if (col - 1 > 0) {
            if (isOpen(row, col - 1)) {
                unionFind.union(index, getIndex(row, col - 1));
            }
        }
        if (col + 1 <= size) {
            if (isOpen(row, col + 1)) {
                unionFind.union(index, getIndex(row, col + 1));
            }
        }

        //int[] neighbors = { row - 1, col + 1, row + 1, col -1 };

    }

    private int getIndex(int row, int col) {
        int index;
        if (row == 1) {
            index = col - 1;
        } else {
            int base = (row - 1) * size;
            index = base + col - 1;
        }
        return index;
        //return row == 1 ? col - 1 : (row - 1) * size + col - 1;
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
        if (isOpen(row, col) && connectsToTop(row, col)) return true;
        return false;
    }

    private boolean connectsToTop(int row, int col) {
        int currentIndex = unionFind.find(getIndex(row, col));

        for (int i = 1; i <= size; i++) {
            int topIndex = unionFind.find(getIndex(1, i));
            if (isOpen(row, i) && unionFind.connected(currentIndex, topIndex)) return true;
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

    // test client (optional)
    public static void main(String[] args) {}

}
