package com.andreamazzon.handout0;

import java.util.ArrayList;

/**
 * This class tests the generation of the exercise times for a Bermudan option performed in
 * ManaginArrayList
 * 
 * @author Andrea Mazzon
 *
 */
public class TestManagingArrayLists {

	public static void main(String[] args) {

		double lengthOfIntervals = 0.5;
		double finalTime = 4;
		
		ManagingArrayLists tester = new ManagingArrayLists(lengthOfIntervals, finalTime);	
		
		ArrayList<ArrayList<Double>> tracker = tester.getAllPossibleVectorsOfExerciseTimes();
		
		//we print all the vectors
		for (int index = 0; index < tracker.size(); index ++) {
			System.out.println(tracker.get(index).toString());
		}
	}

}
