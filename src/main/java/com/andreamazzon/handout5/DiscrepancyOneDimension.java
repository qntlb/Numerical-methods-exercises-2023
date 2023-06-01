package com.andreamazzon.handout5;

import java.util.Arrays;

/**
 * This class is devoted to the computation of the discrepancy and of the star
 * discrepancy of a set of points in one dimension. The points of the set are
 * given as a one-dimensional array, which is not supposed to be already sorted
 * when passed to the argument list of the methods.
 *
 * @author Andrea Mazzon
 *
 */
public class DiscrepancyOneDimension {

	/**
	 * It computes and return the discrepancy of a set of points in one dimension.
	 * In particular, the discrepancy of the one-dimensional set is computed as
	 * max_{a \in set} (max_{b \in set} max(|{x_i \in [a,b]}|/n - (b-a), (b-a)-|{x_i in (a,b)}|/n)),
	 * where set is the one-dimensional array of the points of the
	 * set whose discrepancy must be computed, n is the length of the array, and x_i
	 * are points of the array. So we can check intervals [set[position],b], where
	 * position runs from 0 (first element of the set) to totalNumberOfPoints - 1
	 * (second last) and b is bigger than set[position]
	 *
	 * @param set, a one-dimensional array giving the points of the set whose
	 *             discrepancy must be computed. It is not supposed to be already
	 *             sorted when passed in the argument list, so it must be sorted at
	 *             the beginning by the Java method Arrays.sort.
	 */
	public static double getDiscrepancy(double[] set) {
		Arrays.sort(set); // Java method to sort the set
		int totalNumberOfPoints = set.length;
		/*
		 * We first get the star discrepancy, i.e., we check intervals [0,b], where b
		 * varies in the set of points.
		 */
		double discrepancy = getStarDiscrepancy(set);
		/*
		 * We now check open and closed intervals from set[position] to b, where
		 * position runs from 0 (first element of the set) to totalNumberOfPoints - 2
		 * (second last, we don't consider the last element in the set for the reason
		 * you can see in the "Remark about exercise 2.pdf") and b is bigger than set[position]
		 */
		for (int position = 0; position < totalNumberOfPoints - 1; position++) {
			/*
			 * Maximum value of the absolute value that appears in the definition of
			 * discrepancy, given by intervals whose left end is set[position]
			 */
			double newCandidate = getMaximumValue(set, position);
			
			//If this new value is higher than the current maximum, we update the current maximum
			discrepancy = Math.max(discrepancy, newCandidate);
		}
		return discrepancy;
	}

	/*
	 * Returns
	 * max_{b \in set}max(|{x_i in [set[pos],b]}|/n-(b-set[pos]), (b-set[pos])-|{x_i \in (set[pos],b)}|/n),
	 * where set is the (now sorted!) one-dimensional array of the points of the set whose discrepancy must be
	 * computed, n is the length of set, and x_i are points of the set.
	 */
	private static double getMaximumValue(double[] set, int position) {
		int totalNumberOfPoints = set.length;
		/*
		 * They will get incremented by one every time we make b run in the set. This is
		 * why having a sorted set in convenient.
		 */
		double numberOfPointsInTheOpenIntervals = 0;
		double numberOfPointsInTheClosedIntervals = 2;
		double maxValue = 0;
		// first we check sets not involving b=1 (unless x_n=1)
		for (int index = 1; index <= totalNumberOfPoints - position - 1; index++) {
			double lengthOfNewInterval = set[position + index] - set[position];
			double newCandidate = Math.max(lengthOfNewInterval - numberOfPointsInTheOpenIntervals / totalNumberOfPoints,
					numberOfPointsInTheClosedIntervals / totalNumberOfPoints - lengthOfNewInterval);
			// we update the maximum
			maxValue = Math.max(maxValue, newCandidate);
			numberOfPointsInTheOpenIntervals++;
			numberOfPointsInTheClosedIntervals++;
		}
		// now we check the set involving b=1 (if x_n != 1, otherwise we have already checked it)
		if (set[totalNumberOfPoints - 1] != 1) {
			double lengthOfNewInterval = 1 - set[position];
			/*
			 * this is the only one that can increase the discrepancy: for closed sets, you
			 * have same number of points with bigger length of the interval
			 */
			double newCandidate = lengthOfNewInterval - numberOfPointsInTheOpenIntervals / totalNumberOfPoints;
			maxValue = Math.max(maxValue, newCandidate);
		}
		return maxValue;
	}

	/**
	 * It computes and returns the star discrepancy of a set of points in one
	 * dimension. The star discrepancy of the set of points set is computed as
	 * max_{b \in set} max (b - |{x_i \in (0,b)}|/n), |{x_i \in [0,b]}|/n -b ),
	 * where set is the one-dimensional array of the points of the set whose
	 * discrepancy must be computed, n is the length of the array, and x_i are
	 * points of the array.
	 *
	 * @param set, a one-dimensional array giving the points of the set whose
	 *             discrepancy must be computed. It is not supposed to be already
	 *             sorted when passed in the argument list, so it must be sorted at
	 *             the beginning by the Java method Arrays.sort.
	 */
	public static double getStarDiscrepancy(double[] set) {
		int totalNumberOfPoints = set.length;
		Arrays.sort(set); // Java method to sort the set
		double starDiscrepancy = 0;
		/*
		 * If the first point set[0] of set is not zero, you simply start by checking
		 * the intervals [0, set[0]] and [0, set[0]), where the number of points is the
		 * closed set is 1. If set[0]=0, anyway, 0 is already part of the set, so you start
		 * check from the interval [set[0], set[1]], which has two points.
		 */
		double numberOfPointsInTheClosedIntervals = (set[0] != 0) ? 1 : 2;
		
		/*
		 * the first position we consider: if a=0, 0 is the first element of the array, so 
		 * we start checking from the second one.  
		 */
		int firstPosition  = (set[0] != 0) ? 0 : 1;
		
		for (int index = firstPosition; index < totalNumberOfPoints; index++) {
			double newCandidate = Math.max(set[index] - (numberOfPointsInTheClosedIntervals - 1) / totalNumberOfPoints,
					numberOfPointsInTheClosedIntervals / totalNumberOfPoints - set[index]);
			// we update the maximum
			starDiscrepancy = Math.max(starDiscrepancy, newCandidate);
			numberOfPointsInTheClosedIntervals++;
		}
		// note: no need to check the point 1: it gives value 0
		return starDiscrepancy;
	}
}
