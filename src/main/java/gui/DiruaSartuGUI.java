package gui;

import businessLogic.BLFacade;
import domain.Pertsona;
import domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class DiruaSartuGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTextField textFieldDirua;
	private JLabel labelTitulua;
	private static Pertsona user;
	private static BLFacade appFacadeInterface;
	
	public DiruaSartuGUI() {
		this.user = LoginPantailaGUI.getUser();
		appFacadeInterface = LoginPantailaGUI.getBusinessLogic();
		this.setTitle("Dirua sartu");
		this.setBounds(100, 100, 439, 321);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		final JPanel errorPanel = new JPanel();

		labelTitulua = new JLabel("SARTU NAHI DUZUN KANTITATEA");
		labelTitulua.setFont(new Font("Tahoma", Font.BOLD, 18));
		labelTitulua.setBounds(58, 32, 325, 35);
		getContentPane().add(labelTitulua);
		
		textFieldDirua = new JTextField();
		textFieldDirua.setBounds(168, 98, 73, 35);
		getContentPane().add(textFieldDirua);
		textFieldDirua.setColumns(10);
		
		JButton btnSartuDirua = new JButton("DIRUA SARTU");
		btnSartuDirua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(Double.parseDouble(textFieldDirua.getText()) < 10.0) { // Sartutako diru kantitatea 10 euro baino gutxiago bada
						JOptionPane.showMessageDialog(errorPanel, "Gutxienez 10€ sartu behar dira.", "Error", JOptionPane.ERROR_MESSAGE);
						textFieldDirua.setText(null);
					} else { // Bestela
						User user1 = (User) user;
						long millis = System.currentTimeMillis();
						java.util.Date currentDate = new java.util.Date(millis);
						user1.diruaSartu(Double.parseDouble(textFieldDirua.getText()));
						user1.gehituMugimendua(Double.parseDouble(textFieldDirua.getText()) + " \u20AC SARTUTA " + currentDate.toString() + " EGUNEAN.");
						appFacadeInterface.eguneratuDiruaEtaMugimenduak(user1);
						user = user1;
						JOptionPane.showMessageDialog(errorPanel, textFieldDirua.getText() + " \u20AC sartuta.", "Confirmation", JOptionPane.PLAIN_MESSAGE);
						textFieldDirua.setText(null);
					}
				} catch (Exception ex) { // Diru kantitatea ez bada zenbakizkoa errorea emango du
					JOptionPane.showMessageDialog(errorPanel, "Zerbait gaizki dago!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSartuDirua.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSartuDirua.setBounds(123, 162, 181, 46);
		getContentPane().add(btnSartuDirua);
		
		JLabel label = new JLabel("\u20AC");
		label.setFont(new Font("Tahoma", Font.BOLD, 24));
		label.setBounds(252, 106, 29, 23);
		getContentPane().add(label);
		
		JButton btnAtzera = new JButton("ATZERA");
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainUserGUI a = new MainUserGUI();
				a.setVisible(true);
				setVisible(false);
			}
		});
		btnAtzera.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAtzera.setBounds(154, 232, 118, 26);
		getContentPane().add(btnAtzera);
	}
}
