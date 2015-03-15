import java.math.BigInteger;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		RSA rsa = new RSA();
		String c = "May the Force be with you.";
		System.out.println("## Original Message ##");
		System.out.println(c);
		System.out.println("");
		List<BigInteger> encrypt = rsa.encrypt(c);
		System.out.println("## Encrypted Message ##");
		for (BigInteger character : encrypt) {
			System.out.println(character);
		}
		System.out.println("");
		String decrypt = rsa.decrypt(encrypt);
		System.out.println("## Dectypted Message ##");
		System.out.println(decrypt);
	}
}
