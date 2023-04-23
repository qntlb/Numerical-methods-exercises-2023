package com.andreamazzon.handout0;

import java.util.ArrayList;


/**
 * Consider a set of equally-spaced times T_0=0<T_1<...<T_N. This class is used to return the set of all
 * the possible vectors of exercise times (T_k, T_{k+1}, ..., T_n), n>k, for a Bermudan option, when for
 * every fixed entry time T_k the maturity T_n is iteratively moved backward from T_N to T_{k+1}, in an
 * inner for loop, and the entry time  T_k is moved forward from 0 to T_{N-1}.
 * The times are managed as objects of type ArrayList<Double>.
 * 
 * @author: Andrea Mazzon
 *
 */
public class ManagingArrayLists {
	
	
	/*
	 *  This field is an ArrayList of ArrayLists. It contains all the possible vectors (T_k, T_{k+1}, ..., T_n),
	 *  when we make vary T_k and T_n
	 */
	private ArrayList<ArrayList<Double>> allPossibleVectorsOfExerciseTimes;
		
	private double lengthOfIntervals;//the constant length T_{i+1}-T_i
	private double finalTime;//T_N

	
	/**
	 * It constructs an object to get all the possible vectors of exercise times for a Bermudan option
	 * 
	 * @param lengthOfIntervals, the constant length T_{l+1}-T_l
	 * @param finalTime, T_N
	 */
	public ManagingArrayLists (double lengthOfIntervals, double finalTime) {
		this.lengthOfIntervals = lengthOfIntervals;
		this.finalTime = finalTime;
		
	}
	
	//this method will be called only once and has the goal to initialize and fill allPossibleVectorsOfExerciseTimes
	private void createTenureStructures() {
		
		allPossibleVectorsOfExerciseTimes = new ArrayList<ArrayList<Double>>();//we initialize it

		/*
		 * This will be (T_k,...,T_N), when T_k changes for every iteration of the external for loop. First, T_K=0,
		 * so first this is really the vector of all times.
		 */
		ArrayList<Double> longestPossibleVectorOfExerciseTimesForFixedEntryTime = new ArrayList<Double>();
		
	
		//now we fill it
		for (double currentTime = 0; currentTime <= finalTime; currentTime += lengthOfIntervals) {
			longestPossibleVectorOfExerciseTimesForFixedEntryTime.add(currentTime);		
		}
		
		
		int numberOfTimes = (int) (finalTime / lengthOfIntervals);
		
		/*
		 * We have two nested for loops: the most external one identifies the entry time, deleting at every iteration the
		 * first entry of the vector, whereas the second one one identifies the exercise times for every fixed entry time,
		 * deleting at every iteration the last entry of the vector (i.e., the maturity of the option).
		 */
		for (int index = 0; index < numberOfTimes ; index ++) {

			/*
			 * Now we already work looking at the second loop: we start by T_k,...,T_N, and at every iteration of
			 * the next for loop we will delete the last element  (i.e., the maturity of the option).So first we have T_k,...,T_N,
			 * then T_k,...,T_{N-1}, and so on. These times are represented by dynamicalExerciseTimes. See how it is modified in the
			 * next for loop. Ignore the warning here.
			 */
			ArrayList<Double> dynamicalExerciseTimes = (ArrayList<Double>) longestPossibleVectorOfExerciseTimesForFixedEntryTime.clone();	
			
			//we add the list T_k,...,T_N to our list of lists
			allPossibleVectorsOfExerciseTimes.add(dynamicalExerciseTimes);

			/*
			 * Second nested loop: we manage the exercise times. We start by T_k,...,T_N, and at every iteration of
			 * the next for loop we will delete the last element  (i.e., the maturity of the option). So first we have 
			 * T_k,...,T_N, then T_k,...,T_{N-1},and so on.
			 */
			for (int maturityIndex = longestPossibleVectorOfExerciseTimesForFixedEntryTime.size()-1; maturityIndex > 1;
					maturityIndex--) {

				/*
				 * We remove the last element of the exercise times, i.e., the maturty of the option: if exercise times are T_k, .., T_n,
				 * where T_k is the entry time, we remove T_n
				 */
				dynamicalExerciseTimes.remove(maturityIndex);
				allPossibleVectorsOfExerciseTimes.add(dynamicalExerciseTimes);
			}
			longestPossibleVectorOfExerciseTimesForFixedEntryTime.remove(0);
		}
		
		
	}
	
	/**
	 * It returns the set of all the possible vectors of exercise times (T_k, T_{k+1}, ..., T_n), n>k,
	 * for a Bermudan option, when for every fixed entry time T_k the maturity T_n is iteratively moved
	 * backward from T_N to T_{k+1}, in an inner for loop, and the entry time  T_k is moved forward from
	 * 0 to T_{N-1}.
	 * 
	 * @return ArrayList<ArrayList<Double>>, an ArrayList of ArrayList<Double> containing all the times 
	 */
	public ArrayList<ArrayList<Double>> getAllPossibleVectorsOfExerciseTimes() {
		
		//we generate it only once, and only when we need it
		if (allPossibleVectorsOfExerciseTimes == null) {
			createTenureStructures();
		}
		
		return (ArrayList<ArrayList<Double>>) allPossibleVectorsOfExerciseTimes.clone();
	}
	
}
