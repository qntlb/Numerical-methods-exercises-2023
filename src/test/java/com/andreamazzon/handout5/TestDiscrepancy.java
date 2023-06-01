package com.andreamazzon.handout5;

import java.util.Arrays;

/**
 * This class has a main method where we compute the discrepancy and star
 * discrepancy of two sets and where we print the star discrepancy of Van der
 * Corput sequences of increasing length n, together with log(n)/n.
 *
 * @author Andrea Mazzon
 *
 */
public class TestDiscrepancy {

	public static void main(String[] args) throws Exception {

		// first we compute discrepancy and star discrepancy of some sets..

		double[] firstSet = { 0.125, 0.25, 0.5, 0.75 };
		System.out.println("The discrepancy of the set " + Arrays.toString(firstSet) + " is "
				+ DiscrepancyOneDimension.getDiscrepancy(firstSet));
		System.out.println("The star discrepancy of the set " + Arrays.toString(firstSet) + " is "
				+ DiscrepancyOneDimension.getStarDiscrepancy(firstSet));

		System.out.println();

		double[] secondSet = { 0.25, 0.5, 5.0 / 8, 0.75 };
		System.out.println("The discrepancy of the set " + Arrays.toString(secondSet) + " is "
				+ DiscrepancyOneDimension.getDiscrepancy(secondSet));
		System.out.println("The star discrepancy of the set " + Arrays.toString(secondSet) + " is "
				+ DiscrepancyOneDimension.getStarDiscrepancy(secondSet));

		System.out.println();

		double[] thirdSet = { 0.2, 0.21, 0.22, 0.23, 0.24, 0.65, 0.76, 0.87 };
		System.out.println("The discrepancy of the set " + Arrays.toString(thirdSet) + " is "
				+ DiscrepancyOneDimension.getDiscrepancy(thirdSet));
		System.out.println("The star discrepancy of the set " + Arrays.toString(thirdSet) + " is "
				+ DiscrepancyOneDimension.getStarDiscrepancy(thirdSet));
		System.out.println();

		// in the computation of the discrepancy we are not checking [0.27, 1]! 
		double[] fourthSet = { 0.1, 0.27 };
		System.out.println("The discrepancy of the set " + Arrays.toString(fourthSet) + " is "
				+ DiscrepancyOneDimension.getDiscrepancy(fourthSet));
		System.out.println("The star discrepancy of the set " + Arrays.toString(fourthSet) + " is "
				+ DiscrepancyOneDimension.getStarDiscrepancy(fourthSet));
	}

}