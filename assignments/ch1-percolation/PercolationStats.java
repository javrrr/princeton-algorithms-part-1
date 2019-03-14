import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] thresholds;
    private final int trials;
    private final int size;
    private static final double Z_SCORE = 1.96;
    private final double mean;
    private final double stddev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int t) {
        if (n <= 0) throw new java.lang.IllegalArgumentException("n is out of bounds");
        if (t <= 0) throw new java.lang.IllegalArgumentException("t is out of bounds");
        trials = t;
        size = n;
        thresholds = new double[t];
        for (int i = 0; i < t; i++) {
            thresholds[i] = performMonteCarlo();
        }
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);;
    }

    private double performMonteCarlo() {
        Percolation percolation = new Percolation(size);
        int count = 0;
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, size + 1);
            int col = StdRandom.uniform(1, size + 1);
            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
                count++;
            }
        }
        return count / Math.pow(size, 2);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (Z_SCORE * stddev / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (Z_SCORE * stddev / Math.sqrt(trials));
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean " + stats.mean());
        StdOut.println("stddev: " + stats.stddev());
        StdOut.println("95% confidence interval: [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
