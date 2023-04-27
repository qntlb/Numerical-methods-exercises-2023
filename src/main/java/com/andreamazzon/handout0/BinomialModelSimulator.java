package com.andreamazzon.handout0;

import com.andreamazzon.session3.encapsulation.lazyinitialization.LinearCongruentialGenerator;

/**
 * This class is used in order to simulate some paths of a binomial model: a discrete model for
 * a stochastic process S, such that every time i we have
 * S(i+1)=S(i)*M(i),
 * where M(i)=u>1 with probability p and M(i)=d<1 with probability 1-p.
 * This is done under the risk neutral measure: it can be seen that it must hold
 * p = (1 + r - d)/(u - d),
 * where r is the risk free interest rate. This serves as an example of composition combined to
 * inheritance: the class has an object of type LinearCongruentialGenerator, which is used to
 * generate the realizations M(i), and inherits from the abstract class DiscreteStochasticProcessSimulator,
 * where the implementation of methods giving all the realizations of the process together with
 * some statistics are given.
 *
 * @author Andrea Mazzon
 *
 */
//note that this class implicitly implements StochasticProcessSimulatorInterface
public class BinomialModelSimulator extends DiscreteStochasticProcessSimulator {

	// these fields are specific to this process, i.e., to this class
	private double increaseIfUp; // this is u>r+1
	private double decreaseIfDown; // this is d<1
	// equal to (1 + interestRate - decreaseIfDown)/(increaseIfUp - decreaseIfDown)
	private double riskNeutralProbabilityUp;
	private double[][] upsAndDowns; // the matrix of realizations of M

	// imported! composition: we use this object to simulate the values of M
	private LinearCongruentialGenerator randomGenerator;

	public BinomialModelSimulator(double initialValue, double increaseIfUp, double decreaseIfDown,
			double interestRate, int seed, int lastTime, int numberOfSimulations) {
		/*
		 * Call to the super constructor to set the values of the fields which describe the simulation
		 * of a stochastic process
		 */
		super(initialValue, numberOfSimulations, lastTime);
		this.increaseIfUp = increaseIfUp;
		this.decreaseIfDown = decreaseIfDown;
		riskNeutralProbabilityUp = (1 + interestRate - decreaseIfDown) / (increaseIfUp - decreaseIfDown);
		randomGenerator = new LinearCongruentialGenerator(lastTime * numberOfSimulations, seed);
	}

	
	// Overloaded constructor: if not specified, the interest rate is zero. Note the use of this
	public BinomialModelSimulator(double initialValue, double increaseIfUp, double decreaseIfDown, int seed,
			int lastTime, int numberOfSimulations) {
		this(initialValue, increaseIfUp, decreaseIfDown, 0, seed, lastTime, numberOfSimulations);
	}

	
	// Overloaded constructor: if not specified, the interest rate is zero and the seed is 1897. Note the use of this
	public BinomialModelSimulator(double initialValue, double increaseIfUp, double decreaseIfDown, int lastTime,
			int numberOfSimulations) {
		this(initialValue, increaseIfUp, decreaseIfDown, 0, 1897, lastTime, numberOfSimulations);
	}

	/*
	 * We have to convert the probability, which is a double, into a condition that can be applied to
	 * random natural numbers: we round the multiplication of the probability to have an upper movement
	 * by the maximum number that can be simulated (which is given by randomGenerator.getModulus() - 1).
	 * Thus, if the simulated number is less that this we will return an up, otherwise a down.
	 */
	private double convert() {
		// modulus is private, but we have the getter
		return riskNeutralProbabilityUp * (randomGenerator.getModulus() - 1);
	}

	/*
	 * Generation of the process of ups and downs, i.e., M such that S(i+1)=S(i)*M(i).
	 * The realizations of the process M are stored in the matrix upsAndDowns.
	 * In particular, upsAndDowns[i][j]=M(i,omega(j))
	 */
	private double[][] generateUpsAndDowns() {
		int lastTime = getLastTime();
		int numberOfSimulations = getNumberOfSimulations();
		// rows are simulation at given time, columns paths
		upsAndDowns = new double[lastTime][numberOfSimulations];
		double threshold = convert();// when the simulated number is less than this, we have up
		// double for loop, time and simulations
		for (int timeIndex = 0; timeIndex < lastTime; timeIndex++) {
			for (int simulationIndex = 0; simulationIndex < numberOfSimulations; simulationIndex++) {
				long randomNumber = randomGenerator.getNextInteger();
				// the way we convert the probability into a condition on the generated numbers
				if (randomNumber < threshold) {
					upsAndDowns[timeIndex][simulationIndex] = increaseIfUp;
				} else {
					upsAndDowns[timeIndex][simulationIndex] = decreaseIfDown;
				}
			}
		}
		return upsAndDowns;
	}

	/**
	 * It generates the realizations of the process S, depending on the one of the
	 * process M of ups and downs.
	 */
	/*
	 * Note that the implementation is the same as in TrinomialModel. One could then
	 * define another abstract class MultinomialModelSimulator, extending
	 * DiscreteStochasticProcessSimulator, where the implementation of this method is given.
	 * Then TrinomialModelGenerator and BinomialModelGenerator inherit from that.
	 * Possible exercise!
	 */
	@Override // it overrides the abstract method of the base class!
	protected void generateRealizations() {
		// lastTime + 1 rows because the first hosts the initial value
		double[][] realizations = new double[getLastTime() + 1][getNumberOfSimulations()];
		generateUpsAndDowns();// will be called only once
		// a first for loop the fill the first row
		for (int simulationIndex = 0; simulationIndex < getNumberOfSimulations(); simulationIndex++) {
			realizations[0][simulationIndex] = getInitialValue();
		}
		// double for loop for the realizations. We start from time 1
		for (int timeIndex = 1; timeIndex <= getLastTime(); timeIndex++) {
			for (int simulationIndex = 0; simulationIndex < getNumberOfSimulations(); simulationIndex++) {
				realizations[timeIndex][simulationIndex] = realizations[timeIndex - 1][simulationIndex]
						* upsAndDowns[timeIndex - 1][simulationIndex];
			}
		}
		// then we can set the realizations: setRealizations is indeed protected
		setRealizations(realizations);
	}
}