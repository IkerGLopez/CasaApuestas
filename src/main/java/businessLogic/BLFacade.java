package businessLogic;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;





//import domain.Booking;
import domain.*;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

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
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	@WebMethod public void erregistratu(String userName, String password);
	@WebMethod public boolean existsErabiltzailea (String userName);
	@WebMethod public boolean erabiltzaileaEgiaztatu (String username, String password);
	@WebMethod public int erabiltzaileMota(String userName, String password);
	@WebMethod public void partidoaSortu(String description, Date eventDate);
	@WebMethod public void partidoaEzabatu(Event event);
	@WebMethod public boolean existsPartidoa(String description);
	@WebMethod public Pertsona getUser(String userName);
	@WebMethod public void eguneratuDiruaEtaMugimenduak(User user);
	@WebMethod public boolean emaitzaIpini(Event event, String question, String result);
	@WebMethod public Question galderaLortu(String question);
	@WebMethod public boolean gehituAnswer(String erantzuna, Double kuota,Question question, Event event);
	@WebMethod public Integer apostuKop();
	@WebMethod public void eguneratuApostuak(User user, Apostua apostua, Question question, ArrayList<Question> questions);
	@WebMethod public void gehituMugimendua(User user, String zer);
	@WebMethod public void ordainduApostua(Event event, String question);
	@WebMethod public void ordainduAnulatuak(Event event);
	@WebMethod public boolean emaitzakDaude(Event event);
	@WebMethod public ArrayList<User> erabiltzaileakLortu();
	@WebMethod public void erabiltzaileaKopiatu(String user1, String user2, Double apostua);
	@WebMethod public void eguneratuKopiatzekoDirua(User user1);
	@WebMethod public void apostuaEzabatu(Apostua a);
	@WebMethod public void eguneratuQuestion(Question q);
	@WebMethod public void eguneratuApostuakUserrai(User u, Apostua a);
}
