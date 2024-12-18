package dataAccess;

import java.text.DecimalFormat;
import java.util.ArrayList;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

	public DataAccess(boolean initializeMode)  {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess()  {	
		this(false);
	}


	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){

		db.getTransaction().begin();
		try {


			Calendar today = Calendar.getInstance();

			int month=today.get(Calendar.MONTH);
			month+=1;
			int year=today.get(Calendar.YEAR);
			if (month==12) { month=0; year+=1;}  

			Event ev1=new Event(1, "Atletico-Athletic", UtilDate.newDate(year,month,17));
			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
			Event ev3=new Event(3, "Getafe-Celta", UtilDate.newDate(year,month,17));
			Event ev4=new Event(4, "Alav�s-Deportivo", UtilDate.newDate(year,month,17));
			Event ev5=new Event(5, "Español-Villareal", UtilDate.newDate(year,month,17));
			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
			Event ev8=new Event(8, "Girona-Legan�s", UtilDate.newDate(year,month,17));
			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

			Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month-1,1));
			Event ev22=new Event(22, "Real Sociedad-Athletic", UtilDate.newDate(year,month-1,1));
			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
			Event ev13=new Event(13, "Getafe-Celta", UtilDate.newDate(year,month,1));
			Event ev14=new Event(14, "Alav�s-Deportivo", UtilDate.newDate(year,month,1));
			Event ev15=new Event(15, "Espa�ol-Villareal", UtilDate.newDate(year,month,1));
			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));


			Event ev17=new Event(17, "M�laga-Valencia", UtilDate.newDate(year,month+1,28));
			Event ev18=new Event(18, "Girona-Legan�s", UtilDate.newDate(year,month+1,28));
			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month+1,28));
			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month+1,28));

			Event ev21=new Event(21, "Betis-Real Union", UtilDate.newDate(year,month,25));


			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
			Question q7;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Qui�n ganar� el partido?",1);
				q2=ev1.addQuestion("¿Qui�n meter� el primer gol?",2);
				q3=ev11.addQuestion("¿Qui�n ganar� el partido?",1);
				q7=ev22.addQuestion("¿Qui�n ganar� el partido?",1);
				q4=ev11.addQuestion("¿Cu�ntos goles se marcar�n?",2);
				q7=ev22.addQuestion("¿Cu�ntos goles se marcar�n?",2);
				q5=ev17.addQuestion("¿Qui�n ganar� el partido?",1);
				q6=ev17.addQuestion("¿Habr� goles en la primera parte?",2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",1);
				q1.gehituAnswer("Atletico", 1.2);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
				q7 = ev22.addQuestion("Who will win the match?",1);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);
				q7 = ev22.addQuestion("Zeinek irabaziko du partidua?",1);

			}

			q3.gehituAnswer("Atletico", 1.2);
			q3.gehituAnswer("Athletic", 2.3);
			q1.gehituAnswer("Athletic", 2.4);
			q7.gehituAnswer("Real Sociedad", 1.2);
			q7.gehituAnswer("Athletic", 3.8);
			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);
			db.persist(q7);


			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);
			db.persist(ev21);
			db.persist(ev22);

			db.persist(new Langile("admin", "admin"));
			
			db.persist(new User("user1", "user1"));
			db.persist(new User("user2", "user2"));
			db.persist(new User("user3", "user3"));
			db.persist(new User("user4", "user4"));

			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		//db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev:events){
			System.out.println(ev.toString());		 
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);


		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d:dates){
			System.out.println(d.toString());		 
			res.add(d);
		}
		return res;
	}


	public void open(boolean initializeMode){

		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			db = emf.createEntityManager();
		}

	}
	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}
	
	
	public void erregistratu(String userName, String password) {
		User user = new User(userName,password);
		db.getTransaction().begin();
		db.persist(user);
		db.getTransaction().commit();
		System.out.println(userName + " registered");
	}

	public boolean existsErabiltzailea (String userName) {
		Pertsona kontua = db.find(Pertsona.class, userName);
		return (kontua != null);
	}

	public boolean erabiltzaileaEgiaztatu (String userName, String password) {
		Pertsona kontua = db.find(Pertsona.class, userName);
		if (existsErabiltzailea(userName) && (kontua.getPassword().equals(password))) {
			return true;
		} else return false;
	}

	public int erabiltzaileMota(String userName, String password) {
		Pertsona kontua = db.find(Pertsona.class, userName);
		if (kontua instanceof Langile) return 1;
		else if (kontua instanceof User) return 2;
		else return 0;
	}

	public void partidoaSortu(String description, Date eventDate) {
		Event event = new Event(description, eventDate);
		db.getTransaction().begin();
		db.persist(event);
		db.getTransaction().commit();
	}

	public boolean existsPartidoa(String description) {
		Event event = null;
		db.getTransaction().begin();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev",Event.class);
		List<Event> events = query.getResultList();
		for (Event e : events) {
			if (e.getDescription().equalsIgnoreCase(description)) event = e;
		}
		db.getTransaction().commit();
		if (event == null) return false;
		else return true;
	}

	public Pertsona getUser(String userName) {
		db.getTransaction().begin();
		Pertsona user = db.find(Pertsona.class, userName);
		db.getTransaction().commit();
		return user;
	}

	public void eguneratuDiruaEtaMugimenduak(User user) {
		db.getTransaction().begin();
		User user1 = db.find(User.class, user.getUserName());
		user1.setDirua(user.getDirua());
		user1.setMugimenduak(user.getMugimenduak());
		db.persist(user1);
		db.getTransaction().commit();
	}

	public boolean emaitzaIpini(Event event, String question, String result) {
		db.getTransaction().begin();
		Event event1 = db.find(Event.class, event.getEventNumber());
		boolean erantzuna = event1.emaitzaIpini(question, result);
		db.persist(event1);
		db.getTransaction().commit();
		return erantzuna;
	}

	public Question galderaLortu(String question) {
		db.getTransaction().begin();
		Question question1 = new Question();
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q",Question.class);
		List<Question> questions = query.getResultList();
		for(Question q : questions) {
			if(q.getQuestion().equalsIgnoreCase(question))
				question1 = q;
		}
		db.getTransaction().commit();
		return question1;
	}

	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}
	
	public boolean gehituAnswer(String erantzuna, Double kuota, Question question, Event event) {
		db.getTransaction().begin();
		boolean bai = false;
		Event eventOna = db.find(Event.class, event.getEventNumber());
		for (Question q: eventOna.getQuestions()) {
			if (q.getQuestion().equalsIgnoreCase(question.getQuestion())) {
				bai = q.gehituAnswer(erantzuna, kuota);
			}
		}
		db.persist(eventOna);
		db.getTransaction().commit();
		return bai;
	}
	
	public void partidoaEzabatu(Event event) {
		db.getTransaction().begin();
		Event event1 = db.find(Event.class, event.getEventNumber());
		db.remove(event1);
		System.out.println(event.toString()+" DELETED");
		db.getTransaction().commit();
	}

	public Integer apostuKop() {
		db.getTransaction().begin();
		Query query = db.createQuery("SELECT ap FROM Apostua ap");
		db.getTransaction().commit();
		return (Integer)query.getResultList().size();
	}

	public void eguneratuApostuak(User user, Apostua apostua, Question question, ArrayList<Question> questions) {
		db.getTransaction().begin();
		if(questions == null) { //questions arraylista null bada, apostu normal bat eguneratzen gaude
			User user1 = db.find(User.class, user.getUserName());
			user1.gehituApostua(apostua);
			Question question1 = db.find(Question.class, question.getQuestionNumber());
			question1.gehituUser(user1);
			db.persist(question1);
			db.persist(user1);
		} else { //bestela, apostu anitz bat eguneratzen gaude
			User user1 = db.find(User.class, user.getUserName());
			user1.gehituApostua(apostua);
			for(Question q: questions) {
				Question question1 = db.find(Question.class, q.getQuestionNumber());
				question1.gehituUser(user1);
				db.persist(question1);
			}
			db.persist(user1);
		}
		db.getTransaction().commit();
	}
	

	
	public void gehituMugimendua(User user, String zer) {
		db.getTransaction().begin();
		User user1 = db.find(User.class, user.getUserName());
		user1.gehituMugimendua(zer);
		db.persist(user1);
		db.getTransaction().commit();
	}
	
	
	public void ordainduApostua(Event event, String question) {
		db.getTransaction().begin();
		Question galdera = event.getConcreteQuestion(question);
		Question galdera1 = db.find(Question.class, galdera.getQuestionNumber());
		ArrayList<User> lista = galdera1.getApostatuDuenUser();
		for (User u: lista) {
			ArrayList<Apostua> lista2 = u.getApostuak();
			for (Apostua a: lista2) {
				if(!a.anitzaDa()) {
					if (a.getQuestions().get(a.getQuestions().size()-1).getQuestionNumber().equals(galdera1.getQuestionNumber())) {
						if (a.getEmaitzak().get(0).equals(galdera1.getResult())) {
							if (a.getKopia()) {   //APOSTUA KOPIATUA BADA
								User user1 = db.find(User.class, u.getUserName());
								double irabazikoDenaBerez = (a.getApostatutakoa() * a.getKuota()) - a.getApostatutakoa();
								double kopiatzenDuenak = a.getApostatutakoa() + irabazikoDenaBerez * 0.85;
								double kopDu = Math.round(kopiatzenDuenak * 100.0) / 100.0;
								user1.diruaSartu(kopDu);
								long millis = System.currentTimeMillis();
								java.util.Date currentDate = new java.util.Date(millis);
								user1.gehituMugimendua(kopDu + " \u20AC IRABAZITA " + a.getNoriKopia() + " KOPIATUZ " + event.getDescription() + " APOSTUA IRABAZTEAGATIK " + currentDate.toString() + " EGUNEAN.");
								db.persist(user1);
								User user2 = db.find(User.class, a.getNoriKopia());
								double benefak = irabazikoDenaBerez * 0.15;
								double benB = Math.round(benefak * 100.0) / 100.0;
								user2.diruaSartu(benB);
								user2.gehituMugimendua(benB + "\u20AC IRABAZITA " + user1.getUserName() + "-ek ZURI KOPIATUZ IRABAZI DUELAKO " + event.getDescription() + " APOSTUA IRABAZTEAGATIK " + currentDate.toString() + " EGUNEAN.");
								db.persist(user2);
							} else {   //APOSTUA KOPIA    EZ    BADA!
								User user1 = db.find(User.class, u.getUserName());
								double irabazi = a.getApostatutakoa() * a.getKuota();
								double irabaziB = Math.round(irabazi * 100.0) / 100.0;
								user1.diruaSartu(irabaziB);
								long millis = System.currentTimeMillis();
								java.util.Date currentDate = new java.util.Date(millis);
								user1.gehituMugimendua(irabaziB + " \u20AC IRABAZITA " + event.getDescription() + " APOSTUA IRABAZTEAGATIK " + currentDate.toString() + " EGUNEAN.");
								db.persist(user1);
							}
						} else {
							User user1 = db.find(User.class, u.getUserName());
							long millis = System.currentTimeMillis();
							java.util.Date currentDate = new java.util.Date(millis);
							user1.gehituMugimendua(event.getDescription() + " APOSTUA GALDU DUZU " + currentDate.toString() + " EGUNEAN.");
							db.persist(user1);
						}
					}
				} else {
					boolean denok = true;
					for(Question q: a.getQuestions()) {
						if(!q.resultDauka()) denok=false;
					}
					if(denok) {
						boolean ondoDaude = true;
						for(int i = 0; i<a.getQuestions().size(); i++){
							Question q = a.getQuestions().get(i);
							if(!q.getResult().equalsIgnoreCase(a.getEmaitzak().get(i))) ondoDaude = false;
						}
						if(ondoDaude) {
							if (a.getKopia()) {   //APOSTUA KOPIATUA BADA
								User user1 = db.find(User.class, u.getUserName());
								double irabazikoDenaBerez = (a.getApostatutakoa() * a.getKuota()) - a.getApostatutakoa();
								double kopiatzenDuenak = a.getApostatutakoa() + irabazikoDenaBerez * 0.85;
								double kopDu = Math.round(kopiatzenDuenak * 100.0) / 100.0;
								user1.diruaSartu(kopDu);
								long millis = System.currentTimeMillis();
								java.util.Date currentDate = new java.util.Date(millis);
								user1.gehituMugimendua(kopDu + " \u20AC IRABAZITA " + a.getNoriKopia() + " KOPIATUZ " + event.getDescription() + " APOSTUA IRABAZTEAGATIK " + currentDate.toString() + " EGUNEAN.");
								db.persist(user1);
								User user2 = db.find(User.class, a.getNoriKopia());
								double benefak = irabazikoDenaBerez * 0.15;
								double benB = Math.round(benefak * 100.0) / 100.0;
								user2.diruaSartu(benB);
								user2.gehituMugimendua(benB + "\u20AC IRABAZITA " + user1.getUserName() + "-ek ZURI KOPIATUZ IRABAZI DUELAKO " + event.getDescription() + " APOSTUA IRABAZTEAGATIK " + currentDate.toString() + " EGUNEAN.");
								db.persist(user2);
							} else {   //APOSTUA KOPIA    EZ    BADA!
								User user1 = db.find(User.class, u.getUserName());
								double irabazi = a.getApostatutakoa() * a.getKuota();
								double irabaziB = Math.round(irabazi * 100.0) / 100.0;
								user1.diruaSartu(irabaziB);
								long millis = System.currentTimeMillis();
								java.util.Date currentDate = new java.util.Date(millis);
								user1.gehituMugimendua(irabaziB + " \u20AC IRABAZITA " + event.getDescription() + " APOSTUA IRABAZTEAGATIK " + currentDate.toString() + " EGUNEAN.");
								db.persist(user1);
							}
						} else {
							User user1 = db.find(User.class, u.getUserName());
							long millis = System.currentTimeMillis();
							java.util.Date currentDate = new java.util.Date(millis);
							user1.gehituMugimendua(event.getDescription() + " APOSTUA GALDU DUZU " + currentDate.toString() + " EGUNEAN.");
							db.persist(user1);
						}
					}
				}
			}
		}
		db.getTransaction().commit();
	}
	
	
	public void ordainduAnulatuak(Event event) {
		db.getTransaction().begin();
		Event event1 = db.find(Event.class, event.getEventNumber());
		for (Question q: event1.getQuestions()) {
			if (q.getApostatuDuenUser()!=null) {
				ArrayList<User> lista = q.getApostatuDuenUser();
				for (User u: lista) {
					ArrayList<Apostua> lista2 = u.getApostuak();
					for (Apostua a: lista2) {
						if (a.getQuestions().get(0).getQuestionNumber().equals(q.getQuestionNumber())) {
							User user1 = db.find(User.class, u.getUserName());
							user1.diruaSartu(a.getApostatutakoa());
							long millis = System.currentTimeMillis();
							java.util.Date currentDate = new java.util.Date(millis);
							user1.gehituMugimendua(a.getApostatutakoa() + " \u20AC ITZULITA " + event.getDescription()  +" PARTIDOA ANULATZEAGATIK " + currentDate.toString() + " EGUNEAN.");
							db.persist(user1);
						}
					}
				}
			}
		}
		db.getTransaction().commit();
	}
	
	public boolean emaitzakDaude(Event event) {
		db.getTransaction().begin();
		Event event1 = db.find(Event.class, event.getEventNumber());
		for (Question q: event1.getQuestions()) {
			if (q.getResult() == null) {
				return false;
			}
		}
		db.getTransaction().commit();
		return true;
	}
	
	public ArrayList<User> erabiltzaileakLortu() {
		db.getTransaction().begin();
		Query query = db.createQuery("SELECT u FROM User u");
		ArrayList<User> users = (ArrayList<User>) query.getResultList();
		System.out.println(users.toString());
		db.getTransaction().commit();
		return users;
	}

	public void erabiltzaileaKopiatu(String user1, String user2, Double apostua) {
		db.getTransaction().begin();
		User kopiatutakoa = db.find(User.class, user1);
		User kopiatzenDuenak = db.find(User.class, user2);
		kopiatutakoa.gehituKopiaErabiltzailea(kopiatzenDuenak, apostua);
		System.out.println(kopiatzenDuenak.getUserName());
		db.getTransaction().commit();
	}
	
	
	public void eguneratuKopiatzekoDirua(User u) {
		db.getTransaction().begin();
		User user1 = db.find(User.class, u.getUserName());
		db.persist(user1);
		db.getTransaction().commit();
	}
	
	public void apostuaEzabatu(Apostua a) {
		db.getTransaction().begin();
		Apostua apostua = db.find(Apostua.class, a.getBetNumber());
		db.remove(apostua);
		System.out.println(apostua.toString()+" DELETED");
		db.getTransaction().commit();
	}
	
	public void eguneratuQuestion(Question q) {
		db.getTransaction().begin();
		Question question = db.find(Question.class, q.getQuestionNumber());
		question.setApostatuDuenUser(q.getApostatuDuenUser());
		db.persist(question);
		db.getTransaction().commit();
	}
	
	public void eguneratuApostuaUserrai(User u, Apostua a) {
		db.getTransaction().begin();
		User user1 = db.find(User.class, u.getUserName());
		user1.kenduApostua(a);
		db.persist(user1);
		db.getTransaction().commit();
	}

}
