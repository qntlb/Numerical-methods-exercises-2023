package com.andreamazzon.handout3;

/**
 * This class is a container for the data relative to an histogram describing
 * the distribution of values stored in a one-dimensional array
 *
 * @author Andrea Mazzon
 *
 */
public class HistogramData {

	private int[] histogram;

	private double minBin;
	private double maxBin;

	/**
	 * It returns an array of integers which represents the number of values of a
	 * given array that lie in every subinterval (bin) of an interval [minBin,
	 * maxBin]. All the subintervals are of equal length. The first entry of the
	 * integer array represents the number of values strictly smaller then minBin,
	 * the second one the number of values in the first sub-interval, the second
	 * last one the number of values in the last sub-interval, the last one the
	 * number of values bigger than maxBin.
	 *
	 * @return array of integers representing the histogram
	 */
	public int[] getHistogram() {
		return histogram;
	}

	/**
	 * It sets an array of integers which represents the number of values of a given
	 * array that lie in every subinterval (bin) of an interval [minBin, maxBin].
	 * All the subintervals are of equal length. The first entry of the integer
	 * array represents the number of values strictly smaller then minBin, the
	 * second one the number of values in the first sub-interval, the second last
	 * one the number of values in the last sub-interval, the last one the number of
	 * values bigger than maxBin.
	 *
	 * @param array of integers representing the histogram
	 */
	public void setHistogram(int[] histogram) {
		this.histogram = histogram;
	}

	/**
	 * @return the value minBin such that the histogram represents the number of
	 *         values of a given array that lie in every subinterval (bin) of the
	 *         interval [minBin, maxBin]
	 */
	public double getMinBin() {
		return minBin;
	}

	/**
	 *
	 * @param the value minBin such that the histogram represents the number of
	 *            values of a given array that lie in every subinterval (bin) of the
	 *            interval [minBin, maxBin]
	 */
	public void setMinBin(double minBin) {
		this.minBin = minBin;
	}

	/**
	 * @return the value maxBin such that the histogram represents the number of
	 *         values of a given array that lie in every subinterval (bin) of the
	 *         interval [minBin, maxBin]
	 */
	public double getMaxBin() {
		return maxBin;
	}

	/**
	 *
	 * @param the value maxBin such that the histogram represents the number of
	 *            values of a given array that lie in every subinterval (bin) of the
	 *            interval [minBin, maxBin]
	 */
	public void setMaxBin(double maxBin) {
		this.maxBin = maxBin;
	}

}