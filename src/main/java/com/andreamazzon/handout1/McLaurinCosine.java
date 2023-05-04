package com.andreamazzon.handout1;

/**
 * This class deals with the Maclaurin series for the cosine in one point. Note
 * that the Maclaurin series are an expansion of a function around zero.
 * However, it can be seen that the approximation works well for every number if
 * opportunely reduced to the domain [0, pi).
 * We can note that when computing the expansion, an overflow can be caused by the
 * factorial of i, if i is an integer. In this case, the factorial may give negative
 * numbers because of the overflow, and this can lead to bugs. The situation gets better
 * letting factorial be a long, because in this case the overflow happens for higher
 * orders. The problem gets "almost" solved letting factorial be a double: in this case,
 * not only it takes much more to get an overflow, but the overflow gives now Infinity,
 * which results in a zero term when we divide the power of x by the factorial.
 * Still, we might have a problem: which one?
 *
 * @author Andrea Mazzon
 *
 */
public class McLaurinCosine {

	/**
	 * It computes the Maclaurin series of the cosine in a point x, supposed to be in [0,pi).
	 *
	 * @param x the point where we want to compute the approximation. Note: it is supposed to be
	 * in [0,pi).
	 * @param n the order of the approximation
	 * @return the value of the approximation
	 */
	public static double macLaurinCosineInZeroPi(double argument, int order) {

		double macLaurinApproximation = 0.0; // initialization of the sum

		int factorial = 1;// you can get negative numbers due to an overflow for large order
		double powerOfX = 1;
		int sign = 1;
		
		for (int i = 0; i < (order + 1); i++) {

			// Maclaurin formula
			macLaurinApproximation += sign * powerOfX / factorial;

//			// not such an elegant way to look at what's happening: debug by Eclipse!
//			System.out.println("Power = " + Math.pow(argument, 2 * i));
//			System.out.println("Factorial = " + factorial);
//			System.out.println();
			
			factorial *= (2 * i + 1) * (2 * i + 2);// factorial*(2(k-1)+1)*(2 * (k-1) + 2)=factorial*(2k-1)*(2*k)

			powerOfX *= argument * argument;
			
			sign *= -1;
		}
		return macLaurinApproximation;
	}

	
	


	
	/**
	 * It computes the Maclaurin series of the cosine in a point x.
	 *
	 * @param angle, the point where we want to compute the approximation
	 * @param order, the order of the approximation
	 * @return the value of the approximation
	 */
	public static double macLaurinCosineSeries(double angle, int order) {

		double reduction = angle % (2 * Math.PI);// the reminder of the ratio
		if (reduction < Math.PI) { // we directly return the value of the series
			return macLaurinCosineInZeroPi(reduction, order);
		}
		// note: we don't need else because return already exit the method
		return -macLaurinCosineInZeroPi(reduction - Math.PI, order);// property of the cosine
	}

	/**
	 * It computes and prints the absolute errors approximating cos(x) with a
	 * Maclaurin series of given order, when order goes from 1 to maxOrder
	 *
	 * @param angle,    the point where we want to compute the approximation
	 * @param maxOrder, the maximum order of the approximations
	 */
	public static void macLaurinCosineSeriesConvergence(double angle, int maxOrder) {

		double analyticValue = Math.cos(angle);
		for (int order = 1; order <= maxOrder; order++) {
			double approximatedValue = macLaurinCosineSeries(angle, order);
			// the first entry will be for order = 1
			double absoluteError = Math.abs(analyticValue - approximatedValue);
			System.out.println("The absolute error for order of approximation " + order + " is " + absoluteError);
		}
	}

	public static void main(String[] args) {

		double angle = 3;
		int maxOrder = 6;
		macLaurinCosineSeriesConvergence(angle, maxOrder);

//		double approximatedValueForMaxOrder = macLaurinCosineSeries(angle, maxOrder);
//
//		System.out.println("_".repeat(79) + "\n");
//
//		System.out.println("The cosine of " + angle + " angle " + "computed with Java is " + Math.cos(angle));
//		System.out.println("The approximated value with order " + maxOrder + " is " + approximatedValueForMaxOrder);

	}

}
