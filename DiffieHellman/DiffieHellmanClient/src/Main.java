import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {

	private static Socket connection;
	private static BigInteger secretInteger;
	private static int decimalDigits;

	public static void main(String[] args) {
		try {

			createConnection();
			sendNewRequest();
			readDecimalDigits();
			BigInteger prime = readPrime();
			BigInteger primitiveRoot = readPrimtiveRoot();
			BigInteger resultToSend = calculateFormule(prime, primitiveRoot);
			sendResult(resultToSend);
			BigInteger result = readResult();
			BigInteger secret = calculateSecret(result, secretInteger, prime);
			printTime();
			System.out.printf("[Secret: %s]\n", secret);
			connection.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void printTime() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss,SSSS");
		System.out.printf("%s ", sdf.format(cal.getTime()));
	}

	private static void createConnection() throws IOException {
		printTime();
		int portNumber = 9988;
		String host = "localhost";
		InetAddress address = InetAddress.getByName(host);
		connection = new Socket(address, portNumber);
		System.out.println("--- Diffie-Hellman Client Connection Established ---");
	}

	private static void sendNewRequest() throws IOException {
		printTime();
		System.out.println("Sending new request");
		BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
		OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
		String process = "newRequest" + (char) 13;
		osw.write(process);
		osw.flush();
	}

	private static void readDecimalDigits() throws IOException {
		printTime();
		System.out.println("Waiting for decimal digits.");
		BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
		InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
		int c;
		StringBuffer instr = new StringBuffer();
		while ((c = isr.read()) != (char) 13) {
			instr.append((char) c);
		}
		String decimalDigitsString = instr.toString().split(":")[1];
		printTime();
		System.out.println("Decimal digits received: " + decimalDigitsString);
		decimalDigits = Integer.parseInt(decimalDigitsString);
	}

	private static BigInteger readPrime() throws IOException {
		printTime();
		System.out.println("Waiting for prime.");
		BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
		InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
		int c;
		StringBuffer instr = new StringBuffer();
		while ((c = isr.read()) != (char) 13) {
			instr.append((char) c);
		}
		String prime = instr.toString().split(":")[1];
		printTime();
		System.out.println("Prime received: " + prime);
		return new BigInteger(prime);
	}

	private static BigInteger readPrimtiveRoot() throws IOException {
		printTime();
		System.out.println("Waiting for primitive root.");
		BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
		InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
		int c;
		StringBuffer instr = new StringBuffer();
		while ((c = isr.read()) != (char) 13) {
			instr.append((char) c);
		}
		String primtiveRoot = instr.toString().split(":")[1];
		printTime();
		System.out.println("Primitive root received: " + primtiveRoot);
		return new BigInteger(primtiveRoot);
	}

	private static BigInteger calculateFormule(BigInteger prime, BigInteger primitiveRoot) {
		secretInteger = BigIntegerRandomGenerator.generate(decimalDigits);
		return primitiveRoot.modPow(secretInteger, prime);
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

	private static BigInteger readResult() throws IOException {
		printTime();
		System.out.printf("Waiting for server result\n");
		BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
		InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
		int c;
		StringBuffer instr = new StringBuffer();
		while ((c = isr.read()) != (char) 13) {
			instr.append((char) c);
		}
		String result = instr.toString().split(":")[1];
		printTime();
		System.out.printf("Server result received: %s\n", result);
		return new BigInteger(result);
	}

	private static BigInteger calculateSecret(BigInteger result, BigInteger secretInteger, BigInteger prime) {
		return result.modPow(secretInteger, prime);
	}

}
