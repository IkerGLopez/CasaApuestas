package gui;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;

import businessLogic.BLFacade;
import domain.Pertsona;
import domain.User;

public class KopiatuUserGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JFrame frmKopiatuApostua;
	private JTextField textField_Apostua;
	private JLabel lblApostuBatKopiatu = new JLabel("ERABILTZAILE BATI KOPIATU");
	private JLabel lblErabiltzailea = new JLabel("Erabiltzailea");
	private JLabel lblApostatutakoaAldatuNahi = new JLabel("Zenbat inbertitu nahi duzu?");
	
	private DefaultComboBoxModel comboBoxUsersModel = new DefaultComboBoxModel();
	private JComboBox comboBox_Users;
	
	private JButton btnKopiatu = new JButton("KOPIATU");
	private JButton btnAtzera = new JButton("ATZERA");
	
	private static BLFacade businessLogic;
	private static Pertsona user;

	final JPanel errorPanel = new JPanel();


	/**
	 * Initialize the contents of the frame.
	 */
	public KopiatuUserGUI() {
		this.user = LoginPantailaGUI.getUser();
		this.businessLogic = LoginPantailaGUI.getBusinessLogic();

		this.setTitle("KOPIATU APOSTUA");
		this.setBounds(100, 100, 590, 455);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		btnKopiatu.setEnabled(false);

		lblApostuBatKopiatu.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblApostuBatKopiatu.setBounds(132, 32, 315, 42);
		this.getContentPane().add(lblApostuBatKopiatu);
		
		ArrayList<User> users = businessLogic.erabiltzaileakLortu();
	
		for(User u : users) {
			if(!user.getUserName().equals(u.getUserName()) && !user.getPassword().equals(u.getPassword())) 
				comboBoxUsersModel.addElement(u.getUserName());
		}
		comboBox_Users = new JComboBox(comboBoxUsersModel);
		comboBox_Users.setBounds(115, 148, 350, 26);
		this.getContentPane().add(comboBox_Users);
		
		lblErabiltzailea.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblErabiltzailea.setBounds(115, 103, 91, 20);
		this.getContentPane().add(lblErabiltzailea);
		
		lblApostatutakoaAldatuNahi.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblApostatutakoaAldatuNahi.setBounds(115, 230, 236, 20);
		this.getContentPane().add(lblApostatutakoaAldatuNahi);
		
		textField_Apostua = new JTextField();
		textField_Apostua.setBounds(359, 229, 106, 26);
		this.getContentPane().add(textField_Apostua);
		textField_Apostua.setColumns(10);
		textField_Apostua.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!textField_Apostua.getText().isEmpty()) btnKopiatu.setEnabled(true);
				else btnKopiatu.setEnabled(false);
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!textField_Apostua.getText().isEmpty()) btnKopiatu.setEnabled(true);
				else btnKopiatu.setEnabled(false);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (!textField_Apostua.getText().isEmpty()) btnKopiatu.setEnabled(true);
				else btnKopiatu.setEnabled(false);
			}
		});

		btnKopiatu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					long millis=System.currentTimeMillis();
					java.util.Date currentDate = new java.util.Date(millis);
					Double apostua = Double.parseDouble(textField_Apostua.getText());
					String kopiatutakoa = (String)comboBox_Users.getSelectedItem();
					User kopiaUser = (User)user;
					String kopiatzenDuena = kopiaUser.getUserName();
					if(kopiaUser.getDirua() >= apostua) {
						kopiaUser.kenduDirua(apostua);
						kopiaUser.gehituMugimendua(apostua + " \u20AC INBERTITUTA " + kopiatutakoa + "-ren apostuak kopiatzeko " + currentDate.toString() + " EGUNEAN.");
						businessLogic.erabiltzaileaKopiatu(kopiatutakoa, kopiatzenDuena, apostua);
						businessLogic.eguneratuDiruaEtaMugimenduak(kopiaUser);
						System.out.println(kopiatutakoa + " KOPIATUTA.");
					} else JOptionPane.showMessageDialog(errorPanel, "Ez duzu apostatzeko diru hori!", "Error", JOptionPane.ERROR_MESSAGE);
					textField_Apostua.setText(null);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(errorPanel, "Sartu zenbaki bat mesedez.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnKopiatu.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnKopiatu.setBounds(315, 306, 150, 42);
		this.getContentPane().add(btnKopiatu);
		
		btnAtzera.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnAtzera.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MainUserGUI();
				a.setVisible(true);
				setVisible(false);
			}
		});
		btnAtzera.setBounds(115, 306, 150, 42);
		this.getContentPane().add(btnAtzera);
	}

	public static boolean zenbakiaDa(String balorea){
		try{
			Double.parseDouble(balorea);
			return true;
		}catch(NumberFormatException nfe){
			return false;
		}
	}
}
