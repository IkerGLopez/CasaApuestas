package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.*;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
		}
		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);		

		dbManager.close();
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);
		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	@WebMethod
	public void erregistratu(String userName, String password) {
    	dbManager.open(false);
    	dbManager.erregistratu(userName, password);
    	dbManager.close();
	}

	@WebMethod
	public boolean existsErabiltzailea (String userName) {
    	dbManager.open(false);
    	boolean exits = dbManager.existsErabiltzailea(userName);
    	dbManager.close();
    	return exits;
	}

	@WebMethod
	public boolean erabiltzaileaEgiaztatu(String userName, String password) {
		dbManager.open(false);
		boolean egiaztatuta = dbManager.erabiltzaileaEgiaztatu(userName, password);
		dbManager.close();
		return egiaztatuta;
	}

	@WebMethod
	public int erabiltzaileMota(String userName, String password) {
    	dbManager.open(false);
    	int mota = dbManager.erabiltzaileMota(userName, password);
    	dbManager.close();
    	return mota;
	}

	@WebMethod
	public void partidoaSortu(String description, Date eventDate) {
    	dbManager.open(false);
    	dbManager.partidoaSortu(description, eventDate);
    	dbManager.close();
	}

	@WebMethod
	public boolean existsPartidoa(String description) {
    	dbManager.open(false);
    	boolean emaitza = dbManager.existsPartidoa(description);
    	dbManager.close();
    	return emaitza;
	}

	@WebMethod
	public Pertsona getUser(String userName) {
    	dbManager.open(false);
    	Pertsona user = dbManager.getUser(userName);
    	dbManager.close();
    	return user;
	}

	@WebMethod
	public void eguneratuDiruaEtaMugimenduak(User user) {
    	dbManager.open(false);
    	dbManager.eguneratuDiruaEtaMugimenduak(user);
    	dbManager.close();
	}

	@WebMethod
	public boolean emaitzaIpini(Event event, String question, String result) {
		dbManager.open(false);
		boolean emaitza = dbManager.emaitzaIpini(event, question, result);
		dbManager.close();
		return emaitza;
	}

	@WebMethod
	public Question galderaLortu(String question) {
    	dbManager.open(false);
    	Question question1 = dbManager.galderaLortu(question);
    	dbManager.close();
    	return question1;
	}

	@WebMethod
	public boolean gehituAnswer(String erantzuna, Double kuota, Question question, Event event) {
		dbManager.open(false);
		boolean bai = dbManager.gehituAnswer(erantzuna,kuota,question, event);
		dbManager.close();
		return bai;
	}

	@WebMethod
	public void partidoaEzabatu(Event event) {
		dbManager.open(true);
		dbManager.partidoaEzabatu(event);
		dbManager.close();
	}

	@WebMethod public Integer apostuKop() {
    	dbManager.open(false);
    	Integer kop = dbManager.apostuKop();
    	dbManager.close();
    	return kop;
	}

	@WebMethod public void eguneratuApostuak(User user, Apostua apostua, Question question, ArrayList<Question> questions) {
    	dbManager.open(false);
    	dbManager.eguneratuApostuak(user, apostua, question, questions);
    	dbManager.close();
	}

	@WebMethod public void gehituMugimendua(User user, String zer) {
		dbManager.open(false);
		dbManager.gehituMugimendua(user,zer);
		dbManager.close();
	}

	@WebMethod public void ordainduApostua(Event event, String question) {
		dbManager.open(false);
		dbManager.ordainduApostua(event, question);
		dbManager.close();
	}
	
	@WebMethod public void ordainduAnulatuak( Event event) {
		dbManager.open(false);
		dbManager.ordainduAnulatuak(event);
		dbManager.close();
	}
	
	@WebMethod public boolean emaitzakDaude(Event event) {
		dbManager.open(false);
		boolean b = dbManager.emaitzakDaude(event);
		dbManager.close();
		return b;
	}
	
	@WebMethod 
	public ArrayList<User> erabiltzaileakLortu() {
		dbManager.open(false);
		ArrayList<User> users = dbManager.erabiltzaileakLortu();
		dbManager.close();
		return users;
	}

	@WebMethod
	public void erabiltzaileaKopiatu(String user1, String user2, Double apostua) {
    	dbManager.open(false);
    	dbManager.erabiltzaileaKopiatu(user1, user2, apostua);
    	dbManager.close();
	}
	
	@WebMethod public void eguneratuKopiatzekoDirua(User user1) {
		dbManager.open(false);
		dbManager.eguneratuKopiatzekoDirua(user1);
		dbManager.close();
	}
	
	@WebMethod public void apostuaEzabatu(Apostua a) {
		dbManager.open(false);
		dbManager.apostuaEzabatu(a);
		dbManager.close();
	}
	
	@WebMethod public void eguneratuQuestion(Question q) {
		dbManager.open(false);
		dbManager.eguneratuQuestion(q);
		dbManager.close();
	}
	
	@WebMethod public void eguneratuApostuakUserrai(User u, Apostua a) {
		dbManager.open(false);
		dbManager.eguneratuApostuaUserrai(u,a);
		dbManager.close();
	}
}