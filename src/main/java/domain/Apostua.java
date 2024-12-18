package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Apostua {


    @Id
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    private Integer betNumber;
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
    private ArrayList<Question> questions;
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
    private ArrayList<String> emaitzak;
    private Double apostatutakoa;
    private Double kuota;
    private boolean kopia;
    private String noriKopia;
    @XmlIDREF
    private User user;


    public Apostua() {
    }

    public Apostua(Integer betNumber, User user, Double kuota, Double apostatutakoa) {
        this.betNumber = betNumber;
        this.questions = new ArrayList<Question>();
        this.emaitzak = new ArrayList<String>();
        this.kopia = false;
        this.kuota = kuota;
        this.apostatutakoa = apostatutakoa;
        this.user = user;
    }

    public Apostua(Integer betNumber, Question galdera, String emaitza, Double apostatutakoa, Double kuota, User user) {
        this.betNumber = betNumber;
        this.questions = new ArrayList<Question>();
        this.questions.add(galdera);
        this.emaitzak = new ArrayList<String>();
        this.emaitzak.add(emaitza);
        this.apostatutakoa = apostatutakoa;
        this.kuota = kuota;
        this.kopia = false;
        this.user = user;
    }
    
    public Apostua(Integer betNumber, ArrayList<Question> galderak, ArrayList<String> emaitzak, Double apostatutakoa, Double kuota, User user) {
    	 this.betNumber = betNumber;
         this.questions = galderak;
         this.emaitzak = emaitzak;
         this.apostatutakoa = apostatutakoa;
         this.kuota = kuota;
         this.kopia = false;
         this.user = user;
    }

    public void gehitu(Question question, String emaitza) {
        this.questions.add(question);
        this.emaitzak.add(emaitza);
    }

    public void setKuota(Double kuota) {
        this.kuota = kuota;
    }

    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public ArrayList<String> getEmaitzak() {
        return this.emaitzak;
    }

    public Double getApostatutakoa() {
        return this.apostatutakoa;
    }

    public Double getKuota() {
        return this.kuota;
    }

    public String toString() {
        return this.betNumber + ";" + this.questions.toString() + ";" + this.emaitzak.toString() + ";" + this.apostatutakoa + ";" + this.kuota + ";" + this.user.getUserName();
    }


    public Integer getBetNumber() {
        return this.betNumber;
    }

    public void setKopia() {
        this.kopia = true;
    }

    public void setNori(String userName) {
        this.noriKopia = userName;
    }

    public boolean getKopia() {
        return this.kopia;
    }

    public String getNoriKopia() {
        return this.noriKopia;
    }

    public boolean anitzaDa() {
        return this.questions.size() > 1;
    }
}
