import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF unionFind;
    private final int top = 0;
    private int bottom;
    private boolean[] grid;
    private final int width;
    private int openSites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        openSites = 0;
        width = n;
        grid = new boolean[n * n + 1];
        bottom = width * width + 1;
        unionFind = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        validate(row, col);
        int index = getIndex(row, col);
        if(!grid[index]){
            grid[index] = true;
            unionNeighbors(row, col);
            openSites++;
        };
    }

    private int getIndex(int row, int col) {
        return (row - 1) * width + col;
    }

    private void unionNeighbors(int row, int col) {
        int index = getIndex(row, col);
        if (row == 1) unionFind.union(index, top);
        if (row == width) unionFind.union(index, bottom);
        if (col > 1) unionOpen(index, row, col - 1);
        if (col < width) unionOpen(index, row, col + 1);
        if (row > 1) unionOpen(index, row - 1, col);
        if (row < width) unionOpen(index, row + 1, col);
    }

    private void unionOpen(int index, int row, int col) {
        if (isOpen(row, col)) {
            int neighbor = getIndex(row, col);
            unionFind.union(index, neighbor);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[getIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        int index = getIndex(row, col);
        return unionFind.connected(index, top);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return unionFind.connected(top, bottom);
    }

    private void validate(int row, int col){
        if (row < 1 || row > width || col < 1 || col > width ) {
            throw new IllegalArgumentException();
        }
    }
}
