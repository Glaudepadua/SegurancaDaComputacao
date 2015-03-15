import java.math.BigInteger;

public class MillerRabin {

	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = BigInteger.valueOf(2);

	public static boolean isProbablyPrime(BigInteger n, int precision) {
		if (n.compareTo(ONE) <= 0 || n.mod(TWO) == ZERO) {
			return false;
		} else if (n.intValue() == 3) {
			return true;
		}

		BigInteger nLessOne = n.subtract(ONE);
		BigInteger x;
		int s = 0;
		BigInteger d = nLessOne;
		BigInteger randomBigInteger;
		while (d.mod(TWO).equals(ZERO)) {
			s++;
			d = d.divide(TWO);
		}

		for (BigInteger k = ZERO; k.intValue() <= precision; k = k.add(ONE)) {
			randomBigInteger = BigIntegerRandomGenerator.generate(TWO, n.subtract(TWO));
			x = randomBigInteger.modPow(d, n);
			if (x.compareTo(ONE) == 0 || x.compareTo(nLessOne) == 0) {
				continue;
			}
			for (int r = 0; r < s - 1; r++) {
				x = x.modPow(TWO, n);
				if (x.compareTo(ONE) == 0) {
					return false;
				}
				if (x.compareTo(nLessOne) == 0) {
					continue;
				}
			}
			return false;
		}
		return true;
	}
}