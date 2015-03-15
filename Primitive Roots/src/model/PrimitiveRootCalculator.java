package model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimitiveRootCalculator {

	private final static Random random = new Random();
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger ZERO = BigInteger.ONE;

	public synchronized static BigInteger calculate(BigInteger value) throws NotPrimeException,
			PrimitiveRootNotFoundException {
		if (!isPrime(value)) {
			throw new NotPrimeException();
		}
		List<BigInteger> coprimesAlreadyTested = new ArrayList<BigInteger>();
		BigInteger aux = null;
		List<BigInteger> residues = new ArrayList<BigInteger>();
		BigInteger coprime = getOneCoprimeOfValueThatIsNotInList(value, coprimesAlreadyTested);
		while (coprime != null) {
			for (int k = 1; k < value.intValue(); k++) {
				aux = (coprime.pow(k)).mod(value);
				if (aux == ZERO || residues.contains(aux)) {
					break;
				}
				residues.add(aux);
			}
			if (residues.size() == value.intValue() - 1) {
				return coprime;
			}
			coprimesAlreadyTested.add(coprime);
			residues.clear();
			coprime = getOneCoprimeOfValueThatIsNotInList(value, coprimesAlreadyTested);
		}
		throw new PrimitiveRootNotFoundException();
	}

	private static BigInteger getOneCoprimeOfValueThatIsNotInList(BigInteger value, List<BigInteger> list) {
		BigInteger probableCoprime = ZERO;
		while (probableCoprime != null) {
			probableCoprime = new BigInteger(String.valueOf(random.nextInt(value.intValue())));
			if (list.contains(probableCoprime)) {
				list.add(probableCoprime);
				if (list.size() >= value.intValue()) {
					break;
				}
				continue;
			}
			if (probableCoprime.gcd(value).compareTo(ONE) == 0) {
				return probableCoprime;
			}
		}
		return null;
	}

	private static boolean isPrime(BigInteger value) {
		return value.isProbablePrime(1);
	}

}
