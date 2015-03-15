import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimitiveRootCalculator {

	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger ZERO = BigInteger.ZERO;

	public synchronized static BigInteger calculate(BigInteger value) {
		BigInteger q = value.subtract(ONE);
		List<BigInteger> primeFactors = primeFactorsOf(q);
		List<BigInteger> coprimesAlreadyTested = new ArrayList<BigInteger>();
		BigInteger coprime = ONE;
		while (true) {
			coprime = getOneCoprimeOfValueThatIsNotInList(value, coprimesAlreadyTested);
			if (coprime == null) {
				return null;
			}
			if (verify(value, primeFactors, q, coprime)) {
				return coprime;
			}
			coprimesAlreadyTested.add(coprime);
		}
	}

	private static boolean verify(BigInteger value, List<BigInteger> primeFactors, BigInteger q, BigInteger coprime) {
		for (BigInteger primeFactor : primeFactors) {
			if (coprime.modPow(q.divide(primeFactor), value).compareTo(ONE) == 0) {
				return false;
			}
		}
		return true;
	}

	private static BigInteger getOneCoprimeOfValueThatIsNotInList(BigInteger value, List<BigInteger> list) {
		BigInteger probableCoprime = ZERO;
		while (probableCoprime != null) {
			probableCoprime = BigIntegerRandomGenerator.generate(ZERO, value);
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

	public static List<BigInteger> primeFactorsOf(BigInteger number) {
		BigInteger n = number;
		List<BigInteger> factors = new ArrayList<BigInteger>();
		for (BigInteger i = BigInteger.valueOf(2); i.compareTo(n.divide(i)) <= 0; i = i.add(ONE)) {
			while (n.mod(i).compareTo(ZERO) == 0) {
				factors.add(i);
				n = n.divide(i);
			}
		}
		if (n.compareTo(ONE) == 1) {
			factors.add(n);
		}
		return factors;
	}
}
