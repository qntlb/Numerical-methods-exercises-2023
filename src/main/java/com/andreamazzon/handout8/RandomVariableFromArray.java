package com.andreamazzon.handout8;

import com.andreamazzon.usefulmethodsmatricesandvectors.UsefulMethodsMatricesAndVectors;

/**
 * This class represents a random variable, seen as a bunch of possible realizations.
 * Not to be confused with what we have seen in RandomVariable and RandomVariableInterface:
 * in that case, an object represented a specific distribution, with the possibility of getting
 * the density, the cdf, or to generate realizations of a random variable with such a distribution.
 * In this case, instead, we take a pure Monte-Carlo approach and we don't even care about the
 * specific distribution of the random variable: indeed, an object of this class will represent
 * a sample of realizations. The realizations are stored as an array of doubles.
 * In this way, we can compute the average and the variance of the sample, as well as performing
 * operations between different random variables (by performing those operations between the vector
 * of doubles representing the realizations).
 *
 *
 * @author Andrea Mazzon
 *
 */
public class RandomVariableFromArray {

	/*
	 * It stores the realizations of our random variable: this is the core of our
	 * class, as it is used in basically all the methods.
	 */
	private double[] realizations;

	/**
	 * It creates an object representing a random variable whose realizations are
	 * given by the double[] array passed as an argument
	 *
	 * @param realizations, the array storing the realizations of the random variable                
	 */
	public RandomVariableFromArray(double[] realizations) {
		this.realizations = realizations;
	}

	/**
	 * It returns the realizations of the random variable represented by the object
	 * calling this method
	 *
	 * @return the realizations of the random variable, as an one-dimensional array of doubles
	 */
	public double[] getRealizations() {
		return realizations;
	}

	/**
	 * It returns a specific realization for a given simulation index
	 *
	 * @param realizationIndex, the int identifying the specific simulated realization
	 *
	 * @return the realization
	 */
	public double getSpecificRealization(int realizationIndex) {
		return realizations[realizationIndex];
	}

	/**
	 * It returns the average of the realizations of the random variable, i.e., of
	 * the sample of the realizations represented by the object calling this method
	 *
	 * @return the average of the values of the one-dimensional array of doubles
	 *         representing the realizations of the random variable
	 */
	public double getAverage() {
		return UsefulMethodsMatricesAndVectors.getAverage(realizations);
	}

	/**
	 * It returns the standard deviation of the realizations of the random variable,
	 * i.e., of the sample of the realizations represented by the object calling
	 * this method
	 *
	 * @return the standard deviation of the values of the one-dimensional array of
	 *         doubles representing the realizations of the random variable
	 */
	public double getStandardDeviation() {
		return UsefulMethodsMatricesAndVectors.getStandardDeviation(realizations);
	}

	/**
	 * It returns the omega-wise sum of the random variable calling the method with
	 * the one given as an argument
	 *
	 * @param randomVariable, the random variable we want to sum to the one calling
	 *                        the method
	 *
	 * @return the sum of the two random variables, omega-wise, i.e, realization per
	 *         realization. The arrays representing the two random variables must
	 *         have same length, otherwise an exception is thrown
	 */
	public RandomVariableFromArray add(RandomVariableFromArray randomVariable) {
		/*
		 * Here you see that our object is really a wrapper for the array storing the
		 * realizations. Indeed, everything happens at the inner level, performing the
		 * operations with respect to the array of realizations
		 */
		double[] realizationsOtherRandomVariable = randomVariable.getRealizations();
		/*
		 * If the length of the two arrays is not the same, an exception is thrown by
		 * the method sumVectors
		 */
		double[] realizationsSum = UsefulMethodsMatricesAndVectors.sumVectors(realizations,
				realizationsOtherRandomVariable);
		/*
		 * Basically the scheme is:
		 * 1. Get the realizations wrapped by the two objects.
		 * 2. Perform the operation
		 * 3. Wrap again the result.
		 */
		return new RandomVariableFromArray(realizationsSum);
	}

	/**
	 * It returns the sum of the random variable calling the method with a double
	 * value. That is, all the realizations of the random variable are summed with
	 * the fixed double value
	 *
	 * @param value, the double we want to sum to the random variable calling the
	 *               method
	 *
	 * @return the sum of the random variable with the double value. This is a
	 *         random variable whose realizations are the sum of the ones of the
	 *         random variable and the double value
	 */
	public RandomVariableFromArray add(double value) {

		double[] realizationsSum = UsefulMethodsMatricesAndVectors.sumVectorAndDouble(realizations, value);
		/*
		 * Almost as above, now the scheme is:
		 * 1. Get the realizations wrapped by the object. 
		 * 2. Perform the operation with teh double
		 * 3. Wrap again the result.
		 */
		return new RandomVariableFromArray(realizationsSum);
	}

