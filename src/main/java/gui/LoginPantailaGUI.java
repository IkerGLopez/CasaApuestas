package gui;

import javax.swing.*;
import java.awt.Font;

import java.awt.Color;

import businessLogic.BLFacade;
import domain.Pertsona;
import domain.User;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.awt.event.ActionEvent;

public class LoginPantailaGUI extends JFrame {
	private JTextField erabiltzaileaField;
	private JPasswordField passwordField;
	private static Pertsona user;

	private static BLFacade appFacadeInterface;
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	public static Pertsona getUser() {return user;}
	

	public LoginPantailaGUI() {
		getContentPane().setLayout(null);
		final JPanel errorPanel = new JPanel();
		this.setSize(525, 360);
		this.setTitle("LOGIN");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



		JLabel erabiltzaileaText = new JLabel("ERABILTZAILEA");
		erabiltzaileaText.setFont(new Font("Tahoma", Font.BOLD, 15));
		erabiltzaileaText.setBounds(49, 99, 166, 29);
		getContentPane().add(erabiltzaileaText);

		JLabel passwordText = new JLabel("PASAHITZA");
		passwordText.setFont(new Font("Tahoma", Font.BOLD, 15));
		passwordText.setBounds(49, 141, 100, 49);
		getContentPane().add(passwordText);

		erabiltzaileaField = new JTextField();
		erabiltzaileaField.setBounds(280, 103, 174, 22);
		getContentPane().add(erabiltzaileaField);
		erabiltzaileaField.setColumns(10);

		JButton loginBotoia = new JButton("LOGIN EGIN");
		loginBotoia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = erabiltzaileaField.getText();
				String password = new String(passwordField.getPassword());

				System.out.println(appFacadeInterface.erabiltzaileMota(userName, password));
				if (appFacadeInterface.erabiltzaileaEgiaztatu(userName, password)) {
					if (appFacadeInterface.erabiltzaileMota(userName, password) == 1) { // Langile kontu batekin logeatzen bagara:
						user = appFacadeInterface.getUser(userName);
						JFrame MainLangileGUI = new MainLangileGUI();
						MainLangileGUI.setVisible(true);
						setVisible(false);
					} else if (appFacadeInterface.erabiltzaileMota(userName, password) == 2) { // User kontu batekin logeatzen bagara:
						user = appFacadeInterface.getUser(userName);
						JFrame MainUserGUI = new MainUserGUI();
						MainUserGUI.setVisible(true);
						setVisible(false);
					}
				} else JOptionPane.showMessageDialog(errorPanel, "Berriz saiatu mesedez.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		loginBotoia.setForeground(Color.BLACK);
		loginBotoia.setBackground(new Color(102, 153, 204));
		loginBotoia.setFont(new Font("Tahoma", Font.BOLD, 15));
		loginBotoia.setBounds(306, 220, 148, 42);
		getContentPane().add(loginBotoia);

		JButton erregistratuBotoia = new JButton("ERREGISTRATU");
		erregistratuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame errGUI = new ErregistratuGUI();
				errGUI.setVisible(true);
				setVisible(false);
			}
		});
		erregistratuBotoia.setBackground(UIManager.getColor("CheckBox.darkShadow"));
		erregistratuBotoia.setForeground(new Color(105, 105, 105));
		erregistratuBotoia.setFont(new Font("Tahoma", Font.BOLD, 15));
		erregistratuBotoia.setBounds(49, 221, 166, 42);
		getContentPane().add(erregistratuBotoia);

		passwordField = new JPasswordField();
		passwordField.setBounds(280, 155, 174, 22);
		getContentPane().add(passwordField);

		
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblNewLabel.setBounds(200, 13, 106, 45);
		getContentPane().add(lblNewLabel);
		
		
		
		JButton sartuLoginGabeBotoia = new JButton("Sartu logeatu gabe");
		sartuLoginGabeBotoia.setOpaque(false);
		sartuLoginGabeBotoia.setBackground(new Color(0,0,0,0) );
		sartuLoginGabeBotoia.setBounds(306, 275, 148, 25);
		sartuLoginGabeBotoia.setBorder(null);
		
		sartuLoginGabeBotoia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				sartuLoginGabeBotoia.setForeground(Color.blue);
				
				Font font = sartuLoginGabeBotoia.getFont();
		        Map attributes = font.getAttributes();
		        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		        sartuLoginGabeBotoia.setFont(font.deriveFont(attributes));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sartuLoginGabeBotoia.setForeground(Color.black);
				
				Font font = sartuLoginGabeBotoia.getFont();
		        Map attributes = font.getAttributes();
		        attributes.put(TextAttribute.UNDERLINE, -1);
		        sartuLoginGabeBotoia.setFont(font.deriveFont(attributes));
			}
		});
		
		sartuLoginGabeBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame MainGUI = new MainGUI();
				MainGUI.setVisible(true);
				setVisible(false);
			}
		});
		
		
		getContentPane().add(sartuLoginGabeBotoia);
	}
}
