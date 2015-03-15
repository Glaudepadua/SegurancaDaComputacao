import java.math.BigInteger;
import java.util.Random;

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
		Random random = new Random();

		BigInteger nLessOne = n.subtract(ONE);
		BigInteger x;
		int s = 0;
		BigInteger d = nLessOne;
		BigInteger randomInteger;
		while (d.mod(TWO).equals(ZERO)) {
			s++;
			d = d.divide(TWO);
		}

		for (int k = 0; k <= precision; k++) {
			randomInteger = BigInteger.valueOf(random.nextInt(n.intValue() - 4) + 2);
			x = randomInteger.pow(d.intValue()).mod(n);
			if (x.compareTo(ONE) == 0 || x.compareTo(nLessOne) == 0) {
				continue;
			}
			for (int r = 0; r < s - 1; r++) {
				x = x.pow(TWO.intValue()).mod(n);
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