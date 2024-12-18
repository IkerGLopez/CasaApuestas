package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.HashMap;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User extends Pertsona{

	private Double dirua;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	private ArrayList<Apostua> apostuak;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.REMOVE)
	private HashMap<User, Double> kopiaDutenUser;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	private ArrayList<String> mugimenduak;
	
	public User(){super();}
	
	public User(String user, String pasword) {
		super(user,pasword);
		this.dirua = 0.0;
		this.mugimenduak = new ArrayList<String>();
		this.apostuak = new ArrayList<Apostua>();
		this.kopiaDutenUser = new HashMap<User, Double>();
	}

	public Double getDirua() {
		return this.dirua;
	}

	public ArrayList<String> getMugimenduak() {
		return this.mugimenduak;
	}

	public void setMugimenduak(ArrayList<String> mugimenduak) {
		this.mugimenduak = mugimenduak;
	}

	public void setDirua(Double kantitatea) {
		this.dirua = kantitatea;
	}

	public void kenduDirua(Double kantitatea) {
		this.dirua -= kantitatea;
	}

	public void diruaSartu(Double kantitatea) {
		this.dirua += kantitatea;
	}

	public void gehituMugimendua(String mugimendua) { 
		this.mugimenduak.add(mugimendua);
	}
	
	public void gehituApostua(Apostua apostua) {
		this.apostuak.add(apostua);
	}
	
	public ArrayList<Apostua> getApostuak() {
		return this.apostuak;
	}

	public void setApostuak(ArrayList<Apostua> apostuak) {this.apostuak = apostuak;}

	public void gehituKopiaErabiltzailea(User user, Double dirua) { this.kopiaDutenUser.put(user, dirua);}

	public HashMap<User, Double> getKopiaDutenUser() { return this.kopiaDutenUser;}

	public String toString() { return this.getUserName() + ";" + this.getDirua();}
	
	public void aldatuKopiaLista (User user, Double zenbat) {
		this.kopiaDutenUser.put(user, zenbat);
	}
	
	public void setKopiaDutenUser(HashMap<User,Double> lista) {
		this.kopiaDutenUser = lista;
	}
	
	public void kenduApostua(Apostua a) {
		int i = this.apostuak.size();
		for (int t=0; t<this.apostuak.size();t++) {
			if (apostuak.get(t).getBetNumber() == a.getBetNumber()) {
				i=t;
			}
		}
		if (i!=this.apostuak.size()) {
			this.apostuak.remove(i);
		}
	}

}
