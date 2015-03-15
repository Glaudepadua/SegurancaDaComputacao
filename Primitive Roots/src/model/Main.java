package model;

import javax.swing.ImageIcon;

import view.WelcomeView;

public class Main {

	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(WelcomeView.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(WelcomeView.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(WelcomeView.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(WelcomeView.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				WelcomeView tela = new WelcomeView();
				tela.setIconImage(new ImageIcon(getClass().getResource("/resources/images/icon.png")).getImage());
				tela.setVisible(true);
			}
		});
	}

}
