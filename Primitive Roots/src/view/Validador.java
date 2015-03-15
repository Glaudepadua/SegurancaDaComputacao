package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Validador {

	public static final String REGEX_PARA_INTEIRO = "-?\\d+";

	public static boolean validarInteiro(JTextField textField, JLabel label) {
		String valor = textField.getText();
		boolean semErro = true;
		if (!(valor.matches(REGEX_PARA_INTEIRO))) {
			marcarInvalido(label);
			semErro = false;
		} else {
			marcarValido(label);
		}
		return semErro;
	}

	private static void marcarInvalido(JLabel label) {
		label.setForeground(Color.RED);
	}

	private static void marcarValido(JLabel label) {
		label.setForeground(Color.BLACK);
	}

}
