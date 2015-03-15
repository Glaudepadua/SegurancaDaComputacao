import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {

	private static final BigInteger ONE = BigInteger.ONE;
	private static ServerSocket serverSocket;
	private static Socket connection;
	private static BigInteger secretInteger;
	private static int decimalDigits;

	public static void main(String[] args) {
		if (args.length > 0) {
			decimalDigits = Integer.valueOf(args[0]);
		} else {
			decimalDigits = 20;
		}
		try {
			initializeServer();
			while (true) {
				waitConnection();
				waitNewRequest();
				sendDecimalDigits();
				BigInteger prime = calculatePrime();
				sendPrime(prime);
				BigInteger primitiveRoot = calculatePrimitiveRoot(prime);
				sendPrimitiveRoot(primitiveRoot);
				BigInteger result = readResult();
				BigInteger resultToSend = calculateFormule(prime, primitiveRoot);
				sendResult(resultToSend);
				BigInteger secret = calculateSecret(result, secretInteger, prime);
				printTime();
				System.out.printf("[Secret: %s]\n", secret);
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void printTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss,SSSS");
		System.out.printf("%s ", sdf.format(cal.getTime()));
	}

	private static void initializeServer() throws Exception {
		serverSocket = new ServerSocket(9988);
		printTime();
		System.out.println("--- Diffie-Hellman Server Initialized ---");
	}

	private static void waitConnection() throws IOException {
		printTime();
		System.out.println("Waiting Connection request...");
		connection = serverSocket.accept();
	}

	private static boolean waitNewRequest() throws IOException {
		while (true) {
			BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
			InputStreamReader isr = new InputStreamReader(is);
			StringBuffer process = new StringBuffer();
			int character;
			while ((character = isr.read()) != 13) {
				process.append((char) character);
			}
			printTime();
			System.out.println("Connection request received");
			if (process.toString().contains("newRequest")) {
				return true;
			}
		}
	}

	private static void sendDecimalDigits() throws IOException {
		printTime();
		System.out.printf("Sending decimal digits: %s\n", decimalDigits);
		BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
		OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
		String process = "decimaldigits:" + decimalDigits + (char) 13;
		osw.write(process);
		osw.flush();
	}

	private static BigInteger calculatePrime() {
		BigInteger prime = null;
		boolean primeNotFound = true;
		while (primeNotFound) {
			prime = BigIntegerRandomGenerator.generate(decimalDigits);
			primeNotFound = !MillerRabin.isProbablyPrime(prime, 5);
		}
		return prime;
	}

	private static BigInteger calculatePrimitiveRoot(BigInteger prime) {
		boolean primitiveRootNotFound = true;
		BigInteger primitiveRoot = null;
		while (primitiveRootNotFound) {
			primitiveRoot = PrimitiveRootCalculator.calculate(prime);

			if (primitiveRoot != null && primitiveRoot.compareTo(ONE) == 1) {
				primitiveRootNotFound = false;
			}
		}
		return primitiveRoot;
	}

	private static void sendPrime(BigInteger prime) throws IOException {
		printTime();
		System.out.printf("Sending prime: %s\n", prime);
		BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
		OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
		String process = "prime:" + prime + (char) 13;
		osw.write(process);
		osw.flush();
	}

	private static void sendPrimitiveRoot(BigInteger primitiveRoot) throws IOException {
		printTime();
		System.out.printf("Sending primitive root: %s\n", primitiveRoot);
		BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
		OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
		String process = "primitiveroot:" + primitiveRoot + (char) 13;
		osw.write(process);
		osw.flush();
	}

	private static BigInteger calculateFormule(BigInteger prime, BigInteger primitiveRoot) {
		secretInteger = BigIntegerRandomGenerator.generate(decimalDigits);
		return primitiveRoot.modPow(secretInteger, prime);
	}

	private static BigInteger readResult() throws IOException {
		printTime();
		System.out.printf("Waiting for client result\n");
		BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
		InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
		int c;
		StringBuffer instr = new StringBuffer();
		while ((c = isr.read()) != (char) 13) {
			instr.append((char) c);
		}
		printTime();
		String result = instr.toString().split(":")[1];
		System.out.printf("Client result received: %s\n", result);
		return new BigInteger(result);
	}

	private static void sendResult(BigInteger result) throws IOException {
		printTime();
		System.out.printf("Sending result: %s\n", result);
		BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
		OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
		String process = "result:" + result.toString() + (char) 13;
		osw.write(process);
		osw.flush();
	}

	private static BigInteger calculateSecret(BigInteger result, BigInteger secretInteger, BigInteger prime) {
		return result.modPow(secretInteger, prime);
	}

}
