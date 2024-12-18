package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Apostua;
import domain.Event;
import domain.Question;
import domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;


public class PartidoakKudeatuGUI extends JFrame {
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
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();

	final JPanel errorPanel = new JPanel();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;

	private static BLFacade businessLogic;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private JTextField textFieldEmaitza;
	private JTextField textFieldKuota;
	private final JButton btnEzabatu = new JButton("EZABATU");
	private final JButton btnGorde = new JButton("GORDE");
	private JTextField textFieldErantzuna;

	public PartidoakKudeatuGUI()
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
		this.businessLogic = LoginPantailaGUI.getBusinessLogic();
		this.setSize(new Dimension(726, 510));
		this.setTitle("Partidoak kudeatu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 221, 406, 14);
		jLabelEvents.setBounds(338, 19, 259, 16);

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
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
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

					Date eventDate = jCalendar1.getDate();
					long millis=System.currentTimeMillis();
					java.util.Date currentDate = new java.util.Date(millis);

					if(currentDate.compareTo(eventDate) > 0) {
						textFieldKuota.setEnabled(false);
						textFieldErantzuna.setEnabled(false);
						textFieldEmaitza.setEnabled(true);
					} else {
						textFieldKuota.setEnabled(true);
						textFieldErantzuna.setEnabled(true);
						textFieldEmaitza.setEnabled(false);
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(338, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 246, 406, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		
		textFieldEmaitza = new JTextField();
		textFieldEmaitza.setBounds(598, 337, 86, 25);
		getContentPane().add(textFieldEmaitza);
		textFieldEmaitza.setColumns(10);
		
		textFieldKuota = new JTextField();
		textFieldKuota.setBounds(598, 248, 86, 25);
		getContentPane().add(textFieldKuota);
		textFieldKuota.setColumns(10);
		
		JLabel lblEmaitza = new JLabel("EMAITZA");
		lblEmaitza.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEmaitza.setBounds(466, 342, 82, 20);
		getContentPane().add(lblEmaitza);
		
		JLabel lblKuota = new JLabel("KUOTA");
		lblKuota.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblKuota.setBounds(466, 248, 68, 20);
		getContentPane().add(lblKuota);
		
		JButton btnAtzera = new JButton("ATZERA");
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainLangileGUI a = new MainLangileGUI();
				a.setVisible(true);
				setVisible(false);
			}
		});
		btnAtzera.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAtzera.setBounds(40, 397, 131, 39);
		getContentPane().add(btnAtzera);
		btnEzabatu.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEzabatu.setBounds(305, 397, 131, 39);
		getContentPane().add(btnEzabatu);
		
		btnEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date eventDate = jCalendar1.getDate();
				long millis=System.currentTimeMillis();
				java.util.Date currentDate = new java.util.Date(millis);

				if(currentDate.compareTo(eventDate) > 0) {
					//PASA DA
					int i=tableEvents.getSelectedRow();
					domain.Event event = (domain.Event)tableModelEvents.getValueAt(i,2); // obtain event object
					//Ezabatu
					boolean emaitzakDaude = businessLogic.emaitzakDaude(event);
					if (emaitzakDaude) {
						businessLogic.partidoaEzabatu(event);
					} else {
						JOptionPane.showMessageDialog(errorPanel, "Emaitzak falta dira partido hortan", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					//EZ DA IRITSI
					
					int i=tableEvents.getSelectedRow();
					domain.Event event = (domain.Event)tableModelEvents.getValueAt(i,2); // obtain event object
					System.out.println(event.getQuestions().toString());
					
					businessLogic.ordainduAnulatuak(event);

					businessLogic.partidoaEzabatu(event);
				}
			}
		});

		btnGorde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date eventDate = jCalendar1.getDate();
				long millis=System.currentTimeMillis();
				java.util.Date currentDate = new java.util.Date(millis);
				if(currentDate.compareTo(eventDate) > 0) { //Emaitza ipini
					int i=tableEvents.getSelectedRow();
					Event event = (Event)tableModelEvents.getValueAt(i,2); // obtain event object

					int j=tableQueries.getSelectedRow();
					String question = tableModelQueries.getValueAt(j, 1).toString(); // obtain question String

					if (!businessLogic.emaitzaIpini(event, question, textFieldEmaitza.getText()))
						JOptionPane.showMessageDialog(errorPanel, "Galdera horrek jadanik emaitza bat du.", "Error", JOptionPane.ERROR_MESSAGE);
					else {
						businessLogic.ordainduApostua(event,question);
					}
				} else { //Pronostikoa ipini
					int i=tableEvents.getSelectedRow();
					Event event = (Event)tableModelEvents.getValueAt(i,2); // obtain event object

					int j=tableQueries.getSelectedRow();
					String questionString = tableModelQueries.getValueAt(j, tableQueries.getSelectedColumn()).toString(); // obtain question String
					
					Question question = event.getConcreteQuestion(questionString);
					
					String erantzuna = textFieldErantzuna.getText();
					Double kuota = Double.parseDouble(textFieldKuota.getText());
					if (!businessLogic.gehituAnswer(erantzuna, kuota,question,event)) {
						JOptionPane.showMessageDialog(errorPanel, "Emaitza hori existitzen da!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				textFieldEmaitza.setText(null);
				textFieldKuota.setText(null);
				textFieldErantzuna.setText(null);
			}
		});
		btnGorde.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnGorde.setBounds(553, 397, 131, 39);
		getContentPane().add(btnGorde);
		
		textFieldErantzuna = new JTextField();
		textFieldErantzuna.setColumns(10);
		textFieldErantzuna.setBounds(598, 290, 86, 25);
		getContentPane().add(textFieldErantzuna);
		
		JLabel lblPronostikoa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PartidoakKudeatuGUI.lblPronostikoa.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPronostikoa.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPronostikoa.setBounds(466, 295, 122, 20);
		getContentPane().add(lblPronostikoa);

	}
}
