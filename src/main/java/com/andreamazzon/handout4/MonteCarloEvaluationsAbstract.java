package com.andreamazzon.handout4;

import com.andreamazzon.usefulmethodsmatricesandvectors.UsefulMethodsMatricesAndVectors;

/**
 * This is an abstract class implementing MonteCarloEvaluationsInterface.
 * It provides the implementation of methods that can be called in order to get the
 * vector of several Monte-Carlo approximations of a given quantity, the average
 * and the standard deviation of the vector, as well as its minimum and maximum
 * value and an histogram of its elements.
 * Note that all the methods are based on the protected double[] field
 * monteCarloComputations, that hosts all the elements of the vector.
 * This vector is initialized and filled by the 
 * protected abstract void method generateMonteCarloComputations(), whose
 * implementation depends on the kind of Monte-Carlo approximation considered.
 *
 * @author Andrea Mazzon
 *
 */
public abstract class MonteCarloEvaluationsAbstract implements MonteCarloEvaluationsInterface {

	/*
	 * These fields are protected because they are used in the sub-classes. Another
	 * solution could be to let them private and create some protected setters.
	 */

	/*
	 * The number of times we perform the Monte-Carlo computations, i.e., the length
	 * of monteCarloComputations
	 */
	protected int numberOfMonteCarloComputations;

	// the number of drawings for the single Monte-Carlo computation
	protected int numberOfDrawingsPerMonteCarloComputation;

	
	// its elements are the different values obtained by the Monte-Carlo computations
	protected double[] monteCarloComputations;

	// it initializes and fills the vector monteCarloComputations
	protected abstract void generateMonteCarloComputations();

	// it will be called by the sub-classes
	public MonteCarloEvaluationsAbstract(int numberOfMonteCarloComputations, int numberOfDrawingsPerMonteCarloComputation) {
		this.numberOfMonteCarloComputations = numberOfMonteCarloComputations;
		this.numberOfDrawingsPerMonteCarloComputation = numberOfDrawingsPerMonteCarloComputation;
	}

	// The Javadoc documentation is already given in the interface
	@Override
	public double[] getComputations() {
		/*
		 * Note that this method is called by all the other methods of the interface.
		 * Anyway, the array monteCarloComputations is filled only once, so the values
		 * will always be the same.
		 */
		if (monteCarloComputations == null) {// generated only once!
			generateMonteCarloComputations();
		}
		return monteCarloComputations.clone();
	}

	@Override
	public double getAverageComputations() {
		
		// we get the vector of computations and pass it to UsefulMethodsMatricesVectors.getAverage
		double average = UsefulMethodsMatricesAndVectors.getAverage(getComputations());
		return average;
	}

	@Override
	public double getStandardDeviationComputations() {
		
		// we get the vector of computations and pass it to UsefulMethodsMatricesVectors.getStandardDeviation
		double standardDeviation = UsefulMethodsMatricesAndVectors.getStandardDeviation(getComputations());
		return standardDeviation;
	}

	@Override
	public double[] getMinAndMaxComputations() {
		/*
		 * we get the vector of computations and pass it to
		 * UsefulMethodsMatricesVectors.getMin and UsefulMethodsMatricesVectors.getMax.
		 * Here we create the array computations not to call the method
		 * getComputations() twice
		 */
		double[] computations = getComputations();
		double min = UsefulMethodsMatricesAndVectors.getMin(computations);
		double max = UsefulMethodsMatricesAndVectors.getMax(computations);
		double[] minAndMax = { min, max };
		return minAndMax;
	}

	@Override
	public int[] getHistogramComputations(double leftPointOfInterval, double rightPointOfInterval, int numberOfBins) {
		
		// we get the vector of computations and pass it to UsefulMethodsMatricesVectors.buildHistogram.
		int[] histogram = UsefulMethodsMatricesAndVectors.buildHistogram(getComputations(), leftPointOfInterval,
				rightPointOfInterval, numberOfBins);
		return histogram;
	}

}
