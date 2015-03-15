import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSA {

	private final int NUMBER_OF_BITS = 2048;
	private final BigInteger ONE = BigInteger.ONE;
	private final BigInteger TWO = BigInteger.valueOf(2);

	private Random random;

	private BigInteger p;
	private BigInteger q;
	private BigInteger n;
	private BigInteger eulerPhiFunctionOfN;
	private BigInteger e;
	private BigInteger d;

	public RSA() {
		random = new Random();
		setup();
	}

	public void changeKeys() {
		setup();
	}

	public BigInteger encrypt(char c) {
		BigInteger m = new BigInteger(String.valueOf((int) c));
		m = m.modPow(e, n);
		return m;
	}

	public char decrypt(BigInteger c) {
		return (char) c.modPow(d, n).intValue();
	}

	public List<BigInteger> encrypt(String c) {
		List<BigInteger> result = new ArrayList<>();
		for (char f : c.toCharArray()) {
			result.add(encrypt(f));
		}
		return result;
	}

	public String decrypt(List<BigInteger> list) {
		String result = "";
		for (BigInteger c : list) {
			result = result.concat(String.valueOf(decrypt(c)));
		}
		return result;
	}

	private void setup() {
		p = generatePrimeWithNumberOfBits(NUMBER_OF_BITS);
		q = generatePrimeWithNumberOfBits(NUMBER_OF_BITS);
		n = computeN();
		eulerPhiFunctionOfN = computeEulerPhiFunctionOfN();
		e = generateE();
		d = computeMultiplicativeInverse(e, eulerPhiFunctionOfN);
	}

	private BigInteger generatePrimeWithNumberOfBits(int numberOfBits) {
		return BigInteger.probablePrime(numberOfBits, random);
	}

	private BigInteger computeN() {
		return p.multiply(q);
	}

	private BigInteger computeEulerPhiFunctionOfN() {
		BigInteger eulerPhiFunctionOfP = p.subtract(ONE);
		BigInteger eulerPhiFunctionOfQ = q.subtract(ONE);
		return eulerPhiFunctionOfP.multiply(eulerPhiFunctionOfQ);
	}

	private BigInteger generateE() {
		BigInteger propablyE = null;
		boolean eNotFound = true;
		while (eNotFound) {
			propablyE = BigIntegerRandomGenerator.generate(TWO, eulerPhiFunctionOfN.subtract(ONE));
			if (propablyE.gcd(eulerPhiFunctionOfN).compareTo(ONE) == 0) {
				eNotFound = false;
			}
		}
		return propablyE;
	}

	private BigInteger computeMultiplicativeInverse(BigInteger e, BigInteger m) {
		return e.modInverse(m);
	}

}
