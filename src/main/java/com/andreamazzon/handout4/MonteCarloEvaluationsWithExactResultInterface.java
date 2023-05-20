package com.andreamazzon.handout4;

/**
 * This is an interface inheriting from MonteCarloEvaluationsInterface. Here we
 * suppose that we already know the exact value of the quantity we approximate
 * by Monte-Carlo. So we experiment on the quality of our approximation by
 * getting the vector of the absolute errors and the average of the vector.
 * 
 * @author Andrea Mazzon
 *
 */
public interface MonteCarloEvaluationsWithExactResultInterface extends MonteCarloEvaluationsInterface {

	/**
	 * It returns the vector of the absolute errors of the Monte-Carlo
	 * approximations
	 *
	 * @return the vector of the absolute errors of the Monte-Carlo approximations
	 *
	 */
	public double[] getAbsoluteErrorsOfComputations();

	/**
	 * It returns the average of the vector of the absolute errors of the
	 * Monte-Carlo approximations
	 *
	 * @return the average of the vector of the absolute errors of the Monte-Carlo
	 *         approximations. The vector is computed as the difference between the
	 *         array of the approximation and an array filled with the exact result.
	 */
	public double getAverageAbsoluteError();

}
