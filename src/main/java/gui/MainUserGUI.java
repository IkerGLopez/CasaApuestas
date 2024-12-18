package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Event;
import domain.Pertsona;
import businessLogic.BLFacade;
import domain.User;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainUserGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonAddFounds = null;
	private JButton jButtonQueryFounds = null;
	private JButton jButtonBet = null;
	private JButton jButtonCloseSession = null;
	private JButton jButtonCopyUser = null;
	private JButton jButtonRemoveBet = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static Pertsona user;
	
	/**
	 * This is the default constructor
	 */
	public MainUserGUI() {
		super();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(495, 500);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.user = LoginPantailaGUI.getUser();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(9, 1, 0, 0));
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton3());
			jContentPane.add(getBoton4());
			jContentPane.add(getBoton5());
			jContentPane.add(getBoton6());
			jContentPane.add(getBoton8());
			jContentPane.add(getBoton9());
			jContentPane.add(getBoton7());
			jContentPane.add(getPanel());
		}
		return jContentPane;
	}


	
	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setText("QueryQuestion");
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI();
					a.setVisible(true);
					setVisible(false);
				}
			});
		}
		return jButtonQueryQueries;
	}
	
	/**
	 * This method initializes boton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton4() {
		if (jButtonAddFounds == null) {
			jButtonAddFounds = new JButton();
			jButtonAddFounds.setText("Add Founds");
			jButtonAddFounds.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new DiruaSartuGUI();
					a.setVisible(true);
					setVisible(false);
				}
			});
		}
		return jButtonAddFounds;
	}

	/**
	 * This method initializes boton4
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBoton5() {
		if (jButtonQueryFounds == null) {
			jButtonQueryFounds = new JButton();
			jButtonQueryFounds.setText("Query Founds");
			jButtonQueryFounds.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new MugimenduakIkusiGUI();
					a.setVisible(true);
					setVisible(false);
				}
			});
		}
		return jButtonQueryFounds;
	}

	/**
	 * This method initializes boton5
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBoton6() {
		if (jButtonBet == null) {
			jButtonBet = new JButton();
			jButtonBet.setText("Bet");
			jButtonBet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new ApustuEginGUI();
					a.setVisible(true);
					setVisible(false);
				}
			});
		}
		return jButtonBet;
	}
	
	
	/**
	 * This method initializes boton6
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBoton7() {
		if (jButtonCloseSession == null) {
			jButtonCloseSession = new JButton();
			jButtonCloseSession.setText("Close Session");
			jButtonCloseSession.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new LoginPantailaGUI();
					a.setVisible(true);
					setVisible(false);
				}
			});
		}
		return jButtonCloseSession;
	}
	
	/**
	 * This method initializes boton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton8() {
		if (jButtonCopyUser == null) {
			jButtonCopyUser = new JButton();
			jButtonCopyUser.setText("Copy a user");
			jButtonCopyUser.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new KopiatuUserGUI();
					a.setVisible(true);
					setVisible(false);
				}
			});
		}
		return jButtonCopyUser;
	}

	/**
	 * This method initializes boton8
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBoton9() {
		if (jButtonRemoveBet == null) {
			jButtonRemoveBet = new JButton();
			jButtonRemoveBet.setText("Remove bets");
			jButtonRemoveBet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new ApostuaEzabatuGUI();
					a.setVisible(true);
					setVisible(false);
				}
			});
		}
		return jButtonRemoveBet;
	}

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}
	
	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	
} // @jve:decl-index=0:visual-constraint="0,0"

