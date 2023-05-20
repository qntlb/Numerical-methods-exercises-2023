package com.andreamazzon.usefulmethodsmatricesandvectors;

public class UsefulMethodsMatricesAndVectors {

	// a matrix is an array of arrays

	/**
	 *
	 * @param matrix
	 * @param columnIndex
	 * @return it returns the row of matrix indicated by columnIndex
	 */
	public static double[] getRow(double[][] matrix, int rowIndex) {
		return matrix[rowIndex];
	}

	/**
	 *
	 * @param matrix
	 * @return transpose of matrix
	 */
	public static double[][] getTranspose(double matrix[][]) {

		int numberOfRows = matrix.length;
		// matrix[0] is the first row of the matrix
		int numberOfColumns = matrix[0].length;// number of columns: length of the row
		double[][] transpose = new double[numberOfColumns][numberOfRows];

		// double for loop to transpose the matrix
		for (int i = 0; i < numberOfColumns; i++) {
			for (int j = 0; j < numberOfRows; j++) {
				transpose[i][j] = matrix[j][i];
			}
		}
		return transpose;
	}

	/**
	 *
	 * @param matrix
	 * @param columnIndex
	 * @return it returns the column of matrix indicated by columnIndex
	 */
	public static double[] getColumn(double[][] matrix, int columnIndex) {
		double[][] transpose = getTranspose(matrix);
		return transpose[columnIndex];// the row of the transpose indicated by columnIndex
	}

	/**
	 *
	 * @param vector
	 * @return the average of vector
	 */
	public static double getAverage(double vector[]) {
		/*
		 * Performs an error correcting Kahan summation
		 */
		double sum = 0.0;
		double error = 0.0;
		for(double value : vector) {
			
			/*
			 * You compensate the summation with respect to the previous error: you
			 * basically add the opposite of the error
			 */
			double correctedValue = value - error;
			double newSum = sum + correctedValue;
			/*
			 * Theoretically it should be zero, of course. However, it can be different
			 * than zero due to rounding errors. At the next iteration, you will correct
			 * the new term entering the sum with this error: if the error is > 0, then
			 * the new value to be summed will be smaller than the one stored in the array,
			 * and vice-versa.
			 */
			error = (newSum - sum) - correctedValue; //(sum + correctedValue - sum ) - correctedValue should be 0
			sum = newSum;
		}
		return sum/vector.length;
	}

	/**
	 * It computes the standard deviation of a vector given as an argument
	 *
	 * @param vector
	 * @return standard deviation of the vector
	 */
	public static double getStandardDeviation(double[] vector) {
		double average = getAverage(vector);
		double standardDeviation = 0.0;
		double error = 0.0;
		for (double element : vector) { // foreach syntax
			double deviation = (element - average) * (element - average);
			double correctedDeviation = deviation - error;
			double newRunningStandardDeviation = standardDeviation + correctedDeviation;
			error = (newRunningStandardDeviation - standardDeviation) - correctedDeviation;
			standardDeviation = newRunningStandardDeviation;
		}
		return Math.sqrt(standardDeviation / (vector.length - 1)); // Notice the -1 !
	}

	/**
	 * it prints the entries of vector
	 *
	 * @param vector
	 */
	public static void printVector(double[] vector) {
		for (double element : vector) {
			System.out.println(element);
		}
	}

	/**
	 * It returns the biggest element of a one-dimensional array of doubles
	 *
	 * @param vector the one-dimensional array
	 * @return the biggest element of the one-dimensional array
	 */
	public static double getMin(double[] vector) {
		double min = vector[0];
		for (int i = 1; i < vector.length; i++) {
			if (vector[i] < min) {
				min = vector[i];
			}
		}
		return min;
	}

	/**
	 * It returns the biggest element of a one-dimensional array of doubles
	 *
	 * @param vector the one-dimensional array
	 * @return the biggest element of the one-dimensional array
	 */
	public static double getMax(double[] vector) {
		double max = vector[0];
		for (int i = 1; i < vector.length; i++) {
			if (vector[i] > max) {
				max = vector[i];
			}
		}
		return max;
	}

