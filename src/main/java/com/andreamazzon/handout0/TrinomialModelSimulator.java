package com.andreamazzon.handout0;

import com.andreamazzon.session3.encapsulation.lazyinitialization.LinearCongruentialGenerator;

/**
 * This class is used in order to simulate some paths of a trinomial model: discrete model for
 * a stochastic process S, such that every time i we have
 * S(i+1)=S(i)*M(i),
 * where M(i)=u>1 with probability p_1, M(i) = 1 with probability p_2, M(i)=d<1 with probability
 * 1-p_1-p_2.
 * This is done under a risk neutral measure: it can be seen that the market is not complete, i.e.,
 * infinitely many risk neutral measures exist, with condition 
 * p_1 = (1 + r - d- p_2 * (1 - d)) / (u - d) 
 * where r is the risk free interest rate.
 * This serves as an example of composition combined to inheritance: the class has an object of type
 * LinearCongruentialGenerator, which is used to generate the realizations M(i), and inherits from the
 * abstract class DiscreteStochasticProcessSimulator, where the implementation of methods giving all
 * the realizations of the process together with some statistics are given.
 *
 * @author Andrea Mazzon
 *
 */
//note that this class implicitly implements StochasticProcessSimulatorInterface
public class TrinomialModelSimulator extends DiscreteStochasticProcessSimulator {

	// these fields are specific to this process, i.e., to this class
	private double increaseIfUp; // this is u>1
	private double decreaseIfDown; // this is d<1
	
	/*
	 * It is (1 + interestRate - decreaseIfDown - probabilityStayTheSame * (1 - decreaseIfDown))
	 * /(increaseIfUp - decreaseIfDown)
	 */
	private double riskNeutralProbabilityUp;
	
	private double probabilityStayTheSame; // probability that S(i+1) = S(i). Given in the constructor!
	private double[][] movements; // the matrix of realizations of M

	// imported! composition: we use this object to simulate the values of M
	private LinearCongruentialGenerator randomGenerator;

	public TrinomialModelSimulator(double initialValue, double increaseIfUp, double decreaseIfDown,
			double interestRate, double probabilityStayTheSame, int seed, int lastTime,
			int numberOfSimulations) {
		/*
		 * Call to the super constructor to set the values of the fields which describe the simulation
		 * of a stochastic process
		 */
		super(initialValue, numberOfSimulations, lastTime);
		this.increaseIfUp = increaseIfUp;
		this.decreaseIfDown = decreaseIfDown;
		this.probabilityStayTheSame = probabilityStayTheSame;
		riskNeutralProbabilityUp = (1 + interestRate - decreaseIfDown - probabilityStayTheSame * (1 - decreaseIfDown))
				/ (increaseIfUp - decreaseIfDown);
		randomGenerator = new LinearCongruentialGenerator(lastTime * numberOfSimulations, seed);
	}

	
	// Overloaded constructor: if not specified, the interest rate is zero. Note the use of this
	public TrinomialModelSimulator(double initialValue, double increaseIfUp, double decreaseIfDown,
			double probabilityStayTheSame, int seed, int lastTime, int numberOfSimulations) {
		this(initialValue, increaseIfUp, decreaseIfDown, 0, probabilityStayTheSame, seed, lastTime,
				numberOfSimulations);
	}

	/*
	 * Overloaded constructor: if not specified, the interest rate is zero and the seed is 1897.
	 * Note the use of this
	 */
	public TrinomialModelSimulator(double initialValue, double increaseIfUp, double decreaseIfDown,
			double probabilityStayTheSame, int lastTime, int numberOfSimulations) {
		this(initialValue, increaseIfUp, decreaseIfDown, 0, probabilityStayTheSame, 1897, lastTime,
				numberOfSimulations);
	}

	/*
	 * We have to convert the probability, which is a double, into a condition that can be applied to
	 * random natural numbers: we round the multiplication of the probability to have an upper movement
	 * by the maximum number that can be simulated (which is given by randomGenerator.getModulus() - 1).
	 * Thus, if the simulated number is less that this we will return an up, otherwise a down.
	 */
	private double convertProbabilityUp() {
		// modulus is private, but we have the getter
		return riskNeutralProbabilityUp * (randomGenerator.getModulus() - 1);
	}

	/*
	 * Here we consider the probability to have an up OR stay the same. Same thing as before,
	 * but we multiply riskNeutralProbabilityUp + probabilityStayTheSame
	 */
	private double convertProbabilityNotDown() {
		// modulus is private, but we have the getter
		return (riskNeutralProbabilityUp + probabilityStayTheSame) * (randomGenerator.getModulus() - 1);
	}

	/*
	 * Generation of the process of ups and downs, i.e., M such that S(i+1) = S(i)*M(i).
	 * We have upsAndDowns[i][j]=M(i,omega(j)).
	 * In particular,
	 * upsAndDowns[i][j] = u if randomGenerator.getNextInteger() < thresholdUp,
	 * upsAndDowns[i][j] = 1 if thresholdUp <=randomGenerator.getNextInteger() <= thresholdDown,
	 * upsAndDowns[i][j] = d if randomGenerator.getNextInteger() > thresholdDown.
	 * We ask randomGenerator to generate a sequence of random numbers whose length is equal to
	 * finalTime*numerbOfSimulations.
	 */
	private double[][] generateMovements() {
		int lastTime = getLastTime();
		int numberOfSimulations = getNumberOfSimulations();
		// rows are simulation at given time, columns paths
		movements = new double[lastTime][numberOfSimulations];
		// when the simulated number is smaller than this, we have an up movement
		double thresholdUp = convertProbabilityUp();
		// when the simulated number is bigger than this, we have a down movement
		double thresholdNotDown = convertProbabilityNotDown();
		// double for loop, time and simulations
		for (int timeIndex = 0; timeIndex < getLastTime(); timeIndex++) {
			for (int simulationIndex = 0; simulationIndex < getNumberOfSimulations(); simulationIndex++) {
				double nextRandomNumber = randomGenerator.getNextInteger();
				// the way we convert the probability into a condition on the generated numbers
				if (nextRandomNumber < thresholdUp) {
					movements[timeIndex][simulationIndex] = increaseIfUp;
				} else if (nextRandomNumber > thresholdNotDown) {// note else if!
					movements[timeIndex][simulationIndex] = decreaseIfDown;
				} else {
					movements[timeIndex][simulationIndex] = 1;
				}
			}
		}
		return movements;
	}

	/**
	 * It generates the realizations of the process S, depending on the one of the
	 * process M of ups and downs.
	 */
	@Override // it overrides the abstract method of the base class!
	protected void generateRealizations() {
		// lastTime + 1 rows because the first hosts the initial value
		double[][] realizations = new double[getLastTime() + 1][getNumberOfSimulations()];
		generateMovements();// will be called only once
		// a first for loop the fill the first row
		for (int simulationIndex = 0; simulationIndex < getNumberOfSimulations(); simulationIndex++) {
			realizations[0][simulationIndex] = getInitialValue();
		}
		// double for loop for the realizations. We start from time 1
		for (int timeIndex = 1; timeIndex <= getLastTime(); timeIndex++) {
			for (int simulationIndex = 0; simulationIndex < getNumberOfSimulations(); simulationIndex++) {
				realizations[timeIndex][simulationIndex] = realizations[timeIndex - 1][simulationIndex]
						* movements[timeIndex - 1][simulationIndex];
			}
		}
		// then we can set the realizations: setRealizations is indeed protected
		setRealizations(realizations);
	}
}
