import java.math.BigInteger;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("How many decimal digits?");
		int decimalDigits = in.nextInt();
		int probablyPrime = 0;
		boolean primeNotFound = true;
		while (primeNotFound) {
			long decimalMult = BigInteger.TEN.pow(decimalDigits).longValue();
			do {
				BigInteger.TEN.pow(decimalDigits).longValue();
				probablyPrime = Double.valueOf(Math.random() * decimalMult).intValue();
			} while ((int) (Math.log10(probablyPrime) + 1) != decimalDigits);
			primeNotFound = !MillerRabin.isProbablyPrime(BigInteger.valueOf(probablyPrime), 5);
		}
		System.out.println(probablyPrime);
	}
}