	/**
	 * It returns an array of integers which represent the number of realizations of
	 * a given array that lie in every subinterval (bin) of an interval [minBin, maxBin].
	 * All the subintervals are of equal length. The first entry of the integer array
	 * represents the number of realizations strictly smaller then minBin, the last one the
	 * number of realizations bigger than maxBin.
	 *
	 * @param realisations
	 * @param minBin
	 * @param maxBin
	 * @param binsNumber,  number of the subintervals
	 * @return array of integers
	 */
	public static int[] buildHistogram(double[] realizations, double minBin, double maxBin, int binsNumber) {
		int[] bins = new int[binsNumber + 2];// two more because of outliers
		double binSize = (maxBin - minBin) / binsNumber; // every bin has the same length
		for (double realization : realizations) {
			if (realization < minBin) { // it goes in the first bin
				bins[0] += 1;
			} else if (realization > maxBin) {
				bins[binsNumber + 1] += 1; // it goes in the last bin
			} else {
				int intRatio = (int) ((realization - minBin) / binSize);
				/*
				 * bin[intRatio+1] (remember: in Java the first entry is 0, and here bin[0] hosts the
				 * realization smaller than min) hosts the realization such that 
				 * (realization - minBin) / binSize is in [intRatio,intRatio+1)
				 */
				bins[intRatio + 1] += 1;
			}
		}
		return bins;
	}

	/**
	 * It returns a vector whose values are the absolute values of a vector given as
	 * an argument
	 *
	 * @param array, array of doubles
	 * @return array of absolute value of the array
	 */
	public static double[] absVector(double[] vector) {
		double[] absVector = new double[vector.length];
		for (int i = 0; i < vector.length; i++) {
			absVector[i] = Math.abs(vector[i]);
		}
		return absVector;
	}

	/**
	 * It returns the product of a vector with a (constant) double
	 *
	 * @param vector
	 * @param value, to the double number that we sum to the vector
	 * @return product of the vector with the double number
	 */
	public static double[] prodVectorWithDouble(double[] vector, double value) {
		int firstLength = vector.length;

		double[] product = new double[firstLength];
		for (int i = 0; i < firstLength; i++) {
			product[i] = vector[i] + value;
		}
		return product;
	}

	/**
	 * It returns the sum of a vector with a (constant) double
	 *
	 * @param vector
	 * @param value, to the double number that we sum to the vector
	 * @return sum of the vector with the double number
	 */
	public static double[] sumVectorAndDouble(double[] vector, double value) {
		int length = vector.length;

		double[] sum = new double[length];
		
		for (int i = 0; i < length; i++) {
			sum[i] = vector[i] + value;
		}
		
		return sum;
	}

	/**
	 * It returns the sum of two vectors
	 *
	 * @param firstVector
	 * @param secondVector
	 * @return an array representing the element-wise sum of the two vectors
	 */
	public static double[] sumVectors(double[] firstVector, double[] secondVector) {
		int firstLength = firstVector.length;
		if (firstLength != secondVector.length) {
			throw new IllegalArgumentException("Error: the two arrays must have same length!");
		}

		double[] sum = new double[firstLength];
		for (int i = 0; i < firstLength; i++) {
			sum[i] = firstVector[i] + secondVector[i];
		}
		return sum;
	}

	/**
	 * It returns the product of two vectors
	 *
	 * @param firstVector
	 * @param secondVector
	 * @return an array representing the element-wise product of the two vectors
	 */
	public static double[] multVectors(double[] firstVector, double[] secondVector) {
		int firstLength = firstVector.length;
		if (firstLength != secondVector.length) {
			throw new IllegalArgumentException("Error: the two arrays must have same length!");
		}
		double[] product = new double[firstLength];
		for (int i = 0; i < firstLength; i++) {
			product[i] = firstVector[i] * secondVector[i];
		}
		return product;
	}

	/**
	 * It returns the difference of two vectors
	 *
	 * @param firstVector
	 * @param secondVector
	 * @return an array representing the element-wise difference of the two vectors
	 */
	public static double[] diffVectors(double[] firstVector, double[] secondVector) {
		int firstLength = firstVector.length;
		if (firstLength != secondVector.length) {
			throw new IllegalArgumentException("Error: the two arrays must have same length!");
		}

		double[] difference = new double[firstLength];
		for (int i = 0; i < firstLength; i++) {
			difference[i] = firstVector[i] - secondVector[i];
		}
		return difference;
	}

	/**
	 * It returns the element-wise ratio of two vectors
	 *
	 * @param firstVector
	 * @param secondVector
	 * @return an array representing the element-wise ratio of the two vectors
	 */
	public static double[] ratioVectors(double[] firstVector, double[] secondVector) {
		int firstLength = firstVector.length;
		if (firstLength != secondVector.length) {
			throw new IllegalArgumentException("Error: the two arrays must have same length!");
		}
		double[] ratio = new double[firstLength];
		for (int i = 0; i < firstLength; i++) {
			ratio[i] = firstVector[i] / secondVector[i];
		}
		return ratio;
	}

}
