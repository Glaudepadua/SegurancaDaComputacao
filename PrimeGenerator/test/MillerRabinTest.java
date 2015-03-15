import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MillerRabinTest {

	// List of primes: http://primes.utm.edu/lists/small/10000.txt

	int precision;

	@Before
	public void setup() {
		precision = 10;
	}

	@Ignore
	@Test
	public void should_recognize_3_as_prime() throws Exception {
		assertTrue(MillerRabin.isProbablyPrime(BigInteger.valueOf(3), precision));
	}

	@Ignore
	@Test
	public void should_recognize_5_as_prime() throws Exception {
		assertTrue(MillerRabin.isProbablyPrime(BigInteger.valueOf(5), precision));
	}

	@Ignore
	@Test
	public void should_recognize_6_as_non_prime() throws Exception {
		assertFalse(MillerRabin.isProbablyPrime(BigInteger.valueOf(6), precision));
	}

	@Ignore
	@Test
	public void should_recognize_7_as_prime() throws Exception {
		assertTrue(MillerRabin.isProbablyPrime(BigInteger.valueOf(7), precision));
	}
	
	@Test
	public void should_recognize_11_as_prime() throws Exception {
		BigInteger value = BigInteger.valueOf(11);
		assertTrue(MillerRabin.isProbablyPrime(value, precision));
	}
	
	@Ignore
	@Test
	public void should_recognize_7919_as_non_prime() throws Exception {
		assertFalse(MillerRabin.isProbablyPrime(BigInteger.valueOf(7919), precision));
	}

}
