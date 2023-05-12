package com.andreamazzon.handout2;

import static com.andreamazzon.session3.useful.Print.printn;

import java.util.Arrays;

import com.andreamazzon.session3.encapsulation.lazyinitialization.LinearCongruentialGenerator;

/**
 * This class contains one main method that calls the getRandomNumberSequence()
 *  method of the class LinearCongruentialGenerator.
 *
 * @author Andrea Mazzon
 *
 */

public class PseudoRandomNumbersTesting {
	public static void main(String[] args) {

		long firstSeed = 2814749763100L;// the seed is the first entry of the sequence of pseudo random numbers
		int numberOfPseudoRandomNumbers = 5;

		AdjustedLinearCongruentialGenerator firstGenerator = new AdjustedLinearCongruentialGenerator(
				numberOfPseudoRandomNumbers, firstSeed);

		long[] sequenceGeneratedByTheFirstObject = firstGenerator.getRandomNumberSequence();


		System.out.println("Simulation of " + numberOfPseudoRandomNumbers + " integers with seed " + firstSeed
				+ " using the Java specifications: " + Arrays.toString(sequenceGeneratedByTheFirstObject));

		System.out.println();

		System.out.println("First five number of the random sequence, excluded the seed:");

		for (int i = 0; i < numberOfPseudoRandomNumbers; i++) {
			printn(firstGenerator.getNextInteger());
		}

	}
}