package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;
	private String question; 
	private float betMinimum;
	private String result;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<User> apostatuDuenUser;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private HashMap<String,Double> answers;
	@XmlIDREF
	private Event event;

	public Question(){
		super();
	}
	
	public Question(Integer queryNumber, String query, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
		this.apostatuDuenUser = new ArrayList<User>();
		this.answers = new HashMap<String,Double>();
	}
	
	public Question(String query, float betMinimum,  Event event) {
		super();
		this.question = query;
		this.betMinimum=betMinimum;
		this.answers=new HashMap<String,Double>();
		this.apostatuDuenUser = new ArrayList<User>();
		//this.event = event;
	}

	/**
	 * Get the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}


	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}


	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */	
	public void setQuestion(String question) {
		this.question = question;
	}



	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */
	
	public float getBetMinimum() {
		return betMinimum;
	}


	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param  betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @return the the query result
	 */
	public String getResult() {
		return result;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @param result of the query to be setted
	 */
	
	public void setResult(String result) {
		this.result = result;
	}



	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}



	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	
	
	public boolean gehituAnswer(String erantzuna, Double kuota) {
		if (this.answers.containsKey(erantzuna)) {
			return false;
		}
		this.answers.put(erantzuna, kuota);
		return true;
	}
	
	
	public Double getKuota(String erantzuna) {
		return this.answers.get(erantzuna);
	}

	public HashMap<String, Double> getAnswers() {
		return this.answers;
	}
	



	public String toString(){
		return questionNumber+";"+question+";"+Float.toString(betMinimum);
	}

	public boolean erantzunaBadago(String erantzuna) {
		System.out.println(this.answers.get(erantzuna));
		if (this.answers.containsKey(erantzuna)) {
			return true;
		}
		return false;
	}

	public void gehituUser (User user) {
		this.apostatuDuenUser.add(user);
	}

	public ArrayList<User> getApostatuDuenUser() {
		return this.apostatuDuenUser;
	}

	public boolean resultDauka(){ return this.result!=null;}
	
	public void kenduUser(User u) {
		this.apostatuDuenUser.remove(u);
	}
	
	public void setApostatuDuenUser(ArrayList<User> lista) {
		this.apostatuDuenUser = lista;
	}
	
}