package com.andreamazzon.handout1;

/**
 * This class deals with forward and backward harmonic sums: it can be seen
 * that, for large enough numbers, the two diverge if single precision is used.
 * The point is that most of the significance of a small float number is lost
 * when it is added to a large number: when the ratio of the larger number to
 * the smaller number becomes sufficiently large, the value of the sum will be
 * identical to the value of the larger number.
 * This is the issue it happens when the forward computation is performed.
 * You may also note that methods are NOT identified by the returning type:
 * we have two methods that return the value of the forward harmonic sum, but
 * one returns it as a float, the other as a double: they have to be named
 * differently, otherwise we get a compile time error.
 */

public class HarmonicSum {

	/**
	 * It computes the forward harmonic sum with double precision
	 *
	 * @param n order of the sum
	 * @return the value of the sum, double
	 */
	static double doubleHarmonicSumForward(int n) {
		double sum = 0; // double
		for (int i = 1; i <= n; i++) {
			sum += (1 / (double) i);
		}
		return sum;
	}

	/**
	 * It computes the backward harmonic sum with double precision
	 *
	 * @param n order of the sum
	 * @return the value of the sum, double
	 */
	static double doubleHarmonicSumBackward(int n, boolean isFloat) {
		double sum = 0; // double
		
		for (int i = n; i > 0; i--) {
			sum += (1.0 / i);
		}
		return sum;
	}

	/**
	 * It computes the forward harmonic sum with single precision
	 *
	 * @param n order of the sum
	 * @return the value of the sum, float
	 */
	static float floatHarmonicSumForward(int n) {
		float sum = 0; // float
		for (int i = 1; i < n + 1; i++) {
			sum += (1.0 / i);
		}
		return sum;
	}

	/**
	 * It computes the backward harmonic sum with single precision
	 *
	 * @param n order of the sum
	 * @return the value of the sum, float
	 */
	static float floatHarmonicSumBackward(int n) {
		float sum = 0; // float
		for (int i = n; i > 0; i--) {
			sum += (1.0 / i);
		}
		return sum;
	}

	public static void main(String[] args) {
		
		int harmonicSumOrder = 10000000;

		System.out.println("The forward harmonic sum of order " + harmonicSumOrder + " in single precision is "
				+ floatHarmonicSumForward(harmonicSumOrder));
		System.out.println("The backward harmonic sum of order " + harmonicSumOrder + " in single precision is "
				+ floatHarmonicSumBackward(harmonicSumOrder));

		System.out.println("The forward harmonic sum of order " + harmonicSumOrder + " in double precision is "
				+ doubleHarmonicSumForward(harmonicSumOrder));
		System.out.println("The backward harmonic sum of order " + harmonicSumOrder + " in double precision is "
				+ doubleHarmonicSumBackward(harmonicSumOrder, true));
	}
}