	/**
	 * It returns the omega-wise product of the random variable calling the method
	 * with the one given as an argument
	 *
	 * @param randomVariable, the random variable we want to multiply to the one
	 *                        calling the method
	 *
	 * @return the product of the two random variables, omega-wise, i.e, realization
	 *         per realization. The arrays representing the two random variables
	 *         must have same length, otherwise an exception is thrown
	 */
	public RandomVariableFromArray mult(RandomVariableFromArray randomVariable) {
		double[] realizationsOtherRandomVariable = randomVariable.getRealizations();
		/*
		 * If the length of the two arrays is not the same, an exception is thrown by
		 * the method sumVectors
		 */
		double[] realizationsProduct = UsefulMethodsMatricesAndVectors.multVectors(realizations,
				realizationsOtherRandomVariable);
		return new RandomVariableFromArray(realizationsProduct);
	}

	/**
	 * It returns the product of the random variable calling the method with a
	 * double value. That is, all the realizations of the random variable are
	 * multiplied with the fixed double value
	 *
	 * @param value, the double we want to multiply to the random variable calling
	 *               the method
	 *
	 * @return the product of the random variable with the double value. This is a
	 *         random variable whose realizations are the product of the ones of the
	 *         random variable and the double value
	 */
	public RandomVariableFromArray mult(double value) {

		double[] realizationsProduct = UsefulMethodsMatricesAndVectors.prodVectorWithDouble(realizations, value);

		return new RandomVariableFromArray(realizationsProduct);
	}

	/**
	 * It returns the omega-wise difference of the random variable calling the
	 * method and the one given as an argument
	 *
	 * @param randomVariable, the random variable we want to subtract from the one
	 *                        calling the method
	 *
	 * @return the difference of the two random variable, omega-wise, i.e,
	 *         realization per realization. The arrays representing the two random
	 *         variables must have same length, otherwise an exception is thrown
	 */
	public RandomVariableFromArray sub(RandomVariableFromArray randomVariable) {
		return add(randomVariable.mult(-1));
	}

	/**
	 * It returns the difference of the random variable calling the method and a
	 * double value. That is, the double value is subtracted to all the realizations
	 * of the random variable.
	 *
	 * @param value, the double we want to subtract to the random variable calling
	 *               the method
	 *
	 * @return the difference of the random variable with the double value. This is
	 *         a random variable whose realizations are the sum of the ones of the
	 *         random variable and the double value
	 */
	public RandomVariableFromArray sub(double value) {
		return add(-value);
	}

	/**
	 * It returns the omega-wise ratio of the random variable calling the method
	 * with the one given as an argument
	 *
	 * @param randomVariable, the random variable we want to divide from the one
	 *                        calling the method
	 *
	 * @return the difference of the two random variable, omega-wise, i.e,
	 *         realization per realization. The arrays representing the two random
	 *         variables must have same length, otherwise an exception is thrown
	 */
	public RandomVariableFromArray div(RandomVariableFromArray randomVariable) {
		double[] realizationsOtherRandomVariable = randomVariable.getRealizations();
		/*
		 * If the length of the two arrays is not the same, an exception is thrown by
		 * the method sumVectors
		 */
		double[] realizationsRatio = UsefulMethodsMatricesAndVectors.ratioVectors(realizations,
				realizationsOtherRandomVariable);
		return new RandomVariableFromArray(realizationsRatio);
	}

	/**
	 * It returns the ratio of the random variable calling the method with a double
	 * value. That is, all the realizations of the random variable are divided by
	 * the fixed double value
	 *
	 * @param value, the double by which we want to divide the random variable
	 *
	 * @return the ratio of the random variable with the double value. This is a
	 *         random variable whose realizations are the ratio of the ones of the
	 *         random variable and the double value
	 */
	public RandomVariableFromArray div(double value) {

		double[] realizationsRatio = UsefulMethodsMatricesAndVectors.prodVectorWithDouble(realizations, 1 / value);
		return new RandomVariableFromArray(realizationsRatio);
	}

}
