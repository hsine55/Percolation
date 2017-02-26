import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
	private double mean;
	private double standardDeviation;
	private int trials;
	/**
	 * perform trials independent experiments on an n-by-n grid
	 * @param n
	 * @param trials
	 */
   public PercolationStats(int n, int trials) {
	   if (n<=0 || trials<=0) {
		   throw new java.lang.IndexOutOfBoundsException();
	   }
	   this.trials = trials;
	   double[] fractions = new double[trials];
	   for (int i=1; i<=trials; i++) {
		   System.out.println("trial number : " + i);
		   double currentFraction = runPercolation(n);
		   fractions[i-1] = currentFraction;
	   }
	   this.standardDeviation = StdStats.stddev(fractions);
	   this.mean = StdStats.mean(fractions);
	    
   }
   
   private double runPercolation(int n) {
	   Percolation percolationSystem = new Percolation(n);
	   while(!percolationSystem.percolates()) {
		   int siteX = StdRandom.uniform(1, n+1);
		   int siteY = StdRandom.uniform(1, n+1);
		   while(percolationSystem.isOpen(siteX, siteY)) {
			   siteX = StdRandom.uniform(1, n+1);
			   siteY = StdRandom.uniform(1, n+1);
		   }
		   percolationSystem.open(siteX, siteY);
	   }
	   double fractionOfOpenSites = (double)percolationSystem.numberOfOpenSites();
	   return fractionOfOpenSites / (n*n);
   }
   
   /**
    * sample mean of percolation threshold
    * @return
    */
   public double mean() {
	  return mean; 
   }
   
   /**
    * sample standard deviation of percolation threshold
    * @return
    */
   public double stddev() {
	  return standardDeviation; 
   }
   
   /**
    * low  endpoint of 95% confidence interval
    * @return
    */
   public double confidenceLo() {
	   return (mean - ( (Math.sqrt(standardDeviation)*1.96) / Math.sqrt(trials)));
   }
   
   /**
    * high endpoint of 95% confidence interval
    * @return
    */
   public double confidenceHi() {
	   return (mean + ( (Math.sqrt(standardDeviation)*1.96) / Math.sqrt(trials)));
   }


   public static void main(String[] args) {
	   PercolationStats test = new PercolationStats(15, 1000);
	   System.out.println("mean = " + test.mean);
	   System.out.println("standard deviation = " + test.standardDeviation);
	   System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
   }
}
