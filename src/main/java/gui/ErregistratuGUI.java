package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Font;

import businessLogic.BusinessLogicServer;
import domain.User;
import gui.LoginPantailaGUI;

import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ErregistratuGUI extends JFrame {
	private JTextField erabiltzaileaField;
	private JPasswordField passwordField;
	private JPasswordField passwordErrepField;

	private static BLFacade appFacadeInterface;
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}



	public ErregistratuGUI() {
		BLFacade bLogic = LoginPantailaGUI.getBusinessLogic();
		final JPanel errorPanel = new JPanel();
		getContentPane().setLayout(null);
		this.setSize(550, 420);
		this.setTitle("ERREGISTRATU");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel erregistratuTituloa = new JLabel("ERREGISTRATU");
		erregistratuTituloa.setFont(new Font("Tahoma", Font.BOLD, 27));
		erregistratuTituloa.setBounds(152, 26, 227, 41);
		getContentPane().add(erregistratuTituloa);
		
		JLabel erabiltzaileTestua = new JLabel("ERABILTZAILE IZENA");
		erabiltzaileTestua.setFont(new Font("Tahoma", Font.BOLD, 15));
		erabiltzaileTestua.setBounds(39, 106, 169, 24);
		getContentPane().add(erabiltzaileTestua);
		
		JLabel pasahitzaTestua = new JLabel("PASAHITZA");
		pasahitzaTestua.setFont(new Font("Tahoma", Font.BOLD, 15));
		pasahitzaTestua.setBounds(39, 170, 100, 16);
		getContentPane().add(pasahitzaTestua);
		
		JLabel pasErrepTestua = new JLabel("PASAHITZA ERREPIKATU");
		pasErrepTestua.setFont(new Font("Tahoma", Font.BOLD, 15));
		pasErrepTestua.setBounds(39, 230, 194, 16);
		getContentPane().add(pasErrepTestua);
		
		erabiltzaileaField = new JTextField();
		erabiltzaileaField.setBounds(306, 108, 169, 22);
		getContentPane().add(erabiltzaileaField);
		erabiltzaileaField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(306, 168, 169, 22);
		getContentPane().add(passwordField);
		
		passwordErrepField = new JPasswordField();
		passwordErrepField.setBounds(306, 224, 169, 22);
		getContentPane().add(passwordErrepField);
		
		JButton erregistratuBotoia = new JButton("ERREGISTRATU");
		erregistratuBotoia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = erabiltzaileaField.getText();
				String password1 = new String(passwordField.getPassword());
				String password2 = new String(passwordErrepField.getPassword());

				if (erabiltzaileaField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(errorPanel, "Erabiltzaile izena sartu mesedez.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					if (password1.equals(password2)) {
						if (!bLogic.existsErabiltzailea(userName)) {
							bLogic.erregistratu(userName, password1);
							erabiltzaileaField.setText("");
							passwordField.setText("");
							passwordErrepField.setText("");
						} else {
							JOptionPane.showMessageDialog(errorPanel, "Erabiltzaile izen horrekin jadanik existitzen da norbait.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else JOptionPane.showMessageDialog(errorPanel, "Pasahitzak ez dute koinziditzen.", "Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		erregistratuBotoia.setForeground(Color.BLACK);
		erregistratuBotoia.setBackground(new Color(102, 153, 204));
		erregistratuBotoia.setFont(new Font("Tahoma", Font.BOLD, 15));
		erregistratuBotoia.setBounds(306, 298, 169, 41);
		getContentPane().add(erregistratuBotoia);
		
		JButton atzeraBotoia = new JButton("ATZERA");
		atzeraBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame log = new LoginPantailaGUI();
				log.setVisible(true);
				setVisible(false);
			}
		});
		atzeraBotoia.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		atzeraBotoia.setFont(new Font("Tahoma", Font.BOLD, 15));
		atzeraBotoia.setBounds(39, 298, 100, 41);
		getContentPane().add(atzeraBotoia);
	}

	
	
}
