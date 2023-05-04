package com.andreamazzon.handout1;

/**
 * This class deals with operations concerning double and float numbers: it can
 * be seen that one has to pay attention when checking if two numbers of this
 * type are equal.
 *
 * @author Andrea Mazzon
 *
 */
public class FloatAndDouble {

	/**
	 * Computes the biggest epsilon of the form epsilon = 2^(-n) such that epsilon <= |x-x0|.
	 *
	 * @param x  double
	 * @param x0 double
	 * @return an object of the container class EpsilonAndExponent, where we have
	 *         set the values of the biggest epsilon of the form epsilon = 2^(-n)
	 *         such that epsilon <= |x-x0|, and the correspondent int n.
	 */
	public static EpsilonAndExponent computeBiggestEpsilon(double x, double x0) {
		double absoluteDifference = Math.abs(x0 - x);// float if at least one of x and x0 is a float
//		double epsilon = 1.0;
//		int exponent = 0;
//		// computation of epsilon and of the exponent
//		while (absoluteDifference < epsilon) {// we stop as soon as epsilon <= |x-x0|
//			epsilon /= 2.0; // epsilon=epsilon/2
//			exponent++;
//		}
// 		or:
		/*
		 * Math.getExponent(double d) returns the exponent e used to represent d.
		 * We have diff = (1+c/2^p)2^e, with 0<=c<2^p, so 2^e <= |diff| < 2^(e+1).
		 */
		int negativeExponent = Math.getExponent(absoluteDifference);
		double epsilon = Math.pow(2, negativeExponent);
//		// n = -e
		int exponent = -negativeExponent;

		/*
		 * We create this class because epsilon is a double and exponent int, and we
		 * cannot have an array with different types. Two different ways to do this
		 * could be: 
		 * - upcast exponent to double 
		 * - create and return an ArrayList<Object>, for example:
		 * ArrayList<Object> epsilonAndExponent = new ArrayList<Object>();
		 * epsilonAndExponent.add(epsilon);
		 * epsilonAndExponent.add(exponent);
		 * 
		 */

		EpsilonAndExponent epsilonAndExponent = new EpsilonAndExponent();
		// we set the values of the epsilon and exponent field of the class
		epsilonAndExponent.setEpsilon(epsilon);
		epsilonAndExponent.setExponent(exponent);

		return epsilonAndExponent;
	}

	
	/**
	 * Computes the biggest epsilon of the form epsilon = 2^(-n) such that
	 * |x-x0|<2^(-n).
	 *
	 * @param x  double
	 * @param x0 double
	 * @return an array of doubles containing the value of epsilon and the value of the exponent
	 * 			(upcasted to double)
	 */
	public static double[] computeBiggestEpsilonWithArray(double x, double x0) {
		double absoluteDifference = Math.abs(x0 - x);// float if at least one of x and x0 is a float
		double epsilon = 1.0;
		int exponent = 0;
		// computation of epsilon and of the exponent
		while (absoluteDifference < epsilon) {// we stop as soon as epsilon <= |x-x0|
			epsilon /= 2.0; // epsilon=epsilon/2
			exponent++;
		}
 		//or:
		/*
		 * Math.getExponent(double d) returns the exponent e used to represent d. We
		 * have diff = (1+c/2^p)2^e, with 0<=c<2^p, so 2^e <= |diff| < 2^(e+1).
		 */
//		int negativeExponent = Math.getExponent(absoluteDifference);
//		double epsilon = Math.pow(2, negativeExponent);
//		// n = -e
//		int exponent = -negativeExponent;

		double[] results = {epsilon, exponent};

		return results;
	}

	
	
	public static void main(String[] args) {
		double x0 = 3*0.1; // double
		float xFloat = 0.3f; // float: you have to type f, otherwise it complains
		System.out.println("The statement xFloat=x0 is " + (xFloat == x0));

		EpsilonAndExponent epsilonAndExponentWithFloat = computeBiggestEpsilon(xFloat, x0);

		double epsilonWithFloat = epsilonAndExponentWithFloat.getEpsilon();
		int exponentWithFloat = epsilonAndExponentWithFloat.getExponent();

		System.out.println("The smallest natural number power n such that |xFloat-x0|<=2^(-n) is " + exponentWithFloat
				+ " , for which 2^(-n) equals " + epsilonWithFloat);

		System.out.println();
		double xDouble = 0.3; // now it's double
		System.out.println("The statement xDouble=x0 is " + (xDouble == x0));

		EpsilonAndExponent epsilonAndExponentWithDouble = computeBiggestEpsilon(xDouble, x0);
		double epsilonWithDouble = epsilonAndExponentWithDouble.getEpsilon();
		int exponentWithDouble = epsilonAndExponentWithDouble.getExponent();

		System.out.println("The smallest natural number power n such that |xDouble-x0|>=2^(-n) is " + exponentWithDouble
				+ " , for which 2^(-n) equals " + epsilonWithDouble);
	}

}
