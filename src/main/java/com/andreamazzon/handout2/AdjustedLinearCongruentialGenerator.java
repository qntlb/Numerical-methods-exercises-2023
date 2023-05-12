package com.andreamazzon.handout2;

/**
 * This class generates pseudo random numbers through a linear congruential
 * random number generator, producing natural numbers
 *
 * x[i+1] =(a*x[i] +c) mod_m,
 *
 * where x[0] = seed, for appropriate values of natural numbers a, c and m (for
 * big m).
 *
 * We have a method generate() which generates all the sequence (i.e., an
 * array), up to the number of pseudo random integers we want to simulate. We
 * also have two public methods, getRandomNumberSequence() and getNextInteger(),
 * that return all the sequence and the next integer x[count + 1], where count
 * gets incremented by 1 every time the method is called, respectively. We want
 * that the sequence is generated for the first time only when one of these
 * methods is called by the user: this is called "lazy initialization".
 * Moreover, of course we don't want all the sequence to be generated again
 * every time the user calls one of the methods. We then check if it has been
 * already initialized. In order to do that, the best practice is to make
 * generate() private, as well as the sequence itself.
 *
 * @author Andrea Mazzon
 *
 */
public class AdjustedLinearCongruentialGenerator {

	// note: all the fields are private!
	private long[] randomNumbers;// array of long
	private final long modulus = (long) Math.pow(2, 48);

	private final long a = 6553590L; // if I don't put L after the number, it will complain that is out of range
	private final long c = 11;// automatic upcasting
	private long seed; // it will be the first entry of our pseudo random number list
	private int numberOfPseudoRandomNumbers;// default value
	private int count = 1;

	// constructor
	public AdjustedLinearCongruentialGenerator(int numberOfPseudoRandomNumbers, long seed) {
		this.numberOfPseudoRandomNumbers = numberOfPseudoRandomNumbers;
		this.seed = seed;
	}

	/*
	 * it generates the sequence of pseudo random numbers. It is void because the
	 * sequence is stored in the array, which is the field of the class. Private
	 * because it also gets called internally!
	 */
	private void generate() {

		// initialization! + 1 because the first one is the seed
		randomNumbers = new long[numberOfPseudoRandomNumbers + 1];
		long remainderOfMaxValue = Long.MAX_VALUE % modulus;
		randomNumbers[0] = seed; // the first entry is the seed: first number of the sequence
		for (int indexOfInteger = 0; indexOfInteger < numberOfPseudoRandomNumbers; indexOfInteger++) {
			long observedValue = a * randomNumbers[indexOfInteger] + c;
			if (observedValue < 0 /* overflow! */) {
				// the size of the overflow we got
				long valueOverflow = observedValue - Long.MIN_VALUE + 1;

				long remainderOverflow = valueOverflow % modulus;
				// this is the number to which we apply %
				observedValue = remainderOverflow + remainderOfMaxValue;
			}
			long congruence = observedValue % modulus;
			randomNumbers[indexOfInteger + 1] = congruence;
		}
	}

	/**
	 * getter method for the sequence of pseudo random natural numbers
	 *
	 * @return the sequence of pseudo random numbers
	 */
	public long[] getRandomNumberSequence() {
		/*
		 *  Lazy initialization: the pseudo random sequence is generated only the first
		 *  time one of two methods is called.
		 */
		if (randomNumbers == null) {
			generate();
			return randomNumbers;
		}
		return randomNumbers.clone(); // returns the already generated sequence of numbers.
	}

	/**
	 * getter method for the sequence of pseudo random numbers
	 *
	 * @return the next number of the sequence of pseudo random numbers
	 */
	public long getNextInteger() {
		long[] sequence = getRandomNumberSequence();// it gets really generated only once
		return sequence[count++];
	}

	/**
	 * getter method for the modulus
	 *
	 * @return the modulus of the congruence that generates the pseudo random
	 *         numbers
	 */
	public long getModulus() {
		return modulus;
	}

	/**
	 * getter method for the length of the simulated sequence
	 *
	 * @return the length of the simulated sequence
	 */
	public int getNumberOfPseudoRandomNumbers() {
		return numberOfPseudoRandomNumbers;
	}
}