package com.andreamazzon.handout11;

import java.util.HashMap;
import java.util.Map;

import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.assetderivativevaluation.AssetModelMonteCarloSimulationModel;
import net.finmath.montecarlo.assetderivativevaluation.products.AbstractAssetMonteCarloProduct;
import net.finmath.montecarlo.assetderivativevaluation.products.EuropeanOption;
import net.finmath.stochastic.RandomVariable;

/**
 * This class implements the approximation of the delta of an European call option through finite central differences,
 * using the Monte-Carlo method in order to get the prices of the European call options for shifted initial value. 
 * In particular, the delta of the option is approximated as
 * (V(S_0+delta)-V(S_0-delta))/(2 delta),
 * where S_0 is the true initial value of the underlying and V(x) is the Monte-Carlo price of the option when the
 * underlying has initial value x.
 * Note that the only overridden method is getValue. In this method, the Monte-Carlo derivative is computed by getting
 * the clones of the underlying with modified initial value. This is possible for all the models we are interested in,
 * i.e., among others, for the following classes extending AbstractProcessModel: BachelierModel, BlackScholesModel,
 * DisplacedLognomalModel, HestonModel, MertonModel.
 *
 * @author Andrea Mazzon
 */
public class EuropeanOptionDeltaCentralDifferences extends AbstractAssetMonteCarloProduct {

	private final double maturity;
	private final double strike;
	private final double step;

	/**
	 * It constructs an object to compute the appoximated value of the delta of the option.
	 *
	 * @param maturity, the maturity of the option
	 * @param strike,   the strike of the European call option
	 * @param step,     the step delta>0 such that the approximated derivative is computed as
	 * 					(V(S_0+delta)-V(S_0-delta))/(2 delta),
	 * 					where S_0 is the true initial value of the underlying and V(x) is the Monte-Carlo price of
	 * 					the option when the underlying has initial value x.
	 */
	public EuropeanOptionDeltaCentralDifferences(double maturity, double strike, double step) {
		super();
		this.maturity = maturity;
		this.strike = strike;
		this.step = step;
	}

	/**
	 * This method returns the value of the delta of an European call option through central differences, as a
	 * RandomVariable.
	 *
	 * @param evaluationTime   The time on which the delta of the option value is evaluated.
	 * @param monteCarloModel, object of type AssetModelMonteCarloSimulationModel.
	 * @return The random variable representing the value of the delta discounted to evaluation time
	 * @throws net.finmath.exception.CalculationException
	 */
	@Override
	public RandomVariable getValue(double evaluationTime, AssetModelMonteCarloSimulationModel underlyingSimulation)
			throws CalculationException {
		/*
		 * We want to compute the Monte-Carlo approximation at t of the delta of the option as
		 * (V(S_t + delta) - V(S_t - delta))/(2 delta),
		 * where delta>0 is the step of the differentiation and (S_r)_{r >= t} is represented by
		 * underlyingSimulation.
		 * V(S_t) is here the RandomVariable we get by calling the method getValue(maturity, underlyingSimulation)
		 * of EuropeanOption, where maturity is T and underlyingSimulation has "initial value" ("initial value"
		 * if we think that we evaluate the option at time t) S_t.
		 * Therefore, in order to get V(S_t + delta) and V(S_t - delta) we have to give to getValue a
		 * "clone" of underlyingSimulation with initial value S_t + delta, S_t - delta, respectively.
		 * This is possible for all the models we are interested in, i.e., among others, for the following
		 * classes extending AbstractProcessModel: BachelierModel, BlackScholesModel, DisplacedLognomalModel,
		 * HestonModel, MertonModel.
		 *
		 */

		
		// First thing, we construct an EuropeanOption object with maturity and strike that are fields of the class
		final EuropeanOption callOption = new EuropeanOption(maturity, strike);

		// Now we have to construct the two underlyings.

		
		// First step: get the "initial value" (i.e., the value at time evaluationTime) of underlyingSimulation.
		final RandomVariable initialValue = underlyingSimulation.getAssetValue(evaluationTime, 0);

		/*
		 * We want to construct a new model with modified initial data S_t + delta: to this purpose, we use the
		 * getCloneWithModifiedData method of AssetModelMonteCarloSimulationModel. The data is given by the code below.
		 */
		final Map<String, Object> dataForward = new HashMap<String, Object>();
		/*
		 * Here we specify that we want to change the initial value. This is possible for all the classes listed above.
		 * Also: a priori here initialValue is a RandomVariable. However, it gets converted to double in the
		 * implementation of the Finmath library
		 */
		dataForward.put("initialValue", initialValue.add(step).doubleValue());

		// now we have to use the method getCloneWithModifiedData giving it dataForward
		final AssetModelMonteCarloSimulationModel monteCarloForward = underlyingSimulation.getCloneWithModifiedData(dataForward);

		
		// Same thing for S_{t-delta}. Now the new initial value will be the old one minus the step
		final Map<String, Object> dataBackward = new HashMap<String, Object>();
		
		dataBackward.put("initialValue", initialValue.sub(step).doubleValue());

		final AssetModelMonteCarloSimulationModel monteCarloBackward = underlyingSimulation.getCloneWithModifiedData(dataBackward);

		// So now we have S_{t-delta}, S_{t+delta}, and we want to compute the correspondent values for the call.
		final RandomVariable forwardOptionPayoffs = callOption.getValue(maturity, monteCarloForward);
		final RandomVariable backwardOptionPayoffs = callOption.getValue(maturity, monteCarloBackward);

		/*
		 * Now, having forwardOptionPayoffs and backwardOptionPayoffs and the object for the option, compute
		 * RandomVariable values, which is the central difference.
		 */
		RandomVariable values = (forwardOptionPayoffs.sub(backwardOptionPayoffs)).div(2 * step);

		// Discounting...
		final RandomVariable numeraireAtMaturity = underlyingSimulation.getNumeraire(maturity);
		values = values.div(numeraireAtMaturity);

		// ...to evaluation time.
		final RandomVariable numeraireAtEvalTime = underlyingSimulation.getNumeraire(evaluationTime);

		values = values.mult(numeraireAtEvalTime);

		return values;
	}
}