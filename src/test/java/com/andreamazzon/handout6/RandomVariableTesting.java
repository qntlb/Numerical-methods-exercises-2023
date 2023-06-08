package com.andreamazzon.handout6;

/**
 * This class tests some methods of classes NormalRandomVariable and
 * ExponentialRandomVariable, which (indirectly) implement RandomVariableInterface.
 *
 * @author Andrea Mazzon
 *
 */
public class RandomVariableTesting {

	public static void main(String[] args) {
		
		double lambda = 0.2;
		int numberOfSimulations = 100000;
		ExponentialRandomVariable exponentialSampler = new ExponentialRandomVariable(lambda);

		System.out.println("Exponential random variable: comparing Empirical mean and std dev with mu and sigma");

		System.out.println();

		System.out.println("Sample mean: " + exponentialSampler.getSampleMean(numberOfSimulations));
		System.out.println("Analytic mean: " + exponentialSampler.getAnalyticMean());

		System.out.println();

		System.out.println("Sample std dev: " + exponentialSampler.getSampleStdDeviation(numberOfSimulations));
		System.out.println("Analytic std dev: " + exponentialSampler.getAnalyticStdDeviation());

		System.out.println();

		System.out.println("_".repeat(80) + "\n");

		double mu = 1;
		double sigma = 2.0;
		NormalRandomVariable normalSampler = new NormalRandomVariable(mu, sigma);

		System.out.println("Normal random variable: comparing Empirical mean and std dev with mu and sigma");

		System.out.println();

		System.out.println("Sample mean: " + normalSampler.getSampleMean(numberOfSimulations));
		System.out.println("Mu: " + normalSampler.getAnalyticMean());

		System.out.println();

		System.out.println("Sample std dev: " + normalSampler.getSampleStdDeviation(numberOfSimulations));
		System.out.println("Sigma: " + normalSampler.getAnalyticStdDeviation());

		System.out.println();
		System.out.println("_".repeat(80) + "\n");
		System.out.println();

		double probability = 0.35;

		double y = normalSampler.getQuantileFunction(probability);
		System.out.println("The quantile of a probability of " + probability + " of a  normal random variable of mean " + mu
				+ " and std deviation " + sigma + " is " + y);

		System.out.println("\n");

		System.out.println("The cumulative distribution  function at " + y + " is "
				+ normalSampler.getCumulativeDistributionFunction(y));

	}
}