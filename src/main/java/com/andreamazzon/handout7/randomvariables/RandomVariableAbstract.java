package com.andreamazzon.handout7.randomvariables;


import java.util.function.DoubleUnaryOperator;

import com.andreamazzon.usefulmethodsmatricesandvectors.UsefulMethodsMatricesAndVectors;

/**
 * This is an abstract class implementing the interface RandomVariableInterface.
 * You can see that the methods whose implementation does not directly depend on
 * the specific type of the random variable are implemented here.
 *
 * @author Andrea Mazzon
 *
 */
public abstract class RandomVariableAbstract implements RandomVariableInterface {

	// it stores independent realizations of the random variable
	private double[] randomVariableRealizations;
	
	// it stores independent realizations of a function of the random variable
	private double[] randomVariableRealizationsFunction;
	
	@Override
	public double generate() {
		/*
		 * Generalized inversion of the distribution function: here we use the fact
		 * (see also Lemma 49 of the script) that X := F^(-1)(U) with U uniformly distributed in
		 * (0,1) and F^(-1) defined as F^(-1)(y) := inf{x|F(x) >= y} has cumulative
		 * distribution function F.  F^(-1) is here the quantile function of the random
		 * variable. The implementation of getQuantileFunction(double x) will be given
		 * in the classes extending this abstract one, since of course it depends on the
		 * specific distribution.
		 */
		double generationOfUniformRandomVariable = Math.random();
		return getQuantileFunction(generationOfUniformRandomVariable);// X_i
	}

	/*
	 * This method initializes randomVariableRealizations to be a one-dimensional
	 * array of the given length n, and it fills it by calling generate() n times.
	 * It is used to compute the mean and the standard deviation of a sample of
	 * independent realizations of the random variable.
	 */
	private void generateValues(int n) {
		randomVariableRealizations = new double[n];
		for (int i = 0; i < n; i++) {
			randomVariableRealizations[i] = generate();// generation of the new realization
		}
	}


	@Override
	public double getSampleMean(int n) {
		/*
		 * The method might be called more than once, obtaining different results. So
		 * every time the method is called we call generateValues(n), that is supposed
		 * to give different values to the one-dimensional array randomVariableRealizations
		 * every time is called.
		 */
		generateValues(n);
		double mean = UsefulMethodsMatricesAndVectors.getAverage(randomVariableRealizations);
		return mean;
	}

	@Override
	public double getSampleStdDeviation(int n) {
		/*
		 * The method might be called more than once, obtaining different results. So
		 * every time the method is called we call generateValues(n), that is supposed
		 * to give different values to the one-dimensional array randomVariableRealizations
		 * every time is called.
		 */
		generateValues(n);
		double standardDeviation = UsefulMethodsMatricesAndVectors.getStandardDeviation(randomVariableRealizations);
		return standardDeviation;
	}
	
	@Override
	public double[] generateBivariate() {
		return new double[] { generate(), generate() };
	}

	@Override
	public double generate(DoubleUnaryOperator function) {
		/*
		 * Same thing as before, but here we have f(X_i) with f given function,
		 * represented by a DoubleUnaryOperator. It is indeed evaluated in a realization
		 * of the random variable
		 */
		return function.applyAsDouble(generate());// f(X_i)
	}

	/*
	 * This method initializes randomVariableRealizationsFunction to be a
	 * one-dimensional array of the given length n, and it fills it by calling
	 * generate(DoubleUnaryOperator function) n times. It is used to compute the
	 * mean and the standard deviation of a sample of independent realizations of
	 * the random variable.
	 */
	private void generateValues(int n, DoubleUnaryOperator function) {
		randomVariableRealizationsFunction = new double[n];
		for (int i = 0; i < n; i++) {
			randomVariableRealizationsFunction[i] = generate(function);// generation of the new realization
		}
	}
	
	
	@Override
	public double getSampleMean(int n, DoubleUnaryOperator function) {
		generateValues(n, function);
		double mean = UsefulMethodsMatricesAndVectors.getAverage(randomVariableRealizationsFunction);
		return mean;
	}

	@Override
	public double getSampleStdDeviation(int n, DoubleUnaryOperator function) {
		generateValues(n, function);
		double standardDeviation = UsefulMethodsMatricesAndVectors
				.getStandardDeviation(randomVariableRealizationsFunction);
		return standardDeviation;
	}
	
	@Override
	public double getSampleMeanWithWeightedMonteCarlo(int n, DoubleUnaryOperator function,
			RandomVariableInterface otherRandomVariable) {
		DoubleUnaryOperator weight = x -> (getDensityFunction(x)// the one of the "original" random variable
				/ otherRandomVariable.getDensityFunction(x));
		DoubleUnaryOperator whatToSample = (x -> function.applyAsDouble(x) * weight.applyAsDouble(x));
		return otherRandomVariable.getSampleMean(n, whatToSample);
	}
}
