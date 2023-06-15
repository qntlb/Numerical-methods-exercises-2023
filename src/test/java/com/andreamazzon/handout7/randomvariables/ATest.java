package com.andreamazzon.handout7.randomvariables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ATest {
	public static void main(String[] args) {


		int numberOfSamplePoints = 1000;


		// Initialize the random number generator with a seed

		Random random = new Random(3141);


		List<Double> xValues = new ArrayList<>();

		List<Double> yValues = new ArrayList<>();

		for(int i=0; i<numberOfSamplePoints; i++) {


			// Pick two elements from the sequence to create one element of the vector

			double x = random.nextDouble();

			double y = random.nextDouble();


			xValues.add(x);

			yValues.add(y);


		}



	}
	
}

