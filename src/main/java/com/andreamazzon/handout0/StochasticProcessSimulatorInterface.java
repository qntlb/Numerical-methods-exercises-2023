package com.andreamazzon.handout0;

/**
 * Interface that every class whose goal is to simulate a stochastic process and get its
 * statistics has to implement
 *
 * @author Andrea Mazzon
 *
 */
public interface StochasticProcessSimulatorInterface {

	/**
	 * It gets the matrix of realizations of the process
	 *
	 * @return the matrix of realizations of the process. Rows represent
	 *         realizations of the process at fixed time, columns trajectories of
	 *         the process for fixed simulation
	 */
	double[][] getRealizations();

	/**
	 * @param time, the time i such that the simulated values of S(i) are returned
	 * @return the simulated values of S at time time.
	 */
	double[] getRealizationsAtGivenTime(int time);

	/**
	 * It prints the vector of realizations at time time.
	 *
	 * @param time, the time i such that the simulated values of S(i) are printed
	 */
	void printRealizationsAtGivenTime(int time);

	/**
	 * @param simulationIndex, the index of the simulation for which the path is returned
	 * @return the path of S for the specific simulation index
	 */
	double[] getPath(int simulationIndex);

	/**
	 * It prints the path of S for the specific simulation index
	 *
	 * @param simulationIndex, the index of the simulation for which the path is returned
	 */
	void printPath(int simulationIndex);

	/**
	 * @param time, the time i such that the simulated values of S(i) are returned
	 * @return the average of S at time time.
	 */
	double getAverageAtGivenTime(int time);

	/**
	 * @return the initial value of the process
	 */
	double getInitialValue();

	/**
	 * @return the number of simulated trajectories of the process
	 */
	int getNumberOfSimulations();

	/**
	 * @return the last time at which the process is simulated
	 */
	int getLastTime();
}
