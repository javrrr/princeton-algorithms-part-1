import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        Percolation percolation = new Percolation(n);
        while(trials > 0) {
            int row = StdRandom.uniform(1, n+1);
            int col = StdRandom.uniform(1, n+1);
            if(!percolation.isOpen(row, col)) {
                percolation.open(row, col);
                trials--;
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return 0;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0;
    }

    // test client (described below)
    public static void main(String[] args) {
        StdOut.println("\nEnter size n of the n * n grid:");
        int n = StdIn.readInt();
        StdOut.println("\nEnter number of trials to run:");
        int trials = StdIn.readInt();
        PercolationStats stats = new PercolationStats(n, trials);
    }
}
