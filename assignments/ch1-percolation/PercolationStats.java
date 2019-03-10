import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] thresholds;
    private final int trials;
    private static final double Z_SCORE = 1.96;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        thresholds = new double[0];
        monteCarlo(n, trials);
    }

    private void monteCarlo(int n, int trials) {
        Percolation percolation = new Percolation(n);
        for(int i=0; i<trials; i++) {
            do {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if(!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            } while((!percolation.percolates()));
            double threshold = percolation.numberOfOpenSites() / (double) (n * n);
            double[] temp = new double[thresholds.length +1];
            for(int j=0; j<thresholds.length; j++) {
                temp[j] = thresholds[j];
            }
            temp[temp.length-1] = threshold;
            thresholds = temp;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        return mean - (Z_SCORE * stddev/Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        return mean + (Z_SCORE * stddev/Math.sqrt(trials));
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
       /* StdOut.println("\nEnter size n of the n * n grid:");
        int n = StdIn.readInt();
        StdOut.println("\nEnter number of trials to run:");
        int trials = StdIn.readInt();*/
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean " + stats.mean());
        StdOut.println("stddev: " + stats.stddev());
        StdOut.println("95% confidence interval: [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

    }
}
