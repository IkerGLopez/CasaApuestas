package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Apostua;
import domain.Pertsona;
import domain.Question;
import domain.User;
import domain.AnitzakTemp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class ApustuEginGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPaneAnswers = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableAnswers = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelAnswers;
	
	private DefaultListModel<String> tableModelAnitzak = new DefaultListModel<String>();
	private JList listAnitzak;
    private JScrollPane paneAntizak;

    private AnitzakTemp anitzakTemp = new AnitzakTemp();

	private domain.Event event;

	private DefaultComboBoxModel comboBoxModel;
	
	private static Pertsona user;
	
	private static BLFacade businessLogic;


	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private String[] columnNamesAnswers = new String[]{"Kuota", "Erantzuna"};

	private JTextField textField_ApostuKant;
	private final JButton btnApostatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ApustuEginGUI.btnApostatu.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JButton btnKopiatu = new JButton("APOSTUA KOPIATU");
	private final JLabel jLabelAnswers = new JLabel("Answers");

	final JPanel errorPanel = new JPanel();

	
	public ApustuEginGUI()
	{
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	private void jbInit() throws Exception
	{
		this.user = LoginPantailaGUI.getUser();
		this.businessLogic = LoginPantailaGUI.getBusinessLogic();
		this.setSize(new Dimension(692, 901));
		this.setTitle("Bet");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		btnApostatu.setEnabled(false);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(138, 221, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = LoginPantailaGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));



					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar estÃƒÆ’Ã‚Â¡ 30 de enero y se avanza al mes siguiente, devolverÃƒÆ’Ã‚Â­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃƒÆ’Ã‚Â³digo se dejarÃƒÆ’Ã‚Â¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = LoginPantailaGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);



					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=LoginPantailaGUI.getBusinessLogic();

						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(138, 246, 406, 116));
		scrollPaneAnswers.setBounds(new Rectangle(138, 410, 406, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelAnswers.setRowCount(0);

				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					tableModelQueries.addRow(row);
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2);
				event = ev;

				int j=tableQueries.getSelectedRow();
				String question=(String)tableModelQueries.getValueAt(j,tableQueries.getSelectedColumn());

				Question qu = ev.getConcreteQuestion(question);
				HashMap<String,Double> answers = qu.getAnswers();

				tableModelAnswers.setDataVector(null, columnNamesAnswers);

				if (answers.isEmpty())
					jLabelAnswers.setText("There are no answers yes for this question.");
				else
					jLabelAnswers.setText("Answers for the question \"" + qu.getQuestion() + "\"");

				for (Map.Entry<String, Double> entry : answers.entrySet()) {
					Vector<Object> row = new Vector<Object>();
					row.add(entry.getValue());
					row.add(entry.getKey());
					tableModelAnswers.addRow(row);
				}

				tableAnswers.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableAnswers.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);


		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneAnswers.setViewportView(tableAnswers);
		tableModelAnswers = new DefaultTableModel(null, columnNamesAnswers);

		tableAnswers.setModel(tableModelAnswers);
		tableAnswers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Date eventDate = jCalendar1.getDate();
				long millis=System.currentTimeMillis();
				java.util.Date currentDate = new java.util.Date(millis);
				/*if(currentDate.compareTo(eventDate) > 0) {
					btnApostatu.setEnabled(false);
				} else {*/
				btnApostatu.setEnabled(true);
				//}
			}
		});
		tableAnswers.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableAnswers.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPaneAnswers, null);

		textField_ApostuKant = new JTextField();
		textField_ApostuKant.setBounds(324, 742, 158, 25);
		getContentPane().add(textField_ApostuKant);
		textField_ApostuKant.setColumns(10);

		JLabel lblApostua = new JLabel("ZURE APOSTUA:");
		lblApostua.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblApostua.setBounds(174, 742, 140, 20);
		getContentPane().add(lblApostua);

		JButton btnAtzera = new JButton("ATZERA");
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainUserGUI a = new MainUserGUI();
				a.setVisible(true);
				setVisible(false);
			}
		});
		btnAtzera.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAtzera.setBounds(23, 794, 131, 39);
		getContentPane().add(btnAtzera);

		btnApostatu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User user1 = (User) user;
				long millis = System.currentTimeMillis();
				java.util.Date currentDate = new java.util.Date(millis);
				Double apostatutakoa = Double.parseDouble(textField_ApostuKant.getText());
				if(anitzakTemp.getQuestions().size()==0) {
					if (textField_ApostuKant.getText().isEmpty() || !zenbakiaDa(textField_ApostuKant.getText())) {
						JOptionPane.showMessageDialog(errorPanel, "Zerbait gaizki egin duzu.", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						if (user1.getDirua() >= apostatutakoa) {

							String questionString = (String) tableModelQueries.getValueAt(tableQueries.getSelectedRow(), tableQueries.getSelectedColumn());
							Question question = event.getConcreteQuestion(questionString);
							String emaitza = (String) tableModelAnswers.getValueAt(tableAnswers.getSelectedRow(), 1);
							Double kuota = (Double) tableModelAnswers.getValueAt(tableAnswers.getSelectedRow(), 0);
							Integer betNumber = businessLogic.apostuKop() + 1;
							Apostua apostua = new Apostua(betNumber, question, emaitza, apostatutakoa, kuota, user1);
							user1.gehituApostua(apostua);
							question.gehituUser(user1);

							businessLogic.eguneratuApostuak(user1, apostua, question, null);
							user1.setDirua(user1.getDirua() - apostatutakoa);

							user1.gehituMugimendua(apostatutakoa + " \u20AC APOSTATUTA " + currentDate.toString() + " EGUNEAN.");
							businessLogic.eguneratuDiruaEtaMugimenduak(user1);

							//behean dago metodo hau
							kopiatutakoUserreiApostatu(user1, apostatutakoa, question, emaitza, kuota, currentDate);

							textField_ApostuKant.setText("");
							JOptionPane.showMessageDialog(errorPanel, "Zure apostua ondo egin da!   " + "( " + emaitza + "  " + kuota + " kuotarekin )", "Onarpena", JOptionPane.PLAIN_MESSAGE);
							MainUserGUI a = new MainUserGUI();
							a.setVisible(true);
							setVisible(false);
						} else {
							JOptionPane.showMessageDialog(errorPanel, "Ez daukazu nahiko dirurik apostu hori egiteko.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						user = user1;
					}
				} else if (anitzakTemp.getQuestions().size()==1) {
					JOptionPane.showMessageDialog(errorPanel, "Apostu anitzak apostu bat baino gehioz osatuta daude. Apostu gehio egin mesedez.", "Error", JOptionPane.ERROR_MESSAGE);
				} else { //apostu bat baino gehio gehitu badira apostu anitzetan
					if(user1.getDirua()>=apostatutakoa) {
						Integer betNumber = businessLogic.apostuKop() + 1;
						Double kuota = anitzakTemp.getKuota();
						Apostua apostuAnitza = new Apostua(betNumber, user1, kuota, apostatutakoa);
						for (int i = 0; i < anitzakTemp.getEmaitzak().size(); i++) {
							Question question = anitzakTemp.getQuestions().get(i);
							String emaitza = anitzakTemp.getEmaitzak().get(i);
							apostuAnitza.gehitu(question, emaitza);
						}
						businessLogic.eguneratuApostuak(user1, apostuAnitza, null, anitzakTemp.getQuestions());
						user1.gehituApostua(apostuAnitza);
						user1.setDirua(user1.getDirua() - apostatutakoa);
						user1.gehituMugimendua(apostatutakoa + " \u20AC APOSTATUTA KONBINADA BATEAN " + currentDate.toString() + " EGUNEAN.");
						businessLogic.eguneratuDiruaEtaMugimenduak(user1);
						JOptionPane.showMessageDialog(errorPanel, "Zure apostua ondo egin da! ( Apostu anitza " + kuota + " kuotarekin )", "Onarpena", JOptionPane.PLAIN_MESSAGE);
						
						kopiatutakoUserreiApostatuAnitza(user1, apostatutakoa, anitzakTemp.getQuestions(), anitzakTemp.getEmaitzak(), kuota, currentDate);
						textField_ApostuKant.setText("");
						tableModelAnitzak.removeAllElements();
						MainUserGUI a = new MainUserGUI();
						a.setVisible(true);
						setVisible(false);
					} else JOptionPane.showMessageDialog(errorPanel, "Ez daukazu nahiko dirurik apostu hori egiteko.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	
		btnApostatu.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnApostatu.setBounds(507, 794, 131, 39);
		getContentPane().add(btnApostatu);
		jLabelAnswers.setBounds(138, 385, 406, 14);

		getContentPane().add(jLabelAnswers);

		listAnitzak = new JList<String>(tableModelAnitzak);
		listAnitzak.setBounds(138, 584, 398, 122);
		getContentPane().add(listAnitzak);
		listAnitzak.setLayoutOrientation(JList.VERTICAL);

		paneAntizak = new JScrollPane(listAnitzak);
		paneAntizak.setBounds(137, 580, 405, 120);
		getContentPane().add(paneAntizak);
		
		
		
		JTextPane textPaneKuota = new JTextPane();
		StyledDocument doc = textPaneKuota.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        textPaneKuota.setBackground(UIManager.getColor("Button.background"));
        textPaneKuota.setFont(new Font("Tahoma", Font.BOLD, 13));
        String momentukoKuotaS = String.valueOf(anitzakTemp.getKuota());
		textPaneKuota.setText(momentukoKuotaS);
		textPaneKuota.setBounds(582, 629, 48, 22);
		getContentPane().add(textPaneKuota);
		
		
		
		JButton btnAnitza = new JButton("ANITZA");
		
		btnAnitza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				User user1 = (User)user;
				long millis=System.currentTimeMillis();
				java.util.Date currentDate = new java.util.Date(millis);
				String questionString = (String) tableModelQueries.getValueAt(tableQueries.getSelectedRow(), tableQueries.getSelectedColumn());
				Question question = event.getConcreteQuestion(questionString);
				String emaitza = (String)tableModelAnswers.getValueAt(tableAnswers.getSelectedRow(), 1);
				Double kuota = (Double)tableModelAnswers.getValueAt(tableAnswers.getSelectedRow(), 0);
				anitzakTemp.gehitu(question, emaitza, kuota);
				 String momentukoKuotaAnitza = String.valueOf(anitzakTemp.getKuota());
				textPaneKuota.setText(momentukoKuotaAnitza);
				tableModelAnitzak.addElement(event.getDescription()+" : "+question.getQuestion()+" : "+emaitza+" : x"+kuota);
				listAnitzak.setModel(tableModelAnitzak);
				System.out.println(anitzakTemp.toString());
				textField_ApostuKant.setText(null);
			}
		});
		btnAnitza.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAnitza.setBounds(265, 794, 131, 39);
		getContentPane().add(btnAnitza);
		
		JLabel jLabelAnitzak = new JLabel("Apostu anitza");
		jLabelAnitzak.setBounds(138, 553, 406, 14);
		getContentPane().add(jLabelAnitzak);
		
		JLabel lblKuota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ApustuEginGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblKuota.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblKuota.setBounds(582, 612, 48, 16);
		getContentPane().add(lblKuota);
		

	}

	public static boolean zenbakiaDa(String balorea){     
		try{
			Double.parseDouble(balorea);
			return true;
		}catch(NumberFormatException nfe){
			return false; 
		}
	}

	public void kopiatutakoUserreiApostatu(User user, Double apostatutakoa, Question question, String emaitza, Double kuota, Date currentDate) {
		//KOPIATU DUTENEI APOSTUA EGIN!
		HashMap<User, Double> kopiatzenDutenak = user.getKopiaDutenUser();
		if (kopiatzenDutenak != null) {
			for (Map.Entry<User, Double> entry : kopiatzenDutenak.entrySet()) {
				User userKopia = entry.getKey();
				Double zenbatGelditu = entry.getValue();
				if (zenbatGelditu == 0.0) {
					System.out.println(userKopia.getUserName() + "-ri ez zaio dirurik gelditzen kopia egiteko!");
				} else {
					if (zenbatGelditu >= apostatutakoa) {    //Diru gehio badauka (soberan)
						Integer betNumber2 = businessLogic.apostuKop() + 1;
						Apostua apostua2 = new Apostua(betNumber2, question, emaitza, apostatutakoa, kuota, userKopia);
						apostua2.setKopia();
						apostua2.setNori(user.getUserName());
						userKopia.gehituApostua(apostua2);
						question.gehituUser(userKopia);
						userKopia.gehituMugimendua(apostatutakoa + " \u20AC APOSTATUTA " + user.getUserName() + " kopiatuz " + currentDate.toString() + " EGUNEAN.");
						businessLogic.eguneratuDiruaEtaMugimenduak(userKopia);
						businessLogic.eguneratuApostuak(userKopia, apostua2, question, null);

						// HASHMAP-A ALDATZEKO, DIRUA KANTITATEA AKTUALIZATZEKO
						HashMap<User, Double> listaKopiak = user.getKopiaDutenUser();
						listaKopiak.replace(userKopia, zenbatGelditu - apostatutakoa);
						user.setKopiaDutenUser(listaKopiak);
						businessLogic.eguneratuKopiatzekoDirua(user);
					} else {     //Ez badauka hainbeste diru
						Integer betNumber2 = businessLogic.apostuKop() + 1;
						Apostua apostua2 = new Apostua(betNumber2, question, emaitza, zenbatGelditu, kuota, userKopia);
						apostua2.setKopia();
						apostua2.setNori(user.getUserName());
						userKopia.gehituApostua(apostua2);
						question.gehituUser(userKopia);
						userKopia.gehituMugimendua(zenbatGelditu + " \u20AC APOSTATUTA " + user.getUserName() + " kopiatuz " + currentDate.toString() + " EGUNEAN.");
						businessLogic.eguneratuDiruaEtaMugimenduak(userKopia);
						businessLogic.eguneratuApostuak(userKopia, apostua2, question, null);

						// HASHMAP-A ALDATZEKO, DIRUA KANTITATEA AKTUALIZATZEKO
						HashMap<User, Double> listaKopiak = user.getKopiaDutenUser();
						listaKopiak.replace(userKopia, 0.0);
						user.setKopiaDutenUser(listaKopiak);
						businessLogic.eguneratuKopiatzekoDirua(user);
					}
				}
			}
		}
	}

	public void kopiatutakoUserreiApostatuAnitza(User user, Double apostatutakoa, ArrayList<Question> questions, ArrayList<String> emaitzak, Double kuota, Date currentDate) {
		//KOPIATU DUTENEI APOSTUA EGIN!
		HashMap<User, Double> kopiatzenDutenak = user.getKopiaDutenUser();
		if (kopiatzenDutenak != null) {
			for (Map.Entry<User, Double> entry : kopiatzenDutenak.entrySet()) {
				User userKopia = entry.getKey();
				Double zenbatGelditu = entry.getValue();
				if (zenbatGelditu == 0.0) {
					System.out.println(userKopia.getUserName() + "-ri ez zaio dirurik gelditzen kopia egiteko!");
				} else {
					if (zenbatGelditu >= apostatutakoa) {    //Diru gehio badauka (soberan)
						Integer betNumber2 = businessLogic.apostuKop() + 1;
						Apostua apostua2 = new Apostua(betNumber2, questions, emaitzak, apostatutakoa, kuota, userKopia);
						apostua2.setKopia();
						apostua2.setNori(user.getUserName());
						userKopia.gehituApostua(apostua2);
						//question.gehituUser(userKopia);
						/*for (Question q: questions) {
							q.gehituUser(userKopia);
						}*/
						userKopia.gehituMugimendua(apostatutakoa + " \u20AC APOSTATUTA KONBINADA BATEAN " + user.getUserName() + " kopiatuz " + currentDate.toString() + " EGUNEAN.");
						businessLogic.eguneratuDiruaEtaMugimenduak(userKopia);
						businessLogic.eguneratuApostuak(userKopia, apostua2, null, questions);

						// HASHMAP-A ALDATZEKO, DIRUA KANTITATEA AKTUALIZATZEKO
						HashMap<User, Double> listaKopiak = user.getKopiaDutenUser();
						listaKopiak.replace(userKopia, zenbatGelditu - apostatutakoa);
						user.setKopiaDutenUser(listaKopiak);
						businessLogic.eguneratuKopiatzekoDirua(user);
					} else {     //Ez badauka hainbeste diru
						Integer betNumber2 = businessLogic.apostuKop() + 1;
						Apostua apostua2 = new Apostua(betNumber2, questions, emaitzak, zenbatGelditu, kuota, userKopia);
						apostua2.setKopia();
						apostua2.setNori(user.getUserName());
						userKopia.gehituApostua(apostua2);
						//question.gehituUser(userKopia);
						/*for (Question q: questions) {
							q.gehituUser(userKopia);
						}*/
						userKopia.gehituMugimendua(zenbatGelditu + " \u20AC APOSTATUTA KONBINADA BATEAN " + user.getUserName() + " kopiatuz " + currentDate.toString() + " EGUNEAN.");
						businessLogic.eguneratuDiruaEtaMugimenduak(userKopia);
						businessLogic.eguneratuApostuak(userKopia, apostua2, null, questions);

						// HASHMAP-A ALDATZEKO, DIRUA KANTITATEA AKTUALIZATZEKO
						HashMap<User, Double> listaKopiak = user.getKopiaDutenUser();
						listaKopiak.replace(userKopia, 0.0);
						user.setKopiaDutenUser(listaKopiak);
						businessLogic.eguneratuKopiatzekoDirua(user);
					}
				}
			}
		}
	}
}
