package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import businessLogic.BLFacade;
import domain.Apostua;
import domain.Pertsona;
import domain.Question;
import domain.User;

import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class ApostuaEzabatuGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private static BLFacade businessLogic;
	private static Pertsona user;
	
	private DefaultComboBoxModel comboBoxApustuakModel = new DefaultComboBoxModel();
	private JComboBox comboBoxApustuak;

	final JPanel errorPanel = new JPanel();
	
	
	public ApostuaEzabatuGUI() {
		this.user = LoginPantailaGUI.getUser();
		this.businessLogic = LoginPantailaGUI.getBusinessLogic();

		this.setTitle("Apostua ezabatu");
		this.setBounds(100, 100, 493, 378);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		this.setVisible(true);


		JComboBox comboBoxApustuak = new JComboBox(comboBoxApustuakModel);
		comboBoxApustuak.setBounds(67, 76, 350, 22);
		this.getContentPane().add(comboBoxApustuak);
		
		User user1 = (User) this.user;
		ArrayList<Apostua> apostuak = user1.getApostuak();
		int i = 0;
		for (Apostua a : apostuak) {
			String emaitzakElkartuak = i+" - ";
			for (String e: a.getEmaitzak()) {
				emaitzakElkartuak += e + "; ";
			}
			emaitzakElkartuak += "kuota: " + a.getKuota() + "; Apostutakoa: " + a.getApostatutakoa();
			
			boolean emaitzaDaukate = true;
			for (Question q: a.getQuestions()) {
				if (q.getResult() == null) {
					emaitzaDaukate = false;
				}
			}
			if (!emaitzaDaukate) {
				comboBoxApustuakModel.addElement(emaitzakElkartuak);
			}
			i++;
		}

		JLabel lblAukeratuApostua = new JLabel("ZURE APOSTUAK");
		lblAukeratuApostua.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAukeratuApostua.setBounds(67, 49, 177, 16);
		this.getContentPane().add(lblAukeratuApostua);

		JButton btnEzabatu = new JButton("EZABATU");
		btnEzabatu.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEzabatu.setBounds(305, 245, 112, 38);
		this.getContentPane().add(btnEzabatu);
		btnEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String apostuString = (String)comboBoxApustuak.getSelectedItem();
				String[] apostuArray = apostuString.split("-");
				Integer selected = Integer.parseInt(apostuArray[0].trim());
				Apostua apostua = apostuak.get(selected);
				
				long millis=System.currentTimeMillis();
				java.util.Date currentDate = new java.util.Date(millis);
				user1.diruaSartu(apostua.getApostatutakoa() * 0.90);
				user1.gehituMugimendua(apostua.getApostatutakoa() * 0.90 + " \u20AC SARTUTA APUSTUA ANULATZEAGATIK  "+ currentDate.toString() + " EGUNEAN.");
				businessLogic.eguneratuDiruaEtaMugimenduak(user1);
				for (Question q: apostua.getQuestions()) {
					q.kenduUser(user1);
					businessLogic.eguneratuQuestion(q);
				}
				//user1.kenduApostua(apostua);
				user1.kenduApostua(apostua);
				businessLogic.eguneratuApostuakUserrai(user1,apostua);
				user = user1;
				MainUserGUI a = new MainUserGUI();
				a.setVisible(true);
				setVisible(false);
			}
		});
		

		JButton btnAtzera = new JButton("ATZERA");
		btnAtzera.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAtzera.setBounds(67, 245, 118, 38);
		this.getContentPane().add(btnAtzera);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainUserGUI a = new MainUserGUI();
				a.setVisible(true);
				setVisible(false);
			}
		});
	}
}
