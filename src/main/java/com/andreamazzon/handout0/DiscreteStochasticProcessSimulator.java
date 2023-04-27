package com.andreamazzon.handout0;

import com.andreamazzon.session4.usefulmatrices.UsefulMethodsMatricesVectors;

/**
 * This is an abstract class for the simulation of a general stochastic process, whose realizations
 * are stored in a two-dimensional array: a row i represents the realizations of the process for a
 * fixed time t(i), whereas a column j represents the path of the process for a state of the world
 * omega(j).
 * In particular, an user might want to get all the realizations as a matrix but also the paths, the
 * simulated value at a given time and the average value at given time of the general stochastic process S.
 * The implementation of these methods uses the generation of the realizations of the process. Since this
 * is based on the specific type of the process, the method taking care of it gets implemented in the derived
 * classes and is therefore abstract. However, all the other methods can be implemented and are actually
 * implemented here: their implementation is general, so it would make no sense to repeat it over the derived
 * classes. Note that this class implements the interface StochasticProcessSimulator: in that interface, one
 * can see all the methods that a class adhering to that interface must implement.
 *
 *
 * @author Andrea Mazzon
 *
 */
public abstract class DiscreteStochasticProcessSimulator implements StochasticProcessSimulatorInterface {
	/*
	 * The matrix of realizations of S. It is private, and will be set by the protected method
	 * setRealizations(double[][] realizations)
	 */
	private double[][] realizations;

	/*
	 * These fields are private, and are got by public setters: they are needed in
	 * the derived classes, but they might also be useful for an user. Note that
	 * these are fields that describe the simulation of a general process, so they
	 * are fields of the parent class.
	 */
	private double initialValue; // S(0)
	private int numberOfSimulations; // number of simulated trajectories of the process
	private int lastTime;

	
	// This method generates the realizations of the process: it is process specific, so it is abstract
	protected abstract void generateRealizations();

	/**
	 * Constructor of the abstract class: not used to directly create an object of
	 * such class (this is not possible) but is called from derived classes. It is
	 * not public, but protected
	 *
	 * @param initialValue:        the initial value of the process
	 * @param numberOfSimulations: the number of simulated trajectories
	 * @param lastTime:            the last time for which the process is simulated
	 */
	protected DiscreteStochasticProcessSimulator(double initialValue, int numberOfSimulations, int lastTime) {
		this.initialValue = initialValue;
		this.numberOfSimulations = numberOfSimulations;
		this.lastTime = lastTime;
	}

	/**
	 * It sets the field realizations to a specific value. It gets be called in the implementation of
	 * generateRealizations() of the derived classes.
	 *
	 * @param realizations
	 */
	protected void setRealizations(double[][] realizations) {
		this.realizations = realizations;
	}

	/**
	 * @return the matrix of the realizations of the process S
	 */
	public double[][] getRealizations() {
		/*
		 * Lazy initialization: realizations are generated only when needed, i.e., when
		 * we want to get them
		 */
		if (realizations == null) {// moreover, we generate them only once
			/*
			 * Call of the abstract method, which is of course specific of every process,
			 * i.e., of every derived class
			 */
			generateRealizations(); //it will call setRealizations, then set the value of realizations
		}
		return realizations;
	}

	/**
	 * @param time, the time i such that the simulated values of S(i) are returned
	 * @return the simulated values of S at time time.
	 */
	@Override
	public double[] getRealizationsAtGivenTime(int time) {
		// realizations generated only when needed: lazy initialization
		if (realizations == null) {
			generateRealizations();// and only once
		}
		return UsefulMethodsMatricesVectors.getRow(realizations, time);
	}

	/**
	 * It prints the vector of realizations at time time.
	 *
	 * @param time, the time i such that the simulated values of S(i) are printed
	 */
	@Override
	public void printRealizationsAtGivenTime(int time) {
		UsefulMethodsMatricesVectors.printVector(getRealizationsAtGivenTime(time));
	}

	/**
	 * @param simulationIndex, the index of the simulation for which the path is
	 *                         returned
	 * @return the path of S for the specific simulation index
	 */
	@Override
	public double[] getPath(int simulationIndex) {
		// realizations generated only when needed: lazy initialization
		if (realizations == null) {
			generateRealizations();// and only once
		}
		return UsefulMethodsMatricesVectors.getColumn(realizations, simulationIndex);
	}

	/**
	 * It prints the path of S for the specific simulation index
	 *
	 * @param simulationIndex, the index of the simulation for which the path is
	 *                         returned
	 */
	@Override
	public void printPath(int simulationIndex) {
		UsefulMethodsMatricesVectors.printVector(getPath(simulationIndex));
	}

	/**
	 * @param time, the time i such that the simulated values of S(i) are returned
	 * @return the average of S at time time.
	 */
	@Override
	public double getAverageAtGivenTime(int time) {
		return UsefulMethodsMatricesVectors.getAverage(getRealizationsAtGivenTime(time));
	}

	/**
	 * @return the initial value of the process
	 */
	@Override
	public double getInitialValue() {
		return initialValue;
	}

	/**
	 * @return the number of simulated trajectories of the process
	 */
	@Override
	public int getNumberOfSimulations() {
		return numberOfSimulations;
	}

	/**
	 * @return the last time at which the process is simulated
	 */
	@Override
	public int getLastTime() {
		return lastTime;
	}

}
