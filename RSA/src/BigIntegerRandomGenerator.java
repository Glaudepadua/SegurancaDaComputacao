import java.math.BigInteger;
import java.util.Random;

public class BigIntegerRandomGenerator {
	private static final Random random = new Random();

	public static BigInteger generate(int numberOfDigits) {
		String number = String.valueOf(Math.random() + 1).replace(".", "");
		while (number.length() <= numberOfDigits) {
			number = number.concat(String.valueOf(Math.random() + 1).replace(".", ""));
		}
		return new BigInteger(number.substring(0, numberOfDigits));
	}

	public static BigInteger generate(BigInteger minValue, BigInteger maxValue) {
		String number = String.valueOf(Math.random() + 1).replace(".", "");
		BigInteger result = null;
		int numberOfDigits = random.nextInt(String.valueOf(maxValue).length()) + 1;
		if (numberOfDigits == 1) {
			return BigInteger.valueOf((random.nextInt(9)));
		}
		while (number.length() <= numberOfDigits) {
			number = number.concat(String.valueOf(Math.random() + 1).replace(".", ""));
		}
		result = new BigInteger(number.substring(0, numberOfDigits));
		if (result.compareTo(minValue) >= 0 && result.compareTo(maxValue) <= 0) {
			return result;
		} else {
			return BigIntegerRandomGenerator.generate(minValue, maxValue);
		}
	}
}
